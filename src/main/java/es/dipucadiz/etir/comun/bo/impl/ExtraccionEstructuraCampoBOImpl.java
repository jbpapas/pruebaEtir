package es.dipucadiz.etir.comun.bo.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.ExtraccionEstructuraCampoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.ExtraccionEstructuraCampoDTO;
import es.dipucadiz.etir.comun.dto.ExtraccionEstructuraCampoDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;


public class ExtraccionEstructuraCampoBOImpl
        extends
        AbstractGenericBOImpl<ExtraccionEstructuraCampoDTO, ExtraccionEstructuraCampoDTOId>
        implements ExtraccionEstructuraCampoBO {

	
	private DAOBase<ExtraccionEstructuraCampoDTO, ExtraccionEstructuraCampoDTOId> dao;

	
	private static final Log LOG = LogFactory.getLog(ExtraccionEstructuraCampoBOImpl.class);
	
	public DAOBase<ExtraccionEstructuraCampoDTO, ExtraccionEstructuraCampoDTOId> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<ExtraccionEstructuraCampoDTO, ExtraccionEstructuraCampoDTOId> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}
	
	public void auditorias(ExtraccionEstructuraCampoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}
	

	public List<ExtraccionEstructuraCampoDTO> findByEstructura(String coExtraccion, String tipoRegistro, short ordenExtraccion) throws GadirServiceException{
		//List<ExtraccionEstructuraCampoDTO> campos = new ArrayList<ExtraccionEstructuraCampoDTO>();
		DetachedCriteria criteria = DetachedCriteria.forClass(ExtraccionEstructuraCampoDTO.class);
		criteria.add(Restrictions.eq("id.coExtraccion", coExtraccion));
		criteria.add(Restrictions.eq("id.coExtraccionTipoRegistro", tipoRegistro));
		criteria.add(Restrictions.eq("id.ordenExtraccionEstructura", ordenExtraccion));
		return (this.getDao().findByCriteria(criteria));		
	}
	
	
}
