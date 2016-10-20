package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.BonificacionAutoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.BonificacionAutoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

/**
 * Clase responsable de implementar la lógica de negocio necesaria para el
 * mantenimiento de la clase del banco {@link BonificacionAutoDTO}.
 * 
 */
public class BonificacionAutoBOImpl extends AbstractGenericBOImpl<BonificacionAutoDTO, Long> implements BonificacionAutoBO {
	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 6879318165367306079L;

	/**
	 * Atributo que almacena el DAO asociado a banco.
	 */
	private DAOBase<BonificacionAutoDTO, Long> dao;
	
	// GETTERS AND SETTERS
	@Override
	public DAOBase<BonificacionAutoDTO, Long> getDao() {
		return this.dao;
	}

	public void setDao(final DAOBase<BonificacionAutoDTO, Long> dao) {
		this.dao = dao;
	}

	// MÉTODOS //
	
	public void auditorias(BonificacionAutoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
