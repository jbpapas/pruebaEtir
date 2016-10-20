package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.PeriodoVoluntariaBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.PeriodoVoluntariaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;



public class PeriodoVoluntariaBOImpl extends AbstractGenericBOImpl<PeriodoVoluntariaDTO, Long> implements PeriodoVoluntariaBO {

	private DAOBase<PeriodoVoluntariaDTO, Long> dao;

	public void setDao(DAOBase<PeriodoVoluntariaDTO, Long> dao) {
		this.dao = dao;
	}

	public DAOBase<PeriodoVoluntariaDTO, Long> getDao() {
		return dao;
	}

	public void auditorias(PeriodoVoluntariaDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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