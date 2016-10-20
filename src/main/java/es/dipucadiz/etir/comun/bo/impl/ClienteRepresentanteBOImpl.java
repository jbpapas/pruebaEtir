package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.ClienteRepresentanteBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.ClienteRepresentanteDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public class ClienteRepresentanteBOImpl extends AbstractGenericBOImpl<ClienteRepresentanteDTO, Long> implements ClienteRepresentanteBO {
	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 6879318165367306079L;

	/**
	 * Atributo que almacena el DAO.
	 */
	private DAOBase<ClienteRepresentanteDTO, Long> dao;
	
	// GETTERS AND SETTERS
	@Override
	public DAOBase<ClienteRepresentanteDTO, Long> getDao() {
		return this.dao;
	}

	public void setDao(final DAOBase<ClienteRepresentanteDTO, Long> dao) {
		this.dao = dao;
	}

	// MÉTODOS //
	
	public void auditorias(ClienteRepresentanteDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
	}


}
