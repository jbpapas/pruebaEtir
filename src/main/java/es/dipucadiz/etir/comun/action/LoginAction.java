package es.dipucadiz.etir.comun.action;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import es.dipucadiz.etir.comun.bo.AcmUsuarioLogBO;
import es.dipucadiz.etir.comun.bo.impl.AcmUsuarioLogBOImpl;
import es.dipucadiz.etir.comun.dto.AcmUsuarioLogDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.SessionUtil;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.comun.vo.SessionUtilVO;

final public class LoginAction extends ActionSupport implements ServletRequestAware {

	private static final long serialVersionUID = 4744825327359523705L;
	private static final Log LOG = LogFactory.getLog(LoginAction.class);
	private HttpServletRequest servletRequest;

	private AcmUsuarioLogBO acmUsuarioLogBO;

	private String login_error;
	private String fin_sesion;

	public String execute() throws GadirServiceException {
		String result;
		saveLog();
		limpiarSesion();
		if (Utilidades.isEmpty(DatosSesion.getLogin()) || DatosSesion.getLogin().equals("anonymousUser") || DatosSesion.getLogin().equals("anonymous")) {
			result = INPUT;
		} else {
			result = SUCCESS;
			LOG.debug("Usuario " + DatosSesion.getLogin() + " ya está logado.");
		}
		return result;
	}

	private void limpiarSesion() {
		Enumeration<?> nombreAtributos = ServletActionContext.getRequest().getSession().getAttributeNames();
		while (nombreAtributos.hasMoreElements()) {
			String atributo = (String) nombreAtributos.nextElement();
			ServletActionContext.getRequest().getSession().removeAttribute(atributo);
			LOG.info("Atributo " + atributo + " limpiado de la sesión.");
		}

	}

	private void saveLog() throws GadirServiceException {
		if (Utilidades.isEmpty(login_error) && Utilidades.isEmpty(fin_sesion)) {
			return;
		}

		char accion;
		if ("2".equals(login_error) ||  ("1".equals(login_error))) {
			accion = AcmUsuarioLogBOImpl.ACCION_CONTRASENA_INCORRECTA;
		} else if ("5".equals(login_error)) {
			accion = AcmUsuarioLogBOImpl.ACCION_CUENTA_BAJA;
		} else if ("3".equals(login_error)) {
			accion = AcmUsuarioLogBOImpl.ACCION_CONTRASENA_CADUCADA;
		} else if ("4".equals(login_error)) {
			accion = AcmUsuarioLogBOImpl.ACCION_CUENTA_CERRADA;
		} else if (Utilidades.isNotEmpty(fin_sesion)) {
			accion = AcmUsuarioLogBOImpl.ACCION_FIN_SESION;
			//		} else if ("1".equals(login_error)) {
			//			accion = AcmUsuarioLogBOImpl.ACCION_CONTRASENA_CADUCADA;	
		} else {
			throw new GadirServiceException("Acción desconocida. le" + login_error + ", " + fin_sesion);
		}
		String username = null;
		try {			
			username = (String) servletRequest.getSession().getAttribute("ACEGI_SECURITY_LAST_USERNAME");
			if(Utilidades.isNotEmpty(username)){
				if(username.length()>10){
					username = username.substring(0,10);
				}
				AcmUsuarioLogDTO acmUsuarioLogDTO = new AcmUsuarioLogDTO();
				acmUsuarioLogDTO.setAccion(String.valueOf(accion));
				acmUsuarioLogDTO.setCoAcmUsuario(username);
				acmUsuarioLogDTO.setCoUsuarioActualizacion(username);
				acmUsuarioLogDTO.setUsuarioAjeno(username);
				acmUsuarioLogDTO.setFhAccion(Utilidades.getDateActual());
				acmUsuarioLogDTO.setFhActualizacion(Utilidades.getDateActual());
				SessionUtilVO ipHost = SessionUtil.getIpHost(servletRequest);
				acmUsuarioLogDTO.setIp(ipHost.getIp());
				acmUsuarioLogDTO.setTerminal(ipHost.getHost());
				acmUsuarioLogDTO.setMac(ipHost.getMac());
				acmUsuarioLogBO.save(acmUsuarioLogDTO);
			}
		}catch(Exception e){
			e = new GadirServiceException("Error grabando log de usuario para " + username, e);
			e.printStackTrace();
		}
	}



	public String getFin_sesion() {
		return fin_sesion;
	}

	public void setFin_sesion(String fin_sesion) {
		this.fin_sesion = fin_sesion;
	}

	public AcmUsuarioLogBO getAcmUsuarioLogBO() {
		return acmUsuarioLogBO;
	}

	public void setAcmUsuarioLogBO(AcmUsuarioLogBO acmUsuarioLogBO) {
		this.acmUsuarioLogBO = acmUsuarioLogBO;
	}

	public void setServletRequest(final HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}

	public String getLogin_error() {
		return login_error;
	}

	public void setLogin_error(String login_error) {
		this.login_error = login_error;
	}

}
