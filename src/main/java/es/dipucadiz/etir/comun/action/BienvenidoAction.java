package es.dipucadiz.etir.comun.action;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;

import es.dipucadiz.etir.comun.bo.AcmUsuarioBO;
import es.dipucadiz.etir.comun.bo.AcmUsuarioLogBO;
import es.dipucadiz.etir.comun.bo.impl.AcmUsuarioLogBOImpl;
import es.dipucadiz.etir.comun.dto.AcmUsuarioDTO;
import es.dipucadiz.etir.comun.dto.AcmUsuarioLogDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.listener.SessionCounterListener;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Fichero;
import es.dipucadiz.etir.comun.utilidades.Mensaje;
import es.dipucadiz.etir.comun.utilidades.MensajeConstants;
import es.dipucadiz.etir.comun.utilidades.SessionUtil;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.comun.vo.SessionUtilVO;

final public class BienvenidoAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = 4744825327359523705L;
	private static final Log LOG = LogFactory.getLog(BienvenidoAction.class);
    private static final String LOCKED = "locked";
    private static final String CAMBIAR_PASSWORD = "cambiarPassword";
	private HttpServletRequest servletRequest;
	private HttpServletResponse servletResponse;
	private AcmUsuarioBO acmUsuarioBO; 
	private AcmUsuarioLogBO acmUsuarioLogBO;
	
	private String lg;
	private String passwordAnterior;
	private String password;
	private String passwordConfirmar;
	
	String rutaEncuesta = "/home/gadir/encuesta/";
