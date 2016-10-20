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

import com.ibm.icu.text.SimpleDateFormat;

import es.dipucadiz.etir.comun.bo.HConceptoDomicilioNotificacionBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.HDomicilioNotificacionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.sb07.comun.vo.HGlobalDomiciliosVO;

public class HConceptoDomicilioNotificacionBOImpl
		extends
		AbstractGenericBOImpl<HDomicilioNotificacionDTO, Long>
		implements HConceptoDomicilioNotificacionBO {

	private DAOBase<HDomicilioNotificacionDTO, Long> dao;

	public DAOBase<HDomicilioNotificacionDTO, Long> getDao() {
		return dao;
	}

	public void setDao(
			final DAOBase<HDomicilioNotificacionDTO, Long> dao) {
		this.dao = dao;
	}

	
	@SuppressWarnings("unchecked")
	public HGlobalDomiciliosVO findByRowId(String rowid)
			throws GadirServiceException {
		HGlobalDomiciliosVO domicilio = new HGlobalDomiciliosVO();

		Map<String, Object> parametros = new HashMap<String, Object>();

		try {
			parametros.put("rowid", Utilidades.decodificarRowidFormatoSeguro(rowid));
			List<Object[]> result = (List<Object[]>) this.getDao()
					.ejecutaNamedQuerySelect("HDomicilioNotificacion.findByRowid",
							parametros);
			for (Object[] objeto : result) {
				domicilio = new HGlobalDomiciliosVO();

				domicilio.setRowid((objeto[0] != null) ? objeto[0].toString() : "");
				domicilio.setCoModelo((objeto[3] != null) ? (String) objeto[3] : "");
				domicilio.setCoVersion((objeto[4] != null) ? (String) objeto[4] : "");
				domicilio.setCoDocumento((objeto[5] != null) ? (String) objeto[5] : "");
				domicilio.setConcepto((objeto[6] != null) ? (String) objeto[6] : "");
				domicilio.setDocumentoConcatenado(domicilio.getCoModelo() + " " + domicilio.getCoVersion() + " " + domicilio.getCoDocumento());
				domicilio.setCoDomicilio((objeto[7] != null) ? new Long(objeto[7].toString()) : null);
				domicilio.setFxAlta((objeto[8] != null) ? (Date) objeto[8] : null);
				domicilio.setFxVigenciaDesde((objeto[8] != null) ? (Date) objeto[8] : null);
				domicilio.setFxVigenciaHasta((objeto[9] != null) ? (Date) objeto[9] : null);
				domicilio.setFhActualizacion((objeto[10] != null) ? (Date) objeto[10]
						: null);
				domicilio
						.setCoUsuarioActualizacion((objeto[11] != null) ? (String) objeto[11]
								: "");
				domicilio
						.setHTipoMovimiento((objeto[12] != null) ? (String) objeto[12]
								.toString()
								: "");
				domicilio.setCoProceso((objeto[13] != null) ? (String) objeto[13]
						.toString() : "");
				domicilio.setCoEjecucion((objeto[14] != null) ? new Long(objeto[14]
						.toString()) : 0);
				domicilio.setSigla((objeto[15] != null) ? (String) objeto[15]
						.toString() : "");
				domicilio.setCalle((objeto[16] != null) ? (String) objeto[16]
						.toString() : "");
				domicilio.setNumero((objeto[17] != null) ? (String) objeto[17]
						.toString() : "");
				domicilio.setKm((objeto[18] != null) ? (String) objeto[18]
						.toString() : "");
				domicilio.setCp((objeto[19] != null) ? (String) objeto[19]
						.toString() : "");
				domicilio
						.setMunicipio((objeto[20] != null) ? (String) objeto[20]
								.toString()
								: "");
				domicilio
						.setProvincia((objeto[21] != null) ? (String) objeto[21]
								.toString()
								: "");
				domicilio.setLetra((objeto[22] != null) ? (String) objeto[22]
						.toString() : "");
				domicilio.setBloque((objeto[23] != null) ? (String) objeto[23]
						.toString() : "");
				domicilio
						.setEscalera((objeto[24] != null) ? (String) objeto[24]
								.toString() : "");
				domicilio.setPlanta((objeto[25] != null) ? (String) objeto[25]
						.toString() : "");
				domicilio.setPuerta((objeto[26] != null) ? (String) objeto[26]
						.toString() : "");
				domicilio
						.setUbicacion((objeto[27] != null) ? (String) objeto[27]
								.toString()
								: "");
				domicilio.setBoFiscalAeat(null != objeto[28] ? Boolean
						.valueOf((objeto[28].toString().equals("1")) ? "true"
								: "false") : false);
				domicilio.setProcedencia((objeto[31] != null) ? (String) objeto[31]
				                                   						.toString() : "");
				
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
				
				domicilio.setBoNotificacion(null != objeto[30] ? Boolean
						.valueOf((objeto[30].toString().equals("1")) ? "true"
								: "false") : false);
				break;
			}

			return domicilio;
		} catch (final Exception e) {
			throw new GadirServiceException(
					"Ocurrio un error al obtener el domicilio historico", e);
		}
	}

	
	@SuppressWarnings("unchecked")
	public List<HGlobalDomiciliosVO> findByClienteAndOrMunicipioCalleConceptoFecha(final String coCliente, final String codProvincia, final String codMunicipio, final String codCalle, final String codConcepto, final String fecha) throws GadirServiceException {
		List<HGlobalDomiciliosVO> listado = new ArrayList<HGlobalDomiciliosVO>();
		String sqlString;
		sqlString = "select cl.*, ca.sigla, ca.nombre_calle, " +
		   				"u.numero, u.km, u.cp, m.nombre, p.nombre as provincia, c.nombre as concepto " +
		   			"from ga_h_domicilio_notificacion cl " +
		   				"left outer join ga_domicilio d on d.co_cliente = cl.co_cliente and d.co_domicilio = cl.co_domicilio " +
		   				"left outer join ga_unidad_urbana u on d.co_unidad_urbana = u.co_unidad_urbana " +
		   				"left outer join ga_calle ca on ca.co_calle = u.co_calle " +
		   				"left outer join ga_municipio m on m.co_municipio = ca.co_municipio and m.co_provincia = ca.co_provincia " +
		   				"left outer join ga_provincia p on p.co_provincia = ca.co_provincia " +
		   				"left outer join ga_calle_ubicacion cu on cu.co_calle_ubicacion = ca.co_calle_ubicacion " +
		   				"left outer join ga_concepto c on c.co_concepto = cl.co_concepto " +
		   			"where cl.co_cliente = :id ";
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
			if (Utilidades.isNotEmpty(codConcepto)) {
				sqlString = sqlString + "and cl.co_concepto = :concepto ";
				parametros.put("concepto", codConcepto);
			}
			sqlString = sqlString + "order by cl.fh_actualizacion";
			
				List<Object[]> result = (List<Object[]>) this.getDao().ejecutaSQLQuerySelect(sqlString, parametros);
				for (Object[] objeto : result) {
					HGlobalDomiciliosVO domicilio = new HGlobalDomiciliosVO();

					domicilio.setRowid((objeto[0] != null) ? objeto[0].toString() : "");
					domicilio.setCoModelo((objeto[3] != null) ? (String) objeto[3] : "");
					domicilio.setCoVersion((objeto[4] != null) ? (String) objeto[4] : "");
					domicilio.setCoDocumento((objeto[5] != null) ? (String) objeto[5] : "");
					domicilio.setConcepto((objeto[22] != null) ? (String) objeto[22] : "");
					domicilio.setFxVigenciaDesde((objeto[8] != null) ? (Date) objeto[8] : null);
					domicilio.setFxVigenciaHasta((objeto[9] != null) ? (Date) objeto[9] : null);
					domicilio
							.setFhActualizacion((objeto[10] != null) ? (Date) objeto[10]
									: null);
					domicilio
							.setCoUsuarioActualizacion((objeto[11] != null) ? (String) objeto[11] : "");
					domicilio
							.setHTipoMovimiento((objeto[12] != null) ? (String) objeto[12]
									.toString()
									: "");
					domicilio.setCoProceso((objeto[13] != null) ? (String) objeto[13].toString() : "");
					domicilio.setCoEjecucion((objeto[14] != null) ? new Long(objeto[14].toString()) : 0);
					domicilio.setDocumentoConcatenado(domicilio.getCoModelo() + " " + domicilio.getCoVersion() + " " + domicilio.getCoDocumento());
					domicilio
							.setSigla((objeto[15] != null) ? (String) objeto[15]
									.toString()
									: "");
					domicilio
							.setCalle((objeto[16] != null) ? (String) objeto[16]
									.toString()
									: "");
					domicilio
							.setNumero((objeto[17] != null) ? (String) objeto[17]
									.toString()
									: "");
					domicilio.setKm((objeto[18] != null) ? (String) objeto[18]
							.toString() : "");
					domicilio.setCp((objeto[19] != null) ? (String) objeto[19]
							.toString() : "");
					domicilio
							.setMunicipio((objeto[20] != null) ? (String) objeto[20]
									.toString()
									: "");
					domicilio
							.setProvincia((objeto[21] != null) ? (String) objeto[21]
									.toString()
									: "");

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
	public List<HGlobalDomiciliosVO> findByClienteAndOrMunicipioCalleNombreConceptoFecha(final String coCliente, final String codMunicipio, final String codCalle, final String nombreConcepto, final String fecha) throws GadirServiceException {
		List<HGlobalDomiciliosVO> listado = new ArrayList<HGlobalDomiciliosVO>();
		String sqlString;
		sqlString = "select cl.*, ca.sigla, ca.nombre_calle, " +
		   				"u.numero, u.km, u.cp, m.nombre, p.nombre as provincia, c.nombre as concepto " +
		   			"from ga_h_domicilio_notificacion cl " +
		   				"left outer join ga_domicilio d on d.co_cliente = cl.co_cliente and d.co_domicilio = cl.co_domicilio " +
		   				"left outer join ga_unidad_urbana u on d.co_unidad_urbana = u.co_unidad_urbana " +
		   				"left outer join ga_calle ca on ca.co_calle = u.co_calle " +
		   				"left outer join ga_municipio m on m.co_municipio = ca.co_municipio and m.co_provincia = ca.co_provincia " +
		   				"left outer join ga_provincia p on p.co_provincia = ca.co_provincia " +
		   				"left outer join ga_calle_ubicacion cu on cu.co_calle_ubicacion = ca.co_calle_ubicacion " +
		   				"left outer join ga_concepto c on c.co_concepto = cl.co_concepto " +
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
			if (Utilidades.isNotEmpty(codCalle)) {
				sqlString = sqlString + "and u.co_calle = :calle ";
				parametros.put("calle", codCalle);
			}
			if (Utilidades.isNotEmpty(nombreConcepto)) {
				sqlString = sqlString + "and UPPER(c.nombre) >= UPPER(:concepto) ";
				parametros.put("concepto", nombreConcepto);
			}
			sqlString = sqlString + "order by cl.fh_actualizacion";
			
				List<Object[]> result = (List<Object[]>) this.getDao().ejecutaSQLQuerySelect(sqlString, parametros);
				for (Object[] objeto : result) {
					HGlobalDomiciliosVO domicilio = new HGlobalDomiciliosVO();

					domicilio.setRowid((objeto[0] != null) ? objeto[0].toString() : "");
					domicilio.setCoModelo((objeto[3] != null) ? (String) objeto[3] : "");
					domicilio.setCoVersion((objeto[4] != null) ? (String) objeto[4] : "");
					domicilio.setCoDocumento((objeto[5] != null) ? (String) objeto[5] : "");
					domicilio.setConcepto((objeto[22] != null) ? (String) objeto[22] : "");
					domicilio.setFxVigenciaDesde((objeto[8] != null) ? (Date) objeto[8] : null);
					domicilio.setFxVigenciaHasta((objeto[9] != null) ? (Date) objeto[9] : null);
					domicilio
							.setFhActualizacion((objeto[10] != null) ? (Date) objeto[10]
									: null);
					domicilio
							.setCoUsuarioActualizacion((objeto[11] != null) ? (String) objeto[11] : "");
					domicilio
							.setHTipoMovimiento((objeto[12] != null) ? (String) objeto[12]
									.toString()
									: "");
					domicilio.setCoProceso((objeto[13] != null) ? (String) objeto[13].toString() : "");
					domicilio.setCoEjecucion((objeto[14] != null) ? new Long(objeto[14].toString()) : 0);
					domicilio.setDocumentoConcatenado(domicilio.getCoModelo() + " " + domicilio.getCoVersion() + " " + domicilio.getCoDocumento());
					domicilio
							.setSigla((objeto[15] != null) ? (String) objeto[15]
									.toString()
									: "");
					domicilio
							.setCalle((objeto[16] != null) ? (String) objeto[16]
									.toString()
									: "");
					domicilio
							.setNumero((objeto[17] != null) ? (String) objeto[17]
									.toString()
									: "");
					domicilio.setKm((objeto[18] != null) ? (String) objeto[18]
							.toString() : "");
					domicilio.setCp((objeto[19] != null) ? (String) objeto[19]
							.toString() : "");
					domicilio
							.setMunicipio((objeto[20] != null) ? (String) objeto[20]
									.toString()
									: "");
					domicilio
							.setProvincia((objeto[21] != null) ? (String) objeto[21]
									.toString()
									: "");

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
	
	public void auditorias(HDomicilioNotificacionDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
