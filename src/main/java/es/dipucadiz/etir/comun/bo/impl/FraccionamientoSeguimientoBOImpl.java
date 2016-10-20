package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.FraccionamientoSeguimientoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.FraccionamientoSeguimientoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class FraccionamientoSeguimientoBOImpl extends AbstractGenericBOImpl<FraccionamientoSeguimientoDTO, Long> implements FraccionamientoSeguimientoBO {
	
	
	private DAOBase<FraccionamientoSeguimientoDTO, Long> dao;
	
	
	public void auditorias(FraccionamientoSeguimientoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}
	
	public DAOBase<FraccionamientoSeguimientoDTO, Long> getDao() {
		return dao;
	}

	public void setDao(DAOBase<FraccionamientoSeguimientoDTO, Long> dao) {
		this.dao = dao;
	}
	
}