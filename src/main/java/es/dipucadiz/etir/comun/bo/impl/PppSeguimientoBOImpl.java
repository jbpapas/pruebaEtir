package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.PppSeguimientoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.PppSeguimientoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class PppSeguimientoBOImpl extends AbstractGenericBOImpl<PppSeguimientoDTO, Long> implements PppSeguimientoBO {
	
	
	private DAOBase<PppSeguimientoDTO, Long> dao;
	
	
	public void auditorias(PppSeguimientoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}
	
	public DAOBase<PppSeguimientoDTO, Long> getDao() {
		return dao;
	}

	public void setDao(DAOBase<PppSeguimientoDTO, Long> dao) {
		this.dao = dao;
	}
	
}