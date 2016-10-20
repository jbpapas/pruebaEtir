package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.DocumentoNotificacionBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.DocumentoNotificacionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class DocumentoNotificacionBOImpl extends
		AbstractGenericBOImpl<DocumentoNotificacionDTO, Long> implements
		DocumentoNotificacionBO {


	private static final long serialVersionUID = 6600031313681872877L;

	private DAOBase<DocumentoNotificacionDTO, Long> dao;

	public void auditorias(DocumentoNotificacionDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
	}

	public DAOBase<DocumentoNotificacionDTO, Long> getDao() {
		return dao;
	}

	public void setDao(
			final DAOBase<DocumentoNotificacionDTO, Long> documentoNotificacionDAO) {
		this.dao = documentoNotificacionDAO;
	}
	
}
