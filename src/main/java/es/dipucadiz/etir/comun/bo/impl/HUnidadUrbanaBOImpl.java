package es.dipucadiz.etir.comun.bo.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.HUnidadUrbanaBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.QueryName;
import es.dipucadiz.etir.comun.dto.HUnidadUrbanaDTO;
import es.dipucadiz.etir.comun.dto.UnidadUrbanaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.sb07.comun.vo.UnidadUrbanaVO;

public class HUnidadUrbanaBOImpl extends AbstractGenericBOImpl<HUnidadUrbanaDTO, Long> implements HUnidadUrbanaBO {
	
	private static final Log LOG = LogFactory.getLog(HUnidadUrbanaBOImpl.class);
	
	private DAOBase<HUnidadUrbanaDTO, Long> dao;
	
	public DAOBase<HUnidadUrbanaDTO, Long> getDao() {
		return dao;
	}
	
	public void setDao(final DAOBase<HUnidadUrbanaDTO, Long> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}
	
	public List<HUnidadUrbanaDTO> findHUnidadUrbanaByCodigo(final Long coUnidadUrbana) throws GadirServiceException{
		List<HUnidadUrbanaDTO> lista=Collections.EMPTY_LIST;;
		
		if (coUnidadUrbana == null) {
			if (log.isDebugEnabled()) {
				log.debug("El código de unidadUrbana definido es null, se devuelve una lista vacía.");
			}
		} else {
			try {
				final Map<String, Object> params = new HashMap<String, Object>(
						1);
				params.put("coUnidadUrbana", coUnidadUrbana);

				lista = this.getDao().findByNamedQuery(
						QueryName.H_UNIDAD_URBANA_OBTENER_HISTORIAL, params);

				
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
						"Error al obtener el listado de unidades urbanas es:", e);
			}
		}
		
