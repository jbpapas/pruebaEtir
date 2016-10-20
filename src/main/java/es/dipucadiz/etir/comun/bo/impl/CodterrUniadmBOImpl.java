/**
 * 
 */
package es.dipucadiz.etir.comun.bo.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.CodterrUniadmBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.CodigoTerritorialDTO;
import es.dipucadiz.etir.comun.dto.CodterrUniadmDTO;
import es.dipucadiz.etir.comun.dto.CodterrUniadmDTOId;
import es.dipucadiz.etir.comun.dto.UnidadAdministrativaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class CodterrUniadmBOImpl extends AbstractGenericBOImpl<CodterrUniadmDTO, CodterrUniadmDTOId> implements CodterrUniadmBO {

	private static final Log LOG = LogFactory.getLog(CodterrUniadmBOImpl.class);
	
	private DAOBase<CodterrUniadmDTO, CodterrUniadmDTOId> dao;
	private DAOBase<UnidadAdministrativaDTO, String> unidadAdministrativaDao;
	private DAOBase<CodigoTerritorialDTO, String> codigoTerritorialDao;

	public DAOBase<CodterrUniadmDTO, CodterrUniadmDTOId> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<CodterrUniadmDTO, CodterrUniadmDTOId> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}

	public DAOBase<CodigoTerritorialDTO, String> getCodigoTerritorialDao() {
		return codigoTerritorialDao;
	}

	public void setCodigoTerritorialDao(
			DAOBase<CodigoTerritorialDTO, String> codigoTerritorialDao) {
		this.codigoTerritorialDao = codigoTerritorialDao;
	}

	public void setUnidadAdministrativaDao(DAOBase<UnidadAdministrativaDTO, String> unidadAdministrativaDao) {
		this.unidadAdministrativaDao = unidadAdministrativaDao;
	}

	public DAOBase<UnidadAdministrativaDTO, String> getUnidadAdministrativaDao() {
		return unidadAdministrativaDao;
	}

	public List<UnidadAdministrativaDTO> findByCodter(String coCodigoTerritorial) {
		List<CodterrUniadmDTO> listaCodterUniadm = dao.findFiltered("codigoTerritorialDTO", new CodigoTerritorialDTO(coCodigoTerritorial));
		List<UnidadAdministrativaDTO> listaUnidadesAdministrativas = new ArrayList<UnidadAdministrativaDTO>();
		for (Iterator<CodterrUniadmDTO> i = listaCodterUniadm.iterator(); i.hasNext();) {
			CodterrUniadmDTO uniadmCodter = i.next();
			UnidadAdministrativaDTO uniadm = unidadAdministrativaDao.findById(uniadmCodter.getId().getCoUnidadAdministrativa());
			listaUnidadesAdministrativas.add(uniadm);
		}
		return listaUnidadesAdministrativas;
	}

	public List<CodigoTerritorialDTO> findByUniadm(String coUnidadAdministrativa) {
		List<CodterrUniadmDTO> listaCodterUniadm = dao.findFiltered("unidadAdministrativaDTO", new UnidadAdministrativaDTO(coUnidadAdministrativa), "id.coCodigoTerritorial", 1);
		List<CodigoTerritorialDTO> listaCodigosTerritoriales = new ArrayList<CodigoTerritorialDTO>();
		for (Iterator<CodterrUniadmDTO> i = listaCodterUniadm.iterator(); i.hasNext();) {
			CodterrUniadmDTO uniadmCodter = i.next();
			CodigoTerritorialDTO codter = codigoTerritorialDao.findById(uniadmCodter.getId().getCoCodigoTerritorial());
			listaCodigosTerritoriales.add(codter);
		}
		return listaCodigosTerritoriales;
	}

	public void auditorias(CodterrUniadmDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
