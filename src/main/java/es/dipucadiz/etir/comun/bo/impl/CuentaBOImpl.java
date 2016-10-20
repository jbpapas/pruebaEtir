package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.CuentaBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.CuentaDTO;
import es.dipucadiz.etir.comun.dto.CuentaDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

/**
 * Clase responsable de implementar la lógica de negocio necesaria para el
 * mantenimiento de la clase del Cuenta {@link CuentaDTO}.
 * 
 */
public class CuentaBOImpl extends AbstractGenericBOImpl<CuentaDTO, CuentaDTOId>
        implements CuentaBO {
	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 6879318165367306079L;

	/**
	 * Atributo que almacena el DAO asociado a Cuenta.
	 */
	private DAOBase<CuentaDTO, CuentaDTOId> dao;
	
	// GETTERS AND SETTERS
	@Override
	public DAOBase<CuentaDTO, CuentaDTOId> getDao() {
		return this.dao;
	}

	public void setDao(final DAOBase<CuentaDTO, CuentaDTOId> dao) {
		this.dao = dao;
	}

	// MÉTODOS //
	
	public void auditorias(CuentaDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
	}


}
