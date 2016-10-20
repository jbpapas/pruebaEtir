package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.DomiciliacionBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.DomiciliacionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;



public class DomiciliacionBOImpl extends AbstractGenericBOImpl<DomiciliacionDTO, Integer> implements DomiciliacionBO {

	private DAOBase<DomiciliacionDTO, Integer> dao;

	public void setDao(DAOBase<DomiciliacionDTO, Integer> dao) {
		this.dao = dao;
	}

	public DAOBase<DomiciliacionDTO, Integer> getDao() {
		return dao;
	}

	public void auditorias(DomiciliacionDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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