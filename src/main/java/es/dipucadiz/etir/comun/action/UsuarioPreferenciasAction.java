package es.dipucadiz.etir.comun.action;

import java.util.Date;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.AcmUsuarioBO;
import es.dipucadiz.etir.comun.bo.AcmUsuarioPreferenciaBO;
import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.dto.AcmUsuarioDTO;
import es.dipucadiz.etir.comun.dto.AcmUsuarioPreferenciaDTO;
import es.dipucadiz.etir.comun.dto.AcmUsuarioPreferenciaDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Mensaje;
import es.dipucadiz.etir.comun.utilidades.MensajeConstants;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

final public class UsuarioPreferenciasAction extends AbstractGadirBaseAction {

	private static final long serialVersionUID = -795548564800173316L;
	
	private AcmUsuarioBO acmUsuarioBO;
	private AcmUsuarioPreferenciaBO acmUsuarioPreferenciaBO;
	
	private String passwordAnterior;
	private String password;
	private String passwordConfirmar;
	private Boolean accesibilidad;
	private int numResultados;

	private static final Log LOG = LogFactory.getLog(UsuarioPreferenciasAction.class);

	public String execute() throws GadirServiceException {
		
		accesibilidad=DatosSesion.isUsuarioAccesible();
		
		numResultados=DatosSesion.getNumResultadosPaginacion();
		
		return INPUT;
	}

	public String botonPestanaPassword() throws GadirServiceException{

		return execute();
	}

	public String botonPestanaPreferencias() throws GadirServiceException{

		return execute();
	}
	
	public String botonGuardaPreferencias() throws GadirServiceException{
		try{
			AcmUsuarioPreferenciaDTOId acmUsuarioPreferenciaDTOId = new AcmUsuarioPreferenciaDTOId(DatosSesion.getLogin(), GadirConfig.leerParametro("preferencia.numero.resultados.paginacion"));
			acmUsuarioPreferenciaDTOId.setNombre("filasPag");
			AcmUsuarioPreferenciaDTO acmUsuarioPreferenciaDTO = acmUsuarioPreferenciaBO.findById(acmUsuarioPreferenciaDTOId);
			
			if (acmUsuarioPreferenciaDTO==null){
				acmUsuarioPreferenciaDTO = new AcmUsuarioPreferenciaDTO();
				acmUsuarioPreferenciaDTO.setId(acmUsuarioPreferenciaDTOId);
			}
			acmUsuarioPreferenciaDTO.setValor(""+numResultados);
			acmUsuarioPreferenciaDTO.setCoUsuarioActualizacion(DatosSesion.getLogin());
			acmUsuarioPreferenciaDTO.setFhActualizacion(new Date());
			acmUsuarioPreferenciaBO.save(acmUsuarioPreferenciaDTO);
			
			DatosSesion.setNumResultadosPaginacion(numResultados);
			
			if (accesibilidad == null)accesibilidad=false;
			AcmUsuarioDTO acmUsuarioDTO = acmUsuarioBO.findById(DatosSesion.getLogin());
			acmUsuarioDTO.setBoAccesibilidad(accesibilidad);
			acmUsuarioDTO.setCoUsuarioActualizacion(DatosSesion.getLogin());
			acmUsuarioDTO.setFhActualizacion(new Date());
			acmUsuarioBO.save(acmUsuarioDTO);
			
			DatosSesion.setUsuarioAccesible(accesibilidad);
			
			addActionMessage(Mensaje.getTexto(MensajeConstants.EL_REGISTRO_HA_SIDO_MODIFICADO));
			
		}catch(Exception e){
			addActionError(e.getMessage());
			LOG.error("Error editando preferencias de usuario", e);
		}
		
		
		return execute();
	}
	
	public String botonGuardaPassword() throws GadirServiceException{
		
//		if(Utilidades.isEmpty(passwordAnterior)){
//			addFieldError("passwordAnterior", Mensaje.getTexto(MensajeConstants.V1_CAMPO_OBLIGATORIO, "contraseña anterior"));
//		}
//		
//		if(Utilidades.isEmpty(password)){
//			addFieldError("password", Mensaje.getTexto(MensajeConstants.V1_CAMPO_OBLIGATORIO, "contraseña nueva"));
//		}
//		if(Utilidades.isEmpty(passwordConfirmar)){
//			addFieldError("passwordConfirmar", Mensaje.getTexto(MensajeConstants.V1_CAMPO_OBLIGATORIO, "confirmar contraseña"));
//		}
//		
//		if(!hasErrors()){
//			try{
//				AcmUsuarioDTO acmUsuarioDTO = acmUsuarioBO.findById(DatosSesion.getLogin());
//				if(!passwordAnterior.equals(acmUsuarioDTO.getContrasena())){
//					addFieldError("passwordAnterior", Mensaje.getTexto(MensajeConstants.V1, "Contraseña anterior incorrecta"));
//				}
//				
//			}catch(Exception e){
//				addActionError(e.getMessage());
//				LOG.error("Error cambiando contrasena de usuario", e);
//			}
//		}
//		if(!hasErrors()){
//			if(!password.equals(passwordConfirmar)){
//				addActionError(Mensaje.getTexto(MensajeConstants.ERROR_EN_ALTA, "la contraseña. No coinciden la contraseña y la confirmación"));
//			}
//		}
//		if(!hasErrors()){
//			try{
//				AcmUsuarioDTO acmUsuarioDTO = acmUsuarioBO.findById(DatosSesion.getLogin());
//				
//				acmUsuarioDTO.setContrasena(password);
//				acmUsuarioDTO.setCoUsuarioActualizacion(DatosSesion.getLogin());
//				acmUsuarioDTO.setFhActualizacion(new Date());
//				acmUsuarioBO.save(acmUsuarioDTO);
//				
//				addActionMessage(Mensaje.getTexto(MensajeConstants.EL_REGISTRO_HA_SIDO_MODIFICADO));
//				
//			}catch(Exception e){
//				addActionError(e.getMessage());
//				LOG.error("Error cambiando contrasena de usuario", e);
//			}
//		}
//		return execute();
		
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
			 
		} 
			return execute();
		 
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

	public Boolean getAccesibilidad() {
		return accesibilidad;
	}

	public void setAccesibilidad(Boolean accesibilidad) {
		this.accesibilidad = accesibilidad;
	}

	public int getNumResultados() {
		return numResultados;
	}

	public void setNumResultados(int numResultados) {
		this.numResultados = numResultados;
	}

	public AcmUsuarioBO getAcmUsuarioBO() {
		return acmUsuarioBO;
	}

	public void setAcmUsuarioBO(AcmUsuarioBO acmUsuarioBO) {
		this.acmUsuarioBO = acmUsuarioBO;
	}

	public AcmUsuarioPreferenciaBO getAcmUsuarioPreferenciaBO() {
		return acmUsuarioPreferenciaBO;
	}

	public void setAcmUsuarioPreferenciaBO(
			AcmUsuarioPreferenciaBO acmUsuarioPreferenciaBO) {
		this.acmUsuarioPreferenciaBO = acmUsuarioPreferenciaBO;
	}

	public String getPasswordAnterior() {
		return passwordAnterior;
	}

	public void setPasswordAnterior(String passwordAnterior) {
		this.passwordAnterior = passwordAnterior;
	}
	
	
	
}
