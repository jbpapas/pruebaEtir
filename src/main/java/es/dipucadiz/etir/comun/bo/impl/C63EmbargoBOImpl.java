package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.C63EmbargoBO;
import es.dipucadiz.etir.comun.bo.PortafirmasBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.C63EmbargoDTO;
import es.dipucadiz.etir.comun.dto.PortafirmasDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;



public class C63EmbargoBOImpl extends AbstractGenericBOImpl<C63EmbargoDTO, Long> implements C63EmbargoBO {

	private static final long serialVersionUID = 6879318165687306079L;

	/**
	 * Atributo que almacena el DAO asociado a C63Embargo.
	 */
	private DAOBase<C63EmbargoDTO, Long> dao;
	
	// GETTERS AND SETTERS
	@Override
	public DAOBase<C63EmbargoDTO, Long> getDao() {
		return this.dao;
	}

	public void setDao(final DAOBase<C63EmbargoDTO, Long> dao) {
		this.dao = dao;
	}

	// MÉTODOS //
	
	public void auditorias(C63EmbargoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
