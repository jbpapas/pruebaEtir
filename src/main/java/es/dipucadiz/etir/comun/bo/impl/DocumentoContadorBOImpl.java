package es.dipucadiz.etir.comun.bo.impl;

import java.util.List;

import es.dipucadiz.etir.comun.bo.DocumentoContadorBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.DocumentoContadorDTO;
import es.dipucadiz.etir.comun.dto.DocumentoContadorDTOId;
import es.dipucadiz.etir.comun.dto.DocumentoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;


/**
 * Clase responsable de implementar la lógica de negocio necesaria para el
 * mantenimiento de la clase del modelo {@link DocumentoDTO}.
 * 
 * @version 1.0 14/05/2010
 * @author SDS[cgmesa]
 */
public class DocumentoContadorBOImpl extends
		AbstractGenericBOImpl<DocumentoContadorDTO, DocumentoContadorDTOId> implements
		DocumentoContadorBO {

	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 6600031313681872877L;

	

	/**
	 * Atributo que almacena el DAO asociado a {@link DocumentoDTO}.
	 */
	private DAOBase<DocumentoContadorDTO, DocumentoContadorDTOId> documentoContadorDao;

	

	
	

	@Override
	public DAOBase<DocumentoContadorDTO, DocumentoContadorDTOId> getDao() {
		return this.getDocumentoContadorDao();
	}

	public Integer obtieneNumeroDocumento (String coModelo, String coVersion) throws GadirServiceException{
		try {
			String[] properties = new String[]{"id.coModelo", "id.coVersion"};
			Object[] values = new Object[]{coModelo,coVersion};
			List<DocumentoContadorDTO> result = this.getDao().findFiltered(properties, values);

			if (result.size() > 0){
				return result.get(0).getNuDocumento();
			}else{
				return null;
			}
			

		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Error al obtener el listado de plantillaes.", e);
		}
	}
	// GETTERS AND SETTERS



	public void auditorias(DocumentoContadorDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}

	/**
	 * @return the documentoContadorDao
	 */
	public DAOBase<DocumentoContadorDTO, DocumentoContadorDTOId> getDocumentoContadorDao() {
		return documentoContadorDao;
	}

	/**
	 * @param documentoContadorDao the documentoContadorDao to set
	 */
	public void setDocumentoContadorDao(
			DAOBase<DocumentoContadorDTO, DocumentoContadorDTOId> documentoContadorDao) {
		this.documentoContadorDao = documentoContadorDao;
	}	
	
}
