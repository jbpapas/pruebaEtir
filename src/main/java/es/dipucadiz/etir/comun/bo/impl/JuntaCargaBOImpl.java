package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.JuntaCargaBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.JuntaCargaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;


public class JuntaCargaBOImpl extends AbstractGenericBOImpl<JuntaCargaDTO, Long>
        implements JuntaCargaBO {
	
	
	private static final long serialVersionUID = 6879318165367306079L;

	
	private DAOBase<JuntaCargaDTO, Long> dao;
	

	@Override
	public DAOBase<JuntaCargaDTO, Long> getDao() {
		return this.dao;
	}

	public void setDao(final DAOBase<JuntaCargaDTO, Long> dao) {
		this.dao = dao;
	}

	
	public void auditorias(JuntaCargaDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
