package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.DepositoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.DepositoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class DepositoBOImpl extends AbstractGenericBOImpl<DepositoDTO, Long>
implements DepositoBO {

	private static final long serialVersionUID = 6879318165367306079L;

	/**
	 * Atributo que almacena el DAO asociado a banco.
	 */
	private DAOBase<DepositoDTO, Long> dao;
	
	// GETTERS AND SETTERS
	@Override
	public DAOBase<DepositoDTO, Long> getDao() {
		return this.dao;
	}

	public void setDao(final DAOBase<DepositoDTO, Long> dao) {
		this.dao = dao;
	}

	// MÉTODOS //
	
	public void auditorias(DepositoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
