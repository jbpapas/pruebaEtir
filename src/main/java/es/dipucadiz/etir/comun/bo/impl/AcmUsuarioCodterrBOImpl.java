package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.AcmUsuarioCodterrBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.AcmUsuarioCodterrDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;



public class AcmUsuarioCodterrBOImpl extends AbstractGenericBOImpl<AcmUsuarioCodterrDTO, Long> implements AcmUsuarioCodterrBO {

	private DAOBase<AcmUsuarioCodterrDTO, Long> dao;

	public void setDao(DAOBase<AcmUsuarioCodterrDTO, Long> dao) {
		this.dao = dao;
	}

	public DAOBase<AcmUsuarioCodterrDTO, Long> getDao() {
		return dao;
	}

	public void auditorias(AcmUsuarioCodterrDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
