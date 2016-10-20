package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.GestorTareasSeguimientoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.GestorTareasSeguimientoDTO;
import es.dipucadiz.etir.comun.dto.GestorTareasSeguimientoDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

/**
 * Clase responsable de implementar la lógica de negocio necesaria para el
 * mantenimiento de la clase del banco {@link GestorTareasSeguimientoDTO}.
 * 
 */
public class GestorTareasSeguimientoBOImpl extends AbstractGenericBOImpl<GestorTareasSeguimientoDTO, GestorTareasSeguimientoDTOId> implements GestorTareasSeguimientoBO {
	private static final long serialVersionUID = -3374190060110842673L;
	
	/**
	 * Atributo que almacena el DAO asociado a banco.
	 */
	private DAOBase<GestorTareasSeguimientoDTO, GestorTareasSeguimientoDTOId> dao;
	
	// GETTERS AND SETTERS
	@Override
	public DAOBase<GestorTareasSeguimientoDTO, GestorTareasSeguimientoDTOId> getDao() {
		return this.dao;
	}

	public void setDao(final DAOBase<GestorTareasSeguimientoDTO, GestorTareasSeguimientoDTOId> dao) {
		this.dao = dao;
	}

	// MÉTODOS //
	
	public void auditorias(GestorTareasSeguimientoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
	}


}
