package es.dipucadiz.etir.comun.bo.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.ExtraccionOrdenBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.ExtraccionOrdenDTO;
import es.dipucadiz.etir.comun.dto.ExtraccionOrdenDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;


public class ExtraccionOrdenBOImpl extends
        AbstractGenericBOImpl<ExtraccionOrdenDTO, ExtraccionOrdenDTOId>
        implements ExtraccionOrdenBO {
	
	private DAOBase<ExtraccionOrdenDTO, ExtraccionOrdenDTOId> dao;
	
	private static final Log LOG = LogFactory.getLog(ExtraccionOrdenBOImpl.class);
	
	public DAOBase<ExtraccionOrdenDTO, ExtraccionOrdenDTOId> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<ExtraccionOrdenDTO, ExtraccionOrdenDTOId> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}	
	
	public void auditorias(ExtraccionOrdenDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}
	
	public List<ExtraccionOrdenDTO> findByTipoRegistro(String coExtraccion, String tipoRegistro) throws GadirServiceException {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ExtraccionOrdenDTO.class);
		criteria.add(Restrictions.eq("id.coExtraccion", coExtraccion));
		criteria.add(Restrictions.eq("id.coExtraccionTipoRegistro", tipoRegistro));
		return (this.getDao().findByCriteria(criteria));		
	}
	
}