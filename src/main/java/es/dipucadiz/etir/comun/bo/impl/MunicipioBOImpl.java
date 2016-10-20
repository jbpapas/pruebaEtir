/**
 * 
 */
package es.dipucadiz.etir.comun.bo.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.MunicipioBO;
import es.dipucadiz.etir.comun.config.ParametrosConfig.ValoresParametrosConfig;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.QueryName.QueryCarga;
import es.dipucadiz.etir.comun.dto.CargaDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTOId;
import es.dipucadiz.etir.comun.dto.ProvinciaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class MunicipioBOImpl extends AbstractGenericBOImpl<MunicipioDTO, MunicipioDTOId> implements MunicipioBO {

	private static final Log LOG = LogFactory.getLog(MunicipioBOImpl.class);
	
	private DAOBase<MunicipioDTO, MunicipioDTOId> dao;
	public DAOBase<MunicipioDTO, MunicipioDTOId> getDao() {
		return dao;
	}
	public void setDao(final DAOBase<MunicipioDTO, MunicipioDTOId> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}

	public List<MunicipioDTO> findByProvincia(String provincia) {
		return dao.findFiltered("provinciaDTO", new ProvinciaDTO(provincia, null));
	}
	

	/**
	 * {@inheritDoc}
	 */
	public List<MunicipioDTO> findMunicipiosByParam(final CargaDTO filtro)
	        throws GadirServiceException {
		if (Utilidades.isNull(filtro)) {
			if (log.isDebugEnabled()) {
				log
				        .debug("El objeto recibido es null, no se persiste nada en el sistema.");
			}
			return Collections.emptyList();
		} else {
			try {
				final Map<String, Object> params = new HashMap<String, Object>();
				params.put("coProvincia", ValoresParametrosConfig.VALOR_PROVINCIA);
				return this.findByNamedQuery(QueryCarga.MUNICIPIOS_FIND_BY_PARAM, params);
			} catch (final GadirServiceException ge) {
				throw ge;
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Ocurrio un error al obtener los municipios.", e);
			}
		}
	}
	
	public void auditorias(MunicipioDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
	


	
	
	
	public List<MunicipioDTO> findMunicipiosByCalle(final Long filtro)
    		throws GadirServiceException {
		List<MunicipioDTO> lista;
		if (Utilidades.isNull(filtro)) {
			if (log.isDebugEnabled()) {
				log
				        .debug("El objeto recibido es null, no se persiste nada en el sistema.");
			}
			return Collections.emptyList();
		} else {
			try {
				final Map<String, Object> params = new HashMap<String, Object>();
				params.put("codCalle", filtro);
				lista= this.findByNamedQuery(QueryCarga.MUNICIPIO_BUSQUEDABYCALLE, params);
				return lista;
			} catch (final GadirServiceException ge) {
				throw ge;
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Ocurrio un error al obtener los municipios.", e);
			}
}
}
	/**
	 * {@inheritDoc}
	 */
	public List<MunicipioDTO> findMunicipiosCallejero(String idProvincia)
	        throws GadirServiceException {
		
			try {
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("id", idProvincia);
				return this.findByNamedQuery("Municipio.findMunicipioCallejeroProvincia", parametros);
			} catch (final GadirServiceException ge) {
				throw ge;
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Ocurrio un error al obtener los municipios con callejero.", e);
			}
		}
	
	/**
	 * {@inheritDoc}
	 */
	public List<MunicipioDTO> findMunicipiosByProvincia(String idProvincia) throws GadirServiceException{
		try {
			return this.findFiltered("id.coProvincia", idProvincia);
		} catch (final GadirServiceException ge) {
			throw ge;
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener los municipios con callejero.", e);
		}
	}
	

	
}
