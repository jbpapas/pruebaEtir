package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.FraccionamientoReciboBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.FraccionamientoReciboDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class FraccionamientoReciboBOImpl extends
        AbstractGenericBOImpl<FraccionamientoReciboDTO, Long> implements
        FraccionamientoReciboBO {


	
	private DAOBase<FraccionamientoReciboDTO, Long> dao;

	public void setDao(DAOBase<FraccionamientoReciboDTO, Long> dao) {
		this.dao = dao;
	}
	
	public DAOBase<FraccionamientoReciboDTO, Long> getDao() {
		return dao;
	}
	
	public void auditorias(FraccionamientoReciboDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
