package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.CondEmbargoC63BO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.CondEmbargoC63DTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;


public class CondEmbargoC63BOImpl extends AbstractGenericBOImpl<CondEmbargoC63DTO, Long> implements CondEmbargoC63BO {
	
	
	private static final long serialVersionUID = 6879318165367306079L;

	
	private DAOBase<CondEmbargoC63DTO, Long> dao;
	

	@Override
	public DAOBase<CondEmbargoC63DTO, Long> getDao() {
		return this.dao;
	}

	public void setDao(final DAOBase<CondEmbargoC63DTO, Long> dao) {
		this.dao = dao;
	}

	
	public void auditorias(CondEmbargoC63DTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
