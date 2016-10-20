package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.FraccionamientoCondsBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.FraccionamientoCondsDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class FraccionamientoCondsBOImpl extends
        AbstractGenericBOImpl<FraccionamientoCondsDTO, Long> implements
        FraccionamientoCondsBO {


	
	private DAOBase<FraccionamientoCondsDTO, Long> dao;

	public void setDao(DAOBase<FraccionamientoCondsDTO, Long> dao) {
		this.dao = dao;
	}
	
	public DAOBase<FraccionamientoCondsDTO, Long> getDao() {
		return dao;
	}
	
	public void auditorias(FraccionamientoCondsDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
