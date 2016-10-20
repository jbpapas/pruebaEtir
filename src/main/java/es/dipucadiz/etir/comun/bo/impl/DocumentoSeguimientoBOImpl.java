package es.dipucadiz.etir.comun.bo.impl;

import java.util.List;

import es.dipucadiz.etir.comun.bo.DocumentoSeguimientoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.DAOConstant;
import es.dipucadiz.etir.comun.dto.DocumentoDTOId;
import es.dipucadiz.etir.comun.dto.DocumentoSeguimientoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class DocumentoSeguimientoBOImpl extends AbstractGenericBOImpl<DocumentoSeguimientoDTO, Long> implements DocumentoSeguimientoBO {
	
	
	private DAOBase<DocumentoSeguimientoDTO, Long> dao;
	
	
	public void auditorias(DocumentoSeguimientoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}
	
	public DAOBase<DocumentoSeguimientoDTO, Long> getDao() {
		return dao;
	}

	public void setDao(DAOBase<DocumentoSeguimientoDTO, Long> dao) {
		this.dao = dao;
	}

	public DocumentoSeguimientoDTO getUltimaIncidencia(String coModelo,String coVersion, String coDocumento) {
		DocumentoSeguimientoDTO result;
		String[] propNames = {"documentoDTO.id"};
		Object[] filters = {new DocumentoDTOId(coModelo, coVersion, coDocumento)};
		String[] orderProperty = {"fhActualizacion","coDocumentoSeguimiento"};
		int[] orderType = {DAOConstant.DESC_ORDER,DAOConstant.DESC_ORDER};
		try {
			List<DocumentoSeguimientoDTO> documentoSeguimientoDTOs = findFiltered(propNames, filters, orderProperty, orderType);
			if (documentoSeguimientoDTOs.size() > 0) {
				result = documentoSeguimientoDTOs.get(0);
			} else {
				result = null;
			}
		} catch (GadirServiceException e) {
			log.error(e.getMensaje(), e);
			result = null;
		}
		return result;
	}
	
}
