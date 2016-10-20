package es.dipucadiz.etir.comun.utilidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.AcmUsuarioNotificacionBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.AcmUsuarioNotificacionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public class UsuarioNotificacionUtil {
	
	private static AcmUsuarioNotificacionBO acmUsuarioNotificacionBO;
	
	public static String parsearMensajeNotificacion(String mensaje) {
		return LenguajilloUtil.parsearLenguajillo(mensaje);
	}
	
	public static List<AcmUsuarioNotificacionDTO> getUsuarioNotificaciones(String login) {
		
		List<AcmUsuarioNotificacionDTO> usuarioNotificaciones = new ArrayList<AcmUsuarioNotificacionDTO>();
		
		usuarioNotificaciones=acmUsuarioNotificacionBO.findByAcmUsuario(login);
		
		return usuarioNotificaciones;
	}
	public static List<AcmUsuarioNotificacionDTO> getUsuarioNotificaciones() {
		return getUsuarioNotificaciones(DatosSesion.getLogin());
	}
	
	public static List<AcmUsuarioNotificacionDTO> getUsuarioNotificacionesTipo1(String login) {
		
		List<AcmUsuarioNotificacionDTO> usuarioNotificaciones = new ArrayList<AcmUsuarioNotificacionDTO>();
		
		usuarioNotificaciones=acmUsuarioNotificacionBO.findByAcmUsuarioAndTipo(login, 1);
		
		return usuarioNotificaciones;
	}
	
	public static List<AcmUsuarioNotificacionDTO> getUsuarioNotificacionesTipo1() {
		return getUsuarioNotificacionesTipo1(DatosSesion.getLogin());
	}
	
	public static int getUsuarioNumNotificacionesTipo1(String login) throws GadirServiceException{
		int numUsuarioNotificaciones;
		final DetachedCriteria criteria = DetachedCriteria.forClass(AcmUsuarioNotificacionDTO.class);
		criteria.add(Restrictions.eq("acmUsuarioDTOByCoAcmUsuario.coAcmUsuario", login));
		criteria.add(Restrictions.eq("importancia", (short)1));
		numUsuarioNotificaciones = acmUsuarioNotificacionBO.countByCriteria(criteria);
		return numUsuarioNotificaciones;
	}
	
	public static int getUsuarioNumNotificacionesTipo1() throws GadirServiceException{
		return getUsuarioNumNotificacionesTipo1(DatosSesion.getLogin());
	}
	
	public static List<AcmUsuarioNotificacionDTO> getUsuarioNotificacionesTipo2(String login) {
		
		List<AcmUsuarioNotificacionDTO> usuarioNotificaciones = new ArrayList<AcmUsuarioNotificacionDTO>();
		
		usuarioNotificaciones=acmUsuarioNotificacionBO.findByAcmUsuarioAndFechaAndTipo(login, new Date(), 2);
		
		return usuarioNotificaciones;
	}
	public static List<AcmUsuarioNotificacionDTO> getUsuarioNotificacionesTipo2() {
		return getUsuarioNotificacionesTipo2(DatosSesion.getLogin());
	}
	
	public static List<AcmUsuarioNotificacionDTO> getUsuarioNotificacionesTipo3(String login) {
		
		List<AcmUsuarioNotificacionDTO> usuarioNotificaciones = new ArrayList<AcmUsuarioNotificacionDTO>();
		
		usuarioNotificaciones=acmUsuarioNotificacionBO.findByAcmUsuarioAndTipo(login, 3);
		
		return usuarioNotificaciones;
	}
	public static List<AcmUsuarioNotificacionDTO> getUsuarioNotificacionesTipo3() {
		return getUsuarioNotificacionesTipo3(DatosSesion.getLogin());
	}
	
	public static List<AcmUsuarioNotificacionDTO> getUsuarioNotificacionesTipo3L(String login) {
		
		List<AcmUsuarioNotificacionDTO> usuarioNotificaciones = new ArrayList<AcmUsuarioNotificacionDTO>();
		
		usuarioNotificaciones=acmUsuarioNotificacionBO.findByAcmUsuarioAndImportancia3AndTipo(login, "L");
		
		return usuarioNotificaciones;
	}
	public static List<AcmUsuarioNotificacionDTO> getUsuarioNotificacionesTipo3L() {
		return getUsuarioNotificacionesTipo3L(DatosSesion.getLogin());
	}

	public static List<AcmUsuarioNotificacionDTO> getUsuarioNotificacionesTipo3T(String login) {
	
		List<AcmUsuarioNotificacionDTO> usuarioNotificaciones = new ArrayList<AcmUsuarioNotificacionDTO>();
	
		usuarioNotificaciones=acmUsuarioNotificacionBO.findByAcmUsuarioAndImportancia3AndTipo(login, "T");
	
		return usuarioNotificaciones;
	}
	public static List<AcmUsuarioNotificacionDTO> getUsuarioNotificacionesTipo3T() {
		return getUsuarioNotificacionesTipo3T(DatosSesion.getLogin());
	}

	
//	public int getNumeroUsuarioNotificaciones(){
//		return usuarioNotificaciones.size();
//	}



	public static AcmUsuarioNotificacionBO getAcmUsuarioNotificacionBO() {
		return acmUsuarioNotificacionBO;
	}



	public void setAcmUsuarioNotificacionBO(
			AcmUsuarioNotificacionBO acmUsuarioNotificacionBO) {
		UsuarioNotificacionUtil.acmUsuarioNotificacionBO = acmUsuarioNotificacionBO;
	}
	
	
}
