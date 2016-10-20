package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.BancoSucursalBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.BancoSucursalDTO;
import es.dipucadiz.etir.comun.dto.BancoSucursalDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

/**
 * Clase responsable de implementar la lógica de negocio necesaria para el
 * mantenimiento de la clase del bancoSucursal {@link BancoSucursalDTO}.
 * 
 */
public class BancoSucursalBOImpl extends AbstractGenericBOImpl<BancoSucursalDTO, BancoSucursalDTOId>
        implements BancoSucursalBO {
	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 6879318165367306079L;

	/**
	 * Atributo que almacena el DAO asociado a bancoSucursal.
	 */
	private DAOBase<BancoSucursalDTO, BancoSucursalDTOId> dao;
	
	// GETTERS AND SETTERS
	
	@Override
	public DAOBase<BancoSucursalDTO, BancoSucursalDTOId> getDao() {
		return this.dao;
	}

	public void setDao(final DAOBase<BancoSucursalDTO, BancoSucursalDTOId> dao) {
		this.dao = dao;
	}

	// MÉTODOS //
	
	public void auditorias(BancoSucursalDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
