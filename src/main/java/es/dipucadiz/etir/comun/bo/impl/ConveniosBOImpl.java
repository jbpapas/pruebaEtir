package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.ConveniosBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.ConveniosDTO;
import es.dipucadiz.etir.comun.dto.ConveniosDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;



public class ConveniosBOImpl extends AbstractGenericBOImpl<ConveniosDTO, ConveniosDTOId> implements ConveniosBO {

	private DAOBase<ConveniosDTO, ConveniosDTOId> dao;

	public void setDao(DAOBase<ConveniosDTO, ConveniosDTOId> dao) {
		this.dao = dao;
	}

	public DAOBase<ConveniosDTO, ConveniosDTOId> getDao() {
		return dao;
	}

	public void auditorias(ConveniosDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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