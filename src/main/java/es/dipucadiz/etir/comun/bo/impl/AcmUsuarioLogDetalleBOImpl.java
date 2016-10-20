package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.AcmUsuarioLogDetalleBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.AcmUsuarioLogDetalleDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class AcmUsuarioLogDetalleBOImpl extends AbstractGenericBOImpl<AcmUsuarioLogDetalleDTO, Long>
        implements AcmUsuarioLogDetalleBO {
	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 6879318165367306079L;

	private DAOBase<AcmUsuarioLogDetalleDTO, Long> dao;
	
	// GETTERS AND SETTERS
	@Override
	public DAOBase<AcmUsuarioLogDetalleDTO, Long> getDao() {
		return this.dao;
	}

	public void setDao(final DAOBase<AcmUsuarioLogDetalleDTO, Long> dao) {
		this.dao = dao;
	}

	// MÉTODOS //
	
	public void auditorias(AcmUsuarioLogDetalleDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
