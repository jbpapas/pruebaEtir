package es.dipucadiz.etir.comun.bo.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.ExtraccionOrdenCampoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.ExtraccionOrdenCampoDTO;
import es.dipucadiz.etir.comun.dto.ExtraccionOrdenCampoDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;


public class ExtraccionOrdenCampoBOImpl
        extends
        AbstractGenericBOImpl<ExtraccionOrdenCampoDTO, ExtraccionOrdenCampoDTOId>
        implements ExtraccionOrdenCampoBO {
	
	private DAOBase<ExtraccionOrdenCampoDTO, ExtraccionOrdenCampoDTOId> dao;
	
	private static final Log LOG = LogFactory.getLog(ExtraccionOrdenCampoBOImpl.class);
	
	public DAOBase<ExtraccionOrdenCampoDTO, ExtraccionOrdenCampoDTOId> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<ExtraccionOrdenCampoDTO, ExtraccionOrdenCampoDTOId> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}	
	
	public void auditorias(ExtraccionOrdenCampoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}
	
	public List<ExtraccionOrdenCampoDTO> findByOrden(String coExtraccion, String tipoRegistro, short orden) throws GadirServiceException {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ExtraccionOrdenCampoDTO.class);
		criteria.add(Restrictions.eq("id.coExtraccion", coExtraccion));
		criteria.add(Restrictions.eq("id.coExtraccionTipoRegistro", tipoRegistro));
		criteria.add(Restrictions.eq("id.orden", orden));
		return (this.getDao().findByCriteria(criteria));		
	}
	
}