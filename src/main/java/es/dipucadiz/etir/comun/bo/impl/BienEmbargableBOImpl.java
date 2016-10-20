package es.dipucadiz.etir.comun.bo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.BienEmbargableBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.BienEmbargableDTO;
import es.dipucadiz.etir.comun.dto.CostasDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class BienEmbargableBOImpl extends AbstractGenericBOImpl<BienEmbargableDTO, Long> implements BienEmbargableBO {

	private static final Log LOG = LogFactory.getLog(BienEmbargableBOImpl.class);
	
	private DAOBase<BienEmbargableDTO, Long> dao;

	public DAOBase<BienEmbargableDTO, Long> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<BienEmbargableDTO, Long> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}
	
	
	public void auditorias(BienEmbargableDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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