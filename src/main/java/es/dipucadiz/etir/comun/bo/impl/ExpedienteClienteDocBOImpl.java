package es.dipucadiz.etir.comun.bo.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.ClienteBO;
import es.dipucadiz.etir.comun.bo.ExpedienteClienteDocBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.displaytag.GadirPaginatedList;
import es.dipucadiz.etir.comun.dto.ClienteDTO;
import es.dipucadiz.etir.comun.dto.ExpedienteClienteDocDTO;
import es.dipucadiz.etir.comun.dto.ExpedienteDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.DomicilioUtil;
import es.dipucadiz.etir.comun.utilidades.KeyValue;
import es.dipucadiz.etir.comun.utilidades.MunicipioConceptoModeloUtil;
import es.dipucadiz.etir.comun.utilidades.TablaGt;
import es.dipucadiz.etir.comun.utilidades.TablaGtConstants;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.sb06.G63TramitacionExpediente.C63ClienteVO;
import es.dipucadiz.etir.sb07.tributos.action.G743MantenimientoDocumentos.G743DocumentoVO;

public class ExpedienteClienteDocBOImpl extends AbstractGenericBOImpl<ExpedienteClienteDocDTO, Long> implements ExpedienteClienteDocBO {

	private static final long serialVersionUID = 386079709042823720L;
	private static final Log LOG = LogFactory.getLog(ExpedienteClienteDocBOImpl.class);
	 
	private DAOBase<ExpedienteClienteDocDTO, Long> dao;

