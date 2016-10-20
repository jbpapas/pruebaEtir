package es.dipucadiz.etir.comun.bo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.MovimContableClienteBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.MovimContableClienteDTO;
import es.dipucadiz.etir.comun.dto.MovimContableClienteDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class MovimContableClienteBOImpl extends AbstractGenericBOImpl<MovimContableClienteDTO, MovimContableClienteDTOId> implements MovimContableClienteBO {
	private static final long serialVersionUID = -3983962186669128961L;
	private static final Log LOG = LogFactory.getLog(MovimContableClienteBOImpl.class);

	private DAOBase<MovimContableClienteDTO, MovimContableClienteDTOId> dao;

	public DAOBase<MovimContableClienteDTO, MovimContableClienteDTOId> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<MovimContableClienteDTO, MovimContableClienteDTOId> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}

	public void auditorias(MovimContableClienteDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
