package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.AcmUsuarioLogBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.AcmUsuarioLogDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class AcmUsuarioLogBOImpl extends AbstractGenericBOImpl<AcmUsuarioLogDTO, Long>
        implements AcmUsuarioLogBO {
	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 6879318165367306079L;
	
	public static final char ACCION_LOGIN = 'I';
	public static final char ACCION_LOGOUT = 'O';
	public static final char ACCION_CONTRASENA_INCORRECTA = 'P';
	public static final char ACCION_CUENTA_BAJA = 'B';
	public static final char ACCION_CONTRASENA_CADUCADA = 'C';
	public static final char ACCION_CUENTA_CERRADA = 'E';
	public static final char ACCION_FIN_SESION = 'S';

	private DAOBase<AcmUsuarioLogDTO, Long> dao;
	
	// GETTERS AND SETTERS
	@Override
	public DAOBase<AcmUsuarioLogDTO, Long> getDao() {
		return this.dao;
	}

	public void setDao(final DAOBase<AcmUsuarioLogDTO, Long> dao) {
		this.dao = dao;
	}

	// MÉTODOS //
	
	public void auditorias(AcmUsuarioLogDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		if(Utilidades.isEmpty(transientObject.getCoUsuarioActualizacion()))
			transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
	}


}
