package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.DepositoMovimientoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.DepositoMovimientoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class DepositoMovimientoBOImpl extends AbstractGenericBOImpl<DepositoMovimientoDTO, Long>
implements DepositoMovimientoBO {

	private static final long serialVersionUID = 6879318165367306079L;

	/**
	 * Atributo que almacena el DAO asociado a banco.
	 */
	private DAOBase<DepositoMovimientoDTO, Long> dao;
	
	// GETTERS AND SETTERS
	@Override
	public DAOBase<DepositoMovimientoDTO, Long> getDao() {
		return this.dao;
	}

	public void setDao(final DAOBase<DepositoMovimientoDTO, Long> dao) {
		this.dao = dao;
	}

	// MÉTODOS //
	
	public void auditorias(DepositoMovimientoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
