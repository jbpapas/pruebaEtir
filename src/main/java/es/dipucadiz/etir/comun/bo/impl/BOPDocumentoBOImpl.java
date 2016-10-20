package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.BOPDocumentoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.BOPDocumentoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public class BOPDocumentoBOImpl extends AbstractGenericBOImpl<BOPDocumentoDTO, Long>
        implements BOPDocumentoBO {
	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 6879318165367306079L;

	/**
	 * Atributo que almacena el DAO asociado a banco.
	 */
	private DAOBase<BOPDocumentoDTO, Long> dao;
	
	// GETTERS AND SETTERS
	@Override
	public DAOBase<BOPDocumentoDTO, Long> getDao() {
		return this.dao;
	}

	public void setDao(final DAOBase<BOPDocumentoDTO, Long> dao) {
		this.dao = dao;
	}

	// MÉTODOS //
	
	public void auditorias(BOPDocumentoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
	}


}
