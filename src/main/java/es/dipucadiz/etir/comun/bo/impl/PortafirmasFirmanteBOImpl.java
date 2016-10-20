package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.PortafirmasFirmanteBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.PortafirmasFirmanteDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class PortafirmasFirmanteBOImpl extends AbstractGenericBOImpl<PortafirmasFirmanteDTO, Long> implements PortafirmasFirmanteBO {
	
	/**
	 * Atributo que almacena el DAO asociado a PortafirmasFirmante.
	 */
	private DAOBase<PortafirmasFirmanteDTO, Long> dao;
	
	// GETTERS AND SETTERS
	
	@Override
	public DAOBase<PortafirmasFirmanteDTO, Long> getDao() {
		return this.dao;
	}
	
	public void setDao(final DAOBase<PortafirmasFirmanteDTO, Long> dao) {
		this.dao = dao;
	}
	
	// MÉTODOS //
	
	public void auditorias(PortafirmasFirmanteDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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

