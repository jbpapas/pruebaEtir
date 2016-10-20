package es.dipucadiz.etir.comun.bo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.DocumentacionBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.DocumentacionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class DocumentacionBOImpl extends AbstractGenericBOImpl<DocumentacionDTO, Long> implements DocumentacionBO {
	
	
	private static final long serialVersionUID = -2530737654670139535L;

	private static final Log LOG = LogFactory.getLog(DocumentacionBOImpl.class);
	
	private DAOBase<DocumentacionDTO, Long> dao;
	

	
	public DAOBase<DocumentacionDTO, Long> getDao() {
		return dao;
	}

	public void setDao(DAOBase<DocumentacionDTO, Long> dao) {
		this.dao = dao;
	}

 
	
	public void auditorias(DocumentacionDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
