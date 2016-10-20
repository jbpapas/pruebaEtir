package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.BDDocumentalGrupoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.BDDocumentalGrupoDTO;
import es.dipucadiz.etir.comun.dto.BancoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

/**
 * Clase responsable de implementar la lógica de negocio necesaria para el
 * mantenimiento de la clase del banco {@link BancoDTO}.
 * 
 */
public class BDDocumentalGrupoBOImpl extends AbstractGenericBOImpl<BDDocumentalGrupoDTO, Long> implements BDDocumentalGrupoBO {
	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 6879318165367306079L;

	/**
	 * Atributo que almacena el DAO asociado a banco.
	 */
	private DAOBase<BDDocumentalGrupoDTO, Long> dao;
	
	// GETTERS AND SETTERS
	@Override
	public DAOBase<BDDocumentalGrupoDTO, Long> getDao() {
		return this.dao;
	}

	public void setDao(final DAOBase<BDDocumentalGrupoDTO, Long> dao) {
		this.dao = dao;
	}

	// MÉTODOS //
	
	public void auditorias(BDDocumentalGrupoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
	}


}
