package es.dipucadiz.etir.comun.bo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.DiputacionCuentaBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.DiputacionCuentaDTO;
import es.dipucadiz.etir.comun.dto.DiputacionCuentaDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class DiputacionCuentaBOImpl extends AbstractGenericBOImpl<DiputacionCuentaDTO, DiputacionCuentaDTOId> implements DiputacionCuentaBO {
	private static final long serialVersionUID = -3983962186669128961L;
	private static final Log LOG = LogFactory.getLog(DiputacionCuentaBOImpl.class);

	private DAOBase<DiputacionCuentaDTO, DiputacionCuentaDTOId> dao;

	public DAOBase<DiputacionCuentaDTO, DiputacionCuentaDTOId> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<DiputacionCuentaDTO, DiputacionCuentaDTOId> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}

	public void auditorias(DiputacionCuentaDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
