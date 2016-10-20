package es.dipucadiz.etir.comun.action;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.Action;

import es.dipucadiz.etir.comun.bo.AcmUsuarioNotificacionBO;
import es.dipucadiz.etir.comun.bo.EjecucionBO;
import es.dipucadiz.etir.comun.dto.AcmUsuarioDTO;
import es.dipucadiz.etir.comun.dto.AcmUsuarioNotificacionDTO;
import es.dipucadiz.etir.comun.dto.EjecucionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.listener.SessionCounterListener;
import es.dipucadiz.etir.comun.utilidades.ControlTerritorial;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.UsuarioNotificacionUtil;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

final public class UsuarioNotificacionesAction extends AbstractGadirBaseAction {
	

	private static final long serialVersionUID = 1597531504828280531L;

	private AcmUsuarioNotificacionBO acmUsuarioNotificacionBO;
	private EjecucionBO ejecucionBO;
	
	private String usuario;
	
	private String[] confirmadas;
	
	int numeroUsuarioNotificaciones;
	
	String coEjecucion;
	
	private String mensaje;
	private String timer;
	
	public String muestraNotificaciones() {
		return Action.SUCCESS;
	}
	
	public void totalNumeroUsuariosNotificaciones() throws GadirServiceException
	{
		final DetachedCriteria criteria = DetachedCriteria.forClass(AcmUsuarioNotificacionDTO.class);
		criteria.add(Restrictions.eq("acmUsuarioDTOByCoAcmUsuario.coAcmUsuario", usuario));
		criteria.add(Restrictions.eq("importancia", (short)1));
		numeroUsuarioNotificaciones = acmUsuarioNotificacionBO.countByCriteria(criteria);
	}
	
	public DetachedCriteria notificacionUsuarioPorRemitente(String receptor, Date fhActualizacion, String msg, String importancia, String tipo) throws GadirServiceException
	{
		final DetachedCriteria criteria = DetachedCriteria.forClass(AcmUsuarioNotificacionDTO.class);
		criteria.add(Restrictions.eq("acmUsuarioDTOByCoAcmUsuarioRemitente.coAcmUsuario", usuario));
		criteria.add(Restrictions.eq("acmUsuarioDTOByCoAcmUsuario.coAcmUsuario", receptor));
		criteria.add(Restrictions.eq("fhActualizacion", fhActualizacion));
		criteria.add(Restrictions.eq("mensaje", msg));
		criteria.add(Restrictions.eq("importancia", Short.parseShort(importancia)));
		criteria.add(Restrictions.eq("tipo", tipo));
		return criteria;
	}
	
	public String confirmaNotificacionConsultaDetalle(String receptor, Date fhActualizacion, String msg, String importancia, String tipo) {
		try{	
			List<AcmUsuarioNotificacionDTO> acmUsuarios = acmUsuarioNotificacionBO.findByCriteria(notificacionUsuarioPorRemitente(receptor, fhActualizacion, msg, importancia, tipo));
			if (acmUsuarios.size() == 1)
				acmUsuarioNotificacionBO.delete(acmUsuarios.get(0).getCoAcmUsuarioNotificacion());
		}catch(Exception e){
			e.printStackTrace();
		}

		totalNotificacionesAjax();
		
		return SUCCESS;
	}
	
	public DetachedCriteria usuariosNotificacionesPorRemitente(Date fhActualizacion, String msg, String importancia, String hTipo) throws GadirServiceException
	{
		final DetachedCriteria criteria = DetachedCriteria.forClass(AcmUsuarioNotificacionDTO.class);
		criteria.add(Restrictions.eq("acmUsuarioDTOByCoAcmUsuarioRemitente.coAcmUsuario", usuario));
		criteria.add(Restrictions.eq("fhActualizacion", fhActualizacion));
		criteria.add(Restrictions.eq("mensaje", msg));
		criteria.add(Restrictions.eq("importancia", Short.parseShort(importancia)));
		criteria.add(Restrictions.eq("tipo", hTipo));
		return criteria;
	}
	
