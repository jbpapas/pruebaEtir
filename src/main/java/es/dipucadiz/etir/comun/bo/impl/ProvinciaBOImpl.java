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

import es.dipucadiz.etir.comun.bo.ProvinciaBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.QueryName;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.dto.ProvinciaDTO;
import es.dipucadiz.etir.comun.dto.ProvinciaSinonimoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.KeyValue;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class ProvinciaBOImpl extends AbstractGenericBOImpl<ProvinciaDTO, String> implements ProvinciaBO {

	private static final Log LOG = LogFactory.getLog(ProvinciaBOImpl.class);
	
	private DAOBase<ProvinciaDTO, String> dao;

	public DAOBase<ProvinciaDTO, String> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<ProvinciaDTO, String> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}

//	public List<ProvinciaDTO> findByProvincia(String provincia) {
//		return dao.findFiltered("provinciaDTO", new ProvinciaDTO(provincia, null));
//	}

	public List<String> etiquetasProvincias() throws GadirServiceException {
		final List<ProvinciaDTO> listaProvincias = findAll();
		final List<String> etiquetas = new ArrayList<String>();
		
		etiquetas.add("CABEZA");
		etiquetas.add("TITULPROVINCIAS");
		etiquetas.add("CUERPO");
		for (final Iterator<ProvinciaDTO> i = listaProvincias.iterator(); i.hasNext();) {
			etiquetas.add("LINEA");
			final ProvinciaDTO provincia = i.next();
			etiquetas.add("CO_PR" + provincia.getCoProvincia());
			etiquetas.add("DE_PR" + provincia.getNombre());
		}

		return etiquetas;
	}

	public List<KeyValue> etiquetasProvincia(String coProvincia, List<MunicipioDTO> listaMunicipios, List<ProvinciaSinonimoDTO> listaProvinciasSinonimos) throws GadirServiceException  {
		final List<KeyValue> etiquetas = new ArrayList<KeyValue>();
		final ProvinciaDTO provincia = dao.findById(coProvincia);
		
		etiquetas.add(new KeyValue("CABEZA"));
		etiquetas.add(new KeyValue("TITUL", "PROVINCIA"));
		etiquetas.add(new KeyValue("CUERPO"));
		etiquetas.add(new KeyValue("CO_PR", coProvincia));
		etiquetas.add(new KeyValue("DE_PR", provincia.getNombre()));

		if(!listaProvinciasSinonimos.isEmpty()) {
			for (Iterator<ProvinciaSinonimoDTO> i = listaProvinciasSinonimos.iterator(); i.hasNext();) {
				ProvinciaSinonimoDTO uni = i.next();
				etiquetas.add(new KeyValue("LINEA", "Sinonimos"));
				etiquetas.add(new KeyValue("DE_SN", uni.getId().getSinonimo()));
			}
		} else {
			etiquetas.add(new KeyValue("LINEA", "Sinonimos"));
			etiquetas.add(new KeyValue("DE_SN", "No existen sinónimos"));
		}

		if(!listaMunicipios.isEmpty()) {
			for (Iterator<MunicipioDTO> i = listaMunicipios.iterator(); i.hasNext();) {
				MunicipioDTO mun = i.next();
				etiquetas.add(new KeyValue("LINEA", "Municipios"));
				etiquetas.add(new KeyValue("CO_MU" + mun.getId().getCoMunicipio()));
				etiquetas.add(new KeyValue("DE_MU" + mun.getNombre()));
			}
		} else {
			etiquetas.add(new KeyValue("LINEA", "Municipios"));
			etiquetas.add(new KeyValue("CO_MU", "No existen municipios"));
			etiquetas.add(new KeyValue("DE_MU", ""));
		}		
		
		return etiquetas;
	}
	public ProvinciaDTO getProvinciaByMunicipio(String rowidMunicipio){
		ProvinciaDTO provincia=null;;
		try {
			final Map<String, Object> params = new HashMap<String, Object>(
					1);
			params.put("rowidMunicipio", rowidMunicipio);
			List<Object[]> result = (List<Object[]>)this.getDao().ejecutaNamedQuerySelect(
					QueryName.PROVINCIA_BY_MUNICIPIO, params);
			
			
			
			for (Object[] objeto : result) {
				
				provincia=new ProvinciaDTO();
	        	provincia.setCoProvincia((null != objeto[0] ? new String(objeto[0].toString()) : null));
	        	provincia.setNombre((null != objeto[1] ? new String(objeto[1].toString()) : null));
//	        	provincia.setFhActualizacion((null != objeto[2] ? new Date(objeto[2].toString()) : null));
//	        	provincia.setCoUsuarioActualizacion((null != objeto[3] ? new String(objeto[3].toString()) : null));
			}
			
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			
		}
		
		return provincia;
	}
	
	public void auditorias(ProvinciaDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
