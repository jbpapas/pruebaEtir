package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.ConveniosTipoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.ConveniosTipoDTO;
import es.dipucadiz.etir.comun.dto.ConveniosTipoDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;



public class ConveniosTipoBOImpl extends AbstractGenericBOImpl<ConveniosTipoDTO, ConveniosTipoDTOId> implements ConveniosTipoBO {

	private DAOBase<ConveniosTipoDTO, ConveniosTipoDTOId> dao;

	public void setDao(DAOBase<ConveniosTipoDTO, ConveniosTipoDTOId> dao) {
		this.dao = dao;
	}

	public DAOBase<ConveniosTipoDTO, ConveniosTipoDTOId> getDao() {
		return dao;
	}

	public void auditorias(ConveniosTipoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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