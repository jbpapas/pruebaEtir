/**
 * 
 */
package es.dipucadiz.etir.comun.bo.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.ProvinciaSinonimoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.ProvinciaDTO;
import es.dipucadiz.etir.comun.dto.ProvinciaSinonimoDTO;
import es.dipucadiz.etir.comun.dto.ProvinciaSinonimoDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class ProvinciaSinonimoBOImpl extends AbstractGenericBOImpl<ProvinciaSinonimoDTO, ProvinciaSinonimoDTOId> implements ProvinciaSinonimoBO {

	private static final Log LOG = LogFactory.getLog(ProvinciaSinonimoBOImpl.class);
	
	private DAOBase<ProvinciaSinonimoDTO, ProvinciaSinonimoDTOId> dao;

	public DAOBase<ProvinciaSinonimoDTO, ProvinciaSinonimoDTOId> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<ProvinciaSinonimoDTO, ProvinciaSinonimoDTOId> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}

	public List<ProvinciaSinonimoDTO> findByProvincia(String provincia) throws GadirServiceException {
		return dao.findFiltered("provinciaDTO", new ProvinciaDTO(provincia, null));
	}
	
	public List<ProvinciaSinonimoDTO> findCodigoByNombre(String nombreProvincia) throws GadirServiceException {
		return findFiltered("id.sinonimo", nombreProvincia);
	}
	
	public void auditorias(ProvinciaSinonimoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
