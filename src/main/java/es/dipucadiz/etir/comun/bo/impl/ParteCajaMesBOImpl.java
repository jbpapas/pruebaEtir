/**
 * 
 */
package es.dipucadiz.etir.comun.bo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.ParteCajaMesBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.ParteCajaMesDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class ParteCajaMesBOImpl extends AbstractGenericBOImpl<ParteCajaMesDTO, Long> implements ParteCajaMesBO {

	private static final long serialVersionUID = -2530737654670139535L;

	private static final Log LOG = LogFactory.getLog(ParteCajaMesBOImpl.class);
	
	private DAOBase<ParteCajaMesDTO, Long> dao;
	
	public DAOBase<ParteCajaMesDTO, Long> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<ParteCajaMesDTO, Long> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}

	
	public void auditorias(ParteCajaMesDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
