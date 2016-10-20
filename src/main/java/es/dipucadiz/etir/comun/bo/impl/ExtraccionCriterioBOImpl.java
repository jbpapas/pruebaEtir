package es.dipucadiz.etir.comun.bo.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.ExtraccionCriterioBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.ExtraccionCriterioDTO;
import es.dipucadiz.etir.comun.dto.ExtraccionCriterioDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;


public class ExtraccionCriterioBOImpl extends
        AbstractGenericBOImpl<ExtraccionCriterioDTO, ExtraccionCriterioDTOId>
        implements ExtraccionCriterioBO {
	
	private DAOBase<ExtraccionCriterioDTO, ExtraccionCriterioDTOId> dao;
	
	private static final Log LOG = LogFactory.getLog(ExtraccionCriterioBOImpl.class);
	
	public DAOBase<ExtraccionCriterioDTO, ExtraccionCriterioDTOId> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<ExtraccionCriterioDTO, ExtraccionCriterioDTOId> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}	
	
	public void auditorias(ExtraccionCriterioDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}

	public List<ExtraccionCriterioDTO> findByTipoRegistro(String coExtraccion, String tipoRegistro) throws GadirServiceException {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ExtraccionCriterioDTO.class);
		criteria.add(Restrictions.eq("id.coExtraccion", coExtraccion));
		criteria.add(Restrictions.eq("id.coExtraccionTipoRegistro", tipoRegistro));
		return (this.getDao().findByCriteria(criteria));		
	}
	
}