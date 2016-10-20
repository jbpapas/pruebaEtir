/**
 * 
 */
package es.dipucadiz.etir.comun.bo.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.CalleBO;
import es.dipucadiz.etir.comun.bo.DocumentoBO;
import es.dipucadiz.etir.comun.bo.HUnidadUrbanaBO;
import es.dipucadiz.etir.comun.bo.UnidadUrbanaBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.QueryName;
import es.dipucadiz.etir.comun.dto.CalleDTO;
import es.dipucadiz.etir.comun.dto.UnidadUrbanaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.sb07.comun.utilidades.Busqueda;
import es.dipucadiz.etir.sb07.comun.vo.UnidadUrbanaVO;
import es.dipucadiz.etir.sb07.comun.vo.ViasVO;

public class UnidadUrbanaBOImpl extends AbstractGenericBOImpl<UnidadUrbanaDTO, Long> implements UnidadUrbanaBO {

	private static final long serialVersionUID = -7266104555097949470L;
	private static final Log LOG = LogFactory.getLog(UnidadUrbanaBOImpl.class);
	
	private DAOBase<UnidadUrbanaDTO, Long> dao;
	
	private HUnidadUrbanaBO hunidadUrbanaBO;
	
	private CalleBO calleBO;
	private DocumentoBO documentoBO;
	
	

	/**
	 * @return the hunidadUrbanaBO
	 */
	public HUnidadUrbanaBO getHunidadUrbanaBO() {
		return hunidadUrbanaBO;
	}

	/**
	 * @param hunidadUrbanaBO the hunidadUrbanaBO to set
	 */
	public void setHunidadUrbanaBO(HUnidadUrbanaBO hunidadUrbanaBO) {
		this.hunidadUrbanaBO = hunidadUrbanaBO;
	}
	
	

	/**
	 * @return the calleBO
	 */
	public CalleBO getCalleBO() {
		return calleBO;
	}


	/**
	 * @param calleBO the calleBO to set
	 */
	public void setCalleBO(CalleBO calleBO) {
		this.calleBO = calleBO;
	}
	
	
	/**
	 * @return the documentoBO
	 */
	public DocumentoBO getDocumentoBO() {
		return documentoBO;
	}

	/**
	 * @param documentoBO the documentoBO to set
	 */
	public void setDocumentoBO(DocumentoBO documentoBO) {
		this.documentoBO = documentoBO;
	}

