/**
 * 
 */
package es.dipucadiz.etir.comun.bo.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.MunicipioSinonimoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTOId;
import es.dipucadiz.etir.comun.dto.MunicipioSinonimoDTO;
import es.dipucadiz.etir.comun.dto.MunicipioSinonimoDTOId;
import es.dipucadiz.etir.comun.dto.ProvinciaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class MunicipioSinonimoBOImpl extends AbstractGenericBOImpl<MunicipioSinonimoDTO, MunicipioSinonimoDTOId> implements MunicipioSinonimoBO {

	private static final Log LOG = LogFactory.getLog(MunicipioSinonimoBOImpl.class);
	
	private DAOBase<MunicipioSinonimoDTO, MunicipioSinonimoDTOId> dao;

	public DAOBase<MunicipioSinonimoDTO, MunicipioSinonimoDTOId> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<MunicipioSinonimoDTO, MunicipioSinonimoDTOId> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}

	public List<MunicipioSinonimoDTO> findByMunicipio(String provincia, String municipio) throws GadirServiceException {
		return dao.findFiltered("municipioDTO", new MunicipioDTO(new MunicipioDTOId(provincia, municipio), new ProvinciaDTO(provincia, null), null));
	}
	
	public void auditorias(MunicipioSinonimoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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

	public List<MunicipioSinonimoDTO> findCodigoByProvinciaNombre(String coProvincia, String nombreMunicipio) throws GadirServiceException {
		String[] propNames = {"id.coProvincia", "id.sinonimo"};
		Object[] filters = {coProvincia, nombreMunicipio};
		return findFiltered(propNames, filters);
	}

}
