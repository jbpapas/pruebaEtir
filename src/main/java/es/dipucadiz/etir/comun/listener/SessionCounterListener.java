package es.dipucadiz.etir.comun.listener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import es.dipucadiz.etir.comun.action.UsuariosAction;
import es.dipucadiz.etir.comun.bo.AcmUsuarioBO;
import es.dipucadiz.etir.comun.bo.ClienteBO;
import es.dipucadiz.etir.comun.bo.UnidadAdministrativaBO;
import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.dto.AcmUsuarioDTO;
import es.dipucadiz.etir.comun.dto.ClienteDTO;
import es.dipucadiz.etir.comun.dto.UnidadAdministrativaDTO;
import es.dipucadiz.etir.comun.utilidades.Fichero;
import es.dipucadiz.etir.comun.utilidades.Impresion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.comun.vo.ActiveSessionVO;

public class SessionCounterListener implements HttpSessionListener {

	private static int totalActiveSessions;
	private static Map<String, ActiveSessionVO> activeSessions = new HashMap<String, ActiveSessionVO>();
	private static String rutaSesiones = null;
	private static String ipServidor = null;
	private static final Log LOG = LogFactory.getLog(SessionCounterListener.class);

	public static void modifySessionUser(String key, String user, String ip, String host, HttpSession session) {
		try {
			if (!activeSessions.containsKey(key)) {
				ActiveSessionVO activeSessionVO = new ActiveSessionVO(session);
				activeSessionVO.setDate(Utilidades.getDateActual());
				activeSessions.put(key, activeSessionVO);
			}
			ActiveSessionVO activeSessionVO = activeSessions.get(key);
			activeSessionVO.setUser(user);
			activeSessionVO.setIp(ip);
			activeSessionVO.setHost(host);
			activeSessionVO.setDate(Utilidades.getDateActual());
			AcmUsuarioBO acmUsuarioBO = (AcmUsuarioBO) GadirConfig.getBean("acmUsuarioBO");
			AcmUsuarioDTO acmUsuarioDTO = acmUsuarioBO.findById(user);
			if (acmUsuarioDTO != null) {
				if (acmUsuarioDTO.getClienteDTO() != null) {
					ClienteBO clienteBO = (ClienteBO) GadirConfig.getBean("clienteBO");
					ClienteDTO clienteDTO = clienteBO.findById(acmUsuarioDTO.getClienteDTO().getCoCliente());
					activeSessionVO.setNombre(clienteDTO.getRazonSocial());
				}
				if (acmUsuarioDTO.getUnidadAdministrativaDTO() != null) {
					UnidadAdministrativaBO unidadAdministrativaBO = (UnidadAdministrativaBO) GadirConfig.getBean("unidadAdministrativaBO");
					UnidadAdministrativaDTO unidadAdministrativaDTO = unidadAdministrativaBO.findById(acmUsuarioDTO.getUnidadAdministrativaDTO().getCoUnidadAdministrativa());
					activeSessionVO.setUnidadAdministrativa(unidadAdministrativaDTO.getNombre());
				}
				activeSessionVO.setUsuarioInvalidable(!"******".equals(acmUsuarioDTO.getCodigoTerritorialGenerico()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Contar numero de sesiones con usuario
		int count = 0;
		Iterator<Entry<String, ActiveSessionVO>> it = activeSessions.entrySet().iterator();
		while (it.hasNext()) {
			if (Utilidades.isNotEmpty(it.next().getValue().getUser())) {
				count++;
			}
		}
		totalActiveSessions = count;
		escribirSesionesActivas();
	}
	
	private static String getFileName() {
		if (ipServidor == null) {
			Iterator<Entry<String, ActiveSessionVO>> it = activeSessions.entrySet().iterator();
			while (it.hasNext()) {
				ActiveSessionVO activeSessionVO = it.next().getValue();
				if (Utilidades.isNotEmpty(activeSessionVO.getUser())) {
					ipServidor = activeSessions.entrySet().iterator().next().getValue().getIpServidor();
					break;
				}
			}
		}
		return getFileName(ipServidor);
	}
	
	private static String getFileName(String ip) {
		String resultado = null;
		if (rutaSesiones == null) {
			rutaSesiones = GadirConfig.leerParametro("ruta.sesiones");
		}
		if (ip != null) {
			resultado = rutaSesiones + ip.replace('.', '_') + ".txt";
		}
		return resultado;
	}
	
	private static void escribirSesionesActivas() {
		if (!activeSessions.isEmpty()) {
			String fileName = getFileName();
			if (fileName != null) {
				final File file = new File(fileName);
				try {
					final BufferedWriter bWriterTratado = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), Impresion.CODIFICACION));
					Iterator<Entry<String, ActiveSessionVO>> it = activeSessions.entrySet().iterator();
					while (it.hasNext()) {
						ActiveSessionVO activeSessionVO = it.next().getValue();
						if (Utilidades.isNotEmpty(activeSessionVO.getUser())) {
							String line = activeSessionVO.getUser() + '\t' + 
									activeSessionVO.getSessionId() + '\t' + 
									activeSessionVO.getIp() + '\t' + 
									activeSessionVO.getHost() + '\t' + 
									activeSessionVO.getIpServidor() + '\t' +
									(activeSessionVO.isUsuarioInvalidable() ? '1' : '0');
							bWriterTratado.write(line + '\n');
						}
					}
					bWriterTratado.close();
	
				} catch (UnsupportedEncodingException e) {
					LOG.error("No puede escribir el fichero txt de las sesiones.", e);
				} catch (FileNotFoundException e) {
					LOG.error("No puede escribir el fichero txt de las sesiones. Comprueba si existe la carpeta.", null);
				} catch (IOException e) {
					LOG.error("No puede escribir el fichero txt de las sesiones.", e);
				}
			}
		}
	}

	public static void modifySessionCoProceso(String key, String coProceso) {
		try {
			ActiveSessionVO activeSessionVO = activeSessions.get(key);
			activeSessionVO.setCoProceso(coProceso);
			activeSessionVO.setDate(Utilidades.getDateActual());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void touchSession(String key) {
		if (activeSessions.containsKey(key)) {
			ActiveSessionVO activeSessionVO = activeSessions.get(key);
			activeSessionVO.setDate(Utilidades.getDateActual());
		}
	}
	
	public void sessionCreated(HttpSessionEvent se) {
		String key = se.getSession().getId();
		if (!activeSessions.containsKey(key)) {
			ActiveSessionVO activeSessionVO = new ActiveSessionVO(se.getSession());
			activeSessionVO.setDate(Utilidades.getDateActual());
			String ipServidor = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getLocalAddr();
			activeSessionVO.setIpServidor(ipServidor);
			activeSessionVO.setSessionId(key);
			activeSessions.put(key, activeSessionVO);
		}
	}

	public void sessionDestroyed(HttpSessionEvent se) {
		String key = se.getSession().getId();
		if (activeSessions.containsKey(key)) {
			activeSessions.remove(key);
			totalActiveSessions--;
			escribirSesionesActivas();
		}
	}

	public static String getTotalActiveSessionString(){
		String plural = (totalActiveSessions==1 ? "" : "s");
		return totalActiveSessions + " usuario" + plural + " conectado" + plural;
	}
	
	/**
	 * Controlar si el usuario existe entra las sesiones guardadas con otra ID.
	 * @param user
	 * @param activeSessionId
	 * @return
	 */
	public static boolean isUserInSession(String user, String activeSessionId) {
		boolean result = false;
		Iterator<Entry<String, ActiveSessionVO>> it = activeSessions.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, ActiveSessionVO> entry = it.next();
			ActiveSessionVO activeSessionVO = entry.getValue();
			String storedSessionId = entry.getKey();
			if (!storedSessionId.equals(activeSessionId) && activeSessionVO.isUsuarioInvalidable() && user.equals(activeSessionVO.getUser())) {
				result = true;
				break;
			}
		}
		
		if (!result) {
			String ipServidoresRemotos = GadirConfig.leerParametro("ip.servidores");
			if (Utilidades.isNotEmpty(ipServidoresRemotos)) {
				List<String> ipServidoresList = Arrays.asList(ipServidoresRemotos.split(","));
				for (String ipServidorRemoto : ipServidoresList) {
					if (!ipServidorRemoto.equals(ipServidor)) {
						String fileName = getFileName(ipServidorRemoto);
						try {
							if (Fichero.existeFichero(fileName)) {
								BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), Impresion.CODIFICACION));
								String line;
								while ((line = input.readLine()) != null) {
									String[] words = line.split("\t");
									String storedSessionId = words[1];
									String storedUser = words[0];
									boolean isUsuarioInvalidable = "1".equals(words[4]);
									if (!storedSessionId.equals(activeSessionId) && isUsuarioInvalidable && user.equals(storedUser)) {
										result = true;
										break;
									}
								}
							}
						} catch (UnsupportedEncodingException e) {
							LOG.error("No puede leer el fichero " + fileName + " de sesiones para comprobar si el usuario ya está logado.", e);
						} catch (FileNotFoundException e) {
							LOG.error("No puede leer el fichero " + fileName + " de sesiones para comprobar si el usuario ya está logado.", e);
						} catch (IOException e) {
							LOG.error("No puede leer el fichero " + fileName + " de sesiones para comprobar si el usuario ya está logado.", e);
						}
					}
				}
			}
		}
		
