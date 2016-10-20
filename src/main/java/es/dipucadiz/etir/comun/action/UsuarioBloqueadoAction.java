package es.dipucadiz.etir.comun.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.listener.SessionCounterListener;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;

final public class UsuarioBloqueadoAction extends AbstractGadirBaseAction {

	private static final long serialVersionUID = 4744825327359523705L;
	private static final Log LOG = LogFactory.getLog(UsuarioBloqueadoAction.class);
	
	private String j_username = DatosSesion.getLogin();
	
	public String execute() throws GadirServiceException {
		return INPUT;
	}
	
	public String botonProcederLogin() {
		SessionCounterListener.invalidateSessions(DatosSesion.getLogin(), getServletRequest().getRequestedSessionId());
		return SUCCESS;
	}
	
	public String botonLogout() {
		return VOLVER;
	}


	public String getJ_username() {
		return j_username;
	}

	public void setJ_username(String j_username) {
		this.j_username = j_username;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static Log getLog() {
		return LOG;
	}
	
//	private void saveLog() throws GadirServiceException {
//		if (Utilidades.isEmpty(login_error) && Utilidades.isEmpty(fin_sesion)) {
//			return;
//		}
//		
//		char accion;
//		if ("2".equals(login_error) || "1".equals(login_error)) {
//			accion = AcmUsuarioLogBOImpl.ACCION_CONTRASENA_INCORRECTA;
//		} else if ("5".equals(login_error)) {
//			accion = AcmUsuarioLogBOImpl.ACCION_CUENTA_BAJA;
//		} else if ("3".equals(login_error)) {
//			accion = AcmUsuarioLogBOImpl.ACCION_CONTRASENA_CADUCADA;
//		} else if ("4".equals(login_error)) {
//			accion = AcmUsuarioLogBOImpl.ACCION_CUENTA_CERRADA;
//		} else if (Utilidades.isNotEmpty(fin_sesion)) {
//			accion = AcmUsuarioLogBOImpl.ACCION_FIN_SESION;
//		} else {
//			throw new GadirServiceException("Acción desconocida. le" + login_error + ", " + fin_sesion);
//		}
//		String username = null;
//		try {
//			username = (String) servletRequest.getSession().getAttribute("ACEGI_SECURITY_LAST_USERNAME");
//			if (Utilidades.isNotEmpty(username)) {
//				AcmUsuarioLogDTO acmUsuarioLogDTO = new AcmUsuarioLogDTO();
//				acmUsuarioLogDTO.setAccion(String.valueOf(accion));
//				acmUsuarioLogDTO.setCoAcmUsuario(username);
//				acmUsuarioLogDTO.setCoUsuarioActualizacion(username);
//				acmUsuarioLogDTO.setUsuarioAjeno(username);
//				acmUsuarioLogDTO.setFhAccion(Utilidades.getDateActual());
//				acmUsuarioLogDTO.setFhActualizacion(Utilidades.getDateActual());
//				acmUsuarioLogDTO.setIp(servletRequest.getRemoteAddr());
//				acmUsuarioLogBO.save(acmUsuarioLogDTO);
//			}
//		}catch(Exception e){
//			e = new GadirServiceException("Error grabando log de usuario para " + username, e);
//			e.printStackTrace();
//		}
//	}

	

}