	public DAOBase<ExpedienteClienteDocDTO, Long> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<ExpedienteClienteDocDTO, Long> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}
	
	
	public void auditorias(ExpedienteClienteDocDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}

	public DetachedCriteria getCriterioDocumentosEtirByExpedienteClienteDoc(ExpedienteDTO expedienteDTO, boolean ordenar, boolean limiteUno) {
		DetachedCriteria criterio = DetachedCriteria.forClass(ExpedienteClienteDocDTO.class, "exd");
		criterio.createAlias("exd.documentoDTO", "doc");
		criterio.createAlias("doc.documentoLiquidacionDTO", "docLiq", DetachedCriteria.LEFT_JOIN);
		criterio.createAlias("doc.clienteDTO", "cli");
		criterio.add(Restrictions.eq("exd.expedienteDTO", expedienteDTO));
		if (limiteUno) {
			criterio.add(Restrictions.sqlRestriction("rownum<=1"));
		} 
//		else {
//			criterio.add(Restrictions.sqlRestriction("rownum<=200"));
//		}
		if (ordenar) {
			criterio.addOrder(Order.asc("doc.id.coModelo"));
			criterio.addOrder(Order.asc("doc.id.coVersion"));
			criterio.addOrder(Order.asc("doc.id.coDocumento"));
		}
		return criterio;
	}
	
	public List<G743DocumentoVO> findDocumentosEtirByExpedienteClienteDoc(ExpedienteDTO expedienteDTO, Long coExpedienteSeguimiento, boolean permitirBorrarTodos) {
		List<G743DocumentoVO> result = new ArrayList<G743DocumentoVO>();
		try {
			List<ExpedienteClienteDocDTO> expedientClienteDocDTOs = findByCriteria(getCriterioDocumentosEtirByExpedienteClienteDoc(expedienteDTO, true, false));
			
			Map<String, String> situacionHash = new HashMap<String, String>();
			int cont=0;
			for (ExpedienteClienteDocDTO dto : expedientClienteDocDTOs) {
				G743DocumentoVO vo = new G743DocumentoVO(dto.getDocumentoDTO().getId().getCoModelo(), dto.getDocumentoDTO().getId().getCoVersion(), dto.getDocumentoDTO().getId().getCoDocumento());
				if (dto.getDocumentoDTO().getClienteDTO() != null) {
					vo.setCliente(dto.getDocumentoDTO().getClienteDTO().getNifNombre());
				}
				vo.setRefTrib1(dto.getDocumentoDTO().getRefObjTributario1());
				if (dto.getDocumentoDTO().getDomicilioDTOByCoDomicilio() != null) {
					vo.setDomicilio(DomicilioUtil.getDescripcionDomicilio(dto.getDocumentoDTO().getDomicilioDTOByCoDomicilio().getCoDomicilio(), false));
				}
				vo.setMunicipio(MunicipioConceptoModeloUtil.getMunicipioCodigoDescripcion(dto.getDocumentoDTO().getMunicipioDTO().getId().getCoProvincia(), dto.getDocumentoDTO().getMunicipioDTO().getId().getCoMunicipio()));
				vo.setConcepto(MunicipioConceptoModeloUtil.getConceptoCodigoDescripcion(dto.getDocumentoDTO().getConceptoDTO().getCoConcepto()));
				vo.setEjercicio("" + dto.getDocumentoDTO().getEjercicio());
				vo.setPeriodo(dto.getDocumentoDTO().getPeriodo());
				
				
				if( dto.getDocumentoDTO().getSituacionDTO()!=null){
					String coSituacion = dto.getDocumentoDTO().getSituacionDTO().getCoSituacion();
				if (!situacionHash.containsKey(coSituacion)) {
					Hibernate.initialize(dto.getDocumentoDTO().getSituacionDTO());
					situacionHash.put(coSituacion, dto.getDocumentoDTO().getSituacionDTO().getCodigoDescripcion());
				}
			
				vo.setSituacion(situacionHash.get(coSituacion));
				}
				vo.setBorrable(permitirBorrarTodos || coExpedienteSeguimiento != null);
				
				vo.setImporte("");
				
				if(dto.getDocumentoDTO().getDocumentoLiquidacionDTO()!=null && dto.getDocumentoDTO().getDocumentoLiquidacionDTO().getImPrincipal() != null){
					vo.setImporte(Utilidades.bigDecimalToImporteGadir((dto.getDocumentoDTO().getDocumentoLiquidacionDTO().getImPrincipal())));
				}
				cont++;
				vo.setFila(String.valueOf(cont));
				result.add(vo);
			}
		} catch (GadirServiceException e) {
			LOG.error(e.getMensaje(), e);
		}
		return result;
	}	
	 
	public GadirPaginatedList<C63ClienteVO> findClienteEtirByExpedienteSubexpedientesEstadosPaginado(ExpedienteDTO expedienteDTO,String clienteFiltro, String estadoFiltro, Long coExpedienteSeguimiento, int porPagina, int page, String sort, String dir, int firstResult, int maxResults, ClienteBO clienteBO,  List<KeyValue> listaEstados) {
		List<C63ClienteVO> result = new ArrayList<C63ClienteVO>();
		GadirPaginatedList<C63ClienteVO> listaPaginada = null; 
		try {
			DetachedCriteria criterio = getCriterioClientesEtirByExpedienteSubexpedientesEstadosSinRownum(expedienteDTO,clienteFiltro, estadoFiltro, false, sort, dir);
			int total = 0;
			if(Utilidades.isNotNull(criterio)){
				total = findByCriteriaGenerico(criterio).size();//countByCriteria(criterio);
			}
			criterio = getCriterioClientesEtirByExpedienteSubexpedientesEstadosSinRownum(expedienteDTO, clienteFiltro, estadoFiltro, true, sort, dir);
			
			
			if(Utilidades.isNotNull(criterio)){
				
				if(total>=0){
					List<Object[]> expedientClienteDocDTOs = findByCriteriaGenerico(criterio,firstResult,maxResults);
					 
					Map<String, String> situacionHash = new HashMap<String, String>();
					int cont=0;
					for (Object[] cli : expedientClienteDocDTOs) {
	 					C63ClienteVO vo = new C63ClienteVO();
						vo.setCoC63Cliente(String.valueOf(cli[0]));
						vo.setCoSubexpediente(String.valueOf(cli[1]));
						vo.setEstado(String.valueOf(cli[2]));
						ClienteDTO  clienteDTO  = clienteBO.findById(Long.valueOf(vo.getCoC63Cliente()));
						vo.setCliente(clienteDTO.getIdentificador()+" - "+clienteDTO.getRazonSocial());
 
						for(KeyValue kv:  listaEstados){
							if(kv.getKey().equals(String.valueOf(cli[2]))){
								vo.setEstado(kv.getKey()+" - "+kv.getValue());
							}
						}
						
						 
//						vo.setMunicipio(MunicipioConceptoModeloUtil.getMunicipioCodigoDescripcion(cli.getDocumentoDTO().getMunicipioDTO().getId().getCoProvincia(), cli.getDocumentoDTO().getMunicipioDTO().getId().getCoMunicipio()));
//						vo.setConcepto(MunicipioConceptoModeloUtil.getConceptoCodigoDescripcion(cli.getDocumentoDTO().getConceptoDTO().getCoConcepto()));
//						vo.setEjercicio("" + cli.getDocumentoDTO().getEjercicio());
//						vo.setPeriodo(cli.getDocumentoDTO().getPeriodo());
//						
//						
//						if( dto.getDocumentoDTO().getSituacionDTO()!=null){
//							String coSituacion = cli.getDocumentoDTO().getSituacionDTO().getCoSituacion();
//						if (!situacionHash.containsKey(coSituacion)) {
//							Hibernate.initialize(cli.getDocumentoDTO().getSituacionDTO());
//							situacionHash.put(coSituacion, cli.getDocumentoDTO().getSituacionDTO().getCodigoDescripcion());
//						}
					
	//					vo.setEstado(cli.getEstado());
	//					vo.setCoSubexpediente(Long.toString(cli.getCoSubexpediente()));
	 					 
 
						cont++;
						 
	 		result.add(vo);
					}
					listaPaginada = new GadirPaginatedList<C63ClienteVO>(result, total, porPagina, page, "G62SeleccionRue", sort, dir);
				}
			}
			
			
			
		} catch (GadirServiceException e) {
			LOG.error(e.getMensaje(), e);
		}
		return listaPaginada;
	}	
	
	public GadirPaginatedList<G743DocumentoVO> findDocumentosEtirByExpedienteClienteDocPaginado(ExpedienteDTO expedienteDTO,String clienteFiltro, String estadoFiltro, Long coExpedienteSeguimiento, boolean permitirBorrarTodos,int porPagina, int page, String sort, String dir, int firstResult, int maxResults) {
		List<G743DocumentoVO> result = new ArrayList<G743DocumentoVO>();
		GadirPaginatedList<G743DocumentoVO> listaPaginada = null; 
		try {
			DetachedCriteria criterio = getCriterioDocumentosEtirByExpedienteClienteDocSinRownum(expedienteDTO,clienteFiltro, estadoFiltro, false, sort, dir);
			int total = 0;
			if(Utilidades.isNotNull(criterio)){
				total = countByCriteria(criterio);
			}
			criterio = getCriterioDocumentosEtirByExpedienteClienteDocSinRownum(expedienteDTO, clienteFiltro, estadoFiltro, true, sort, dir);
			if(Utilidades.isNotNull(criterio)){
				
				if(total>=0){
					List<ExpedienteClienteDocDTO> expedientClienteDocDTOs = findByCriteria(criterio,firstResult,maxResults);
					
					Map<String, String> situacionHash = new HashMap<String, String>();
					int cont=0;
					for (ExpedienteClienteDocDTO dto : expedientClienteDocDTOs) {
						G743DocumentoVO vo = new G743DocumentoVO(dto.getDocumentoDTO().getId().getCoModelo(), dto.getDocumentoDTO().getId().getCoVersion(), dto.getDocumentoDTO().getId().getCoDocumento());
						if (dto.getDocumentoDTO().getClienteDTO() != null) {
							vo.setCliente(dto.getDocumentoDTO().getClienteDTO().getNifNombre());
						}
						vo.setRefTrib1(dto.getDocumentoDTO().getRefObjTributario1());
						if (dto.getDocumentoDTO().getDomicilioDTOByCoDomicilio() != null) {
							vo.setDomicilio(DomicilioUtil.getDescripcionDomicilio(dto.getDocumentoDTO().getDomicilioDTOByCoDomicilio().getCoDomicilio(), false));
						}
						vo.setMunicipio(MunicipioConceptoModeloUtil.getMunicipioCodigoDescripcion(dto.getDocumentoDTO().getMunicipioDTO().getId().getCoProvincia(), dto.getDocumentoDTO().getMunicipioDTO().getId().getCoMunicipio()));
						vo.setConcepto(MunicipioConceptoModeloUtil.getConceptoCodigoDescripcion(dto.getDocumentoDTO().getConceptoDTO().getCoConcepto()));
						vo.setEjercicio("" + dto.getDocumentoDTO().getEjercicio());
						vo.setPeriodo(dto.getDocumentoDTO().getPeriodo());
						
						
						if( dto.getDocumentoDTO().getSituacionDTO()!=null){
							String coSituacion = dto.getDocumentoDTO().getSituacionDTO().getCoSituacion();
						if (!situacionHash.containsKey(coSituacion)) {
							Hibernate.initialize(dto.getDocumentoDTO().getSituacionDTO());
							situacionHash.put(coSituacion, dto.getDocumentoDTO().getSituacionDTO().getCodigoDescripcion());
						}
					
						vo.setSituacion(situacionHash.get(coSituacion));
						}
						vo.setBorrable(permitirBorrarTodos || coExpedienteSeguimiento != null);
						
						vo.setImporte("");
						
						if(dto.getDocumentoDTO().getDocumentoLiquidacionDTO()!=null && dto.getDocumentoDTO().getDocumentoLiquidacionDTO().getImPrincipal() != null){
							vo.setImporte(Utilidades.bigDecimalToImporteGadir((dto.getDocumentoDTO().getDocumentoLiquidacionDTO().getImPrincipal())));
						}
						cont++;
						vo.setFila(String.valueOf(cont));
						result.add(vo);
					}
					listaPaginada = new GadirPaginatedList<G743DocumentoVO>(result, total, porPagina, page, "G62SeleccionRue", sort, dir);
				}
			}
			
			
			
		} catch (GadirServiceException e) {
			LOG.error(e.getMensaje(), e);
		}
		return listaPaginada;
	}	
	
	public DetachedCriteria getCriterioDocumentosEtirByExpedienteClienteDocSinRownum(ExpedienteDTO expedienteDTO,String clienteFiltro, String estadoFiltro, boolean ordenar, String sort, String dir) {
		DetachedCriteria criterio = DetachedCriteria.forClass(ExpedienteClienteDocDTO.class, "exd");
		criterio.createAlias("exd.documentoDTO", "doc");
		criterio.createAlias("doc.documentoLiquidacionDTO", "docLiq", DetachedCriteria.LEFT_JOIN);
		criterio.createAlias("doc.clienteDTO", "cli");
		criterio.add(Restrictions.eq("exd.expedienteDTO", expedienteDTO));
		if(Utilidades.isNotEmpty(clienteFiltro)){
			criterio.add(Restrictions.eq("exd.clienteDTO.coCliente", Long.parseLong(clienteFiltro)));
		}
		if (Utilidades.isNotEmpty(estadoFiltro)) {
			criterio.add(Restrictions.eq("exd.estado", estadoFiltro));
		}
		
		if (ordenar) {
			if(Utilidades.isEmpty(sort)){
				sort = "codigoConEspacios";
			}
			if ("codigoConEspacios".equals(sort)) {
				if ("desc".equals(dir)) {
					criterio.addOrder(Order.desc("doc.id.coModelo"));
					criterio.addOrder(Order.desc("doc.id.coVersion"));
					criterio.addOrder(Order.desc("doc.id.coDocumento"));
				}else{
					criterio.addOrder(Order.asc("doc.id.coModelo"));
					criterio.addOrder(Order.asc("doc.id.coVersion"));
					criterio.addOrder(Order.asc("doc.id.coDocumento"));
				}
			}
			if ("cliente".equals(sort)) {
				if ("desc".equals(dir)) {
					criterio.addOrder(Order.desc("cli.identificador"));
				}else{
					criterio.addOrder(Order.asc("cli.identificador"));
				}
			}
			if ("refTrib1".equals(sort)) {
				if ("desc".equals(dir)) {
					criterio.addOrder(Order.desc("doc.refObjTributario1"));
				}else{
					criterio.addOrder(Order.asc("doc.refObjTributario1"));
				}
			}
			if ("municipio".equals(sort)) {
				if ("desc".equals(dir)) {
					criterio.addOrder(Order.desc("doc.municipioDTO.id.coProvincia"));
    				criterio.addOrder(Order.desc("doc.municipioDTO.id.coMunicipio"));
				}else{
					criterio.addOrder(Order.desc("doc.municipioDTO.id.coProvincia"));
    				criterio.addOrder(Order.desc("doc.municipioDTO.id.coMunicipio"));
				}
			}
			if ("ejercicio".equals(sort)) {
				if ("desc".equals(dir)) {
					criterio.addOrder(Order.desc("doc.ejercicio"));
				}else{
					criterio.addOrder(Order.asc("doc.ejercicio"));
				}
			}
		}
		return criterio;
	}
	
	public DetachedCriteria getCriterioClientesEtirByExpedienteSubexpedientesEstadosSinRownum(ExpedienteDTO expedienteDTO,String clienteFiltro, String estadoFiltro, boolean ordenar, String sort, String dir) {
		DetachedCriteria criterio = DetachedCriteria.forClass(ExpedienteClienteDocDTO.class, "exd");
		//criterio.createAlias("exd.documentoDTO", "doc");
		//criterio.createAlias("doc.documentoLiquidacionDTO", "docLiq", DetachedCriteria.LEFT_JOIN);
		criterio.createAlias("clienteDTO", "cli");
		criterio.add(Restrictions.eq("exd.expedienteDTO", expedienteDTO));
		
 
		
		criterio.setProjection(	Projections.projectionList() 
				.add(Projections.groupProperty("cli.coCliente"))
				.add(Projections.groupProperty("coSubexpediente"))
				.add(Projections.groupProperty("estado"))
				//.add(Projections.groupProperty("cli.identificador"))
				);
		
		
		if(Utilidades.isNotEmpty(clienteFiltro)){
			criterio.add(Restrictions.eq("exd.clienteDTO.coCliente", Long.parseLong(clienteFiltro)));
		}
		if (Utilidades.isNotEmpty(estadoFiltro)) {
			criterio.add(Restrictions.eq("exd.estado", estadoFiltro));
		}
		
		if (ordenar) {
		//				if(Utilidades.isEmpty(sort)){
			//					criterio.addOrder(Order.desc("cli.identificador"));
			//				}
//			if ("codigoConEspacios".equals(sort)) {
//				if ("desc".equals(dir)) {
//					criterio.addOrder(Order.desc("doc.id.coModelo"));
//					criterio.addOrder(Order.desc("doc.id.coVersion"));
//					criterio.addOrder(Order.desc("doc.id.coDocumento"));
//				}else{
//					criterio.addOrder(Order.asc("doc.id.coModelo"));
//					criterio.addOrder(Order.asc("doc.id.coVersion"));
//					criterio.addOrder(Order.asc("doc.id.coDocumento"));
//				}
//			}
			if ("cliente".equals(sort)) {
				if ("desc".equals(dir)) {
					criterio.addOrder(Order.desc("cli.identificador"));
				}else{
					criterio.addOrder(Order.asc("cli.identificador"));
				}
			}
//			if ("refTrib1".equals(sort)) {
//				if ("desc".equals(dir)) {
//					criterio.addOrder(Order.desc("doc.refObjTributario1"));
//				}else{
//					criterio.addOrder(Order.asc("doc.refObjTributario1"));
//				}
//			}
//			if ("municipio".equals(sort)) {
//				if ("desc".equals(dir)) {
//					criterio.addOrder(Order.desc("doc.municipioDTO.id.coProvincia"));
//    				criterio.addOrder(Order.desc("doc.municipioDTO.id.coMunicipio"));
//				}else{
//					criterio.addOrder(Order.desc("doc.municipioDTO.id.coProvincia"));
//    				criterio.addOrder(Order.desc("doc.municipioDTO.id.coMunicipio"));
//				}
//			}
//			if ("ejercicio".equals(sort)) {
//				if ("desc".equals(dir)) {
//					criterio.addOrder(Order.desc("doc.ejercicio"));
//				}else{
//					criterio.addOrder(Order.asc("doc.ejercicio"));
//				}
//			}
		}
		return criterio;
	}
	

}