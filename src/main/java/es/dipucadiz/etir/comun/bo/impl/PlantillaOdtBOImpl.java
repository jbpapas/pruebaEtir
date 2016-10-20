/**
 * 
 */
package es.dipucadiz.etir.comun.bo.impl;

//import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.PlantillaOdtBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.PlantillaOdtDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class PlantillaOdtBOImpl extends AbstractGenericBOImpl<PlantillaOdtDTO, Long> implements PlantillaOdtBO {

	private static final Log LOG = LogFactory.getLog(PlantillaOdtBOImpl.class);
	
	private DAOBase<PlantillaOdtDTO, Long> dao;

	public DAOBase<PlantillaOdtDTO, Long> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<PlantillaOdtDTO, Long> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}
	
	public void auditorias(PlantillaOdtDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
