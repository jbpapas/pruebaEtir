package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.CondEmbargoAeatBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.CondEmbargoAeatDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;


public class CondEmbargoAeatBOImpl extends AbstractGenericBOImpl<CondEmbargoAeatDTO, Long>
        implements CondEmbargoAeatBO {
	
	
	private static final long serialVersionUID = 6879318165367306079L;

	
	private DAOBase<CondEmbargoAeatDTO, Long> dao;
	

	@Override
	public DAOBase<CondEmbargoAeatDTO, Long> getDao() {
		return this.dao;
	}

	public void setDao(final DAOBase<CondEmbargoAeatDTO, Long> dao) {
		this.dao = dao;
	}

	
	public void auditorias(CondEmbargoAeatDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
