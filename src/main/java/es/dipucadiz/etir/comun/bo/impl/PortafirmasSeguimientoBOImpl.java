package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.PortafirmasSeguimientoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.PortafirmasSeguimientoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;



public class PortafirmasSeguimientoBOImpl extends AbstractGenericBOImpl<PortafirmasSeguimientoDTO, Long> implements PortafirmasSeguimientoBO {

	private static final long serialVersionUID = 6879318165687306079L;

	/**
	 * Atributo que almacena el DAO asociado a PortafirmasSeguimiento.
	 */
	private DAOBase<PortafirmasSeguimientoDTO, Long> dao;
	
	// GETTERS AND SETTERS
	@Override
	public DAOBase<PortafirmasSeguimientoDTO, Long> getDao() {
		return this.dao;
	}

	public void setDao(final DAOBase<PortafirmasSeguimientoDTO, Long> dao) {
		this.dao = dao;
	}

	// MÉTODOS //
	
	public void auditorias(PortafirmasSeguimientoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
