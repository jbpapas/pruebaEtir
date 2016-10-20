package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.SituacionCargoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.SituacionCargoDTO;
import es.dipucadiz.etir.comun.dto.SituacionCargoDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;



public class SituacionCargoBOImpl extends AbstractGenericBOImpl<SituacionCargoDTO, SituacionCargoDTOId>  implements SituacionCargoBO {

	private DAOBase<SituacionCargoDTO, SituacionCargoDTOId> dao;

	public DAOBase<SituacionCargoDTO, SituacionCargoDTOId> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<SituacionCargoDTO, SituacionCargoDTOId> dao) {
		this.dao = dao;
	}
	public void auditorias(SituacionCargoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
