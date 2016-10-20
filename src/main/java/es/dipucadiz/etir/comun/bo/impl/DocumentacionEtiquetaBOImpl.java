package es.dipucadiz.etir.comun.bo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.DocumentacionEtiquetaBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.DocumentacionEtiquetaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class DocumentacionEtiquetaBOImpl extends AbstractGenericBOImpl<DocumentacionEtiquetaDTO, Long> implements DocumentacionEtiquetaBO {
	
	
	private static final long serialVersionUID = -2530737654670139535L;

	private static final Log LOG = LogFactory.getLog(DocumentacionBOImpl.class);
	
	private DAOBase<DocumentacionEtiquetaDTO, Long> dao;
	
	
	public void auditorias(DocumentacionEtiquetaDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}
	
	public DAOBase<DocumentacionEtiquetaDTO, Long> getDao() {
		return dao;
	}

	public void setDao(DAOBase<DocumentacionEtiquetaDTO, Long> dao) {
		this.dao = dao;
	}

 
	
}
