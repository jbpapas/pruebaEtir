/**
 * 
 */
package es.dipucadiz.etir.comun.bo.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.DomicilioBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.QueryName;
import es.dipucadiz.etir.comun.dto.ConceptoDTO;
import es.dipucadiz.etir.comun.dto.DomicilioDTO;
import es.dipucadiz.etir.comun.dto.DomicilioNotificacionDTO;
import es.dipucadiz.etir.comun.dto.HDomicilioDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.dto.UnidadAdministrativaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.KeyValue;
import es.dipucadiz.etir.comun.utilidades.MunicipioConceptoModeloUtil;
import es.dipucadiz.etir.comun.utilidades.TablaGt;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.sb07.comun.vo.DomicilioVO;

public class DomicilioBOImpl extends AbstractGenericBOImpl<DomicilioDTO, Long> implements DomicilioBO {
	
	private static final long serialVersionUID = -6003779507558498233L;

	private DAOBase<HDomicilioDTO, Long> hDomicilioDAO;
	
	private DAOBase<UnidadAdministrativaDTO, String> unidadAdministrativaDAO;
	
	private DAOBase<DomicilioDTO, Long> dao;

	public DomicilioDTO findByIdFetch(final String coDomicilio) throws GadirServiceException {
		Map<String, Object> parametros = new HashMap<String, Object>();
		try {
			parametros.put("id", new Long(coDomicilio));
			return findByNamedQuery(QueryName.DOMICILIO_FINDBYIDFETCH, parametros)
					.get(0);
		} catch (final Exception e) {
			throw new GadirServiceException(
					"Ocurrio un error al obtener el domicilio seleccionado", e);
		}	
	}
	
	public DomicilioVO findByIdFetchConTributario(final String coDomicilio) throws GadirServiceException {
		Map<String, Object> parametros = new HashMap<String, Object>();
		DomicilioVO domicilioVO = new DomicilioVO();
		try {
			parametros.put("id", new Long(coDomicilio));
			List<DomicilioDTO> lista = findByNamedQuery(QueryName.DOMICILIO_FINDBYIDFETCH, parametros);
			if (lista.size() > 0) {
				DomicilioDTO domicilio = lista.get(0);
					domicilioVO.setCoDomicilio(domicilio.getCoDomicilio());
					domicilioVO.setDomicilioAEAT(domicilio.getBoFiscalAeat());
					// Miramos el especial caso de domicilio tributario
//					if (Utilidades.isNotNull(domicilio.getCoDomicilio())) {
//						final HashMap map = new HashMap();
//						map.put("coDomicilio", new Long(domicilio.getCoDomicilio()));
//						List<Long> temporal = (List<Long>) this
//								.getDao()
//								.ejecutaNamedQuerySelect(
//										"Cliente.findByIdClienteYDomicilioTributario",
//										map);
//
//						if (null != temporal && temporal.size() > 0)
//							domicilioVO.setDomicilioTributario(true);
//						else
//							domicilioVO.setDomicilioTributario(false);
//					} else
//						domicilioVO.setDomicilioTributario(false);
					
					domicilioVO.setDomicilioTributario(domicilio.isBoTributario());
					domicilioVO.setDomicilioKm(domicilio.getUnidadUrbanaDTO().getKm());
					domicilioVO.setDomicilioNum(domicilio.getUnidadUrbanaDTO().getNumero());
					domicilioVO.setDomicilioNombreVia(domicilio.getUnidadUrbanaDTO().getCalleDTO().getNombreCalle());
					domicilioVO.setDomicilioSigla(domicilio.getUnidadUrbanaDTO().getCalleDTO().getSigla());
					domicilioVO.setMunicipio(domicilio.getUnidadUrbanaDTO().getCalleDTO().getMunicipioDTO().getNombre());
					domicilioVO.setCoMunicipio(domicilio.getUnidadUrbanaDTO().getCalleDTO().getMunicipioDTO().getId().getCoMunicipio());
					domicilioVO.setProvincia(domicilio.getUnidadUrbanaDTO().getCalleDTO().getMunicipioDTO().getProvinciaDTO().getNombre());
					domicilioVO.setDomicilioLetra(domicilio.getUnidadUrbanaDTO().getLetra());
					domicilioVO.setDomicilioBloque(domicilio.getUnidadUrbanaDTO().getBloque());
					domicilioVO.setDomicilioEscalera(domicilio.getUnidadUrbanaDTO().getEscalera());
					domicilioVO.setDomicilioPlanta(domicilio.getUnidadUrbanaDTO().getPlanta());
					domicilioVO.setDomicilioPuerta(domicilio.getUnidadUrbanaDTO().getPuerta());
					domicilioVO.setDomicilioNotificacion(domicilio.getBoNotificacion());
					
					if (Utilidades.isNotEmpty(domicilio.getProcedencia())) {
						KeyValue tfuepro = TablaGt.getCodigoDescripcion(TablaGt.TABLA_FUENTE_PROCEDENCIA, domicilio.getProcedencia());
						if(tfuepro.getValue() != null)
							domicilioVO.setProcedencia(tfuepro.getValue());
												
						if (Utilidades.isEmpty(domicilioVO.getProcedencia())) {
							UnidadAdministrativaDTO unidad = this.getUnidadAdministrativaDAO().findById(domicilio.getProcedencia());
							if (unidad != null && Utilidades.isNotEmpty(unidad.getNombre())) {
								domicilioVO.setProcedencia(unidad.getNombre());
							}
						}
						
						if(Utilidades.isEmpty(domicilioVO.getProcedencia())){
							domicilioVO.setProcedencia(domicilio.getProcedencia());
						}
					}
					domicilioVO.setFxAlta(domicilio.getFxAlta());
					if (Utilidades.isNotNull(domicilio.getUnidadUrbanaDTO().getCalleDTO().getCalleUbicacionDTO())) {
						domicilioVO.setUbicacion(domicilio.getUnidadUrbanaDTO().getCalleDTO().getCalleUbicacionDTO().getUbicacion());
					}
					domicilioVO.setDomicilioCp(domicilio.getUnidadUrbanaDTO().getCp());
			}
		} catch (final Exception e) {
			throw new GadirServiceException(
					"Ocurrio un error al obtener el domicilio seleccionado", e);
		}	
		return domicilioVO;
	}
	
