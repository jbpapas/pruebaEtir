package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.FraccGarantiaAportadaBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.FraccGarantiaAportadaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class FraccGarantiaAportadaBOImpl extends
        AbstractGenericBOImpl<FraccGarantiaAportadaDTO, Long> implements
        FraccGarantiaAportadaBO {


	
	private DAOBase<FraccGarantiaAportadaDTO, Long> dao;

	public void setDao(DAOBase<FraccGarantiaAportadaDTO, Long> dao) {
		this.dao = dao;
	}
	
	public DAOBase<FraccGarantiaAportadaDTO, Long> getDao() {
		return dao;
	}
	
	public void auditorias(FraccGarantiaAportadaDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
