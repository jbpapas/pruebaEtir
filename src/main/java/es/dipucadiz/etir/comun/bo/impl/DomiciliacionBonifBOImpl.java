package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.DomiciliacionBonifBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.DomiciliacionBonifDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;



public class DomiciliacionBonifBOImpl extends AbstractGenericBOImpl<DomiciliacionBonifDTO, Long> implements DomiciliacionBonifBO {

	private DAOBase<DomiciliacionBonifDTO, Long> dao;

	public void setDao(DAOBase<DomiciliacionBonifDTO, Long> dao) {
		this.dao = dao;
	}

	public DAOBase<DomiciliacionBonifDTO, Long> getDao() {
		return dao;
	}

	public void auditorias(DomiciliacionBonifDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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