//	String rutaEncuesta = "C:\\Temp\\encuesta\\";
	
	public String execute() throws GadirServiceException {
		String result = SUCCESS;
		if (Utilidades.isNotEmpty(lg)) {
			if (SessionCounterListener.isUserInSession(DatosSesion.getLogin(), servletRequest.getRequestedSessionId())) {
				result = LOCKED;
			}
			saveLog();
			
			if (Utilidades.isNotNull(SecurityContextHolder.getContext().getAuthentication())) {
				String username;
				String password;
				final Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 
					username = ((UserDetails)obj).getUsername();
					password= ((UserDetails)obj).getPassword();
					if(username.equals(password)){
						addActionError("Debe cambiar su clave de usuario");
						result= CAMBIAR_PASSWORD;
					}
				 
			}  
		}
		return result;
	}
	
	public String botonGuardaPassword() throws GadirServiceException{
		
		String username="";
		 
		if (Utilidades.isNotNull(SecurityContextHolder.getContext().getAuthentication())) {
			
			final Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

				username = ((UserDetails)obj).getUsername();
				 
			 
		}
		
		
		if(Utilidades.isEmpty(passwordAnterior)){
			addFieldError("passwordAnterior", Mensaje.getTexto(MensajeConstants.V1_CAMPO_OBLIGATORIO, "contraseña anterior"));
		}
		
		if(Utilidades.isEmpty(password)){
			addFieldError("password", Mensaje.getTexto(MensajeConstants.V1_CAMPO_OBLIGATORIO, "contraseña nueva"));
		}
		if(Utilidades.isEmpty(passwordConfirmar)){
			addFieldError("passwordConfirmar", Mensaje.getTexto(MensajeConstants.V1_CAMPO_OBLIGATORIO, "confirmar contraseña"));
		}
		
		if(!hasErrors()){
			try{
				AcmUsuarioDTO acmUsuarioDTO = acmUsuarioBO.findById(DatosSesion.getLogin());
				if(!passwordAnterior.equals(acmUsuarioDTO.getContrasena())){
					addFieldError("passwordAnterior", Mensaje.getTexto(MensajeConstants.V1, "Contraseña anterior incorrecta"));
				}
				
				if( !Utilidades.isEmpty(username) &&  password.equals(username)){
					addFieldError("password", "No puede coincidir el usuario y la contraseña");
				}
			}catch(Exception e){
				addActionError(e.getMessage());
				LOG.error("Error cambiando contrasena de usuario", e);
			}
		}

		if(!hasErrors()){
			if(password.length()<=5){
				addFieldError("password", "La contraseña debe tener una longitud igual o mayor a seis caracteres");
			}
		}
		if(!hasErrors()){
			if(username.equals(password)){
				addFieldError("password", "La contraseña no debe coincidir con el nombre de usuario");
			}
		}
		if(!hasErrors()){
			
			if (!password.matches ("^.*\\d.*$")) { 
				addFieldError("password", "La contraseña debe contener caracteres numéricos");
			}
		}
		if(!hasErrors()){
			if(!password.equals(passwordConfirmar)){
				addFieldError("password",   "No coinciden la contraseña y la confirmación");
			}
		}
		
		if(!hasErrors()){
			if(passwordAnterior.equals(password)){
				addFieldError("password",   "No pueden coincidir la contraseña anterior y la nueva");
			}
		}
		
		if(!hasErrors()){
			try{
				AcmUsuarioDTO acmUsuarioDTO = acmUsuarioBO.findById(DatosSesion.getLogin());
				
				acmUsuarioDTO.setContrasena(password);
				acmUsuarioDTO.setCoUsuarioActualizacion(DatosSesion.getLogin());
				acmUsuarioDTO.setFhActualizacion(new Date());
				acmUsuarioBO.save(acmUsuarioDTO);
				
				addActionMessage(Mensaje.getTexto(MensajeConstants.EL_REGISTRO_HA_SIDO_MODIFICADO));
				
			}catch(Exception e){
				addActionError(e.getMessage());
				LOG.error("Error cambiando contrasena de usuario", e);
			}
			return execute();
		}else{
			return CAMBIAR_PASSWORD;
		}
		
	}

	public String getUrlEncuesta() {
		return getUrlEncuesta(true);
	}

	public String getUrlEncuesta(boolean controlarYaHecha) {
		String result = "YA";

		boolean yaHecha;
		if (controlarYaHecha) {
			yaHecha = Fichero.existeFichero(rutaEncuesta + DatosSesion.getLogin() + ".txt");
		} else {
			yaHecha = false;
		}
		
		if (!yaHecha) {
			try {
				BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(rutaEncuesta + "url.txt"), "UTF8"));
				result = input.readLine();
				input.close();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				LOG.warn("Dirección de encuesta no encontrada en el fichero: " + rutaEncuesta + "url.txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	public String botonIrEncuesta() {
		String url = getUrlEncuesta(false);
		try {
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(rutaEncuesta + DatosSesion.getLogin() + ".txt", true)));
		    String usuario = DatosSesion.getLogin();
			String direccionIP = servletRequest.getRemoteHost();
		    String fechaHora = Utilidades.dateToYYYYMMDDHHMMSSformateado(Utilidades.getDateActual());
			out.println(usuario + '\t' + direccionIP + '\t' + fechaHora);
		    out.close();
		    servletResponse.sendRedirect(url);
		    SessionCounterListener.invalidateSessionId(servletRequest.getRequestedSessionId());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void saveLog() throws GadirServiceException {
		char accion;
		if ("1".equals(lg)) {
			accion = AcmUsuarioLogBOImpl.ACCION_LOGIN;
			SessionUtilVO ipHost = SessionUtil.getIpHost(servletRequest);
			SessionCounterListener.modifySessionUser(servletRequest.getRequestedSessionId(), DatosSesion.getLogin(), ipHost.getIp(), ipHost.getHost(), servletRequest.getSession());
		} else {
			throw new GadirServiceException("Acción desconocida. lg" + lg);
		}
		String username = DatosSesion.getLogin();
		try {
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
		}catch(Exception e){
			LOG.error(e);
		}
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

	public void setServletResponse(HttpServletResponse response) {
		this.servletResponse = response;
	}
	
	public String getLg() {
		return lg;
	}

	public void setLg(String lg) {
		this.lg = lg;
	}

	public String getPasswordAnterior() {
		return passwordAnterior;
	}

	public void setPasswordAnterior(String passwordAnterior) {
		this.passwordAnterior = passwordAnterior;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirmar() {
		return passwordConfirmar;
	}

	public void setPasswordConfirmar(String passwordConfirmar) {
		this.passwordConfirmar = passwordConfirmar;
	}

	public AcmUsuarioBO getAcmUsuarioBO() {
		return acmUsuarioBO;
	}

	public void setAcmUsuarioBO(AcmUsuarioBO acmUsuarioBO) {
		this.acmUsuarioBO = acmUsuarioBO;
	}

}
