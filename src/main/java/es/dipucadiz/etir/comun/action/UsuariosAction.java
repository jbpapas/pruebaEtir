package es.dipucadiz.etir.comun.action;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.listener.SessionCounterListener;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Pair;
import es.dipucadiz.etir.comun.utilidades.ProcesoUtil;
import es.dipucadiz.etir.comun.utilidades.SessionUtil;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.comun.vo.ActiveSessionVO;

final public class UsuariosAction extends AbstractGadirBaseAction {

	private static final long serialVersionUID = 576955877318420849L;
	//	private static final Log LOG = LogFactory.getLog(UsuariosAction.class);

	private static final String ACTIVE_SESSIONS = "ACTIVE_SESSIONS";

	private List<ActiveSessionVO> activeSessions;
	private String sessionIdSel;
	private String ipServidor;
	private String mensaje;
	private boolean isMostrarDetalleUsuario = false;

	public String execute() throws GadirServiceException {
		activeSessions = obtenerUsuariosConectados(getServletRequest().getLocalAddr());
		isMostrarDetalleUsuario = SessionUtil.permitidoMostrarDetalle(DatosSesion.getLogin());
		return INPUT;
	}

	public static List<ActiveSessionVO> obtenerUsuariosConectados(String localAddr) {
		List<ActiveSessionVO> activeSessions = obtenerUsuariosSesion();
		if (activeSessions == null) {
			activeSessions = new ArrayList<ActiveSessionVO>();
			Iterator<Entry<String, ActiveSessionVO>> it = SessionCounterListener.getActiveSessions().entrySet().iterator();
			while (it.hasNext()) {
				ActiveSessionVO activeSessionVO = it.next().getValue();
				if (Utilidades.isNotEmpty(activeSessionVO.getUser())) {
					activeSessions.add(activeSessionVO);
				}
			}
			activeSessions.addAll(llamadasWebservice(localAddr));

			Collections.sort(activeSessions);
			List<ActiveSessionVO> activeSessionsTmp = new ArrayList<ActiveSessionVO>();
			for (ActiveSessionVO activeSessionVO : activeSessions) {
				if (Utilidades.isNotEmpty(activeSessionVO.getUser())) {
					if (activeSessionVO.getDate() != null) {
						activeSessionVO.setHora(Utilidades.dateToYYYYMMDDHHMMSSformateado(activeSessionVO.getDate()));
					}
					if (Utilidades.isNotEmpty(activeSessionVO.getCoProceso())) {
						activeSessionVO.setPrograma(ProcesoUtil.getProcesoByCoProceso(activeSessionVO.getCoProceso()).getDescripcion());
					}
					activeSessionsTmp.add(activeSessionVO);
				}
			}
			activeSessions = activeSessionsTmp;

			guardarUsuariosSesion(activeSessions);
		}
		return activeSessions;
	}

	private static void guardarUsuariosSesion(List<ActiveSessionVO> activeSessions) {
		Pair<Long, List<ActiveSessionVO>> activeSessionsPair = new Pair<Long, List<ActiveSessionVO>>(Utilidades.getDateActual().getTime(), activeSessions);
		DatosSesion.setObjetoUnico(ACTIVE_SESSIONS, activeSessionsPair);
	}

	private static List<ActiveSessionVO> obtenerUsuariosSesion() {
		List<ActiveSessionVO> activeSessions = null;
		if (DatosSesion.existeObjetoUnico(ACTIVE_SESSIONS)) {
			@SuppressWarnings("unchecked")
			Pair<Long, List<ActiveSessionVO>> activeSessionsPair = (Pair<Long, List<ActiveSessionVO>>) DatosSesion.getObjetoUnico(ACTIVE_SESSIONS);
			if (Utilidades.getDateActual().getTime() - activeSessionsPair.getLeft() < 30000) {
				// Si no ha pasado un tiempo limite, devolvemos la lista guardada en la sesión
				activeSessions = activeSessionsPair.getRight();
			}
		}
		return activeSessions;
	}

