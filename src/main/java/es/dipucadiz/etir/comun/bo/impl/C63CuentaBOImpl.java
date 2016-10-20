package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.C63CuentaBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.C63CuentaDTO;
import es.dipucadiz.etir.comun.dto.C63CuentaDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;



public class C63CuentaBOImpl extends AbstractGenericBOImpl<C63CuentaDTO, C63CuentaDTOId> implements C63CuentaBO {

	private static final long serialVersionUID = 6879318165687306079L;

	/**
	 * Atributo que almacena el DAO asociado a ClienteCuenta.
	 */
	private DAOBase<C63CuentaDTO, C63CuentaDTOId> dao;
	
	// GETTERS AND SETTERS
	@Override
	public DAOBase<C63CuentaDTO, C63CuentaDTOId> getDao() {
		return this.dao;
	}

	public void setDao(final DAOBase<C63CuentaDTO, C63CuentaDTOId> dao) {
		this.dao = dao;
	}

	// MÉTODOS //
	
	public void auditorias(C63CuentaDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