		return lista;
	}
	
	public List<HUnidadUrbanaDTO> findHUnidadUrbanaByCodigoYFecha(final Long coUnidadUrbana, Date filtroFecha ) throws GadirServiceException{
		List<HUnidadUrbanaDTO> lista=Collections.EMPTY_LIST;;
		
		if (coUnidadUrbana == null) {
			if (log.isDebugEnabled()) {
				log.debug("El código de unidadUrbana definido es null, se devuelve una lista vacía.");
			}
		} else {
			try {
				final Map<String, Object> params = new HashMap<String, Object>(
						1);
				params.put("coUnidadUrbana", coUnidadUrbana);
				params.put("fecha", filtroFecha);
				lista = this.getDao().findByNamedQuery(
						QueryName.H_UNIDAD_URBANA_OBTENER_HISTORIAL_FFECHA, params);

				
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
						"Error al obtener el listado de unidades urbanas es:", e);
			}
		}
		
		return lista;
	}
	
	
	public void guardarHUrbana(UnidadUrbanaDTO unidad, String tipoMovimiento) throws GadirServiceException{
		
		HUnidadUrbanaDTO hunidad = new HUnidadUrbanaDTO();
		hunidad.setFhActualizacion(new Date());
		hunidad.setCoUsuarioActualizacion(DatosSesion.getLogin());
		hunidad.setCp(unidad.getCp());
		hunidad.setBloque(unidad.getBloque());
		hunidad.setCoCalle(unidad.getCalleDTO().getCoCalle());
		hunidad.setCoUnidadUrbana(unidad.getCoUnidadUrbana());
		hunidad.setEscalera(unidad.getEscalera());
		hunidad.setKm(unidad.getKm());
		hunidad.setLetra(unidad.getLetra());
		hunidad.setNumero(unidad.getNumero());
		hunidad.setPlanta(unidad.getPlanta());
		hunidad.setProcedencia(unidad.getProcedencia());
		hunidad.setPuerta(unidad.getPuerta());
		hunidad.setRefCatastral(unidad.getRefCatastral());
		hunidad.setHTipoMovimiento(tipoMovimiento);
		if (unidad.getUnidadUrbanaDTO() != null){
			hunidad.setCoUnidadUrbanaAsociada(unidad.getUnidadUrbanaDTO().getCoUnidadUrbana());
		}
		
		try {
			this.saveOnly(hunidad);
			
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Error al registrar el historico:", e);
		}
		
	}
	
				   
				   
	public List<HUnidadUrbanaDTO> buscarHUnidadesUrbanasVO(final UnidadUrbanaVO unidad, Date fecha, String nombre, int inicio, int fin) throws GadirServiceException{
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		List<HUnidadUrbanaDTO> result= new ArrayList<HUnidadUrbanaDTO>();
		if(unidad!=null){
			String sql = "select * from (select ROWNUM AS FILAS, x.* from (select  huu.co_unidad_urbana, " +
								"huu.co_calle, "+
								"huu.NUMERO," +
							    "huu.LETRA, " +
							    "huu.ESCALERA, " +
							    "huu.PLANTA, " +
							    "huu.KM, " +
							    "huu.puerta, " +
							    "huu.BLOQUE, " +
							    "huu.cp, " +
							    "huu.REF_CATASTRAL, " +
							    "huu.PROCEDENCIA, " +
							    "huu.FH_ACTUALIZACION, "+
							    "huu.CO_USUARIO_ACTUALIZACION, "+
							    "huu.H_TIPO_MOVIMIENTO, "+
							    "huu.co_proceso, " +
							    "huu.co_ejecucion, " +
							    "ROWIDTOCHAR(huu.rowid) r "+
//							    ",ROWNUM AS FILAS "+ 
									
					  "from GA_H_UNIDAD_URBANA huu "+
				 	  "left outer join ga_calle ca on huu.CO_CALLE = ca.co_calle "+
					  "left outer join ga_unidad_urbana uu on huu.co_unidad_urbana = uu.co_unidad_urbana "+
					  "left outer join ga_municipio mun on mun.co_municipio = ca.co_municipio and mun.co_provincia = ca.co_provincia "+ 
					  "left outer join ga_provincia pr on pr.co_provincia = ca.co_provincia "+ 
					  "left outer join ga_calle_ubicacion cu  on cu.co_calle_ubicacion = ca.co_calle_ubicacion  "+
					  "WHERE 1 = 1 ";
			
				if (unidad.getCoUnidadUrbana() != null){
					sql+= " and huu.co_unidad_urbana = "+unidad.getCoUnidadUrbana();
				}
				if (Utilidades.isNotEmpty(unidad.getBloque())){
					sql+= " and huu.bloque = '"+unidad.getBloque() + "'";
				}
				if (Utilidades.isNotEmpty(unidad.getEscalera())){
					sql+= " and huu.escalera = '"+unidad.getEscalera() + "'";
				}
				if (Utilidades.isNotEmpty(unidad.getLetra())){
					sql+= " and huu.letra = '"+unidad.getLetra() + "'";
				}
				if (Utilidades.isNotEmpty(unidad.getPlanta())){
					sql+= " and huu.planta = '"+unidad.getPlanta() + "'";
				}
				if (unidad.getKm() != null){
					sql+= " and huu.km = "+unidad.getKm();
				}
				if (Utilidades.isNotEmpty(unidad.getRefCatastral())){
					sql+= " and huu.ref_catastral = '"+unidad.getRefCatastral() + "'";
				}
				if (unidad.getNumero() != null){
					sql+= " and huu.numero = "+unidad.getNumero();
				} 
				if (Utilidades.isNotEmpty(unidad.getNombreCalle())){
					sql+= " and upper(ca.nombre_calle) like upper('%"+unidad.getNombreCalle()+"%')";
//						sql+= " and ca.nombre_calle = upper('"+unidad.getNombreCalle()+"')";
				}
				if (Utilidades.isNotEmpty(unidad.getCoMunicipio())){
							sql+= " and mun.co_municipio = '"+unidad.getCoMunicipio() + "'";
				}
				if (Utilidades.isNotEmpty(unidad.getCoProvincia())){
							sql+= " and pr.co_provincia = '"+unidad.getCoProvincia() + "'";		
				}		
				
				if (fecha != null){
					
					sql+= " and huu.FH_ACTUALIZACION >='"+formato.format(fecha) + " 0:00:00,000000'";		
				}
				if	(Utilidades.isNotEmpty(nombre)){
					sql+= " and huu.co_calle = calle.co_calle and upper(calle.nombre_calle) like upper('%"+nombre+"%')";	
				}
				
//				sql+="order by huu.FH_ACTUALIZACION asc) WHERE FILAS BETWEEN "+inicio+" AND "+fin;
				sql+="order by pr.nombre, mun.nombre,ca.sigla,ca.nombre_calle)x) WHERE FILAS BETWEEN "+inicio+" AND "+fin;
	
			List<Object[]> lista = (List<Object[]>)this.getDao().ejecutaSQLQuerySelect(sql);
		    for (Object[] objeto : lista) {
		    	HUnidadUrbanaDTO unidadurbana = new HUnidadUrbanaDTO();
		    	unidadurbana.setCoUnidadUrbana(null != objeto[1] ? new Long(objeto[1].toString()) : null);
		    	unidadurbana.setCoCalle(null != objeto[2] ? new Long(objeto[2].toString()) : null);
		    	unidadurbana.setNumero(null != objeto[3] ? (new Integer(objeto[3].toString())) : null);
		    	unidadurbana.setLetra(null != objeto[4] ? (String)objeto[4] : null);
		    	unidadurbana.setEscalera(null != objeto[5] ? (String)objeto[5] : null);
		    	unidadurbana.setPlanta(null != objeto[6] ? (String)objeto[6] : null);
		    	unidadurbana.setKm(null != objeto[7] ? (BigDecimal)objeto[7] : null);
		    	unidadurbana.setPuerta(null != objeto[8] ? (String)objeto[8] : null);
		    	unidadurbana.setBloque(null != objeto[9] ? (String)objeto[9] : null);
		    	unidadurbana.setCp(null != objeto[10] ? new Integer(objeto[10].toString()) : null);
		    	unidadurbana.setRefCatastral(null != objeto[11] ? (String)objeto[11] : null);
		    	unidadurbana.setProcedencia(null != objeto[12] ? (String)objeto[12] : null);
		    	unidadurbana.setFhActualizacion(null != objeto[13] ? (Date)objeto[13] : null);
		    	unidadurbana.setCoUsuarioActualizacion(null != objeto[14] ? (String)objeto[14] : null);
		    	unidadurbana.setHTipoMovimiento(null != objeto[15] ? (String)objeto[15] : null);
		    	unidadurbana.setCoProceso(null != objeto[16] ? (String)objeto[16] : null);
		    	unidadurbana.setCoEjecucion(null != objeto[17] ? new Long(objeto[17].toString()) : null);
		    	
		    	
		    	
		    	
		    	unidadurbana.setRowid(null != objeto[18] ? (String)objeto[18] : null);
				   
		    
			    
		    	
		    	result.add(unidadurbana);  	
		    }
		}else{
			result=null;
		}
		return result;
	}
	
	public Integer buscarHUnidadesUrbanasVOCount(final UnidadUrbanaVO unidad, Date fecha, String nombre) throws GadirServiceException{
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		Integer numResultados = 0;
		
			String sql = "select count (*) "+		
					  "from GA_H_UNIDAD_URBANA huu "+
				 	  "left outer join ga_calle ca on huu.CO_CALLE = ca.co_calle "+
					  "left outer join ga_unidad_urbana uu on huu.co_unidad_urbana = uu.co_unidad_urbana "+
					  "left outer join ga_municipio mun on mun.co_municipio = ca.co_municipio and mun.co_provincia = ca.co_provincia "+ 
					  "left outer join ga_provincia pr on pr.co_provincia = ca.co_provincia "+ 
					  "left outer join ga_calle_ubicacion cu  on cu.co_calle_ubicacion = ca.co_calle_ubicacion  "+
					  "WHERE 1 = 1 ";
			
				if (unidad.getCoUnidadUrbana() != null){
					sql+= " and huu.co_unidad_urbana = "+unidad.getCoUnidadUrbana();
				}
				if (Utilidades.isNotEmpty(unidad.getBloque())){
					sql+= " and huu.bloque = '"+unidad.getBloque() + "'";
				}
				if (Utilidades.isNotEmpty(unidad.getEscalera())){
					sql+= " and huu.escalera = '"+unidad.getEscalera() + "'";
				}
				if (Utilidades.isNotEmpty(unidad.getLetra())){
					sql+= " and huu.letra = '"+unidad.getLetra() + "'";
				}
				if (Utilidades.isNotEmpty(unidad.getPlanta())){
					sql+= " and huu.planta = '"+unidad.getPlanta() + "'";
				}
				if (unidad.getKm() != null){
					sql+= " and huu.km = "+unidad.getKm();
				}
				if (Utilidades.isNotEmpty(unidad.getRefCatastral())){
					sql+= " and huu.ref_catastral = '"+unidad.getRefCatastral() + "'";
				}
				if (unidad.getNumero() != null){
					sql+= " and huu.numero = "+unidad.getNumero();
				} 
				if (Utilidades.isNotEmpty(unidad.getNombreCalle())){
						sql+= " and upper(ca.nombre_calle) like upper('%"+unidad.getNombreCalle()+"%')";
				}
				if (Utilidades.isNotEmpty(unidad.getCoMunicipio())){
							sql+= " and mun.co_municipio = '"+unidad.getCoMunicipio() + "'";
				}
				if (Utilidades.isNotEmpty(unidad.getCoProvincia())){
							sql+= " and pr.co_provincia = '"+unidad.getCoProvincia() + "'";		
				}		
				
				if (fecha != null){
					
					sql+= " and huu.FH_ACTUALIZACION >='"+formato.format(fecha) + " 0:00:00,000000'";		
				}
				if	(Utilidades.isNotEmpty(nombre)){
					sql+= " and huu.co_calle = calle.co_calle and upper(calle.nombre_calle) like upper('%"+nombre+"%')";	
				}
				
				List<BigDecimal> result = (List<BigDecimal>) this.getDao().ejecutaSQLQuerySelect(sql);
				
				for (BigDecimal objeto : result) {
					numResultados = new Integer(objeto.toString());
				}
		
			
		return numResultados;
	}
	
	public void auditorias(HUnidadUrbanaDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}

	
}