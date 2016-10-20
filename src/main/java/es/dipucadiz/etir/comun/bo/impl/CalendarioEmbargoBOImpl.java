package es.dipucadiz.etir.comun.bo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.CalendarioEmbargoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.CalendarioEmbargoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class CalendarioEmbargoBOImpl extends AbstractGenericBOImpl<CalendarioEmbargoDTO, Long> implements CalendarioEmbargoBO {

	private static final long serialVersionUID = -6662193891573788258L;
	private static final Log LOG = LogFactory.getLog(CalendarioEmbargoBOImpl.class);
	
	private DAOBase<CalendarioEmbargoDTO, Long> dao;

	public DAOBase<CalendarioEmbargoDTO, Long> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<CalendarioEmbargoDTO, Long> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}
	
	
	public void auditorias(CalendarioEmbargoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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