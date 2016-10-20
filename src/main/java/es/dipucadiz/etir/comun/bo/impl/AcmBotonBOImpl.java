package es.dipucadiz.etir.comun.bo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dipucadiz.etir.comun.bo.AcmBotonBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.AcmBotonDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;



public class AcmBotonBOImpl extends AbstractGenericBOImpl<AcmBotonDTO, Long> implements AcmBotonBO {

	private DAOBase<AcmBotonDTO, Long> dao;

	public void setDao(DAOBase<AcmBotonDTO, Long> dao) {
		this.dao = dao;
	}

	public DAOBase<AcmBotonDTO, Long> getDao() {
		return dao;
	}

	public List<AcmBotonDTO> findByAcmPerfil(String coAcmPerfil) throws GadirServiceException {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("coAcmPerfil", coAcmPerfil);
		return findByNamedQuery("AcmBoton.findAcmBotonsByAcmPerfil", parametros);
	}
	
	public List<AcmBotonDTO> findByAcmUsuario(String coAcmUsuario) throws GadirServiceException {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("coAcmUsuario", coAcmUsuario);
		return findByNamedQuery("AcmBoton.findAcmBotonsByAcmUsuario", parametros);
	}
	
	public void auditorias(AcmBotonDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
