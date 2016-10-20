package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.AcmPerfilBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.AcmPerfilDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;



public class AcmPerfilBOImpl extends AbstractGenericBOImpl<AcmPerfilDTO, String> implements AcmPerfilBO {

	private DAOBase<AcmPerfilDTO, String> dao;

	public void setDao(DAOBase<AcmPerfilDTO, String> dao) {
		this.dao = dao;
	}

	public DAOBase<AcmPerfilDTO, String> getDao() {
		return dao;
	}

	public void auditorias(AcmPerfilDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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

}
