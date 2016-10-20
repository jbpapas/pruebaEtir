/**
 * 
 */
package es.dipucadiz.etir.comun.bo.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.MunicipioCodterBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.CodigoTerritorialDTO;
import es.dipucadiz.etir.comun.dto.MunicipioCodterDTO;
import es.dipucadiz.etir.comun.dto.MunicipioCodterDTOId;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class MunicipioCodterBOImpl extends AbstractGenericBOImpl<MunicipioCodterDTO, MunicipioCodterDTOId> implements MunicipioCodterBO {

	private static final Log LOG = LogFactory.getLog(MunicipioCodterBOImpl.class);
	
	private DAOBase<MunicipioCodterDTO, MunicipioCodterDTOId> dao;
	private DAOBase<MunicipioDTO, MunicipioDTOId> municipioDao;

	public DAOBase<MunicipioCodterDTO, MunicipioCodterDTOId> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<MunicipioCodterDTO, MunicipioCodterDTOId> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}

	public void setMunicipioDao(DAOBase<MunicipioDTO, MunicipioDTOId> municipioDao) {
		this.municipioDao = municipioDao;
	}

	public DAOBase<MunicipioDTO, MunicipioDTOId> getMunicipioDao() {
		return municipioDao;
	}

	public List<MunicipioDTO> findByCodter(String coCodigoTerritorial) {
		List<MunicipioCodterDTO> listaMunCodter = dao.findFiltered("codigoTerritorialDTO", new CodigoTerritorialDTO(coCodigoTerritorial));
		List<MunicipioDTO> listaMunicipios = new ArrayList<MunicipioDTO>();
		for (java.util.Iterator<MunicipioCodterDTO> i = listaMunCodter.iterator(); i.hasNext();) {
			MunicipioCodterDTO munCodter = i.next();
			MunicipioDTO municipio = municipioDao.findById(munCodter.getMunicipioDTO().getId());
			listaMunicipios.add(municipio);
		}
		return listaMunicipios;
	}

	public MunicipioCodterDTO findById(final String codigoTerritorial, final String provincia, final String municipio) {
		return null;
	}
	
	public void auditorias(MunicipioCodterDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
