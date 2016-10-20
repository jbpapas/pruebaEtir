package es.dipucadiz.etir.comun.action;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import es.dipucadiz.etir.comun.listener.SessionCounterListener;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.comun.vo.ActiveSessionVO;



final public class WebserviceSessionCounterAction extends AbstractGadirBaseAction {
	private static final long serialVersionUID = -5992980123456197414L;

	public final static String CORRECTO = "00"; 
	public final static String ERROR = "01"; 
	public final static String SESSION_INEXISTENTE = "02"; 
	public final static String OPERACION_INEXISTENTE = "99"; 

	public final static String OBTENER_USUARIOS = "01";
	public final static String INVALIDAR_SESION = "02";
	
	private String xmlRespuesta;
	String codigoOperacion;
	String codigoError;
	String sessionId;

	public String execute(){
		try {
			if (OBTENER_USUARIOS.equals(codigoOperacion)) {
				codigoError = obtenerUsuarios();
			} else if (INVALIDAR_SESION.equals(codigoOperacion)) {
				codigoError = invalidarSession();
			} else {
				codigoError = OPERACION_INEXISTENTE;
			}
		} catch (Exception e) {
			codigoError = ERROR;
			LOG.error(e.getMessage(), e);
		}
		
		if (!CORRECTO.equals(codigoError)) {
			escribirError(codigoError);
		} else if (Utilidades.isEmpty(xmlRespuesta)) {
			escribirCorrecto();
		}
		
		getServletResponse().setContentType("text/xml;charset=UTF-8");
		return "webservice";
	}

	private void escribirError(String codigoError) {
		xmlRespuesta="";
		xmlRespuesta+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		xmlRespuesta+="<error>";
		xmlRespuesta+=codigoError;
		xmlRespuesta+="</error>";
	}

	private void escribirCorrecto() {
		xmlRespuesta="";
		xmlRespuesta+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		xmlRespuesta+="<ok/>";
	}

	private String invalidarSession() {
		String result;
		if (SessionCounterListener.invalidateSessionId(sessionId)) {
			result = CORRECTO;
		} else {
			result = SESSION_INEXISTENTE;
		}
		return result;
	}

	private String obtenerUsuarios() {
		xmlRespuesta="";
		xmlRespuesta+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		xmlRespuesta+="<usuarios>";

		Map<String, ActiveSessionVO> activeSessions = SessionCounterListener.getActiveSessions();
		Iterator<Entry<String, ActiveSessionVO>> it = activeSessions.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, ActiveSessionVO> pairs = it.next();
			if (Utilidades.isNotEmpty(pairs.getValue().getUser())) {
				xmlRespuesta+="<usuario ";
				xmlRespuesta+="sessionId=\""            + pairs.getValue().getSessionId() + "\" ";
				xmlRespuesta+="coProceso=\""            + (pairs.getValue().getCoProceso()==null ? "" : pairs.getValue().getCoProceso()) + "\" ";
				xmlRespuesta+="ip=\""                   + pairs.getValue().getIp() + "\" ";
				xmlRespuesta+="host=\""                 + pairs.getValue().getHost() + "\" ";
				xmlRespuesta+="ipServidor=\""           + pairs.getValue().getIpServidor() + "\" ";
				xmlRespuesta+="nombre=\""               + pairs.getValue().getNombre() + "\" ";
				xmlRespuesta+="unidadAdministrativa=\"" + pairs.getValue().getUnidadAdministrativa() + "\" ";
				xmlRespuesta+="user=\""                 + pairs.getValue().getUser() + "\" ";
				xmlRespuesta+="date=\""                 + pairs.getValue().getDate().getTime() + "\" ";
				xmlRespuesta+="/>";
			}
		}
		xmlRespuesta+="</usuarios>";
		return CORRECTO;
	}

	
	
	public String getXmlRespuesta() {
		return xmlRespuesta;
	}

	public void setXmlRespuesta(String xmlRespuesta) {
		this.xmlRespuesta = xmlRespuesta;
	}

	public String getCodigoOperacion() {
		return codigoOperacion;
	}

	public void setCodigoOperacion(String codigoOperacion) {
		this.codigoOperacion = codigoOperacion;
	}

	public String getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static String getCorrecto() {
		return CORRECTO;
	}

	public static String getOperacionInexistente() {
		return OPERACION_INEXISTENTE;
	}

	public static String getObtenerUsuarios() {
		return OBTENER_USUARIOS;
	}

	public static String getInvalidarSesion() {
		return INVALIDAR_SESION;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public static String getError() {
		return ERROR;
	}

}
