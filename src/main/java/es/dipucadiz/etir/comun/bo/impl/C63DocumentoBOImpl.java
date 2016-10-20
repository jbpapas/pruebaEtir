package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.C63DocumentoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.C63DocumentoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;



public class C63DocumentoBOImpl extends AbstractGenericBOImpl<C63DocumentoDTO, Long> implements C63DocumentoBO {

	private static final long serialVersionUID = 6879318165687306079L;

	/**
	 * Atributo que almacena el DAO asociado a ClienteCuenta.
	 */
	private DAOBase<C63DocumentoDTO, Long> dao;
	
	// GETTERS AND SETTERS
	@Override
	public DAOBase<C63DocumentoDTO, Long> getDao() {
		return this.dao;
	}

	public void setDao(final DAOBase<C63DocumentoDTO, Long> dao) {
		this.dao = dao;
	}

	// MÉTODOS //
	
	public void auditorias(C63DocumentoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
