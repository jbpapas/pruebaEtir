package es.dipucadiz.etir.comun.bo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dipucadiz.etir.comun.bo.AcmMenuBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.AcmMenuDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;



public class AcmMenuBOImpl extends AbstractGenericBOImpl<AcmMenuDTO, String> implements AcmMenuBO {

	private DAOBase<AcmMenuDTO, String> dao;

	public void setDao(DAOBase<AcmMenuDTO, String> dao) {
		this.dao = dao;
	}

	public DAOBase<AcmMenuDTO, String> getDao() {
		return dao;
	}

	
	
	public List<AcmMenuDTO> findByAcmPerfil(String coAcmPerfil) throws GadirServiceException{
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("coAcmPerfil", coAcmPerfil);
		
		return findByNamedQuery("AcmMenu.findAcmMenusActivosByAcmPerfil", parametros);
	}
	
	public List<AcmMenuDTO> findByAcmPerfilSinOrden(String coAcmPerfil) throws GadirServiceException{
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("coAcmPerfil", coAcmPerfil);
		
		return findByNamedQuery("AcmMenu.findAcmMenusActivosByAcmPerfilSinOrden", parametros);
	}
	
	
	public void auditorias(AcmMenuDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
