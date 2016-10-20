package es.dipucadiz.etir.comun.bo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

import es.dipucadiz.etir.comun.bo.ExtraccionBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.QueryName;
import es.dipucadiz.etir.comun.dto.ExtraccionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;


public class ExtraccionBOImpl extends
        AbstractGenericBOImpl<ExtraccionDTO, String> implements ExtraccionBO {
	
	private DAOBase<ExtraccionDTO, String> dao;
	
	private static final Log LOG = LogFactory.getLog(ExtraccionBOImpl.class);
	
	public DAOBase<ExtraccionDTO, String> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<ExtraccionDTO, String> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}	
	
	public void auditorias(ExtraccionDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		//transientObject.setFhActualizacion(Utilidades.getDateActual());
		//como es una tabla cabecera, se graba lo que venga. Si viene null, entonces si meto la fecha actual
		if(transientObject.getFhActualizacion()==null){
			transientObject.setFhActualizacion(Utilidades.getDateActual());
		}
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}
	
	//TODO este lo dejo para que no pete cargos, pero debe desaparecer
	public List<ExtraccionDTO> findEdtructuraParametrosYGenerico(
	        final String codMunicipio, final String codModelo) throws GadirServiceException {
		try {
			final Map<String, Object> params = new HashMap<String, Object>(2);
			params.put("coModelo", codModelo);
			params.put("coMunicipio", codMunicipio);

			final List<ExtraccionDTO> listaEstructuras = this.findByNamedQuery(
					QueryName.ENV_GEN_OBTENER_ESTRUCTURA, params);
			for (int i = 0; i < listaEstructuras.size(); i++) {
				Hibernate.initialize(listaEstructuras.get(i)
				        .getModeloVersionDTOByVermodExtraDatosDestino());
				Hibernate.initialize(listaEstructuras.get(i)
				        .getMunicipioDTO());
			}
			return listaEstructuras;
		} catch (final GadirServiceException ge) {
			throw ge;
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error obtener las Estructuras de Salida.", e);
		}
	}
	
	public List<Object[]> findCompatibles(final String coCodigoTerritorialGenerico) throws GadirServiceException{
		String sql="select * from ga_extraccion where control_territorial.isCodterCompatible('"+coCodigoTerritorialGenerico+"', ga_extraccion.co_codigo_territorial)=1";
		
		return (List<Object[]>)dao.ejecutaSQLQuerySelect(sql);
	}
	
	public List<Object[]> findCompatiblesNoExpertos(final String coCodigoTerritorialGenerico) throws GadirServiceException{
		String sql="select * from ga_extraccion where bo_con_tipos_registro = 0 and " +
				"co_modelo_destino is null and co_version_destino is null and " +
				"control_territorial.isCodterCompatible('"+coCodigoTerritorialGenerico+"', ga_extraccion.co_codigo_territorial)=1";
		
		return (List<Object[]>)dao.ejecutaSQLQuerySelect(sql);
	}
	
}