package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.PppPlazoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.PppPlazoDTO;
import es.dipucadiz.etir.comun.dto.PppPlazoDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class PppPlazoBOImpl extends
        AbstractGenericBOImpl<PppPlazoDTO, PppPlazoDTOId> implements
        PppPlazoBO {
	
	private DAOBase<PppPlazoDTO, PppPlazoDTOId> dao;

	public void setDao(DAOBase<PppPlazoDTO, PppPlazoDTOId> dao) {
		this.dao = dao;
	}
	
	public DAOBase<PppPlazoDTO, PppPlazoDTOId> getDao() {
		return this.dao;

	}
	
	public void auditorias(PppPlazoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
