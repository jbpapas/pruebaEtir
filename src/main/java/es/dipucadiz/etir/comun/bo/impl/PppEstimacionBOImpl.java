package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.PppEstimacionBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.PppEstimacionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class PppEstimacionBOImpl extends
        AbstractGenericBOImpl<PppEstimacionDTO, Long> implements
        PppEstimacionBO {
	
	private DAOBase<PppEstimacionDTO, Long> dao;

	public void setDao(DAOBase<PppEstimacionDTO, Long> dao) {
		this.dao = dao;
	}
	
	public DAOBase<PppEstimacionDTO, Long> getDao() {
		return this.dao;

	}
	
	public void auditorias(PppEstimacionDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
