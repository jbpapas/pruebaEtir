package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.JuntaCargaErrorBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.JuntaCargaErrorDTO;
import es.dipucadiz.etir.comun.dto.JuntaCargaErrorDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;


public class JuntaCargaErrorBOImpl extends AbstractGenericBOImpl<JuntaCargaErrorDTO, JuntaCargaErrorDTOId>
        implements JuntaCargaErrorBO {
	
	
	private static final long serialVersionUID = 6879318165367306079L;

	
	private DAOBase<JuntaCargaErrorDTO, JuntaCargaErrorDTOId> dao;
	

	@Override
	public DAOBase<JuntaCargaErrorDTO, JuntaCargaErrorDTOId> getDao() {
		return this.dao;
	}

	public void setDao(final DAOBase<JuntaCargaErrorDTO, JuntaCargaErrorDTOId> dao) {
		this.dao = dao;
	}

	
	public void auditorias(JuntaCargaErrorDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
