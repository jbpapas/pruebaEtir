/**
 * 
 */
package es.dipucadiz.etir.comun.bo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.ReplicaBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.ReplicaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class ReplicaBOImpl extends AbstractGenericBOImpl<ReplicaDTO, Long> implements ReplicaBO {

	private static final long serialVersionUID = -2530737654670139535L;

	private static final Log LOG = LogFactory.getLog(ReplicaBOImpl.class);
	
	private DAOBase<ReplicaDTO, Long> dao;
	
	public DAOBase<ReplicaDTO, Long> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<ReplicaDTO, Long> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}

	
	public void auditorias(ReplicaDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