	public String confirmaTodasNotificacionesConsulta(Date fhActualizacion, String msg, String importancia, String hTipo){
		try{	
			List<AcmUsuarioNotificacionDTO> acmUsuarios = acmUsuarioNotificacionBO.findByCriteria(usuariosNotificacionesPorRemitente(fhActualizacion, msg, importancia, hTipo));
			if (acmUsuarios.size() > 0){
				for(AcmUsuarioNotificacionDTO usuarioDTO : acmUsuarios)
					acmUsuarioNotificacionBO.delete(usuarioDTO.getCoAcmUsuarioNotificacion());
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		totalNotificacionesAjax();
		
		return SUCCESS;
	}
	
	public String totalNotificacionesAjax() {
		if (!controlSesionExpirado()) {
			try{
				totalNumeroUsuariosNotificaciones();
			}catch(Exception e){
				e.printStackTrace();
			}
			
			actualizaNotificacionesEnSesionAjax();
		}

		return SUCCESS;
	}
	
	public String confirmaNotificacionesAjax(){
		try{
			if (confirmadas != null) {
				for (int i=0; i<confirmadas.length; i++){
					acmUsuarioNotificacionBO.delete(Long.valueOf(confirmadas[i]));
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}

		totalNotificacionesAjax();
		
		return SUCCESS;
	}
	
	public String confirmaTodasNotificacionesAjax(){
		try{
			totalNumeroUsuariosNotificaciones();
			if (numeroUsuarioNotificaciones > 0)
				acmUsuarioNotificacionBO.deleteByCoAcmUsuario(usuario);
		}catch(Exception e){
			e.printStackTrace();
		}

		totalNotificacionesAjax();
		
		return SUCCESS;
	}
	
	public String actualizaNotificacionesEnSesionAjax(){
		
		try{
			DatosSesion.setUsuarioNotificacionesTipo1(UsuarioNotificacionUtil.getUsuarioNotificacionesTipo1());
			DatosSesion.setUsuarioNumNotificacionesTipo1(UsuarioNotificacionUtil.getUsuarioNumNotificacionesTipo1());
			DatosSesion.setUsuarioNotificacionesTipo2(UsuarioNotificacionUtil.getUsuarioNotificacionesTipo2());
			DatosSesion.setUsuarioNotificacionesTipo3T(UsuarioNotificacionUtil.getUsuarioNotificacionesTipo3T());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String verEjecucion(){
		
		try{
			EjecucionDTO ejecucionDTO=ejecucionBO.findById(Long.valueOf(coEjecucion));
			
			getServletResponse().sendRedirect("/etir/G46Detalle.action?ventanaBotonLateral=true&procesoMasivoRowid=" + ejecucionDTO.getRowid());
		}catch(Exception e){
			LOG.error("Error en notificaciones redireccionando al detalle de un batch", e);
		}
		
		return SUCCESS;
	}
	
	public String grabaNotificacionRecibosParciales() {
		try {
			if (mensaje == null) {
				mensaje = "";
			} else if (mensaje.length() > 200) {
				mensaje = new String(mensaje.getBytes(), "UTF-8");
				mensaje = mensaje.substring(0, 200);
			}
			AcmUsuarioDTO para = new AcmUsuarioDTO();
			para.setCoAcmUsuario(DatosSesion.getLogin());
			AcmUsuarioNotificacionDTO notificacionDTO = new AcmUsuarioNotificacionDTO();
			notificacionDTO.setMensaje(mensaje);
			notificacionDTO.setAcmUsuarioDTOByCoAcmUsuario(para);
			notificacionDTO.setAcmUsuarioDTOByCoAcmUsuarioRemitente(para);
			notificacionDTO.setTipo("M");
			notificacionDTO.setImportancia((short)1);
			notificacionDTO.setFxInicio(Utilidades.getFechaActual());
			acmUsuarioNotificacionBO.save(notificacionDTO);
			acmUsuarioNotificacionBO.delete(notificacionDTO.getCoAcmUsuarioNotificacion());
		} catch (GadirServiceException e) {
			LOG.error(e.getMensaje(), e);
		} catch (UnsupportedEncodingException e) {
			LOG.error(e.getMessage(), e);
		}
		return SUCCESS;
	}

	// Controlar la actividad del usuario
	private boolean controlSesionExpirado() {
		if (ControlTerritorial.isUsuarioExperto() || Utilidades.isEmpty(timer)) {
			return false;
		}
		int maxMinutos = 45; // Maximo número de minutos de inactividad permitido 
		int timerValue = Integer.parseInt(timer);
		int maxTimerValue = maxMinutos * 60000; // Conversión a milisegundos
		boolean isExpirado = timerValue >= maxTimerValue;
		if (isExpirado) {
			String miSesion = getServletRequest().getSession().getId();
			SessionCounterListener.invalidateSessionId(miSesion);
			numeroUsuarioNotificaciones = -1;
		}
		return isExpirado;
	}


	
	public AcmUsuarioNotificacionBO getAcmUsuarioNotificacionBO() {
		return acmUsuarioNotificacionBO;
	}

	public void setAcmUsuarioNotificacionBO(
			AcmUsuarioNotificacionBO acmUsuarioNotificacionBO) {
		this.acmUsuarioNotificacionBO = acmUsuarioNotificacionBO;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String[] getConfirmadas() {
		return confirmadas;
	}

	public void setConfirmadas(String[] confirmadas) {
		this.confirmadas = confirmadas;
	}

	public int getNumeroUsuarioNotificaciones() {
		return numeroUsuarioNotificaciones;
	}

	public void setNumeroUsuarioNotificaciones(int numeroUsuarioNotificaciones) {
		this.numeroUsuarioNotificaciones = numeroUsuarioNotificaciones;
	}

	public String getCoEjecucion() {
		return coEjecucion;
	}

	public void setCoEjecucion(String coEjecucion) {
		this.coEjecucion = coEjecucion;
	}

	public EjecucionBO getEjecucionBO() {
		return ejecucionBO;
	}

	public void setEjecucionBO(EjecucionBO ejecucionBO) {
		this.ejecucionBO = ejecucionBO;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getTimer() {
		return timer;
	}

	public void setTimer(String timer) {
		this.timer = timer;
	}


}
