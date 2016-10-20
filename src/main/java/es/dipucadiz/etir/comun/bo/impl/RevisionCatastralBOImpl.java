package es.dipucadiz.etir.comun.bo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.RevisionCatastralBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.RevisionCatastralDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;



public class RevisionCatastralBOImpl extends AbstractGenericBOImpl<RevisionCatastralDTO, Long>  implements RevisionCatastralBO{
	
	private static final Log LOG = LogFactory.getLog(RevisionCatastralBOImpl.class);

	private DAOBase<RevisionCatastralDTO, Long> dao;


	public DAOBase<RevisionCatastralDTO, Long> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<RevisionCatastralDTO, Long> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}
	
	public void auditorias(RevisionCatastralDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
