package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.CodigoTerritorialCompatBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.CodigoTerritorialCompatDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;



public class CodigoTerritorialCompatBOImpl extends AbstractGenericBOImpl<CodigoTerritorialCompatDTO, Long> implements CodigoTerritorialCompatBO {

	private DAOBase<CodigoTerritorialCompatDTO, Long> dao;

	public void setDao(DAOBase<CodigoTerritorialCompatDTO, Long> dao) {
		this.dao = dao;
	}

	public DAOBase<CodigoTerritorialCompatDTO, Long> getDao() {
		return dao;
	}

	public void auditorias(CodigoTerritorialCompatDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