	public List<DomicilioVO> findVOByClienteAndOrMunicipioCalle(final String coCliente, final String codProvincia, final String codMunicipio, final String codCalle) throws GadirServiceException {
		String sqlString;
		List<DomicilioVO> domicilios = new ArrayList<DomicilioVO>();
		sqlString = "from DomicilioDTO cl " +
		   				"left join fetch cl.unidadUrbanaDTO " +
		   				"left join fetch cl.unidadUrbanaDTO.calleDTO " +
		   				"left join fetch cl.unidadUrbanaDTO.calleDTO.municipioDTO " +
		   				"left join fetch cl.unidadUrbanaDTO.calleDTO.calleUbicacionDTO "+
		   				"left join fetch cl.unidadUrbanaDTO.calleDTO.municipioDTO.provinciaDTO " +
		   			"where cl.clienteDTO.coCliente = "+coCliente+" and (cl.boFiscalMunicipal != 1 or cl.boFiscalMunicipal is null) ";
		try {
			if (Utilidades.isNotEmpty(codProvincia)  && Utilidades.isNumeric(codProvincia)) {
				sqlString = sqlString + "and cl.unidadUrbanaDTO.calleDTO.municipioDTO.id.coProvincia like '"+codProvincia+"' ";
			}
			if (Utilidades.isNotEmpty(codMunicipio)  && Utilidades.isNumeric(codMunicipio)) {
				sqlString = sqlString + "and cl.unidadUrbanaDTO.calleDTO.municipioDTO.id.coMunicipio like '"+codMunicipio+"' ";
			}
			else if(Utilidades.isNotEmpty(codMunicipio) )
			{
				sqlString = sqlString + "and cl.unidadUrbanaDTO.calleDTO.municipioDTO.nombre like '"+codMunicipio+"' ";
			}
			if (Utilidades.isNotEmpty(codCalle) && Utilidades.isNumeric(codCalle)) {
				sqlString = sqlString + "and cl.unidadUrbanaDTO.calleDTO.coCalle = "+codCalle+" ";
			}
			else if(Utilidades.isNotEmpty(codCalle))
			{
				sqlString = sqlString + "and cl.unidadUrbanaDTO.calleDTO.nombreCalle like '"+codCalle+"' ";
			}
			
//			sqlString = sqlString + "order by cl.coDomicilio";
			sqlString = sqlString + "order by cl.unidadUrbanaDTO.calleDTO.municipioDTO.provinciaDTO.nombre,"+
			"cl.unidadUrbanaDTO.calleDTO.municipioDTO, cl.unidadUrbanaDTO.calleDTO.sigla,"+
			"cl.unidadUrbanaDTO.calleDTO.nombreCalle";
			Iterator<DomicilioDTO> it = this.getDao().findByQuery(sqlString).iterator();
			while (it.hasNext()) {
				DomicilioDTO domicilio = it.next();
				DomicilioVO domicilioVO = new DomicilioVO();
				domicilioVO.setCoDomicilio(domicilio.getCoDomicilio());
				domicilioVO.setDomicilioAEAT(domicilio.getBoFiscalAeat());
				// Miramos el especial caso de domicilio tributario
//				if (Utilidades.isNotNull(domicilio.getCoDomicilio())) {
//					final HashMap map = new HashMap();
//					map.put("coDomicilio", new Long(domicilio.getCoDomicilio()));
//					List<Long> temporal = (List<Long>) this
//							.getDao()
//							.ejecutaNamedQuerySelect(
//									"Cliente.findByIdClienteYDomicilioTributario",
//									map);
//
//					if (null != temporal && temporal.size() > 0)
//						domicilio.setBoFiscalMunicipal(true);
//					else
//						domicilio.setBoFiscalMunicipal(false);
//				} else
//					domicilio.setBoFiscalMunicipal(false);
				if(domicilio.getUnidadUrbanaDTO()!=null){
					if (!Utilidades.isEmpty(domicilio.getUnidadUrbanaDTO().getCoordenadaX()) && !Utilidades.isEmpty(domicilio.getUnidadUrbanaDTO().getCoordenadaY()) ){
						domicilioVO.setDisponeCoordenadasXY("true");
					}
				}
				domicilioVO.setDomicilioTributario(domicilio.isBoTributario());
				domicilioVO.setDomicilioKm(domicilio.getUnidadUrbanaDTO().getKm());
				domicilioVO.setDomicilioNum(domicilio.getUnidadUrbanaDTO().getNumero());
				domicilioVO.setDomicilioNombreVia(domicilio.getUnidadUrbanaDTO().getCalleDTO().getNombreCalle());
				domicilioVO.setDomicilioSigla(domicilio.getUnidadUrbanaDTO().getCalleDTO().getSigla());
				domicilioVO.setMunicipio(domicilio.getUnidadUrbanaDTO().getCalleDTO().getMunicipioDTO().getNombre());
				domicilioVO.setProvincia(domicilio.getUnidadUrbanaDTO().getCalleDTO().getMunicipioDTO().getProvinciaDTO().getNombre());
				domicilioVO.setDomicilioLetra(domicilio.getUnidadUrbanaDTO().getLetra());
				domicilioVO.setDomicilioBloque(domicilio.getUnidadUrbanaDTO().getBloque());
				domicilioVO.setDomicilioEscalera(domicilio.getUnidadUrbanaDTO().getEscalera());
				domicilioVO.setDomicilioPlanta(domicilio.getUnidadUrbanaDTO().getPlanta());
				domicilioVO.setDomicilioPuerta(domicilio.getUnidadUrbanaDTO().getPuerta());
				domicilioVO.setDomicilioNotificacion(domicilio.getBoNotificacion());
				if (Utilidades.isNotEmpty(domicilio.getProcedencia())) {
					KeyValue tfuepro = TablaGt.getCodigoDescripcion(TablaGt.TABLA_FUENTE_PROCEDENCIA, domicilio.getProcedencia());
					if(tfuepro.getValue() != null){
						domicilioVO.setProcedencia(tfuepro.getValue());
					}
					if (Utilidades.isEmpty(domicilioVO.getProcedencia())) {
						UnidadAdministrativaDTO unidad = this.getUnidadAdministrativaDAO().findById(domicilio.getProcedencia());
						if (unidad != null && Utilidades.isNotEmpty(unidad.getNombre())) {
							domicilioVO.setProcedencia(unidad.getNombre());
						}
					}
					
					if(Utilidades.isEmpty(domicilioVO.getProcedencia())){
						domicilioVO.setProcedencia(domicilio.getProcedencia());
					}
					
					if(domicilio.getUnidadUrbanaDTO().getCalleDTO().getCalleUbicacionDTO() != null){
						domicilioVO.setUbicacion(domicilio.getUnidadUrbanaDTO().getCalleDTO().getCalleUbicacionDTO().getUbicacion());
					}
				}
				
				
				domicilioVO.setFxAlta(domicilio.getFxAlta());
				
				domicilios.add(domicilioVO);
			}
		} catch (final Exception e) {
			throw new GadirServiceException(
					"Ocurrio un error al obtener los domicilios asociados al cliente", e);
		}
		return domicilios;
	}
	
