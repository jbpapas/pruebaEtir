package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.DatosBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.DatosDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;


public class DatosBOImpl extends AbstractGenericBOImpl<DatosDTO, Long>
        implements DatosBO {
	
	
	private static final long serialVersionUID = 6879318165367306079L;

	
	private DAOBase<DatosDTO, Long> dao;
	

	@Override
	public DAOBase<DatosDTO, Long> getDao() {
		return this.dao;
	}

	public void setDao(final DAOBase<DatosDTO, Long> dao) {
		this.dao = dao;
	}

	
	public void auditorias(DatosDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
