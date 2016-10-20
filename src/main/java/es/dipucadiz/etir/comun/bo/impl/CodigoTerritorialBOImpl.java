/**
 * 
 */
package es.dipucadiz.etir.comun.bo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.CodigoTerritorialBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.CodigoTerritorialDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.dto.UnidadAdministrativaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.TablaGt;
import es.dipucadiz.etir.comun.utilidades.TablaGtConstants;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class CodigoTerritorialBOImpl extends AbstractGenericBOImpl<CodigoTerritorialDTO, String> implements CodigoTerritorialBO {

	private static final Log LOG = LogFactory.getLog(CodigoTerritorialBOImpl.class);
	
	private DAOBase<CodigoTerritorialDTO, String> dao;

	public DAOBase<CodigoTerritorialDTO, String> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<CodigoTerritorialDTO, String> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}

	public List<String> etiquetasCodigosTerritoriales() throws GadirServiceException {
		final List<CodigoTerritorialDTO> listaCodter = findAll();
		final List<String> etiquetas = new ArrayList<String>();
		
		etiquetas.add("CABEZA");
		etiquetas.add("TITULCÓDIGOS TERRITORIALES");
		etiquetas.add("CUERPO");
		for (final Iterator<CodigoTerritorialDTO> i = listaCodter.iterator(); i.hasNext();) {
			etiquetas.add("LINEA");
			final CodigoTerritorialDTO codigoTerritorial = i.next();
			final String primerNivel = TablaGt.getValor(TablaGtConstants.TABLA_ORGANO_PRIMER_NIVEL, codigoTerritorial.getCoCodigoTerritorial().substring(0, 2), "DESCRIPCION");
			final String ambito = TablaGt.getValor(TablaGtConstants.TABLA_AMBITO_TERRITORIAL, codigoTerritorial.getCoCodigoTerritorial().substring(2, 4), "DESCRIPCION");
			final String territorio = TablaGt.getValor(TablaGtConstants.TABLA_ORGANO_TERCER_NIVEL, codigoTerritorial.getCoCodigoTerritorial().substring(4, 6), "DESCRIPCION");
			etiquetas.add("COTER" + codigoTerritorial.getCoCodigoTerritorial());
			etiquetas.add("NOMBR" + codigoTerritorial.getNombre());
			etiquetas.add("ORGPN" + primerNivel);
			etiquetas.add("AMTER" + ambito);
			etiquetas.add("CODTE" + territorio);
		}
		
		return etiquetas;
	}

	public List<String> etiquetasCodigoTerritorial(String coCodigoTerritorial,
			List<MunicipioDTO> listaMunicipios,
			List<UnidadAdministrativaDTO> listaUnidadesAdministrativas) throws GadirServiceException {
		final List<String> etiquetas = new ArrayList<String>();
		final CodigoTerritorialDTO codter = dao.findById(coCodigoTerritorial);
		
		etiquetas.add("CABEZA");
		etiquetas.add("TITULCÓDIGO TERRITORIAL");
		etiquetas.add("CUERPO");
		etiquetas.add("CO_CT" + coCodigoTerritorial);
		etiquetas.add("DE_CT" + codter.getNombre());
		etiquetas.add("CO_OR" + coCodigoTerritorial.substring(0, 2));
		etiquetas.add("DE_OR" + TablaGt.getValor(TablaGtConstants.TABLA_ORGANO_PRIMER_NIVEL, coCodigoTerritorial.substring(0, 2), "DESCRIPCION"));
		etiquetas.add("CO_AT" + coCodigoTerritorial.substring(2, 4));
		etiquetas.add("DE_AT" + TablaGt.getValor(TablaGtConstants.TABLA_AMBITO_TERRITORIAL, coCodigoTerritorial.substring(2, 4), "DESCRIPCION"));
		etiquetas.add("CO_TE" + coCodigoTerritorial.substring(4, 6));
		etiquetas.add("DE_TE" + TablaGt.getValor(TablaGtConstants.TABLA_ORGANO_TERCER_NIVEL, coCodigoTerritorial.substring(4, 6), "DESCRIPCION"));

		if (listaMunicipios.isEmpty()) {
			etiquetas.add("LINEAMunicipios");
			etiquetas.add("CO_MU");
			etiquetas.add("DE_MU" + "No existen municipios.");
		} else {
			for (Iterator<MunicipioDTO> i = listaMunicipios.iterator(); i.hasNext();) {
				MunicipioDTO mun = i.next();
				etiquetas.add("LINEAMunicipios");
				etiquetas.add("CO_MU" + mun.getId().getCoProvincia() + mun.getId().getCoMunicipio());
				etiquetas.add("DE_MU" + mun.getNombre());
			}
		}
		
		if (listaUnidadesAdministrativas.isEmpty()) {
			etiquetas.add("LINEAUnidades");
			etiquetas.add("CO_UN");
			etiquetas.add("DE_UN" + "No existen unidades administrativas.");
		} else {
			for (Iterator<UnidadAdministrativaDTO> i = listaUnidadesAdministrativas.iterator(); i.hasNext();) {
				UnidadAdministrativaDTO uni = i.next();
				etiquetas.add("LINEAUnidades");
				etiquetas.add("CO_UN" + uni.getCoUnidadAdministrativa());
				etiquetas.add("DE_UN" + uni.getNombre());
				if (uni.getBoPrincipal()!= null && uni.getBoPrincipal() == true)
					etiquetas.add("UN_PR" + "Sí");
				else
					etiquetas.add("UN_PR" + "No");
			}
		}
		
		return etiquetas;
	}

	public List<CodigoTerritorialDTO> findByMunicipio(String provincia, String municipio) {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("coProvincia", provincia);
		parametros.put("coMunicipio", municipio);
		try {
			return findByNamedQuery("CodigoTerritorial.findCodigosTerritorialesDistintosByMunicipio", parametros);
		} catch (Exception e) {
			return null;
		}
	}

	public List<CodigoTerritorialDTO> findByNotMunicipio(String provincia, String municipio) {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("coProvincia", provincia);
		parametros.put("coMunicipio", municipio);
		try {
			return findByNamedQuery("CodigoTerritorial.findCodigosTerritorialesDistintosByNotMunicipio", parametros);
		} catch (Exception e) {
			return null;
		}
	}
	
	public void auditorias(CodigoTerritorialDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
