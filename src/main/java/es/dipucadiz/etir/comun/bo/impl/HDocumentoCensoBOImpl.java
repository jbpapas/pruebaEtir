package es.dipucadiz.etir.comun.bo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.HDocumentoCensoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.HDocumentoCensoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class HDocumentoCensoBOImpl extends
		AbstractGenericBOImpl<HDocumentoCensoDTO, Long> implements HDocumentoCensoBO {

	private static final Log LOG = LogFactory.getLog(HDocumentoCensoBOImpl.class);

	private DAOBase<HDocumentoCensoDTO, Long> dao;

	public DAOBase<HDocumentoCensoDTO, Long> getDao() {
		return dao;
	}
	public void setDao(final DAOBase<HDocumentoCensoDTO, Long> dao) {
		this.dao = dao;
	}


	public void auditorias(HDocumentoCensoDTO transientObject, Boolean saveOnly)
			throws GadirServiceException {
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
