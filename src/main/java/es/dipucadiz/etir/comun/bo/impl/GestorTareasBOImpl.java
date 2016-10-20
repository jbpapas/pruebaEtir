package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.GestorTareasBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.GestorTareasDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

/**
 * Clase responsable de implementar la lógica de negocio necesaria para el
 * mantenimiento de la clase del banco {@link GestorTareasDTO}.
 * 
 */
public class GestorTareasBOImpl extends AbstractGenericBOImpl<GestorTareasDTO, Long> implements GestorTareasBO {
	private static final long serialVersionUID = -3374190060110842673L;
	
	/**
	 * Atributo que almacena el DAO asociado a banco.
	 */
	private DAOBase<GestorTareasDTO, Long> dao;
	
	// GETTERS AND SETTERS
	@Override
	public DAOBase<GestorTareasDTO, Long> getDao() {
		return this.dao;
	}

	public void setDao(final DAOBase<GestorTareasDTO, Long> dao) {
		this.dao = dao;
	}

	// MÉTODOS //
	
	public void auditorias(GestorTareasDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
	}


}