	public DAOBase<UnidadUrbanaDTO, Long> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<UnidadUrbanaDTO, Long> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}

	
	public boolean hayEnCalle(CalleDTO calleDTO) throws GadirServiceException{
		boolean resultado = false;
		List<UnidadUrbanaDTO> lista = dao.findFiltered(new String[]{"calleDTO"}, new Object[]{calleDTO}, 0 , 1);
		if (lista!= null && !lista.isEmpty()){
			resultado = true;
		}
		return resultado;
	}
	
	/**
	 * Incluye dependencias (initialize)
	 */
	@SuppressWarnings("unchecked")
	public UnidadUrbanaDTO findUnidadUrbanaByCodigo(final Long coUnidadUrbana)
			throws GadirServiceException {
		List<UnidadUrbanaDTO> unidadUrbana = Collections.EMPTY_LIST;
		UnidadUrbanaDTO resultado = new UnidadUrbanaDTO();
		if (coUnidadUrbana == null) {
			if (log.isDebugEnabled()) {
				log
						.debug("El código de unidadUrbana definido es null, se devuelve una lista vacía.");
			}
		} else {
			try {
				final Map<String, Object> params = new HashMap<String, Object>(
						1);
				params.put("coUnidadUrbana", coUnidadUrbana);

				unidadUrbana = this.getDao().findByNamedQuery(
						QueryName.UNIDAD_URBANA_OBTENER_DATOS, params);

				if (unidadUrbana.size() > 0) {
					resultado = unidadUrbana.get(0);
					if (resultado != null){
						Hibernate.initialize(resultado.getCalleDTO());
						Hibernate.initialize(resultado.getUnidadUrbanaDTO());
					}
					if (resultado.getCalleDTO() != null){
						Hibernate.initialize(resultado.getCalleDTO().getMunicipioDTO());
						Hibernate.initialize(resultado.getCalleDTO().getCalleUbicacionDTO());
					}
					if (resultado.getCalleDTO().getMunicipioDTO() != null){
						Hibernate.initialize(resultado.getCalleDTO().getMunicipioDTO().getProvinciaDTO());	
					}
				}
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
						"Error al obtener el listado de unidades urbanas es:", e);
			}
		}
		return resultado;
	}

	public UnidadUrbanaDTO saveOrFindByDatos(Long coCalle, Integer numero,
			String letra, String escalera, String planta, String puerta,
			String bloque, BigDecimal km, Integer cp) throws GadirServiceException {
	
		
		//List<UnidadUrbanaDTO> listaUnidadUrbana = dao.findFiltered(new String[]{"calleDTO.coCalle", "numero", "letra", "escalera", "planta", "puerta", "bloque", "km"}, 
		//		new Object[]{coCalle, numero, letra, escalera, planta, puerta, bloque, km});
		DetachedCriteria criteria = DetachedCriteria.forClass(UnidadUrbanaDTO.class);
		criteria.add(Restrictions.eq("calleDTO.coCalle", coCalle));
		criteria.add(Utilidades.isEmpty(numero) ? Restrictions.isNull("numero") : Restrictions.eq("numero", numero));
		criteria.add(Utilidades.isEmpty(letra) ? Restrictions.isNull("letra") : Restrictions.eq("letra", letra));
		criteria.add(Utilidades.isEmpty(escalera) ? Restrictions.isNull("escalera") : Restrictions.eq("escalera", escalera));
		criteria.add(Utilidades.isEmpty(planta) ? Restrictions.isNull("planta") : Restrictions.eq("planta", planta));
		criteria.add(Utilidades.isEmpty(puerta) ? Restrictions.isNull("puerta") : Restrictions.eq("puerta", puerta));
		criteria.add(Utilidades.isEmpty(bloque) ? Restrictions.isNull("bloque") : Restrictions.eq("bloque", bloque));
		criteria.add(Utilidades.isNull(km) ? Restrictions.isNull("km") : Restrictions.eq("km", km));

		List<UnidadUrbanaDTO> listaUnidadUrbana = dao.findByCriteria(criteria);
		if(listaUnidadUrbana.isEmpty()){
			UnidadUrbanaDTO unidadUrbana = new UnidadUrbanaDTO();	
			CalleDTO calleDTO = new CalleDTO();
			calleDTO.setCoCalle(coCalle);
			unidadUrbana.setCalleDTO(calleDTO);
			unidadUrbana.setNumero(numero);
			unidadUrbana.setLetra(letra);
			unidadUrbana.setEscalera(escalera);
			unidadUrbana.setPlanta(planta);
			unidadUrbana.setPuerta(puerta);
			unidadUrbana.setBloque(bloque);
			unidadUrbana.setKm(km);
			unidadUrbana.setCp(cp);
			unidadUrbana.setFhActualizacion(new Date());
			unidadUrbana.setCoUsuarioActualizacion(DatosSesion.getLogin());
			this.save(unidadUrbana);
			
			return unidadUrbana;
		} else {
			UnidadUrbanaDTO unidadUrbana = listaUnidadUrbana.get(0);
			if (!cp.equals(unidadUrbana.getCp())) {
				unidadUrbana.setCp(cp);
				this.save(unidadUrbana);
			}
			return unidadUrbana;
		}
	}
	
	public List<UnidadUrbanaDTO> findUnidadUrbanaCallejero(final UnidadUrbanaDTO filtro) throws GadirServiceException{
		List<UnidadUrbanaDTO> result= new ArrayList<UnidadUrbanaDTO>();
		String sql = "from UnidadUrbanaDTO uu " +
						"left join fetch uu.calleDTO " +
						"left join fetch uu.calleDTO.municipioDTO " +
						"left join fetch uu.calleDTO.municipioDTO.provinciaDTO " +
						"left join fetch uu.calleDTO.calleUbicacionDTO " +
					"where 1 = 1";
		
		try {
			if (Utilidades.isNotNull(filtro.getCalleDTO().getCoCalle())){
				sql+= " and uu.calleDTO.coCalle = " + filtro.getCalleDTO().getCoCalle() + "";
			}
			if (filtro.getNumero() != null){
				sql+= " and uu.numero = "+filtro.getNumero();
			}
			else {
				sql+= " and uu.numero is null";
			}
			if (Utilidades.isNotEmpty(filtro.getLetra())){
				sql+= " and uu.letra = '"+filtro.getLetra() + "'";
			}
			else {
				sql+= " and uu.letra is null";
			}
			if (Utilidades.isNotEmpty(filtro.getEscalera())){
				sql+= " and uu.escalera = '"+filtro.getEscalera() + "'";
			}
			else {
				sql+= " and uu.escalera is null";
			}
			if (Utilidades.isNotEmpty(filtro.getPlanta())){
				sql+= " and uu.planta = '"+filtro.getPlanta() + "'";
			}
			else {
				sql+= " and uu.planta is null";
			}
			if (filtro.getKm() != null){
				sql+= " and uu.km = "+filtro.getKm();
			}
			else {
				sql+= " and uu.km is null";
			}
			if (Utilidades.isNotEmpty(filtro.getPuerta())){
				sql+= " and uu.puerta = '"+filtro.getPuerta() + "'";
			}
			else {
				sql+= " and uu.puerta is null";
			}
			if (Utilidades.isNotEmpty(filtro.getBloque())){
				sql+= " and uu.bloque = '"+filtro.getBloque() + "'";
			}
			else {
				sql+= " and uu.bloque is null";
			}
			if (filtro.getCp() != null){
				sql+= " and uu.cp = "+filtro.getCp();
			}
			else {
				sql+= " and uu.cp is null";
			}
		
			result = this.getDao().findByQuery(sql);
		} catch (final Exception e) {
			throw new GadirServiceException(
					"Ocurrio un error al obtener los unidades urbanas a partir del filtro dado", e);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<UnidadUrbanaVO> buscarUnidadesUrbanasVO(final UnidadUrbanaVO unidad, boolean asociados,Integer pagina, Integer elementos) throws GadirServiceException{
		List<UnidadUrbanaVO> result= new ArrayList<UnidadUrbanaVO>();
		String sql = "select * from (select rownum as r, x.* from (select uu.co_unidad_urbana, " +
							"uu.NUMERO," +
						    "uu.LETRA, " +
						    "uu.ESCALERA, " +
						    "uu.PLANTA, " +
						    "uu.KM, " +
						    "uu.puerta, " +
						    "uu.BLOQUE, " +
						    "uu.cp, " +
						    "uu.REF_CATASTRAL, " +
						    "uu.PROCEDENCIA, " +
						    "ca.sigla as aliasSigla, " +
						    "ca.nombre_calle as nombreCalle, " +
						    "cu.ubicacion, " +
						    "mun.co_municipio, " +
						    "mun.nombre as nombreMunicipio, " +
						    "pr.co_provincia, " +
						    "pr.nombre as nombreProvincia, " +
						    "uu.co_unidad_urbana_asociada as asociada " +
							"from ga_unidad_urbana uu left outer join ga_calle ca on uu.CO_CALLE = ca.co_calle " +
						     	  "left outer join ga_municipio mun on mun.co_municipio = ca.co_municipio and mun.co_provincia = ca.co_provincia " +
						     	  "left outer join ga_provincia pr on pr.co_provincia = ca.co_provincia " +
						     	  "left outer join ga_calle_ubicacion cu  on cu.co_calle_ubicacion = ca.co_calle_ubicacion " +
						     	  "where 1 = 1 ";
		

			if (unidad.getCoUnidadUrbana() != null){
				sql+= " and uu.co_unidad_urbana = "+unidad.getCoUnidadUrbana();
			}
			if (Utilidades.isNotEmpty(unidad.getBloque())){
				sql+= " and uu.bloque = '"+unidad.getBloque() + "'";
			}
			if (Utilidades.isNotEmpty(unidad.getEscalera())){
				sql+= " and uu.escalera = '"+unidad.getEscalera() + "'";
			}
			if (Utilidades.isNotEmpty(unidad.getLetra())){
				sql+= " and uu.letra = '"+unidad.getLetra() + "'";
			}
			if (Utilidades.isNotEmpty(unidad.getPlanta())){
				sql+= " and uu.planta = '"+unidad.getPlanta() + "'";
			}
			if (unidad.getKm() != null){
				sql+= " and uu.km = "+unidad.getKm();
			}
			if (Utilidades.isNotEmpty(unidad.getPuerta())){
				sql+= " and uu.puerta = '"+unidad.getPuerta() + "'";
			}
			if (Utilidades.isNotEmpty(unidad.getRefCatastral())){
				sql+= " and uu.ref_catastral = '"+unidad.getRefCatastral().toUpperCase() + "'";
			}
			if (unidad.getNumero() != null){
				sql+= " and uu.numero = "+unidad.getNumero();
			} 
			if (Utilidades.isNotEmpty(unidad.getNombreCalle())){
					sql+= " and "+ Busqueda.getParametroTokenizado("ca.nombre_calle ", unidad.getNombreCalle().toUpperCase(), "contiene");
			}
			if (Utilidades.isNotEmpty(unidad.getSigla())){
				sql+= " and ca.sigla = '"+ unidad.getSigla() + "'";
			}
			if (Utilidades.isNotEmpty(unidad.getCoMunicipio())){
						sql+= " and mun.co_municipio = '"+unidad.getCoMunicipio() + "'";
			}
			if (Utilidades.isNotEmpty(unidad.getCoProvincia())){
						sql+= " and pr.co_provincia = '"+unidad.getCoProvincia() + "'";		
			}		
			if (!asociados){
				sql+= " and uu.CO_UNIDAD_URBANA_ASOCIADA is null";		
			}
			Integer inicio = pagina - (elementos-1);
			sql = sql
//					+ " order by uu.co_unidad_urbana)x) where r between "
					+ " order by nombreProvincia, nombreMunicipio, aliasSigla, nombreCalle )x) where r between "
					+ inicio + " and " + pagina;
		
		List<Object[]> lista = (List<Object[]>)this.getDao().ejecutaSQLQuerySelect(sql);
	    for (Object[] objeto : lista) {
	    	UnidadUrbanaVO unidadurbana = new UnidadUrbanaVO();
	    	unidadurbana.setCoUnidadUrbana(null != objeto[1] ? new Long(objeto[1].toString()) : null);
	    	unidadurbana.setNumero(null != objeto[2] ? (new Integer(objeto[2].toString())) : null);
	    	unidadurbana.setLetra(null != objeto[3] ? (String)objeto[3] : null);
	    	unidadurbana.setEscalera(null != objeto[4] ? (String)objeto[4] : null);
	    	unidadurbana.setPlanta(null != objeto[5] ? (String)objeto[5] : null);
	    	unidadurbana.setKm(null != objeto[6] ? (BigDecimal)objeto[6] : null);
	    	unidadurbana.setPuerta(null != objeto[7] ? (String)objeto[7] : null);
	    	unidadurbana.setBloque(null != objeto[8] ? (String)objeto[8] : null);
	    	unidadurbana.setCp(null != objeto[9] ? new Integer(objeto[9].toString()) : null);
	    	unidadurbana.setRefCatastral(null != objeto[10] ? (String)objeto[10] : null);
	    	unidadurbana.setProcedencia(null != objeto[11] ? (String)objeto[11] : null);
	    	unidadurbana.setSigla(null != objeto[12] ? (String)objeto[12] : null);
	    	unidadurbana.setNombreCalle(null != objeto[13] ? (String)objeto[13] : null);
	    	unidadurbana.setUbicacion(null != objeto[14] ? (String)objeto[14] : null);
	    	unidadurbana.setCoMunicipio(null != objeto[15] ? (String)objeto[15] : null);
	    	unidadurbana.setNombreMunicipio(null != objeto[16] ? (String)objeto[16] : null);
	    	unidadurbana.setCoProvincia(null != objeto[17] ? (String)objeto[17] : null);
	    	unidadurbana.setNombreProvincia(null != objeto[18] ? (String)objeto[18] : null);
	    	unidadurbana.setCoUnidadUrbanaAsociada(null != objeto[19] ? new Long(objeto[19].toString()) : null);
	    	unidadurbana.setTieneAsociada(null != objeto[19] ? "Sí" : "No");
	    	
	    	result.add(unidadurbana);  	
	    }
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Integer contadorUnidadesUrbanasVO(final UnidadUrbanaVO unidad, boolean asociados) throws GadirServiceException{
		String sql = "select count(*) from (select rownum as r,uu.co_unidad_urbana, " +
							"uu.NUMERO," +
						    "uu.LETRA, " +
						    "uu.ESCALERA, " +
						    "uu.PLANTA, " +
						    "uu.KM, " +
						    "uu.puerta, " +
						    "uu.BLOQUE, " +
						    "uu.cp, " +
						    "uu.REF_CATASTRAL, " +
						    "uu.PROCEDENCIA, " +
						    "ca.sigla, " +
						    "ca.nombre_calle, " +
						    "cu.ubicacion, " +
						    "mun.co_municipio, " +
						    "mun.nombre, " +
						    "pr.co_provincia, " +
						    "pr.nombre as nombreProvincia " +
							"from ga_unidad_urbana uu left outer join ga_calle ca on uu.CO_CALLE = ca.co_calle " +
						     	  "left outer join ga_municipio mun on mun.co_municipio = ca.co_municipio and mun.co_provincia = ca.co_provincia " +
						     	  "left outer join ga_provincia pr on pr.co_provincia = ca.co_provincia " +
						     	  "left outer join ga_calle_ubicacion cu  on cu.co_calle_ubicacion = ca.co_calle_ubicacion " +
						     	  "where 1 = 1 ";
		

			if (unidad.getCoUnidadUrbana() != null){
				sql+= " and uu.co_unidad_urbana = "+unidad.getCoUnidadUrbana();
			}
			if (Utilidades.isNotEmpty(unidad.getBloque())){
				sql+= " and uu.bloque = '"+unidad.getBloque() + "'";
			}
			if (Utilidades.isNotEmpty(unidad.getEscalera())){
				sql+= " and uu.escalera = '"+unidad.getEscalera() + "'";
			}
			if (Utilidades.isNotEmpty(unidad.getLetra())){
				sql+= " and uu.letra = '"+unidad.getLetra() + "'";
			}
			if (Utilidades.isNotEmpty(unidad.getPlanta())){
				sql+= " and uu.planta = '"+unidad.getPlanta() + "'";
			}
			if (unidad.getKm() != null){
				sql+= " and uu.km = "+unidad.getKm();
			}
			if (Utilidades.isNotEmpty(unidad.getPuerta())){
				sql+= " and uu.puerta = '"+unidad.getPuerta() + "'";
			}
			if (Utilidades.isNotEmpty(unidad.getRefCatastral())){
				sql+= " and uu.ref_catastral = '"+unidad.getRefCatastral() + "'";
			}
			if (unidad.getNumero() != null){
				sql+= " and uu.numero = "+unidad.getNumero();
			} 
			if (Utilidades.isNotEmpty(unidad.getNombreCalle())){
				sql+= " and "+ Busqueda.getParametroTokenizado("ca.nombre_calle ", unidad.getNombreCalle().toUpperCase(), "contiene");
			}
			if (Utilidades.isNotEmpty(unidad.getSigla())){
				sql+= " and ca.sigla = '"+ unidad.getSigla() + "'";
			}
			if (Utilidades.isNotEmpty(unidad.getCoMunicipio())){
						sql+= " and mun.co_municipio = '"+unidad.getCoMunicipio() + "'";
			}
			if (Utilidades.isNotEmpty(unidad.getCoProvincia())){
						sql+= " and pr.co_provincia = '"+unidad.getCoProvincia() + "'";		
			}		
			if (!asociados){
				sql+= " and uu.CO_UNIDAD_URBANA_ASOCIADA is null";		
			}
			sql = sql
					+ ")";
		
			List<BigDecimal> result = (List<BigDecimal>) this.getDao().ejecutaSQLQuerySelect(sql);
			Integer numResultados = 0;
			for (BigDecimal objeto : result) {
				numResultados = new Integer(objeto.toString());
			}
			
		return numResultados;
	}
	
	
	@SuppressWarnings("unchecked")
	public boolean tieneRelaciones(final Long coUnidadUrbana) throws GadirServiceException{
		boolean result = false;
		final Map<String, Object> params = new HashMap<String, Object>(
				1);
		params.put("coUnidadUrbana", coUnidadUrbana);

		Object obj = (Object)this.getDao().findByNamedQuery(
				QueryName.UNIDAD_URBANA_TIENE_DOMICILIOS, params);
		List<BigDecimal> list = (List<BigDecimal>)obj;

			if (list.get(0).intValue() > 0){
				result = true;
			}else{
				Object obj2 = (Object)this.getDao().findByNamedQuery(
						QueryName.UNIDAD_URBANA_ES_UNIDAD_ADMINISTRATIVA, params);
				List<BigDecimal> list2 = (List<BigDecimal>)obj2;
				if (list2.get(0).intValue() > 0){
					result = true;
				}			
			}

		return result;
	}
	@SuppressWarnings("unchecked")
	public boolean tieneAsociacion(final Long coUnidadUrbana) throws GadirServiceException{
		boolean result = false;
		final Map<String, Object> params = new HashMap<String, Object>(
				1);
		params.put("coUnidadUrbana", coUnidadUrbana);

		Object obj = (Object)this.getDao().findByNamedQuery(
				QueryName.UNIDAD_URBANA_TIENE_ASOCIADA, params);
		List<BigDecimal> list = (List<BigDecimal>)obj;

			if (list.get(0).intValue() > 0){
				result = true;
			}

		return result;
	}
	
	public void eliminarUUrbana(final Long coUnidadUrbana) throws GadirServiceException{
		
		UnidadUrbanaDTO unidad = findUnidadUrbanaByCodigo(coUnidadUrbana);
		try {
			//guardamos el historico
			this.hunidadUrbanaBO.guardarHUrbana(unidad, "B");
			//damos de baja la unidad.
			this.delete(coUnidadUrbana);
		
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Error al eliminar la unidad urbana:", e);
		}
			
		
	}
public void modificarUUrbana(final Long coUnidadUrbanaOriginal,UnidadUrbanaDTO unidadUrbanaModificada) throws GadirServiceException{
		
		try {
			UnidadUrbanaDTO original = findUnidadUrbanaByCodigo(coUnidadUrbanaOriginal);
			//guardamos la nueva
			unidadUrbanaModificada.setCoUnidadUrbana(null);
			this.saveOnly(unidadUrbanaModificada);
			hunidadUrbanaBO.guardarHUrbana(unidadUrbanaModificada, "A");
			// a la anterior le asociamos la nueva
			original.setUnidadUrbanaDTO(unidadUrbanaModificada);
			this.save(original);
			hunidadUrbanaBO.guardarHUrbana(original, "M");
			// se hay alguna asociada a la original, se la asociamos a la nueva
			List <UnidadUrbanaDTO> listaAsociadas =  findFiltered("unidadUrbanaDTO", original);
			if (listaAsociadas!= null){
				for (UnidadUrbanaDTO unidad : listaAsociadas){
					unidad.setUnidadUrbanaDTO(unidadUrbanaModificada);
					this.save(unidad);
					hunidadUrbanaBO.guardarHUrbana(unidad, "M");
				}
			}
		
		
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Error al modificar la unidad urbana:", e);
		}
		
		
	}

public void modificarUUrbanaExiste(final Long coUnidadUrbanaOriginal,UnidadUrbanaDTO unidadUrbanaModificadaExistente) throws GadirServiceException{
	
	try {
		UnidadUrbanaDTO original = findUnidadUrbanaByCodigo(coUnidadUrbanaOriginal);
		
		// a la original le asociamos la nueva encontrada
		original.setUnidadUrbanaDTO(unidadUrbanaModificadaExistente);
		this.save(original);
		hunidadUrbanaBO.guardarHUrbana(original, "M");
		// se hay alguna asociada a la original, se la asociamos a la nueva
		List <UnidadUrbanaDTO> listaAsociadas =  findFiltered("unidadUrbanaDTO", original);
		if (listaAsociadas!= null){
			for (UnidadUrbanaDTO unidad : listaAsociadas){
				unidad.setUnidadUrbanaDTO(unidadUrbanaModificadaExistente);
				this.save(unidad);
				hunidadUrbanaBO.guardarHUrbana(unidad, "M");
			}
		}
	
	
	} catch (final Exception e) {
		log.error(e.getCause(), e);
		throw new GadirServiceException(
				"Error al modificar la unidad urbana:", e);
	}
}
	
	public void modificarUUrbanaExisteConModifDocumentos(final Long coUnidadUrbanaOriginal,UnidadUrbanaDTO unidadUrbanaModificadaExistente) throws GadirServiceException{
		
		try {
			modificarUUrbanaExiste(coUnidadUrbanaOriginal, unidadUrbanaModificadaExistente);
			//documentoBO.modificaRefCatastral(coUnidadUrbanaOriginal, unidadUrbanaModificadaExistente.getRefCatastral());
				
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Error al modificar la unidad urbana:", e);
		}
	
}
	
	
	
	@SuppressWarnings("unchecked")
	public Integer findByParamsPorMunicipio(String coProvincia,String coMunicipio,String codSigla, String nombreVia, Integer pagina,
	        List<ViasVO> vias, Integer limite)
	        throws GadirServiceException {

		Integer totalPaginas = 0;
		String sqlQuery = "";
		
		if(Utilidades.isEmpty(codSigla)){
			sqlQuery = "select * from (select rownum as r, x.* from (select cl.co_calle, cl.nombre_calle, ur.numero, ur.bloque, ur.letra, ur.escalera, ur.planta"
				+ ", ur.km, ur.puerta, tp.cp, ub.ubicacion, cl.sigla, md.cp_generico "
		        + "from ga_calle cl left outer join ga_unidad_urbana ur on cl.co_calle = ur.co_calle "
		        + " left outer join ga_calle_ubicacion ub on ub.co_calle_ubicacion = cl.co_calle_ubicacion "
		        + " left outer join ga_calle_tramo_postal tp on tp.co_calle = cl.co_calle and ur.numero >= tp.desde  and ur.numero <= tp.hasta "
		        + " left outer join ga_municipio_datos md on md.co_provincia = cl.co_provincia and md.co_municipio = cl.co_municipio "
		        + "where cl.co_Municipio = '"
		        + coMunicipio + "'" 
		        + " and cl.co_provincia = '"
		        + coProvincia + "'";
		}
		else{
			sqlQuery = "select * from (select rownum as r, x.* from (select cl.co_calle, cl.nombre_calle, ur.numero, ur.bloque, ur.letra, ur.escalera, ur.planta"
				+ ", ur.km, ur.puerta, tp.cp, ub.ubicacion, cl.sigla, md.cp_generico "
		        + "from ga_calle cl left outer join ga_unidad_urbana ur on cl.co_calle = ur.co_calle "
		        + " left outer join ga_calle_ubicacion ub on ub.co_calle_ubicacion = cl.co_calle_ubicacion "
		        + " left outer join ga_calle_tramo_postal tp on tp.co_calle = cl.co_calle and ur.numero >= tp.desde  and ur.numero <= tp.hasta "
		        + " left outer join ga_municipio_datos md on md.co_provincia = cl.co_provincia and md.co_municipio = cl.co_municipio "	
		        + "where cl.co_Municipio = '"
		        + coMunicipio + "' and cl.sigla = '" + codSigla+"'"
		        + " and cl.co_provincia = '"
		        + coProvincia + "'";
		}
		if(Utilidades.isNotEmpty(nombreVia))
		{
			sqlQuery += " and cl.nombre_calle like UPPER('" + nombreVia + "%')";
		}
		
		try {
			Integer inicio = pagina - (limite - 1);
			sqlQuery = sqlQuery
			        + " order by cl.nombre_calle,ur.numero)x) where r between "
			        + inicio + " and " + pagina;
			List<Object[]> result = (List<Object[]>) this.getDao()
			        .ejecutaSQLQuerySelect(sqlQuery);
			for (Object[] objeto : result) {
				ViasVO cli = new ViasVO();
				if (objeto[1] != null) {
					cli.setCoCalle(new Long(objeto[1].toString()));
				}
				cli.setNombreVia((((String) objeto[2] != null) ? (String) objeto[2]: ""));
				cli.setNumero(((objeto[3] != null) ? new Integer(((BigDecimal) objeto[3]).toString()): new Integer(0)));
				cli.setBloque((((String) objeto[4] != null) ? (String) objeto[4]: ""));
				cli.setLetra((((String) objeto[5] != null) ? (String) objeto[5]: ""));
				cli.setEscalera((((String) objeto[6] != null) ? (String) objeto[6]: ""));
				cli.setPlanta((((String) objeto[7] != null) ? (String) objeto[7]: ""));
				cli.setKm((((BigDecimal) objeto[8] != null) ? (BigDecimal) objeto[8]: new BigDecimal(0)));
				cli.setPuerta((((String) objeto[9] != null) ? (String) objeto[9]: ""));
				if(objeto[10] != null){
					cli.setCp((((BigDecimal) objeto[10] != null) ? new Integer(((BigDecimal) objeto[10]).toString()): new Integer(0)));
				}
				else{
					cli.setCp((((BigDecimal) objeto[13] != null) ? new Integer(((BigDecimal) objeto[13]).toString()): null));
				}
				cli.setUbicacion((((String) objeto[11] != null) ? (String) objeto[11]: ""));
				cli.setSigla((((String) objeto[12] != null) ? (String) objeto[12]: ""));
				vias.add(cli);
			}
			
			if(Utilidades.isEmpty(codSigla)){
				sqlQuery = "select count(*) " + "from ga_calle cl left outer join ga_unidad_urbana ur on cl.co_calle = ur.co_calle "
			        + " left outer join ga_calle_ubicacion ub on ub.co_calle_ubicacion = cl.co_calle_ubicacion " 
		        + "where cl.co_Municipio = '"
		        + coMunicipio + "'";
			}else{
				sqlQuery = "select count(*) " + "from ga_calle cl left outer join ga_unidad_urbana ur on cl.co_calle = ur.co_calle "
		        + " left outer join ga_calle_ubicacion ub on ub.co_calle_ubicacion = cl.co_calle_ubicacion " 
		        + "where cl.co_Municipio = '"
	        	+ coMunicipio + "' and cl.sigla = '" + codSigla+"'";
			}
			if(Utilidades.isNotEmpty(nombreVia))
			{
				sqlQuery += " and cl.nombre_calle like UPPER('" + nombreVia + "%')";
			}
			
			List<BigDecimal> resultado = (List<BigDecimal>) this.getDao()
			        .ejecutaSQLQuerySelect(sqlQuery);
			for (BigDecimal objeto : resultado) {
				totalPaginas = new Integer(objeto.toString());
			}

		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Error al obtener el listado de clientes.", e);
		}

		return totalPaginas;
	}
	
	
	public UnidadUrbanaDTO buscaUUrbanaIdentica(UnidadUrbanaDTO unidadUrbana) throws GadirServiceException{
		
		
		UnidadUrbanaDTO result = null;
		if (unidadUrbana == null) {
			if (log.isDebugEnabled()) {
				log
						.debug("La unidadUrbana definido es null, se devuelve null.");
			}
		} else {
			try {				
				String hql = "	from UnidadUrbanaDTO uu where 1=1";

				
				if (unidadUrbana.getCalleDTO() != null){
					if (Utilidades.isNotEmpty(unidadUrbana.getCalleDTO().getSigla())){
						hql+=" and uu.calleDTO.sigla = '"+unidadUrbana.getCalleDTO().getSigla() + "'";
					}else{
						hql+=" and uu.calleDTO.sigla is null";
					}
					if (Utilidades.isNotEmpty(unidadUrbana.getCalleDTO().getNombreCalle())){
						hql+=" and uu.calleDTO.nombreCalle = '"+unidadUrbana.getCalleDTO().getNombreCalle() + "'";
					}else{
						hql+=" and uu.calleDTO.nombreCalle is null";
					}
					if (Utilidades.isNotEmpty(unidadUrbana.getCalleDTO().getMunicipioDTO().getId().getCoMunicipio())){
						hql+=" and uu.calleDTO.municipioDTO.id.coMunicipio = '"+unidadUrbana.getCalleDTO().getMunicipioDTO().getId().getCoMunicipio() + "'";
					}else{
						hql+=" and uu.calleDTO.municipioDTO.id.coMunicipio is null";
					}
					if (Utilidades.isNotEmpty(unidadUrbana.getCalleDTO().getMunicipioDTO().getId().getCoProvincia())){
						hql+=" and uu.calleDTO.municipioDTO.id.coProvincia = '"+unidadUrbana.getCalleDTO().getMunicipioDTO().getId().getCoProvincia() + "'";
					}else{
						hql+=" and uu.calleDTO.municipioDTO.id.coProvincia is null";
					}
				}else {
					hql+=" and uu.calleDTO is null";
				}
				if (!Utilidades.isEmpty(unidadUrbana.getNumero())){
					hql+=" and uu.numero = '"+unidadUrbana.getNumero()+ "'";
				}else{
					hql+=" and uu.numero is null";
				}
				if (Utilidades.isNotEmpty(unidadUrbana.getLetra())){
					hql+=" and uu.letra = '"+unidadUrbana.getLetra() + "'";
				}else{
					hql+=" and uu.letra is null";
				}
				if (Utilidades.isNotEmpty(unidadUrbana.getEscalera())){
					hql+=" and uu.escalera = '"+unidadUrbana.getEscalera() + "'";
				}else{
					hql+=" and uu.escalera is null";
				}
				if (Utilidades.isNotEmpty(unidadUrbana.getPlanta())){
					hql+=" and uu.planta = '"+unidadUrbana.getPlanta() + "'";
				}else{
					hql+=" and uu.planta is null";
				}
				if (unidadUrbana.getKm() != null){
					hql+=" and uu.km = '"+unidadUrbana.getKm()+ "'";
				}else{
					hql+=" and uu.km is null";
				}
				if (Utilidades.isNotEmpty(unidadUrbana.getPuerta())){
					hql+=" and uu.puerta = '"+unidadUrbana.getPuerta() + "'";
				}else{
					hql+=" and uu.puerta is null";
				}
				if (Utilidades.isNotEmpty(unidadUrbana.getBloque())){
					hql+=" and uu.bloque = '"+unidadUrbana.getBloque() + "'";
				}else{
					hql+=" and uu.bloque is null";
				}
				if (Utilidades.isNotEmpty(unidadUrbana.getRefCatastral())){
					hql+=" and uu.refCatastral = '"+unidadUrbana.getRefCatastral() + "'";
				}else{
					hql+=" and uu.refCatastral is null";
				}
				if (Utilidades.isNotEmpty(unidadUrbana.getProcedencia())){
					hql+=" and uu.procedencia = '"+unidadUrbana.getProcedencia() + "'";
				}else{
					hql+=" and uu.procedencia is null";
				}
				List<UnidadUrbanaDTO> listaUnidadUrbana = this.getDao().findByQuery(hql);
					
				if (listaUnidadUrbana.size() >0){
					result = listaUnidadUrbana.get(0);
				}
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
						"Error al obtener el listado de unidades urbanas es:", e);
			}
		}
		return result;
		
		
			
		
	}
	
	public void modificarUUrbanaExisteConModifDocumentosYRefCatastral(final Long coUnidadUrbanaOriginal, final Long coUnidadUrbanaDestino) throws GadirServiceException {
		try {
			
			UnidadUrbanaDTO destino = findUnidadUrbanaByCodigo(coUnidadUrbanaDestino);
			UnidadUrbanaDTO original = findUnidadUrbanaByCodigo(coUnidadUrbanaOriginal);
			
			//modificamos la unidad urbaba de la destino con la ref catastral de la de origen
			destino.setRefCatastral(original.getRefCatastral());
			this.save(destino);
			hunidadUrbanaBO.guardarHUrbana(destino, "M");
			
			//asociamos la origen a la destino

			modificarUUrbanaExiste(coUnidadUrbanaOriginal, destino);
			//modificamos todas las ref catastrales de los documentos de origen y destino con la ref catastral de origen
			
			//documentoBO.modificaRefCatastral(coUnidadUrbanaOriginal, original.getRefCatastral());
			//documentoBO.modificaRefCatastral(coUnidadUrbanaDestino, original.getRefCatastral());
				
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Error al modificar la unidad urbana:", e);
		}
	}
	
	public void asociarUnidadesSinReferencia(Long coOrigen, Long coDestino) throws GadirServiceException{
		try {
			UnidadUrbanaDTO destino = findUnidadUrbanaByCodigo(coDestino);
			UnidadUrbanaDTO original = findUnidadUrbanaByCodigo(coOrigen);
			original.setUnidadUrbanaDTO(destino);
			this.save(original);
			hunidadUrbanaBO.guardarHUrbana(original, "M");
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Error al asociar las unidades urbanas:", e);
		}
	}
	
	public void asociarUnidadesUnaReferencia(Long coOrigen, Long coDestino) throws GadirServiceException{
		try {
			UnidadUrbanaDTO destino = findUnidadUrbanaByCodigo(coDestino);
			UnidadUrbanaDTO original = findUnidadUrbanaByCodigo(coOrigen);
			destino.setRefCatastral(original.getRefCatastral());
			this.saveOnly(destino);
			asociarUnidadesSinReferencia(coOrigen,coDestino);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Error al asociar las unidades urbanas:", e);
		}
	}
	
	
	
	
	public void auditorias(UnidadUrbanaDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