		return result;
	}

	/**
	 * Invalidar todas las sesiones asociadas al usuario, salvo la ID recibida.
	 * @param user
	 * @param activeSessionId
	 */
	public static void invalidateSessions(String user, String activeSessionId) {
		// Se invalidan las sesiones del nodo actual
		Iterator<Entry<String, ActiveSessionVO>> it = activeSessions.entrySet().iterator();
		List<HttpSession> invalidateSessions = new ArrayList<HttpSession>();
		while (it.hasNext()) {
			Entry<String, ActiveSessionVO> entry = it.next();
			ActiveSessionVO activeSessionVO = entry.getValue();
			String storedSessionId = entry.getKey();
			if (user.equals(activeSessionVO.getUser()) && !storedSessionId.equals(activeSessionId)) {
				invalidateSessions.add(activeSessionVO.getSession());
			}
		}
		for (HttpSession invalidateSession : invalidateSessions) {
			invalidateSession.invalidate();
		}
		
		// También hay que invalidar sesiones de otros nodos.
		String ipServidoresRemotos = GadirConfig.leerParametro("ip.servidores");
		if (Utilidades.isNotEmpty(ipServidoresRemotos)) {
			List<String> ipServidoresList = Arrays.asList(ipServidoresRemotos.split(","));
			for (String ipServidorRemoto : ipServidoresList) {
				if (!ipServidorRemoto.equals(ipServidor)) {
					String fileName = getFileName(ipServidorRemoto);
					try {
						if (Fichero.existeFichero(fileName)) {
							BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), Impresion.CODIFICACION));
							String line;
							while ((line = input.readLine()) != null) {
								String[] words = line.split("\t");
								String storedSessionId = words[1];
								String storedUser = words[0];
								boolean isUsuarioInvalidable = "1".equals(words[4]);
								if (!storedSessionId.equals(activeSessionId) && isUsuarioInvalidable && user.equals(storedUser)) {
									UsuariosAction.invalidarSesionWs(ipServidorRemoto, storedSessionId);
								}
							}
						}
					} catch (UnsupportedEncodingException e) {
						LOG.error("No puede leer el fichero " + fileName + " de sesiones para comprobar si el usuario ya está logado.", e);
					} catch (FileNotFoundException e) {
						LOG.error("No puede leer el fichero " + fileName + " de sesiones para comprobar si el usuario ya está logado.", e);
					} catch (IOException e) {
						LOG.error("No puede leer el fichero " + fileName + " de sesiones para comprobar si el usuario ya está logado.", e);
					}
				}
			}
		}
	}
	
	/**
	 * Invalidará la sesión indicada
	 * @param sessionIdToInvalidate
	 * @return Si se ha podido invalidar la sesión o no.
	 */
	public static boolean invalidateSessionId(String sessionIdToInvalidate) {
		boolean result = false;
		if (activeSessions.containsKey(sessionIdToInvalidate)) {
			ActiveSessionVO activeSessionVO = activeSessions.get(sessionIdToInvalidate);
			activeSessionVO.getSession().invalidate();
			result = true;
		}
		return result;
	}

	public static int getTotalActiveSession(){
		return totalActiveSessions;
	}
	
	public static Map<String, ActiveSessionVO> getActiveSessions() {
		return activeSessions;
	}

}