	private static List<ActiveSessionVO> llamadasWebservice(String localAddr) {
		List<ActiveSessionVO> activeSessions = new ArrayList<ActiveSessionVO>();
		try {
			String ipServidores = GadirConfig.leerParametro("ip.servidores");
			if (Utilidades.isNotEmpty(ipServidores)) {
				List<String> ipServidoresList = Arrays.asList(ipServidores.split(","));
				for (String ipServidor : ipServidoresList) {
					if (!ipServidor.equals(localAddr)) {
						String text = llamarWebservice(ipServidor, "codigoOperacion=" + WebserviceSessionCounterAction.OBTENER_USUARIOS);
						if (text.toString().contains("<usuarios>")) {

							try {
								Document documentoXml = DocumentHelper.parseText(text.toString());
								@SuppressWarnings("unchecked")
								List<Element> elementos = documentoXml.selectNodes("//usuarios/usuario");
								for (Element el : elementos) {
									ActiveSessionVO activeSessionVO = new ActiveSessionVO(null);
									activeSessionVO.setUser(el.attributeValue("user"));
									if (Utilidades.isNotEmpty(activeSessionVO.getUser())) {
										activeSessionVO.setSessionId(el.attributeValue("sessionId"));
										activeSessionVO.setDate(new Date(Long.valueOf(el.attributeValue("date"))));
										activeSessionVO.setUnidadAdministrativa(el.attributeValue("unidadAdministrativa"));
										activeSessionVO.setNombre(el.attributeValue("nombre"));
										activeSessionVO.setIpServidor(el.attributeValue("ipServidor"));
										activeSessionVO.setIp(el.attributeValue("ip"));
										activeSessionVO.setHost(el.attributeValue("host"));
										activeSessionVO.setCoProceso(el.attributeValue("coProceso"));
										activeSessions.add(activeSessionVO);
									}
								}
							} catch (DocumentException e) {
								//								addActionError(Mensaje.getTexto(MensajeConstants.FICHERO_INCORRECTO));
								LOG.error("XML devuelto incorrecto: " + text.toString(), e);
							}

						}
					}
				}
			}
		} catch (Exception e) {
			LOG.warn("Problemas recuperando property ip.servidores", e);
		}
		return activeSessions;
	}

	private static String llamarWebservice(String ipServidor, String parametros) throws IOException {
		String urlS = "http://" + ipServidor + ":8080/etir/webserviceSessionCounter.action?" + parametros;
		HttpURLConnection connection = (HttpURLConnection) new URL(urlS).openConnection();
		connection.setRequestMethod("POST");

		String requestContent;
		requestContent = "\r\n";

		//		connection.setRequestProperty("Accept-Encoding", "gzip;q=0,deflate;q=0");
		connection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
		//		connection.setRequestProperty("Content-Length", requestContent.length() + "");
		//		connection.setRequestProperty("Host", ipServidor + ":8080");
		//		connection.setRequestProperty("Connection", "Keep-Alive");
		//		connection.setRequestProperty("User-Agent", "WebService eTir");

		connection.setDoOutput(true);
		connection.setConnectTimeout(10000); // El WebService debe conectar dentro de un tiempo limitado.
		connection.setReadTimeout(20000); // El WebService debe devolver datos dentro de un tiempo limitado.
		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
		wr.writeBytes(requestContent);
		wr.flush();
		wr.close();

		int responseCode = connection.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + urlS);
		//		System.out.println("Post parameters : " + requestContent);
		if (responseCode != 200) {
			System.out.println("Response Code debe ser 200, pero es: " + responseCode);
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuffer text = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			text.append(inputLine);
		}
		in.close();

		return text.toString();
	}

	public static boolean invalidarSesionWs(String ipServidor, String sessionIdSel) throws IOException {
		String resultado = llamarWebservice(ipServidor, "codigoOperacion=" + WebserviceSessionCounterAction.INVALIDAR_SESION + "&sessionId=" + sessionIdSel);
		return resultado.contains("<ok/>");
	}

	public String botonInvalidarSesion() throws GadirServiceException {
		if (getServletRequest().getSession().getId().equals(sessionIdSel)) {
			mensaje = "La sesión que está intentando invalidar es la suya";
		} else {
			if (getServletRequest().getLocalAddr().equals(ipServidor)) {
				// Sesión del mismo servidor
				if (SessionCounterListener.invalidateSessionId(sessionIdSel)) {
					mensaje = "La sesión ha sido invalidada";
				} else {
					mensaje = "La sesión no se ha podido invalidar";
				}
			} else {
				// Sesión de otro servidor
				try {
					if (invalidarSesionWs(ipServidor, sessionIdSel)) {
						mensaje = "La sesión ha sido invalidada en " + ipServidor;
					} else {
						mensaje = "La sesión no se ha podido invalidar en " + ipServidor;
					}
				} catch (IOException e) {
					LOG.error(e.getMessage(), e);
					addActionError("No puede invalidar la sesión seleccionada: " + e.getMessage());
				}
			}
		}
		limpiarUsuariosSesion();
		return execute();
	}

	/**
	 * Se borra el objeto en sesión para obligar que obtenga los usuarios conectados reales
	 */
	private void limpiarUsuariosSesion() {
		borraObjetoSesion(ACTIVE_SESSIONS);
	}

	public List<ActiveSessionVO> getActiveSessions() {
		return activeSessions;
	}

	public void setActiveSessions(List<ActiveSessionVO> activeSessions) {
		this.activeSessions = activeSessions;
	}

	public String getSessionIdSel() {
		return sessionIdSel;
	}

	public void setSessionIdSel(String sessionIdSel) {
		this.sessionIdSel = sessionIdSel;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getIpServidor() {
		return ipServidor;
	}

	public void setIpServidor(String ipServidor) {
		this.ipServidor = ipServidor;
	}

	public boolean isMostrarDetalleUsuario() {
		return isMostrarDetalleUsuario;
	}

	public void setMostrarDetalleUsuario(boolean isMostrarDetalleUsuario) {
		this.isMostrarDetalleUsuario = isMostrarDetalleUsuario;
	}

}