	public List<DomicilioVO> findByClienteMunicipioCalleConTributario(final String coCliente, final String codProvincia, final String codMunicipio, final String nombreCalle, int first, int max) throws GadirServiceException {
		String sqlString;
		List<DomicilioVO> domicilios = new ArrayList<DomicilioVO>();
		sqlString = "from DomicilioDTO cl " +
		   				"left join fetch cl.unidadUrbanaDTO " +
		   				"left join fetch cl.unidadUrbanaDTO.calleDTO " +
		   				"left join fetch cl.unidadUrbanaDTO.calleDTO.municipioDTO " +
		   				"left join fetch cl.unidadUrbanaDTO.calleDTO.calleUbicacionDTO "+
		   				"left join fetch cl.unidadUrbanaDTO.calleDTO.municipioDTO.provinciaDTO " +
		   			"where cl.clienteDTO.coCliente = "+coCliente+" and (cl.boFiscalMunicipal != 1 or cl.boFiscalMunicipal is null) ";
		try {
			if (Utilidades.isNotEmpty(codProvincia) && Utilidades.isNumeric(codProvincia)) {
				sqlString = sqlString + "and cl.unidadUrbanaDTO.calleDTO.municipioDTO.id.coProvincia like '"+codProvincia+"' ";
			}
			if (Utilidades.isNotEmpty(codMunicipio)  && Utilidades.isNumeric(codMunicipio)) {
				sqlString = sqlString + "and cl.unidadUrbanaDTO.calleDTO.municipioDTO.id.coMunicipio like '"+codMunicipio+"' ";
			}
			else if(Utilidades.isNotEmpty(codMunicipio) )
			{
				sqlString = sqlString + "and cl.unidadUrbanaDTO.calleDTO.municipioDTO.nombre like '"+codMunicipio+"' ";
			}
			if (Utilidades.isNotEmpty(nombreCalle)){
				sqlString = sqlString + "and upper(cl.unidadUrbanaDTO.calleDTO.nombreCalle) like upper('%"+nombreCalle+"%') ";
			}
//			sqlString = sqlString + "order by cl.coDomicilio";
			sqlString = sqlString + "order by cl.unidadUrbanaDTO.calleDTO.municipioDTO.provinciaDTO.nombre,"+
			"cl.unidadUrbanaDTO.calleDTO.municipioDTO, cl.unidadUrbanaDTO.calleDTO.sigla,"+
			"cl.unidadUrbanaDTO.calleDTO.nombreCalle";
			//TODO hay que cambiar esta sadielada: se trae todos los domicilios
			//Iterator<DomicilioDTO> it = this.getDao().findByQuery(sqlString,first, max).iterator();
			Iterator<DomicilioDTO> it = this.getDao().findByQuery(sqlString).iterator();
			while (it.hasNext()) {
				DomicilioDTO domicilio = it.next();
				DomicilioVO domicilioVO = new DomicilioVO();
				domicilioVO.setCoDomicilio(domicilio.getCoDomicilio());
				domicilioVO.setDomicilioAEAT(domicilio.getBoFiscalAeat());
				// Miramos el especial caso de domicilio tributario
//				if (Utilidades.isNotNull(domicilio.getCoDomicilio())) {
//					final HashMap map = new HashMap();
//					map.put("coDomicilio", new Long(domicilio.getCoDomicilio()));
//					List<Long> temporal = (List<Long>) this
//							.getDao()
//							.ejecutaNamedQuerySelect(
//									"Cliente.findByIdClienteYDomicilioTributario",
//									map);
//
//					if (null != temporal && temporal.size() > 0)
//						domicilio.setBoFiscalMunicipal(true);
//					else
//						domicilio.setBoFiscalMunicipal(false);
//				} else
//					domicilio.setBoFiscalMunicipal(false);
//				domicilioVO.setDomicilioTributario(domicilio.getBoFiscalMunicipal());
				domicilioVO.setDomicilioTributario(domicilio.isBoTributario());
				
				if(domicilio.getUnidadUrbanaDTO() != null){
					domicilioVO.setDomicilioKm(domicilio.getUnidadUrbanaDTO().getKm());
					domicilioVO.setDomicilioNum(domicilio.getUnidadUrbanaDTO().getNumero());
					if(domicilio.getUnidadUrbanaDTO().getCalleDTO() != null){
						domicilioVO.setDomicilioNombreVia(domicilio.getUnidadUrbanaDTO().getCalleDTO().getNombreCalle());
						domicilioVO.setDomicilioSigla(domicilio.getUnidadUrbanaDTO().getCalleDTO().getSigla());
						domicilioVO.setMunicipio(domicilio.getUnidadUrbanaDTO().getCalleDTO().getMunicipioDTO().getNombre());
						domicilioVO.setProvincia(domicilio.getUnidadUrbanaDTO().getCalleDTO().getMunicipioDTO().getProvinciaDTO().getNombre());
					}
					
					
					if(domicilio.getUnidadUrbanaDTO().getCalleDTO().getCalleUbicacionDTO() != null){
						domicilioVO.setUbicacion(domicilio.getUnidadUrbanaDTO().getCalleDTO().getCalleUbicacionDTO().getUbicacion());
					}
					domicilioVO.setDomicilioLetra(domicilio.getUnidadUrbanaDTO().getLetra());
					domicilioVO.setDomicilioBloque(domicilio.getUnidadUrbanaDTO().getBloque());
					domicilioVO.setDomicilioEscalera(domicilio.getUnidadUrbanaDTO().getEscalera());
					domicilioVO.setDomicilioPlanta(domicilio.getUnidadUrbanaDTO().getPlanta());
					domicilioVO.setDomicilioPuerta(domicilio.getUnidadUrbanaDTO().getPuerta());
					
					if (!Utilidades.isEmpty(domicilio.getUnidadUrbanaDTO().getCoordenadaX()) && !Utilidades.isEmpty(domicilio.getUnidadUrbanaDTO().getCoordenadaY()) ){
						domicilioVO.setDisponeCoordenadasXY("true");
					}
				}
				domicilioVO.setDomicilioNotificacion(domicilio.getBoNotificacion());
				if (Utilidades.isNotEmpty(domicilio.getProcedencia())) {
					KeyValue tfuepro = TablaGt.getCodigoDescripcion(TablaGt.TABLA_FUENTE_PROCEDENCIA, domicilio.getProcedencia());
					if(tfuepro.getValue() != null){
						domicilioVO.setProcedencia(tfuepro.getValue());
					}
					if (Utilidades.isEmpty(domicilioVO.getProcedencia())) {
						UnidadAdministrativaDTO unidad = this.getUnidadAdministrativaDAO().findById(domicilio.getProcedencia());
						if (unidad != null && Utilidades.isNotEmpty(unidad.getNombre())) {
							domicilioVO.setProcedencia(unidad.getNombre());
						}
					}
					
					if(Utilidades.isEmpty(domicilioVO.getProcedencia())){
						domicilioVO.setProcedencia(domicilio.getProcedencia());
					}
				}
				
				
				domicilioVO.setFxAlta(domicilio.getFxAlta());
				domicilioVO.setFxAltaString(Utilidades.dateToDDMMYYYY( domicilio.getFxAlta()));
				
				domicilios.add(domicilioVO);
			}
		} catch (final Exception e) {
			throw new GadirServiceException(
					"Ocurrio un error al obtener los domicilios asociados al cliente", e);
		}
		return domicilios;
	}
	
