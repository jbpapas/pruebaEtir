package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.ContadorRefObjBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.ContadorRefObjDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public class ContadorRefObjBOImpl extends AbstractGenericBOImpl<ContadorRefObjDTO, Long> implements ContadorRefObjBO {
	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 6879318165367306079L;

	/**
	 * Atributo que almacena el DAO.
	 */
	private DAOBase<ContadorRefObjDTO, Long> dao;
	
	// GETTERS AND SETTERS
	@Override
	public DAOBase<ContadorRefObjDTO, Long> getDao() {
		return this.dao;
	}

	public void setDao(final DAOBase<ContadorRefObjDTO, Long> dao) {
		this.dao = dao;
	}

	// MÉTODOS //
	
	public void auditorias(ContadorRefObjDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
	}


}
