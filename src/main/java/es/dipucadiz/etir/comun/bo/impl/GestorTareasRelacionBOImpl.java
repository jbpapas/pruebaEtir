package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.GestorTareasRelacionBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.GestorTareasRelacionDTO;
import es.dipucadiz.etir.comun.dto.GestorTareasRelacionDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

/**
 * Clase responsable de implementar la lógica de negocio necesaria para el
 * mantenimiento de la clase del banco {@link GestorTareasRelacionDTO}.
 * 
 */
public class GestorTareasRelacionBOImpl extends AbstractGenericBOImpl<GestorTareasRelacionDTO, GestorTareasRelacionDTOId> implements GestorTareasRelacionBO {
	private static final long serialVersionUID = -3374190060110842673L;
	
	/**
	 * Atributo que almacena el DAO asociado a banco.
	 */
	private DAOBase<GestorTareasRelacionDTO, GestorTareasRelacionDTOId> dao;
	
	// GETTERS AND SETTERS
	@Override
	public DAOBase<GestorTareasRelacionDTO, GestorTareasRelacionDTOId> getDao() {
		return this.dao;
	}

	public void setDao(final DAOBase<GestorTareasRelacionDTO, GestorTareasRelacionDTOId> dao) {
		this.dao = dao;
	}

	// MÉTODOS //
	
	public void auditorias(GestorTareasRelacionDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
	}


}
