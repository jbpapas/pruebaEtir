package es.dipucadiz.etir.comun.bo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.ExtraccionTipoRegistroBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.ExtraccionTipoRegistroDTO;
import es.dipucadiz.etir.comun.dto.ExtraccionTipoRegistroDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;


public class ExtraccionTipoRegistroBOImpl
        extends
        AbstractGenericBOImpl<ExtraccionTipoRegistroDTO, ExtraccionTipoRegistroDTOId>
        implements ExtraccionTipoRegistroBO {
	
	private DAOBase<ExtraccionTipoRegistroDTO, ExtraccionTipoRegistroDTOId> dao;
	
	private static final Log LOG = LogFactory.getLog(ExtraccionTipoRegistroBOImpl.class);
	
	public DAOBase<ExtraccionTipoRegistroDTO, ExtraccionTipoRegistroDTOId> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<ExtraccionTipoRegistroDTO, ExtraccionTipoRegistroDTOId> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}	
	
	public void auditorias(ExtraccionTipoRegistroDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}
	
}
