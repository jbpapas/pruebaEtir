package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.AcercaPeticionBO;
import es.dipucadiz.etir.comun.bo.BOPBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.AcercaPeticionDTO;
import es.dipucadiz.etir.comun.dto.BOPDTO;
import es.dipucadiz.etir.comun.dto.BOPDTOId;
import es.dipucadiz.etir.comun.dto.BancoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

/**
 * Clase responsable de implementar la lógica de negocio necesaria para el
 * mantenimiento de la clase del banco {@link BancoDTO}.
 * 
 */
public class BOPBOImpl extends AbstractGenericBOImpl<BOPDTO, BOPDTOId>
        implements BOPBO {
	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 6879318165367306079L;

	/**
	 * Atributo que almacena el DAO asociado a banco.
	 */
	private DAOBase<BOPDTO, BOPDTOId> dao;
	
	// GETTERS AND SETTERS
	@Override
	public DAOBase<BOPDTO, BOPDTOId> getDao() {
		return this.dao;
	}

	public void setDao(final DAOBase<BOPDTO, BOPDTOId> dao) {
		this.dao = dao;
	}

	// MÉTODOS //
	
	public void auditorias(BOPDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
	}


}
