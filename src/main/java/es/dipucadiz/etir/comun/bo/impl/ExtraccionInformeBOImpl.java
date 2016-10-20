package es.dipucadiz.etir.comun.bo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.ExtraccionInformeBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.ExtraccionInformeDTO;
import es.dipucadiz.etir.comun.dto.ExtraccionInformeDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class ExtraccionInformeBOImpl
		extends
		AbstractGenericBOImpl<ExtraccionInformeDTO, ExtraccionInformeDTOId>
		implements ExtraccionInformeBO {
	
	private DAOBase<ExtraccionInformeDTO, ExtraccionInformeDTOId> dao;
	
	private static final Log LOG = LogFactory.getLog(ExtraccionInformeBOImpl.class);
	
	public DAOBase<ExtraccionInformeDTO, ExtraccionInformeDTOId> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<ExtraccionInformeDTO, ExtraccionInformeDTOId> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}	
	
	public void auditorias(ExtraccionInformeDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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