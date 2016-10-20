package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.FraccionamientoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.FraccionamientoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class FraccionamientoBOImpl extends
        AbstractGenericBOImpl<FraccionamientoDTO, Long> implements
        FraccionamientoBO {


	
	private DAOBase<FraccionamientoDTO, Long> dao;

	public void setDao(DAOBase<FraccionamientoDTO, Long> dao) {
		this.dao = dao;
	}
	
	public DAOBase<FraccionamientoDTO, Long> getDao() {
		return dao;
	}
	
	public void auditorias(FraccionamientoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
