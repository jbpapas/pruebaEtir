package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.FraccPlazoReciboBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.FraccPlazoReciboDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class FraccPlazoReciboBOImpl extends
        AbstractGenericBOImpl<FraccPlazoReciboDTO, Long> implements
        FraccPlazoReciboBO {


	
	private DAOBase<FraccPlazoReciboDTO, Long> dao;

	public void setDao(DAOBase<FraccPlazoReciboDTO, Long> dao) {
		this.dao = dao;
	}
	
	public DAOBase<FraccPlazoReciboDTO, Long> getDao() {
		return dao;
	}
	
	public void auditorias(FraccPlazoReciboDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
