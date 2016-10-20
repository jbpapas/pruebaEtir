/**
 * 
 */
package es.dipucadiz.etir.comun.bo.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.icu.text.SimpleDateFormat;

import es.dipucadiz.etir.comun.bo.HDomicilioBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.DomicilioDTO;
import es.dipucadiz.etir.comun.dto.HDomicilioDTO;
import es.dipucadiz.etir.comun.dto.UnidadUrbanaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.ControlTerritorial;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.sb07.comun.vo.HGlobalDomiciliosVO;

public class HDomicilioBOImpl extends AbstractGenericBOImpl<HDomicilioDTO, Long> implements HDomicilioBO {

	private static final Log LOG = LogFactory.getLog(HDomicilioBOImpl.class);
	
	private DAOBase<HDomicilioDTO, Long> dao;

	public DAOBase<HDomicilioDTO, Long> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<HDomicilioDTO, Long> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}
	
	@SuppressWarnings("unchecked")
	public List<HDomicilioDTO> findHDomicilioById(Long id) throws GadirServiceException {
		List<HDomicilioDTO> domicilios = new ArrayList<HDomicilioDTO>();
		try {
			final Map<String, Object> params = new HashMap<String, Object>(
			        1);
			params.put("id", id);
			List<Object[]> result = (List<Object[]>) this.getDao()
			        .ejecutaNamedQuerySelect(
			        		"HDomicilio.findById",
			                params);
			for (Object[] objeto : result) {
				HDomicilioDTO cli = new HDomicilioDTO();
				
				cli.setRowid((objeto[0]!=null)? objeto[0].toString():"");
				cli.setCoCliente((objeto[1]!= null)? new Long(objeto[1].toString()):0);
				cli.setCoDomicilio((objeto[2]!= null)? new Long(objeto[2].toString()):0);
				cli.setCoUnidadUrbana((objeto[3]!= null)? new Long(objeto[3].toString()):0);
				cli.setFxAlta((objeto[4]!=null)? (Date) objeto[4]:null);
				cli.setBoFiscalAeat((objeto[5]!=null)? (objeto[5].toString().equalsIgnoreCase("1") ?  true : false):false);
				cli.setBoFiscalMunicipal((objeto[6]!=null)? (objeto[6].toString().equalsIgnoreCase("1") ?  true : false):false);
				cli.setBoNotificacion((objeto[7]!=null)? (objeto[7].toString().equalsIgnoreCase("1") ?  true : false):false);
				cli.setProcedencia((objeto[8]!=null)? (String) objeto[8]:"");
				cli.setFhActualizacion((objeto[9]!=null)? (Date) objeto[9]:null);
				
				if(ControlTerritorial.isUsuarioExperto()){
					cli.setCoUsuarioActualizacion((objeto[10]!= null)? (String)objeto[10].toString():"");
				}
				cli.setHTipoMovimiento((objeto[11]!= null)? (String)objeto[11].toString():"");
				cli.setCoProceso((objeto[12]!= null)? (String)objeto[12].toString():"");
				cli.setCoEjecucion((objeto[13]!= null)? new Long(objeto[13].toString()):0);
				
				domicilios.add(cli);
			}
			return domicilios;
		} catch (final Exception e) {
			throw new GadirServiceException(
			        "Error al obtener el listado de domicilios.", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<HDomicilioDTO> findHDomicilioByIdYFecha(Long id, Date fecha)
			throws GadirServiceException {
		List<HDomicilioDTO> domicilios = new ArrayList<HDomicilioDTO>();
		try {
			final Map<String, Object> params = new HashMap<String, Object>(
			        1);
			params.put("id", id);
			params.put("fecha", fecha);
			List<Object[]> result = (List<Object[]>) this.getDao()
			        .ejecutaNamedQuerySelect(
			        		"HDomicilio.findByIdYFecha",
			                params);
			for (Object[] objeto : result) {
				HDomicilioDTO cli = new HDomicilioDTO();
				
				cli.setRowid((objeto[0]!=null)? objeto[0].toString():"");
				cli.setCoCliente((objeto[1]!= null)? new Long(objeto[1].toString()):0);
				cli.setCoDomicilio((objeto[2]!= null)? new Long(objeto[2].toString()):0);
				cli.setCoUnidadUrbana((objeto[3]!= null)? new Long(objeto[3].toString()):0);
				cli.setFxAlta((objeto[4]!=null)? (Date) objeto[4]:null);
				cli.setBoFiscalAeat((objeto[5]!=null)? (objeto[5].toString().equalsIgnoreCase("1") ?  true : false):false);
				cli.setBoFiscalMunicipal((objeto[6]!=null)? (objeto[6].toString().equalsIgnoreCase("1") ?  true : false):false);
				cli.setBoNotificacion((objeto[7]!=null)? (objeto[7].toString().equalsIgnoreCase("1") ?  true : false):false);
				cli.setProcedencia((objeto[8]!=null)? (String) objeto[8]:"");
				cli.setFhActualizacion((objeto[9]!=null)? (Date) objeto[9]:null);
				
				if(ControlTerritorial.isUsuarioExperto()){
					cli.setCoUsuarioActualizacion((objeto[10]!= null)? (String)objeto[10].toString():"");
				}
				cli.setHTipoMovimiento((objeto[11]!= null)? (String)objeto[11].toString():"");
				cli.setCoProceso((objeto[12]!= null)? (String)objeto[12].toString():"");
				cli.setCoEjecucion((objeto[13]!= null)? new Long(objeto[13].toString()):0);
				
				domicilios.add(cli);
			}
			return domicilios;
		} catch (final Exception e) {
			throw new GadirServiceException(
			        "Error al obtener el listado de domicilios.", e);
		}
	}
	
	public void guardarHDomicilio(DomicilioDTO domicilio, UnidadUrbanaDTO unidadUrbana, String tipoMovimiento) throws GadirServiceException{
		
		HDomicilioDTO hdomicilio = new HDomicilioDTO();
		hdomicilio.setCoDomicilio(domicilio.getCoDomicilio());
		hdomicilio.setCoUnidadUrbana(unidadUrbana.getCoUnidadUrbana());
		hdomicilio.setFhActualizacion(Utilidades.getDateActual());
		hdomicilio.setHTipoMovimiento(tipoMovimiento);
		hdomicilio.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		try {
			this.saveOnly(hdomicilio);
			
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Error al registrar el historico:", e);
		}
		
	}
	
	
	@SuppressWarnings("unchecked")
	public List<HGlobalDomiciliosVO> findFISCALByClienteOrMunicipioCalleFecha(final String coCliente, final String codProvincia, final String codMunicipio, final String codCalle, final String fecha) throws GadirServiceException {
		List<HGlobalDomiciliosVO> listado = new ArrayList<HGlobalDomiciliosVO>();
		String sqlString;
		sqlString = "select cl.*, ca.sigla, ca.nombre_calle, " +
		   				"u.numero, u.km, u.cp, m.nombre, p.nombre as provincia, u.letra, u.bloque, u.escalera, u.planta, u.puerta " +
		   			"from ga_h_domicilio cl " +
		   				"left outer join ga_domicilio d on d.co_cliente = cl.co_cliente and d.co_domicilio = cl.co_domicilio " +
		   				"left outer join ga_unidad_urbana u on d.co_unidad_urbana = u.co_unidad_urbana " +
		   				"left outer join ga_calle ca on ca.co_calle = u.co_calle " +
		   				"left outer join ga_municipio m on m.co_municipio = ca.co_municipio and m.co_provincia = ca.co_provincia " +
		   				"left outer join ga_provincia p on p.co_provincia = ca.co_provincia " +
		   				"left outer join ga_calle_ubicacion cu on cu.co_calle_ubicacion = ca.co_calle_ubicacion " +
		   			"where cl.co_cliente = :id  and cl.bo_fiscal_municipal = 1";
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
			parametros.put("id", new Long(coCliente));
			if (Utilidades.isNotEmpty(fecha)) {
				sqlString = sqlString + "and cl.fh_actualizacion >= :fecha ";
				parametros.put("fecha", formateador.parse(fecha));
			}
			if (Utilidades.isNotEmpty(codProvincia)) {
				sqlString = sqlString + "and m.co_provincia like :provincia ";
				parametros.put("provincia", codProvincia);
			}
			if (Utilidades.isNotEmpty(codMunicipio)) {
				sqlString = sqlString + "and m.co_municipio like :municipio ";
				parametros.put("municipio", codMunicipio);
			}
			if (Utilidades.isNotEmpty(codCalle)) {
				sqlString = sqlString + "and u.co_calle = :calle ";
				parametros.put("calle", codCalle);
			}
			sqlString = sqlString + "order by cl.fh_actualizacion";
			
				List<Object[]> result = (List<Object[]>) this.getDao().ejecutaSQLQuerySelect(sqlString, parametros);
				for (Object[] objeto : result) {
					HGlobalDomiciliosVO domicilio = new HGlobalDomiciliosVO();

					domicilio.setRowid((objeto[0] != null) ? objeto[0].toString() : "");
					domicilio.setProcedencia((objeto[8] != null) ? objeto[8].toString() : "");
					domicilio
							.setFhActualizacion((objeto[9] != null) ? (Date) objeto[9]
									: null);
					domicilio
							.setHTipoMovimiento((objeto[11] != null) ? (String) objeto[11]
									.toString()
									: "");
					domicilio
							.setSigla((objeto[14] != null) ? (String) objeto[14]
									.toString()
									: "");
					domicilio
							.setCalle((objeto[15] != null) ? (String) objeto[15]
									.toString()
									: "");
					domicilio
							.setNumero((objeto[16] != null) ? (String) objeto[16]
									.toString()
									: "");
					domicilio.setKm((objeto[17] != null) ? (String) objeto[17]
							.toString() : "");
					domicilio.setCp((objeto[18] != null) ? (String) objeto[18]
							.toString() : "");
					domicilio
							.setMunicipio((objeto[19] != null) ? (String) objeto[19]
									.toString()
									: "");
					domicilio
							.setProvincia((objeto[20] != null) ? (String) objeto[20]
									.toString()
									: "");
					domicilio.setLetra(null != objeto[21] ? (String) objeto[21] : "");
					domicilio.setBloque(null != objeto[22] ? (String) objeto[22] : "");
					domicilio.setEscalera(null != objeto[23] ? (String) objeto[23] : "");
					domicilio.setPlanta(null != objeto[24] ? (String) objeto[24] : "");
					domicilio.setPuerta(null != objeto[25] ? (String) objeto[25] : "");
					
					listado.add(domicilio);
				}

			if (null == listado)
				listado = Collections.emptyList();

			return listado;
		} catch (final Exception e) {
			throw new GadirServiceException(
					"Ocurrio un error al obtener los domicilios historicos", e);
		}
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public List<HGlobalDomiciliosVO> findByClienteOrMunicipioCalleFecha(final String coCliente, final String codProvincia, final String codMunicipio, final String codCalle, final String fecha) throws GadirServiceException {
		List<HGlobalDomiciliosVO> listado = new ArrayList<HGlobalDomiciliosVO>();
		String sqlString;
		sqlString = "select cl.*, ca.sigla, ca.nombre_calle, " +
		   				"u.numero, u.km, u.cp, m.nombre, p.nombre as provincia, u.letra, u.bloque, u.escalera, u.planta, u.puerta " +
		   			"from ga_h_domicilio cl " +
		   				"left outer join ga_domicilio d on d.co_cliente = cl.co_cliente and d.co_domicilio = cl.co_domicilio " +
		   				"left outer join ga_unidad_urbana u on d.co_unidad_urbana = u.co_unidad_urbana " +
		   				"left outer join ga_calle ca on ca.co_calle = u.co_calle " +
		   				"left outer join ga_municipio m on m.co_municipio = ca.co_municipio and m.co_provincia = ca.co_provincia " +
		   				"left outer join ga_provincia p on p.co_provincia = ca.co_provincia " +
		   				"left outer join ga_calle_ubicacion cu on cu.co_calle_ubicacion = ca.co_calle_ubicacion " +
		   			"where cl.co_cliente = :id ";
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
			parametros.put("id", new Long(coCliente));
			if (Utilidades.isNotEmpty(fecha)) {
				sqlString = sqlString + "and cl.fh_actualizacion >= :fecha ";
				parametros.put("fecha", formateador.parse(fecha));
			}
			if (Utilidades.isNotEmpty(codMunicipio)) {
				sqlString = sqlString + "and m.co_municipio like :municipio ";
				parametros.put("municipio", codMunicipio);
			}
			if (Utilidades.isNotEmpty(codProvincia)) {
				sqlString = sqlString + "and m.co_provincia like :provincia ";
				parametros.put("provincia", codProvincia);
			}
			if (Utilidades.isNotEmpty(codCalle)) {
				sqlString = sqlString + "and u.co_calle = :calle ";
				parametros.put("calle", codCalle);
			}
			sqlString = sqlString + "order by cl.fh_actualizacion";
			
				List<Object[]> result = (List<Object[]>) this.getDao().ejecutaSQLQuerySelect(sqlString, parametros);
				for (Object[] objeto : result) {
					HGlobalDomiciliosVO domicilio = new HGlobalDomiciliosVO();

					domicilio.setRowid((objeto[0] != null) ? objeto[0].toString() : "");
					domicilio.setProcedencia((objeto[8] != null) ? objeto[8].toString() : "");
					domicilio
							.setFhActualizacion((objeto[9] != null) ? (Date) objeto[9]
									: null);
					domicilio
							.setHTipoMovimiento((objeto[11] != null) ? (String) objeto[11]
									.toString()
									: "");
					domicilio
							.setSigla((objeto[14] != null) ? (String) objeto[14]
									.toString()
									: "");
					domicilio
							.setCalle((objeto[15] != null) ? (String) objeto[15]
									.toString()
									: "");
					domicilio
							.setNumero((objeto[16] != null) ? (String) objeto[16]
									.toString()
									: "");
					domicilio.setKm((objeto[17] != null) ? (String) objeto[17]
							.toString() : "");
					domicilio.setCp((objeto[18] != null) ? (String) objeto[18]
							.toString() : "");
					domicilio
							.setMunicipio((objeto[19] != null) ? (String) objeto[19]
									.toString()
									: "");
					domicilio
							.setProvincia((objeto[20] != null) ? (String) objeto[20]
									.toString()
									: "");
					domicilio.setLetra(null != objeto[21] ? (String) objeto[21] : "");
					domicilio.setBloque(null != objeto[22] ? (String) objeto[22] : "");
					domicilio.setEscalera(null != objeto[23] ? (String) objeto[23] : "");
					domicilio.setPlanta(null != objeto[24] ? (String) objeto[24] : "");
					domicilio.setPuerta(null != objeto[25] ? (String) objeto[25] : "");
					
					listado.add(domicilio);
				}

			if (null == listado)
				listado = Collections.emptyList();

			return listado;
		} catch (final Exception e) {
			throw new GadirServiceException(
					"Ocurrio un error al obtener los domicilios historicos", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<HGlobalDomiciliosVO> findByClienteAndOrMunicipioCalleFecha(final String coCliente, final String codProvincia, final String codMunicipio, final String codCalle, final String fecha) throws GadirServiceException {
		List<HGlobalDomiciliosVO> listado = new ArrayList<HGlobalDomiciliosVO>();

		try {
			Map<String, Object> parametros = new HashMap<String, Object>();

			if (Utilidades.isEmpty(codMunicipio)
					|| Utilidades.isEmpty(codCalle)
					|| Utilidades.isEmpty(fecha)) {
				parametros.put("id", new Long(coCliente));
				List<Object[]> result = (List<Object[]>) this.getDao()
						.ejecutaNamedQuerySelect(
								"HDomicilio.findByCliente", parametros);
				for (Object[] objeto : result) {
					HGlobalDomiciliosVO domicilio = new HGlobalDomiciliosVO();

					domicilio.setRowid((objeto[0] != null) ? objeto[0].toString() : "");
					domicilio.setProcedencia((objeto[8] != null) ? objeto[8].toString() : "");
					domicilio
							.setFhActualizacion((objeto[9] != null) ? (Date) objeto[9]
									: null);
					domicilio
							.setHTipoMovimiento((objeto[11] != null) ? (String) objeto[11]
									.toString()
									: "");
					domicilio
							.setSigla((objeto[14] != null) ? (String) objeto[14]
									.toString()
									: "");
					domicilio
							.setCalle((objeto[15] != null) ? (String) objeto[15]
									.toString()
									: "");
					domicilio
							.setNumero((objeto[16] != null) ? (String) objeto[16]
									.toString()
									: "");
					domicilio.setKm((objeto[17] != null) ? (String) objeto[17]
							.toString() : "");
					domicilio.setCp((objeto[18] != null) ? (String) objeto[18]
							.toString() : "");
					domicilio
							.setMunicipio((objeto[19] != null) ? (String) objeto[19]
									.toString()
									: "");
					domicilio
							.setProvincia((objeto[20] != null) ? (String) objeto[20]
									.toString()
									: "");
					domicilio.setLetra(null != objeto[21] ? (String) objeto[21] : "");
					domicilio.setBloque(null != objeto[22] ? (String) objeto[22] : "");
					domicilio.setEscalera(null != objeto[23] ? (String) objeto[23] : "");
					domicilio.setPlanta(null != objeto[24] ? (String) objeto[24] : "");
					domicilio.setPuerta(null != objeto[25] ? (String) objeto[25] : "");
					
					listado.add(domicilio);
				}
			} else {
				SimpleDateFormat formateador = new SimpleDateFormat(
				"dd/MM/yyyy");

				parametros.put("id", new Long(coCliente));
				parametros.put("calle", codCalle);
				parametros.put("provincia", codProvincia);
				parametros.put("municipio", codMunicipio);
				parametros.put("fecha", formateador.parse(fecha));

				List<Object[]> result = (List<Object[]>) this.getDao()
					.ejecutaNamedQuerySelect("HDomicilio.findByFiltro",
						parametros);
				
				for (Object[] objeto : result) {
					HGlobalDomiciliosVO domicilio = new HGlobalDomiciliosVO();

					domicilio.setRowid((objeto[0] != null) ? objeto[0].toString() : "");
					domicilio.setProcedencia((objeto[8] != null) ? objeto[8].toString() : "");
					domicilio
							.setFhActualizacion((objeto[9] != null) ? (Date) objeto[9]
									: null);
					domicilio
							.setHTipoMovimiento((objeto[11] != null) ? (String) objeto[11]
									.toString()
									: "");
					domicilio
							.setSigla((objeto[14] != null) ? (String) objeto[14]
									.toString()
									: "");
					domicilio
							.setCalle((objeto[15] != null) ? (String) objeto[15]
									.toString()
									: "");
					domicilio
							.setNumero((objeto[16] != null) ? (String) objeto[16]
									.toString()
									: "");
					domicilio.setKm((objeto[17] != null) ? (String) objeto[17]
							.toString() : "");
					domicilio.setCp((objeto[18] != null) ? (String) objeto[18]
							.toString() : "");
					domicilio
							.setMunicipio((objeto[19] != null) ? (String) objeto[19]
									.toString()
									: "");
					domicilio
							.setProvincia((objeto[20] != null) ? (String) objeto[20]
									.toString()
									: "");
					domicilio.setLetra(null != objeto[21] ? (String) objeto[21] : "");
					domicilio.setBloque(null != objeto[22] ? (String) objeto[22] : "");
					domicilio.setEscalera(null != objeto[23] ? (String) objeto[23] : "");
					domicilio.setPlanta(null != objeto[24] ? (String) objeto[24] : "");
					domicilio.setPuerta(null != objeto[25] ? (String) objeto[25] : "");
					
					listado.add(domicilio);
				}
			}

			if (null == listado)
				listado = Collections.emptyList();

			return listado;
		} catch (final Exception e) {
			throw new GadirServiceException(
					"Ocurrio un error al obtener los domicilios historicos", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public HGlobalDomiciliosVO findByRowId(String rowid)
			throws GadirServiceException {
		HGlobalDomiciliosVO domicilio = new HGlobalDomiciliosVO();

		Map<String, Object> parametros = new HashMap<String, Object>();

		try {
			parametros.put("rowid", Utilidades.decodificarRowidFormatoSeguro(rowid));
			List<Object[]> result = (List<Object[]>) this.getDao()
					.ejecutaNamedQuerySelect("HDomicilio.findByRowid",
							parametros);
			for (Object[] objeto : result) {
				domicilio = new HGlobalDomiciliosVO();

				domicilio.setRowid((objeto[0] != null) ? objeto[0].toString() : "");
				domicilio.setCoDomicilio((objeto[1] != null) ? new Long(objeto[1].toString()) : null);
				domicilio.setFxAlta((objeto[4] != null) ? (Date) objeto[4] : null);
				domicilio.setProcedencia((objeto[8] != null) ? objeto[8].toString() : "");
				domicilio.setFhActualizacion((objeto[9] != null) ? (Date) objeto[9]
						: null);
				domicilio
						.setCoUsuarioActualizacion((objeto[10] != null) ? (String) objeto[10]
								: "");
				domicilio
						.setHTipoMovimiento((objeto[11] != null) ? (String) objeto[11]
								.toString()
								: "");
				domicilio.setCoProceso((objeto[12] != null) ? (String) objeto[12]
						.toString() : "");
				domicilio.setCoEjecucion((objeto[13] != null) ? new Long(objeto[13]
						.toString()) : 0);
				domicilio.setSigla((objeto[14] != null) ? (String) objeto[14]
						.toString() : "");
				domicilio.setCalle((objeto[15] != null) ? (String) objeto[15]
						.toString() : "");
				domicilio.setNumero((objeto[16] != null) ? (String) objeto[16]
						.toString() : "");
				domicilio.setKm((objeto[17] != null) ? (String) objeto[17]
						.toString() : "");
				domicilio.setCp((objeto[18] != null) ? (String) objeto[18]
						.toString() : "");
				domicilio
						.setMunicipio((objeto[19] != null) ? (String) objeto[19]
								.toString()
								: "");
				domicilio
						.setProvincia((objeto[20] != null) ? (String) objeto[20]
								.toString()
								: "");
				domicilio.setLetra((objeto[21] != null) ? (String) objeto[21]
						.toString() : "");
				domicilio.setBloque((objeto[22] != null) ? (String) objeto[22]
						.toString() : "");
				domicilio
						.setEscalera((objeto[23] != null) ? (String) objeto[23]
								.toString() : "");
				domicilio.setPlanta((objeto[24] != null) ? (String) objeto[24]
						.toString() : "");
				domicilio.setPuerta((objeto[25] != null) ? (String) objeto[25]
						.toString() : "");
				domicilio
						.setUbicacion((objeto[26] != null) ? (String) objeto[26]
								.toString()
								: "");
				domicilio.setBoFiscalAeat(null != objeto[5] ? Boolean
						.valueOf((objeto[5].toString().equals("1")) ? "true"
								: "false") : false);
				// Miramos el especial caso de domicilio tributario
				if (Utilidades.isNotNull(domicilio.getCoDomicilio())) {
					final HashMap map = new HashMap();
					map.put("coDomicilio", domicilio.getCoDomicilio());
					List<Long> temporal = (List<Long>) this
							.getDao()
							.ejecutaNamedQuerySelect(
									"Cliente.findByIdClienteYDomicilioTributario",
									map);

					if (null != temporal && temporal.size() > 0)
						domicilio.setDomicilioTributario(true);
					else
						domicilio.setDomicilioTributario(false);
				} else
					domicilio.setDomicilioTributario(false);
				domicilio.setBoNotificacion(null != objeto[7] ? Boolean
						.valueOf((objeto[7].toString().equals("1")) ? "true"
								: "false") : false);
				break;
			}

			return domicilio;
		} catch (final Exception e) {
			throw new GadirServiceException(
					"Ocurrio un error al obtener el domicilio historico", e);
		}
	}
	public void auditorias(HDomicilioDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
