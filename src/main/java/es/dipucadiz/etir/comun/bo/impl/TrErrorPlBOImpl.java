package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.TrErrorPlBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.TrErrorPlDTO;
import es.dipucadiz.etir.comun.dto.TrErrorPlDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

/**
 * Clase responsable de implementar la lógica de negocio necesaria para el
 * mantenimiento de la clase del Cuenta {@link CuentaDTO}.
 * 
 */
public class TrErrorPlBOImpl extends AbstractGenericBOImpl<TrErrorPlDTO, TrErrorPlDTOId>
        implements TrErrorPlBO {
	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 6879318165367306079L;

	/**
	 * Atributo que almacena el DAO asociado a Cuenta.
	 */
	private DAOBase<TrErrorPlDTO, TrErrorPlDTOId> dao;
	
	// GETTERS AND SETTERS
	@Override
	public DAOBase<TrErrorPlDTO, TrErrorPlDTOId> getDao() {
		return this.dao;
	}

	public void setDao(final DAOBase<TrErrorPlDTO, TrErrorPlDTOId> dao) {
		this.dao = dao;
	}

	// MÉTODOS //
	
	public void auditorias(TrErrorPlDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.getId().setFhActualizacion(Utilidades.getDateActual());
		transientObject.getId().setCoUsuario(DatosSesion.getLogin());
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
	}


}
