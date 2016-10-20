package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.FraccionamientoGarDocBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.FraccionamientoGarDocDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class FraccionamientoGarDocBOImpl extends
        AbstractGenericBOImpl<FraccionamientoGarDocDTO, Long> implements
        FraccionamientoGarDocBO {


	
	private DAOBase<FraccionamientoGarDocDTO, Long> dao;

	public void setDao(DAOBase<FraccionamientoGarDocDTO, Long> dao) {
		this.dao = dao;
	}
	
	public DAOBase<FraccionamientoGarDocDTO, Long> getDao() {
		return dao;
	}
	
	public void auditorias(FraccionamientoGarDocDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
