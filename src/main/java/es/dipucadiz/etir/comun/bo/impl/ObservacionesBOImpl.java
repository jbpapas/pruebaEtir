package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.ObservacionesBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.BancoDTO;
import es.dipucadiz.etir.comun.dto.ObservacionesDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

/**
 * Clase responsable de implementar la lógica de negocio necesaria para el
 * mantenimiento de la clase del banco {@link BancoDTO}.
 * 
 */
public class ObservacionesBOImpl extends AbstractGenericBOImpl<ObservacionesDTO, Long> implements ObservacionesBO {
	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 6879318165367306079L;

	/**
	 * Atributo que almacena el DAO asociado a banco.
	 */
	private DAOBase<ObservacionesDTO, Long> dao;
	
	// GETTERS AND SETTERS
	@Override
	public DAOBase<ObservacionesDTO, Long> getDao() {
		return this.dao;
	}

	public void setDao(final DAOBase<ObservacionesDTO, Long> dao) {
		this.dao = dao;
	}

	// MÉTODOS //
	
	public void auditorias(ObservacionesDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
	}


}
