package es.dipucadiz.etir.comun.bo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.AcmUsuarioNotificacionBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.DAOConstant;
import es.dipucadiz.etir.comun.dto.AcmUsuarioDTO;
import es.dipucadiz.etir.comun.dto.AcmUsuarioNotificacionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;



public class AcmUsuarioNotificacionBOImpl extends AbstractGenericBOImpl<AcmUsuarioNotificacionDTO, Long>  implements AcmUsuarioNotificacionBO {

	private static final Log LOG = LogFactory.getLog(AcmUsuarioNotificacionBOImpl.class);

	private DAOBase<AcmUsuarioNotificacionDTO, Long> dao;


	public DAOBase<AcmUsuarioNotificacionDTO, Long> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<AcmUsuarioNotificacionDTO, Long> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}
	
	public List<AcmUsuarioNotificacionDTO> findByAcmUsuario(String coAcmUsuario){
		
		//List<AcmUsuarioNotificacionDTO> lista = new ArrayList<AcmUsuarioNotificacionDTO>();
		
		return dao.findFiltered("acmUsuarioDTOByCoAcmUsuario", new AcmUsuarioDTO(coAcmUsuario, null, null), "fhActualizacion", DAOConstant.DESC_ORDER, 0, 10);
	}
	
	public List<AcmUsuarioNotificacionDTO> findByAcmUsuarioAndTipo(String coAcmUsuario, int tipo){
		
		List<AcmUsuarioNotificacionDTO> lista;
		
		lista = dao.findFiltered(
				new String[]{"acmUsuarioDTOByCoAcmUsuario", "importancia"}, 
				new Object[]{new AcmUsuarioDTO(coAcmUsuario, null, null), new Short(""+tipo)}, 
				"fhActualizacion", DAOConstant.DESC_ORDER, 0, 10);
		
		
		return lista;
	}
	
	public List<AcmUsuarioNotificacionDTO> findByAcmUsuarioAndImportancia3AndTipo(String coAcmUsuario, String tipo){
		
		List<AcmUsuarioNotificacionDTO> lista;
		
		lista = dao.findFiltered(
				new String[]{"acmUsuarioDTOByCoAcmUsuario", "importancia", "tipo"}, 
				new Object[]{new AcmUsuarioDTO(coAcmUsuario, null, null), new Short(""+3), tipo}, 
				"fhActualizacion", DAOConstant.DESC_ORDER);
		
		
		return lista;
	}
	
	public List<AcmUsuarioNotificacionDTO> findByAcmUsuarioAndFechaAndTipo(String coAcmUsuario, Date fecha, int tipo){
		
		List<AcmUsuarioNotificacionDTO> lista;
		
		Criterion[] criterions = {
				Restrictions.eq("acmUsuarioDTOByCoAcmUsuario", new AcmUsuarioDTO(coAcmUsuario, null, null)),
				Restrictions.eq("importancia", new Short(""+tipo)),
				Restrictions.le("fxInicio", fecha),
				Restrictions.or(Restrictions.ge("fxFin", fecha), Restrictions.isNull("fxFin"))
			};
		
		lista = dao.findFiltered(criterions, null, null, -1, -1);		
		
		return lista;
	}
	
	public void auditorias(AcmUsuarioNotificacionDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		if(transientObject.getFhActualizacion()==null) transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
	}

	public void deleteByCoAcmUsuario(String coAcmUsuario) throws GadirServiceException {
		getDao().deleteAll(findFiltered("acmUsuarioDTOByCoAcmUsuario.coAcmUsuario", coAcmUsuario));
		getDao().deleteAll(findFiltered("acmUsuarioDTOByCoAcmUsuarioRemitente.coAcmUsuario", coAcmUsuario));
	}

}
