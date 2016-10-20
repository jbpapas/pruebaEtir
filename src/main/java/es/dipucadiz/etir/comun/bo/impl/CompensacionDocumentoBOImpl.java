package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.CompensacionDocumentoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.CompensacionDocumentoDTO;
import es.dipucadiz.etir.comun.dto.CompensacionDocumentoDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

/**
 * Clase responsable de implementar la lógica de negocio necesaria para el
 * mantenimiento de la clase de compensacion_documento {@link CompensacionDocumentoDTO}.
 * 
 */
public class CompensacionDocumentoBOImpl extends AbstractGenericBOImpl<CompensacionDocumentoDTO, CompensacionDocumentoDTOId>
        implements CompensacionDocumentoBO {
	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 6879318165367306079L;

	/**
	 * Atributo que almacena el DAO asociado a compensacion.
	 */
	private DAOBase<CompensacionDocumentoDTO, CompensacionDocumentoDTOId> dao;
	
	

	// MÉTODOS //
	
	public void auditorias(CompensacionDocumentoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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



	public DAOBase<CompensacionDocumentoDTO, CompensacionDocumentoDTOId> getDao() {
		return dao;
	}



	public void setDao(DAOBase<CompensacionDocumentoDTO, CompensacionDocumentoDTOId> dao) {
		this.dao = dao;
	}


}
