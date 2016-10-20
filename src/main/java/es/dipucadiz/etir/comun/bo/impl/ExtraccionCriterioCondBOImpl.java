package es.dipucadiz.etir.comun.bo.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.ExtraccionCriterioCondBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.ExtraccionCriterioCondDTO;
import es.dipucadiz.etir.comun.dto.ExtraccionCriterioCondDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;


public class ExtraccionCriterioCondBOImpl
        extends
        AbstractGenericBOImpl<ExtraccionCriterioCondDTO, ExtraccionCriterioCondDTOId>
        implements ExtraccionCriterioCondBO {
	
	private DAOBase<ExtraccionCriterioCondDTO, ExtraccionCriterioCondDTOId> dao;
	
	private static final Log LOG = LogFactory.getLog(ExtraccionCriterioCondBOImpl.class);
	
	public DAOBase<ExtraccionCriterioCondDTO, ExtraccionCriterioCondDTOId> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<ExtraccionCriterioCondDTO, ExtraccionCriterioCondDTOId> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}	
	
	public void auditorias(ExtraccionCriterioCondDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}
	
	public List<ExtraccionCriterioCondDTO> findByCriterio(String coExtraccion, String tipoRegistro, short coExtraccionCriterio) throws GadirServiceException {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ExtraccionCriterioCondDTO.class);
		criteria.add(Restrictions.eq("id.coExtraccion", coExtraccion));
		criteria.add(Restrictions.eq("id.coExtraccionTipoRegistro", tipoRegistro));
		criteria.add(Restrictions.eq("id.coExtraccionCriterio", coExtraccionCriterio));
		return (this.getDao().findByCriteria(criteria));		
	}

}