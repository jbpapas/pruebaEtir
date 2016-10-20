package es.dipucadiz.etir.comun.bo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.IncidenciaCargoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.IncidenciaCargoDTO;
import es.dipucadiz.etir.comun.dto.IncidenciaCargoDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class IncidenciaCargoBOImpl extends AbstractGenericBOImpl<IncidenciaCargoDTO, IncidenciaCargoDTOId> implements IncidenciaCargoBO {

	private static final long serialVersionUID = -4611672535556431456L;

	private static final Log LOG = LogFactory.getLog(IncidenciaCargoBOImpl.class);
	
	private DAOBase<IncidenciaCargoDTO, IncidenciaCargoDTOId> dao;
	
	public DAOBase<IncidenciaCargoDTO, IncidenciaCargoDTOId> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<IncidenciaCargoDTO, IncidenciaCargoDTOId> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}
	
	public void auditorias(IncidenciaCargoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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