/**
 * 
 */
package es.dipucadiz.etir.comun.bo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.OtrBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.OtrDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class OtrBOImpl extends AbstractGenericBOImpl<OtrDTO, String> implements OtrBO {

	private static final long serialVersionUID = -2530737654670139535L;

	private static final Log LOG = LogFactory.getLog(OtrBOImpl.class);
	
	private DAOBase<OtrDTO, String> dao;
	
	public DAOBase<OtrDTO, String> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<OtrDTO, String> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}

	
	public void auditorias(OtrDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
