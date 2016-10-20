/**
 * 
 */
package es.dipucadiz.etir.comun.bo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.IncidSitCargoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.IncidSitCargoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class IncidSitCargoBOImpl extends AbstractGenericBOImpl<IncidSitCargoDTO, Long> implements IncidSitCargoBO {

	private static final long serialVersionUID = -2530737654670139535L;

	private static final Log LOG = LogFactory.getLog(IncidSitCargoBOImpl.class);
	
	private DAOBase<IncidSitCargoDTO, Long> dao;
	
	public DAOBase<IncidSitCargoDTO, Long> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<IncidSitCargoDTO, Long> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}

	
	public void auditorias(IncidSitCargoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