	public List<DomicilioVO> findByClienteAndOrMunicipioCalleConcepto(final String coCliente, final String codProvincia, final String codMunicipio, final String codCalle, final String codConcepto, final boolean isAyuntamiento) throws GadirServiceException {
		String sqlString;
		List<DomicilioVO> domiciliosNotificacion = new ArrayList<DomicilioVO>();
		Date fxActual;
		sqlString = "select cl from DomicilioDTO cl";
		if(Utilidades.isEmpty(codConcepto))
			sqlString += ", DomicilioNotificacionDTO dn";
					
		sqlString += " left join fetch cl.unidadUrbanaDTO " +
	   				 "left join fetch cl.unidadUrbanaDTO.calleDTO " +
	   				 "left join fetch cl.unidadUrbanaDTO.calleDTO.municipioDTO " +
	   				 "left join fetch cl.unidadUrbanaDTO.calleDTO.calleUbicacionDTO "+
	   				 "left join fetch cl.unidadUrbanaDTO.calleDTO.municipioDTO.provinciaDTO "+					
	   				 "where cl.boNotificacion = 1 " +
	   			"and cl.clienteDTO.coCliente = "+coCliente+" ";
				if(Utilidades.isEmpty(codConcepto))
					sqlString += "and cl.coDomicilio = dn.domicilioDTO.coDomicilio ";
		try {
			if (Utilidades.isNotEmpty(codProvincia)) {
				sqlString = sqlString + "and cl.unidadUrbanaDTO.calleDTO.municipioDTO.id.coProvincia like '"+codProvincia+"' ";
			}
			if (Utilidades.isNotEmpty(codMunicipio)) {
				sqlString = sqlString + "and cl.unidadUrbanaDTO.calleDTO.municipioDTO.id.coMunicipio like '"+codMunicipio+"' ";
			}
			if (Utilidades.isNotEmpty(codCalle)) {
				sqlString = sqlString + "and cl.unidadUrbanaDTO.calleDTO.coCalle = "+codCalle+" ";
			}
			if (Utilidades.isNotEmpty(codConcepto)) {
				sqlString = sqlString + "and cl.coDomicilio in " +
											"(select distinct det.domicilioDTO.coDomicilio from DomicilioNotificacionDTO det " +
											"where det.conceptoDTO.coConcepto like '"+codConcepto+"') ";
			}
			
//			sqlString = sqlString + "order by cl.coDomicilio";
			sqlString = sqlString + "order by cl.coDomicilio, dn.municipioDTO.id.coProvincia, dn.municipioDTO.id.coMunicipio,";
			if(Utilidades.isEmpty(codConcepto))
				sqlString += "dn.conceptoDTO.coConcepto,";
			sqlString += "dn.coModeloCenso, dn.coVersionCenso, dn.coDocumentoCenso, cl.unidadUrbanaDTO.calleDTO.municipioDTO.provinciaDTO.nombre,"+
			"cl.unidadUrbanaDTO.calleDTO.municipioDTO, cl.unidadUrbanaDTO.calleDTO.sigla,"+
			"cl.unidadUrbanaDTO.calleDTO.nombreCalle";
			
//			Iterator<DomicilioDTO> it = 
			List<DomicilioDTO> listaDomicilios = new ArrayList<DomicilioDTO>(new HashSet<DomicilioDTO>(this.getDao().findByQuery(sqlString)));
			String coDomicilio = "";
			for (DomicilioDTO domicilio : listaDomicilios) {
//				DomicilioDTO domicilio = it.next();
				if(!coDomicilio.equals(domicilio.getCoDomicilio().toString())) {
					coDomicilio = domicilio.getCoDomicilio().toString();
					DomicilioVO domicilioNotificacionVO = new DomicilioVO();
					domicilioNotificacionVO.setCoDomicilio(domicilio.getCoDomicilio());
					domicilioNotificacionVO.setDomicilioAEAT(domicilio.getBoFiscalAeat());
					domicilioNotificacionVO.setDomicilioTributario(domicilio.isBoTributario());
					
					domicilioNotificacionVO.setDomicilioKm(domicilio.getUnidadUrbanaDTO().getKm());
					domicilioNotificacionVO.setDomicilioNum(domicilio.getUnidadUrbanaDTO().getNumero());
					domicilioNotificacionVO.setDomicilioNombreVia(domicilio.getUnidadUrbanaDTO().getCalleDTO().getNombreCalle());
					domicilioNotificacionVO.setDomicilioSigla(domicilio.getUnidadUrbanaDTO().getCalleDTO().getSigla());
					domicilioNotificacionVO.setMunicipio(domicilio.getUnidadUrbanaDTO().getCalleDTO().getMunicipioDTO().getNombre());
					domicilioNotificacionVO.setProvincia(domicilio.getUnidadUrbanaDTO().getCalleDTO().getMunicipioDTO().getProvinciaDTO().getNombre());
					domicilioNotificacionVO.setDomicilioLetra(domicilio.getUnidadUrbanaDTO().getLetra());
					domicilioNotificacionVO.setDomicilioBloque(domicilio.getUnidadUrbanaDTO().getBloque());
					domicilioNotificacionVO.setDomicilioEscalera(domicilio.getUnidadUrbanaDTO().getEscalera());
					domicilioNotificacionVO.setDomicilioPlanta(domicilio.getUnidadUrbanaDTO().getPlanta());
					domicilioNotificacionVO.setDomicilioPuerta(domicilio.getUnidadUrbanaDTO().getPuerta());
					domicilioNotificacionVO.setVigente(false);
	
					if (Utilidades.isNotNull(domicilio.getUnidadUrbanaDTO().getCalleDTO().getCalleUbicacionDTO())) {
						domicilioNotificacionVO.setUbicacion(domicilio.getUnidadUrbanaDTO().getCalleDTO().getCalleUbicacionDTO().getUbicacion());
					}
					if (!Utilidades.isEmpty(domicilio.getUnidadUrbanaDTO().getCoordenadaX()) && !Utilidades.isEmpty(domicilio.getUnidadUrbanaDTO().getCoordenadaY()) ){
						domicilioNotificacionVO.setDisponeCoordenadasXY("true");
					}
					fxActual = Utilidades.getFechaActual();
					
					 
					List<DomicilioNotificacionDTO> listaDomiciliosNotificacion = new ArrayList<DomicilioNotificacionDTO>(domicilio.getDomicilioNotificacionDTOs());
					Collections.sort(listaDomiciliosNotificacion);
					List<DomicilioNotificacionDTO> domicilios = new ArrayList<DomicilioNotificacionDTO>();
					for(DomicilioNotificacionDTO notificacion : listaDomiciliosNotificacion) {
//						DomicilioNotificacionDTO notificacion = i.next();
						
						MunicipioDTO municipio = MunicipioConceptoModeloUtil.getMunicipioDTO(notificacion.getMunicipioDTO().getId().getCoProvincia(), notificacion.getMunicipioDTO().getId().getCoMunicipio());
						ConceptoDTO concepto = MunicipioConceptoModeloUtil.getConceptoDTO(notificacion.getConceptoDTO().getCoConcepto());
						
						notificacion.setMunicipioDTO(municipio);
						notificacion.setConceptoDTO(concepto);					
						domicilios.add(notificacion);
						
						Date desde = notificacion.getFxVigenciaDesde();
						Date hasta = notificacion.getFxVigenciaHasta();
						if ((fxActual.compareTo(hasta) < 0 || fxActual.compareTo(hasta) == 0) 
								&& (fxActual.compareTo(desde) > 0 || fxActual.compareTo(desde) == 0)) {
							domicilioNotificacionVO.setVigente(true);
						}					
					}
					domicilioNotificacionVO.setDomicilios(domicilios);
					if(domicilios.size() > 0)
						domicilioNotificacionVO.setTieneDomiciliosAsociados(true);
					else
						domicilioNotificacionVO.setTieneDomiciliosAsociados(false);
					domiciliosNotificacion.add(domicilioNotificacionVO);
				}
			}
		} catch (final Exception e) {
			throw new GadirServiceException(
					"Ocurrio un error al obtener los domicilios notificacion asociados al cliente", e);
		}	
		return domiciliosNotificacion;
	}
	
