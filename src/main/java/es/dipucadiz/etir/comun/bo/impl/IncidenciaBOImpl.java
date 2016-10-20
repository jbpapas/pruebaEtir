/**
 * 
 */
package es.dipucadiz.etir.comun.bo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.IncidenciaBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.IncidenciaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class IncidenciaBOImpl extends AbstractGenericBOImpl<IncidenciaDTO, String> implements IncidenciaBO {

	private static final long serialVersionUID = -4611672535556431456L;

	private static final Log LOG = LogFactory.getLog(IncidenciaBOImpl.class);
	
	private DAOBase<IncidenciaDTO, String> dao;
	
	public DAOBase<IncidenciaDTO, String> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<IncidenciaDTO, String> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}
	
	public void auditorias(IncidenciaDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
