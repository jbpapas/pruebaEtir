package es.dipucadiz.etir.comun.bo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.CargaControlRecepcionTotalizadoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.CargaControlRecepcionTotalizadoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class CargaControlRecepcionTotalizadoBOImpl extends
		AbstractGenericBOImpl<CargaControlRecepcionTotalizadoDTO, Long>
		implements CargaControlRecepcionTotalizadoBO {

	private static final Log LOG = LogFactory
			.getLog(CargaControlRecepcionTotalizadoBOImpl.class);

	private DAOBase<CargaControlRecepcionTotalizadoDTO, Long> dao;

	public DAOBase<CargaControlRecepcionTotalizadoDTO, Long> getDao() {
		return dao;
	}

	public void setDao(
			final DAOBase<CargaControlRecepcionTotalizadoDTO, Long> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}

	public void auditorias(CargaControlRecepcionTotalizadoDTO transientObject,
			Boolean saveOnly) throws GadirServiceException {
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
