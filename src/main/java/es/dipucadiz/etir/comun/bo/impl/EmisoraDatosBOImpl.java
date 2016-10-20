package es.dipucadiz.etir.comun.bo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.EmisoraDatosBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.EmisoraDatosDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;


public class EmisoraDatosBOImpl extends AbstractGenericBOImpl<EmisoraDatosDTO, Long> implements EmisoraDatosBO {
	private static final long serialVersionUID = 1340169969023198258L;

	private static final Log LOG = LogFactory.getLog(EmisoraDatosBOImpl.class);

	private DAOBase<EmisoraDatosDTO, Long> dao;
	
	// Getters and setters
	@Override
	public DAOBase<EmisoraDatosDTO, Long> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<EmisoraDatosDTO, Long> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}

	public void auditorias(EmisoraDatosDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
