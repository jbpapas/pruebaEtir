/**
 * 
 */
package es.dipucadiz.etir.comun.bo.impl;

//import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.PaisBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.PaisDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class PaisBOImpl extends AbstractGenericBOImpl<PaisDTO, Short> implements PaisBO {

	private static final Log LOG = LogFactory.getLog(PaisBOImpl.class);
	
	private DAOBase<PaisDTO, Short> dao;

	public DAOBase<PaisDTO, Short> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<PaisDTO, Short> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}
	
	public void auditorias(PaisDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
