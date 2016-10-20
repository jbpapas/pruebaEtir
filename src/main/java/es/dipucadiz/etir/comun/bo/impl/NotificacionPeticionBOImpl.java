package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.NotificacionPeticionBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.NotificacionPeticionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;



public class NotificacionPeticionBOImpl extends AbstractGenericBOImpl<NotificacionPeticionDTO, Long> implements NotificacionPeticionBO {

	private static final long serialVersionUID = 6879318165687306079L;

	/**
	 * Atributo que almacena el DAO asociado a NotificacionPeticion.
	 */
	private DAOBase<NotificacionPeticionDTO, Long> dao;
	
	// GETTERS AND SETTERS
	@Override
	public DAOBase<NotificacionPeticionDTO, Long> getDao() {
		return this.dao;
	}

	public void setDao(final DAOBase<NotificacionPeticionDTO, Long> dao) {
		this.dao = dao;
	}

	// MÉTODOS //
	
	public void auditorias(NotificacionPeticionDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
