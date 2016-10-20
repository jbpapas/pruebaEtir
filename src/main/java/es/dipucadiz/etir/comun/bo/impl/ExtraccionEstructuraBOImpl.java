package es.dipucadiz.etir.comun.bo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.ExtraccionEstructuraBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.ExtraccionEstructuraDTO;
import es.dipucadiz.etir.comun.dto.ExtraccionEstructuraDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class ExtraccionEstructuraBOImpl
        extends
        AbstractGenericBOImpl<ExtraccionEstructuraDTO, ExtraccionEstructuraDTOId>
        implements ExtraccionEstructuraBO {

	private DAOBase<ExtraccionEstructuraDTO, ExtraccionEstructuraDTOId> dao;

	
	private static final Log LOG = LogFactory.getLog(ExtraccionEstructuraBOImpl.class);
	
	public DAOBase<ExtraccionEstructuraDTO, ExtraccionEstructuraDTOId> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<ExtraccionEstructuraDTO, ExtraccionEstructuraDTOId> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}
	
	public void auditorias(ExtraccionEstructuraDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
