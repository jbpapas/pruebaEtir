package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.DomiciliacionBonifRuegoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.DomiciliacionBonifRuegoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;



public class DomiciliacionBonifRuegoBOImpl extends AbstractGenericBOImpl<DomiciliacionBonifRuegoDTO, Long> implements DomiciliacionBonifRuegoBO {

	private DAOBase<DomiciliacionBonifRuegoDTO, Long> dao;

	public void setDao(DAOBase<DomiciliacionBonifRuegoDTO, Long> dao) {
		this.dao = dao;
	}

	public DAOBase<DomiciliacionBonifRuegoDTO, Long> getDao() {
		return dao;
	}

	public void auditorias(DomiciliacionBonifRuegoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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