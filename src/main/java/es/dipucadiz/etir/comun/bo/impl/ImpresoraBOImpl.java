package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.ImpresoraBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.ImpresoraDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class ImpresoraBOImpl extends AbstractGenericBOImpl<ImpresoraDTO, String>
        implements ImpresoraBO {


	private static final long serialVersionUID = -2340497702134956775L;

	private DAOBase<ImpresoraDTO, String> dao;


	// GETTERS AND SETTERS

	@Override
	public DAOBase<ImpresoraDTO, String> getDao() {
		return this.dao;
	}

	/**
	 * Método que establece el atributo dao.
	 * 
	 * @param dao
	 *            El procesoDao.
	 */
	public void setDao(final DAOBase<ImpresoraDTO, String> impresoraDao) {
		this.dao = impresoraDao;
	}
	
	public void auditorias(ImpresoraDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