	public List<DomicilioVO> findByClienteAndOrMunicipioCalleSinNotificacion(final String coCliente, final String codProvincia, final String codMunicipio, final String codCalle) throws GadirServiceException {
		List<DomicilioVO> domicilios = new ArrayList<DomicilioVO>();
		String sqlString;
		sqlString = "from DomicilioDTO cl " +
		   				"left join fetch cl.unidadUrbanaDTO " +
		   				"left join fetch cl.unidadUrbanaDTO.calleDTO " +
		   				"left join fetch cl.unidadUrbanaDTO.calleDTO.municipioDTO " +
		   				"left join fetch cl.unidadUrbanaDTO.calleDTO.calleUbicacionDTO "+
		   				"left join fetch cl.unidadUrbanaDTO.calleDTO.municipioDTO.provinciaDTO " +
		   			"where cl.boNotificacion != 1 and cl.clienteDTO.coCliente = "+coCliente+" ";
		try {
			if (Utilidades.isNotEmpty(codProvincia)  && Utilidades.isNumeric(codProvincia)) {
				sqlString = sqlString + "and cl.unidadUrbanaDTO.calleDTO.municipioDTO.id.coProvincia like '"+codProvincia+"' ";
			}
			if (Utilidades.isNotEmpty(codMunicipio)  && Utilidades.isNumeric(codMunicipio)) {
				sqlString = sqlString + "and cl.unidadUrbanaDTO.calleDTO.municipioDTO.id.coMunicipio like '"+codMunicipio+"' ";
			}
			else if(Utilidades.isNotEmpty(codMunicipio) )
			{
				sqlString = sqlString + "and cl.unidadUrbanaDTO.calleDTO.municipioDTO.nombre like '"+codMunicipio+"' ";
			}
			if (Utilidades.isNotEmpty(codCalle) && Utilidades.isNumeric(codCalle)) {
				sqlString = sqlString + "and cl.unidadUrbanaDTO.calleDTO.coCalle = "+codCalle+" ";
			}
			else if(Utilidades.isNotEmpty(codCalle))
			{
				sqlString = sqlString + "and cl.unidadUrbanaDTO.calleDTO.nombreCalle like '"+codCalle+"' ";
			}
			
			sqlString = sqlString + "order by cl.coDomicilio";
			Iterator<DomicilioDTO> it = this.getDao().findByQuery(sqlString).iterator();
			while (it.hasNext()) {
				DomicilioDTO domicilio = it.next();
				DomicilioVO domicilioVO = new DomicilioVO();
				domicilioVO.setCoDomicilio(domicilio.getCoDomicilio());
				domicilioVO.setDomicilioAEAT(domicilio.getBoFiscalAeat());
				// Miramos el especial caso de domicilio tributario
//				if (Utilidades.isNotNull(domicilio.getCoDomicilio())) {
//					final HashMap map = new HashMap();
//					map.put("coDomicilio", new Long(domicilio.getCoDomicilio()));
//					List<Long> temporal = (List<Long>) this
//							.getDao()
//							.ejecutaNamedQuerySelect(
//									"Cliente.findByIdClienteYDomicilioTributario",
//									map);
//
//					if (null != temporal && temporal.size() > 0)
//						domicilio.setBoFiscalMunicipal(true);
//					else
//						domicilio.setBoFiscalMunicipal(false);
//				} else
//					domicilio.setBoFiscalMunicipal(false);
//				domicilioVO.setDomicilioTributario(domicilio.getBoFiscalMunicipal());
				
				domicilioVO.setDomicilioTributario(domicilio.isBoTributario());
				domicilioVO.setDomicilioKm(domicilio.getUnidadUrbanaDTO().getKm());
				domicilioVO.setDomicilioNum(domicilio.getUnidadUrbanaDTO().getNumero());
				domicilioVO.setDomicilioNombreVia(domicilio.getUnidadUrbanaDTO().getCalleDTO().getNombreCalle());
				domicilioVO.setDomicilioSigla(domicilio.getUnidadUrbanaDTO().getCalleDTO().getSigla());
				domicilioVO.setMunicipio(domicilio.getUnidadUrbanaDTO().getCalleDTO().getMunicipioDTO().getNombre());
				domicilioVO.setProvincia(domicilio.getUnidadUrbanaDTO().getCalleDTO().getMunicipioDTO().getProvinciaDTO().getNombre());
				domicilioVO.setDomicilioLetra(domicilio.getUnidadUrbanaDTO().getLetra());
				domicilioVO.setDomicilioBloque(domicilio.getUnidadUrbanaDTO().getBloque());
				domicilioVO.setDomicilioEscalera(domicilio.getUnidadUrbanaDTO().getEscalera());
				domicilioVO.setDomicilioPlanta(domicilio.getUnidadUrbanaDTO().getPlanta());
				domicilioVO.setDomicilioPuerta(domicilio.getUnidadUrbanaDTO().getPuerta());
				domicilioVO.setDomicilioNotificacion(domicilio.getBoNotificacion());
				
				if (Utilidades.isNotEmpty(domicilio.getProcedencia())) {
					KeyValue tfuepro = TablaGt.getCodigoDescripcion(TablaGt.TABLA_FUENTE_PROCEDENCIA, domicilio.getProcedencia());
					if(tfuepro.getValue() != null){
						domicilioVO.setProcedencia(tfuepro.getValue());
					}
					if (Utilidades.isEmpty(domicilioVO.getProcedencia())) {
						UnidadAdministrativaDTO unidad = this.getUnidadAdministrativaDAO().findById(domicilio.getProcedencia());
						if (unidad != null && Utilidades.isNotEmpty(unidad.getNombre())) {
							domicilioVO.setProcedencia(unidad.getNombre());
						}
					}
					
					if(Utilidades.isEmpty(domicilioVO.getProcedencia())){
						domicilioVO.setProcedencia(domicilio.getProcedencia());
					}
				}
				
				if (Utilidades.isNotNull(domicilio.getUnidadUrbanaDTO().getCalleDTO().getCalleUbicacionDTO())) {
					domicilioVO.setUbicacion(domicilio.getUnidadUrbanaDTO().getCalleDTO().getCalleUbicacionDTO().getUbicacion());
				}
				
				domicilioVO.setFxAlta(domicilio.getFxAlta());
				
				domicilios.add(domicilioVO);
			}
		} catch (final Exception e) {
			throw new GadirServiceException(
					"Ocurrio un error al obtener los domicilios asociados al cliente", e);
		}	
		return domicilios;
	}
	
