package es.dipucadiz.etir.comun.bo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.HSubCargoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.HCargoSubcargoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class HSubCargoBOImpl extends
		AbstractGenericBOImpl<HCargoSubcargoDTO, Long> implements HSubCargoBO {

	private static final Log LOG = LogFactory.getLog(HSubCargoBOImpl.class);

	private DAOBase<HCargoSubcargoDTO, Long> dao;

	public DAOBase<HCargoSubcargoDTO, Long> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<HCargoSubcargoDTO, Long> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}

	public void auditorias(HCargoSubcargoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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