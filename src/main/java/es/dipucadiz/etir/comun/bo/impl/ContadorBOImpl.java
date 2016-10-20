/**
 * 
 */
package es.dipucadiz.etir.comun.bo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.ContadorBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.ContadorDTO;
import es.dipucadiz.etir.comun.dto.ContadorDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class ContadorBOImpl extends AbstractGenericBOImpl<ContadorDTO, ContadorDTOId> implements ContadorBO {

	private static final Log LOG = LogFactory.getLog(ContadorBOImpl.class);
	
	private DAOBase<ContadorDTO, ContadorDTOId> dao;

	public DAOBase<ContadorDTO, ContadorDTOId> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<ContadorDTO, ContadorDTOId> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}
	
	public void auditorias(ContadorDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}	
}