	public List<DomicilioDTO> findByClienteAndUnidadUrbana(final String coCliente, final String coUnidadUrbana) throws GadirServiceException {
		Map<String, Object> parametros = new HashMap<String, Object>();
		try {
			parametros.put("cliente", new Long(coCliente));
			parametros.put("unidadUrbana", new Long(coUnidadUrbana));
			return findByNamedQuery("Domicilio.findByClienteAndUnidadUrbana", parametros);
		} catch (final Exception e) {
			throw new GadirServiceException(
					"Ocurrio un error al obtener el domicilio seleccionado", e);
		}	
	}
	
	public void actualizarDomicilio(DomicilioDTO domicilio, String Movimiento) throws GadirServiceException {
		// Actualizamos
		this.save(domicilio);		
	}
	
	public DAOBase<HDomicilioDTO, Long> getHDomicilioDAO() {
		return hDomicilioDAO;
	}

	public void setHDomicilioDAO(DAOBase<HDomicilioDTO, Long> domicilioDAO) {
		hDomicilioDAO = domicilioDAO;
	}
	
	public DAOBase<DomicilioDTO, Long> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<DomicilioDTO, Long> dao) {
		this.dao = dao;
	}
	
	/**
	 * @return the unidadAdministrativaDAO
	 */
	public DAOBase<UnidadAdministrativaDTO, String> getUnidadAdministrativaDAO() {
		return unidadAdministrativaDAO;
	}

	/**
	 * @param unidadAdministrativaDAO the unidadAdministrativaDAO to set
	 */
	public void setUnidadAdministrativaDAO(
			DAOBase<UnidadAdministrativaDTO, String> unidadAdministrativaDAO) {
		this.unidadAdministrativaDAO = unidadAdministrativaDAO;
	}

	public void eliminarDomicilio(DomicilioDTO domicilio)
			throws GadirServiceException {
		// Actualizamos
		this.getDao().delete(domicilio.getCoDomicilio());
		
		// Registramos el cambio en el histórico
		HDomicilioDTO hDomicilio = new HDomicilioDTO();
		
		hDomicilio.setCoDomicilio(domicilio.getCoDomicilio());
		hDomicilio.setBoFiscalAeat(domicilio.getBoFiscalAeat());
		hDomicilio.setBoFiscalMunicipal(domicilio.getBoFiscalMunicipal());
		hDomicilio.setBoNotificacion(domicilio.getBoNotificacion());
		hDomicilio.setCoCliente((null != domicilio.getClienteDTO())?domicilio.getClienteDTO().getCoCliente():null);
		hDomicilio.setCoUnidadUrbana((null != domicilio.getUnidadUrbanaDTO())?domicilio.getUnidadUrbanaDTO().getCoUnidadUrbana():null);
		hDomicilio.setHTipoMovimiento("B");
		hDomicilio.setFxAlta(domicilio.getFxAlta());
		hDomicilio.setProcedencia(domicilio.getProcedencia());
		hDomicilio.setFhActualizacion(new Date());
		hDomicilio.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		hDomicilioDAO.saveOnly(hDomicilio);
	}
	
	
	
	public List<DomicilioDTO> findByCliente(final String coCliente) throws GadirServiceException{
		List<DomicilioDTO> lista;
		final DetachedCriteria criteria = DetachedCriteria.forClass(DomicilioDTO.class);
		criteria.add(Restrictions.eq("clienteDTO.coCliente",Long.valueOf(coCliente)));
		
		lista=this.dao.findByCriteria(criteria);
	
		return lista;
		
		
	}
	public void auditorias(DomicilioDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}

	public DomicilioDTO findDomicilioFiscalByCliente(Long coCliente, final boolean conClienteDTO) throws GadirServiceException {
		DetachedCriteria criteria = DetachedCriteria.forClass(DomicilioDTO.class, "dom");
		criteria.createAlias("dom.unidadUrbanaDTO", "uu");
		criteria.createAlias("uu.calleDTO", "cal");
		criteria.createAlias("cal.municipioDTO", "mun");
		criteria.createAlias("mun.provinciaDTO", "pro");
		criteria.createAlias("mun.paisDTO", "pai");
		criteria.add(Restrictions.eq("clienteDTO.coCliente", coCliente));
		criteria.add(Restrictions.eq("boFiscalMunicipal", true));
		criteria.add(Restrictions.sqlRestriction("ROWNUM=1"));
		if (conClienteDTO) {
			criteria.createAlias("clienteDTO", "cli");
		}
		List<DomicilioDTO> domicilioDTOs = findByCriteria(criteria);
		DomicilioDTO result;
		if (domicilioDTOs.size() > 0) {
			result = domicilioDTOs.get(0);
			if (result.getUnidadUrbanaDTO().getCalleDTO().getCalleUbicacionDTO() != null) {
				Hibernate.initialize(result.getUnidadUrbanaDTO().getCalleDTO().getCalleUbicacionDTO());
			}
		} else {
			result = null;
		}
		return result;
	}

	public DomicilioDTO findDomicilioByIdInicializado(Long coDomicilio, final boolean conClienteDTO) throws GadirServiceException {
		DetachedCriteria criteria = DetachedCriteria.forClass(DomicilioDTO.class, "dom");
		criteria.createAlias("dom.unidadUrbanaDTO", "uu");
		criteria.createAlias("uu.calleDTO", "cal");
		criteria.createAlias("cal.municipioDTO", "mun");
		criteria.createAlias("mun.provinciaDTO", "pro");
		criteria.createAlias("mun.paisDTO", "pai");
		criteria.add(Restrictions.eq("dom.coDomicilio", coDomicilio));
		if (conClienteDTO) {
			criteria.createAlias("clienteDTO", "cli");
		}
		List<DomicilioDTO> domicilioDTOs = findByCriteria(criteria);
		DomicilioDTO result;
		if (domicilioDTOs.size() > 0) {
			result = domicilioDTOs.get(0);
			if (result.getUnidadUrbanaDTO().getCalleDTO().getCalleUbicacionDTO() != null) {
				Hibernate.initialize(result.getUnidadUrbanaDTO().getCalleDTO().getCalleUbicacionDTO());
			}
		} else {
			result = null;
		}
		return result;
	}
}
