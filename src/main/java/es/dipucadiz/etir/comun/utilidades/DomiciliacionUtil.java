package es.dipucadiz.etir.comun.utilidades;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.DomiciliacionBO;
import es.dipucadiz.etir.comun.bo.MunicipioBO;
import es.dipucadiz.etir.comun.displaytag.GadirPaginatedList;
import es.dipucadiz.etir.comun.dto.ConceptoDTO;
import es.dipucadiz.etir.comun.dto.DomiciliacionDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.sb07.domiciliaciones.action.G7A1.G7A1TributoVO;

public class DomiciliacionUtil {
	private static DomiciliacionBO domiciliacionBO;
	private static MunicipioBO municipioBO;
	private static final Log LOG = LogFactory.getLog(DomiciliacionUtil.class);
	
	/**
	 * Devolver domiciliaciones hábiles a darse de alta para un cliente.
	 * @param coCliente
	 * @return
	 */
	public static List<G7A1TributoVO> getTributosDomiciliables(final String coCliente, final String coProvincia, final String coMunicipio) {
		List<G7A1TributoVO> listaTributosVO = new ArrayList<G7A1TributoVO>();
		String cadena="";
		String[] cadenaSplit = new String[0];
		boolean hayMas=false;
		try{
			Object o = domiciliacionBO.ejecutaQuerySelect("SELECT FU_G7A1_TRIBUTOS_DOMICILIABLES('" + coCliente + "', '" + DatosSesion.getCodigoTerritorialGenerico() + "', '" + coProvincia + "', '" + coMunicipio + "', '', '') FROM DUAL");
			//System.out.println(o);
			cadena= (String)((List<?>)o).get(0);
			hayMas = cadena.endsWith("#+");
			cadenaSplit = cadena.split("#");
		}catch(Exception e){

		}
		
		for (int i=0; i <cadenaSplit.length; i++){
			
			if(!Utilidades.isEmpty(cadenaSplit[i]) && !"+".equals(cadenaSplit[i])){
				try{
					String[] datos = cadenaSplit[i].split("\\|");
					
					G7A1TributoVO tributo= new G7A1TributoVO();
					
					// Posible control de filtro por municipio
					if (Utilidades.isNotEmpty(coProvincia) && Utilidades.isNotEmpty(coMunicipio)) {
						if (datos[0].equals(coProvincia + coMunicipio)) {
							tributo.setCoMunicipio(datos[0]);
							tributo.setCoConcepto(datos[1]);
							tributo.setConcepto(MunicipioConceptoModeloUtil.getConceptoCodigoDescripcion(datos[1]));
							if(datos.length==4)
								tributo.setDomicilio(DomicilioUtil.getDescripcionDomicilio(Long.valueOf(datos[3]), false));							
							tributo.setMunicipio(MunicipioConceptoModeloUtil.getMunicipioCodigoDescripcion(datos[0]));
							tributo.setRefObjTrib((String)datos[2]);

							listaTributosVO.add(tributo);
						}
					} else {
						tributo.setCoMunicipio(datos[0]);
						tributo.setCoConcepto(datos[1]);
						tributo.setConcepto(MunicipioConceptoModeloUtil.getConceptoCodigoDescripcion(datos[1]));
						tributo.setDomicilio(DomicilioUtil.getDescripcionDomicilio(Long.valueOf(datos[3]), false));
						tributo.setMunicipio(MunicipioConceptoModeloUtil.getMunicipioCodigoDescripcion(datos[0]));
						tributo.setRefObjTrib((String)datos[2]);

						tributo.setHayMas(hayMas);
						
						listaTributosVO.add(tributo);
					}
					
					
				}catch(Exception e){
					LOG.error(e);
				}
			}
		}
		
		return listaTributosVO;
	}
	// Sobrecarga de la función getTributosDomiciliables. 
	public static List<G7A1TributoVO> getTributosDomiciliables(final String coCliente, final String coProvincia, final String coMunicipio, final String coConcepto, final String refObjTrib) {
		List<G7A1TributoVO> listaTributosVO = new ArrayList<G7A1TributoVO>();
		String cadena="";
		String[] cadenaSplit = new String[0];
		boolean hayMas=false;
		try{
			String coProvinciaAux = coProvincia;
			String coMunicipioAux = coMunicipio;
			String coConceptoAux = coConcepto;
			String refObjTribAux = refObjTrib;
			if(Utilidades.isEmpty(coProvinciaAux)){
				coProvinciaAux="X";
			}
			if(Utilidades.isEmpty(coMunicipioAux)){
				coMunicipioAux="X";
			}
			
			if(Utilidades.isEmpty(coConceptoAux)){
				coConceptoAux="X";
			}
			
			if(Utilidades.isEmpty(refObjTribAux)){
				refObjTribAux="X";
			}
		 
			
			Object o = domiciliacionBO.ejecutaQuerySelect("SELECT FU_G7A1_TRIBUTOS_DOMICILIABLES('" + coCliente + "', '" + DatosSesion.getCodigoTerritorialGenerico()  + "', '" + coProvinciaAux + "', '" + coMunicipioAux + "', '" + coConceptoAux + "', '" + refObjTribAux+ "') FROM DUAL");
			//System.out.println(o);
			cadena= (String)((List<?>)o).get(0);
			hayMas = cadena.endsWith("#+");
			cadenaSplit = cadena.split("#");
		}catch(Exception e){

		}
		
		for (int i=0; i <cadenaSplit.length; i++){
			
			if(!Utilidades.isEmpty(cadenaSplit[i]) && !"+".equals(cadenaSplit[i])){
				try{
					String[] datos = cadenaSplit[i].split("\\|");
					
					G7A1TributoVO tributo= new G7A1TributoVO();
					
					// Posible control de filtro por municipio
					if (Utilidades.isNotEmpty(coProvincia) && Utilidades.isNotEmpty(coMunicipio)) {
						if (datos[0].equals(coProvincia + coMunicipio)) {
							tributo.setCoMunicipio(datos[0]);
							tributo.setCoConcepto(datos[1]);
							tributo.setMunicipio(MunicipioConceptoModeloUtil.getMunicipioCodigoDescripcion(datos[0]));							
							tributo.setConcepto(MunicipioConceptoModeloUtil.getConceptoCodigoDescripcion(datos[1]));
							tributo.setRefObjTrib((String)datos[2]);
							if(datos.length==4)
								tributo.setDomicilio(DomicilioUtil.getDescripcionDomicilio(Long.valueOf(datos[3]), false));
							listaTributosVO.add(tributo);
						}
					} else {
						tributo.setCoMunicipio(datos[0]);
						tributo.setCoConcepto(datos[1]);
						tributo.setMunicipio(MunicipioConceptoModeloUtil.getMunicipioCodigoDescripcion(datos[0]));
						tributo.setConcepto(MunicipioConceptoModeloUtil.getConceptoCodigoDescripcion(datos[1]));
						tributo.setRefObjTrib((String)datos[2]);
						if(datos.length==4)
							tributo.setDomicilio(DomicilioUtil.getDescripcionDomicilio(Long.valueOf(datos[3]), false));
	


						tributo.setHayMas(hayMas);
						
						listaTributosVO.add(tributo);
					}
					
					
				}catch(Exception e){
					LOG.error(e);
				}
			}
		}
		
		return listaTributosVO;
	}
	/**
	 * Devolver un DetachedCriteria de domiciliaciones hábiles a mantener o dar de baja según ciertos filtros.
	 * @param coCliente
	 * @param coClienteTitularCuenta
	 * @param coBanco
	 * @param coSucursal
	 * @param dc
	 * @param cuenta
	 * @param refDomiciliacion
	 * @param municipioRowid
	 * @param coConcepto
	 * @param refObjetoTributario
	 * @param confirmada
	 * @param temporal
	 * @param baja
	 * @param sort
	 * @param dir
	 * @param orden
	 * @return
	 * @throws GadirServiceException
	 */
	public static DetachedCriteria getCriteriaTributosDomiciliados(String coCliente, String coClienteTitularCuenta, String coBanco, String coSucursal, String dc, String cuenta, String refDomiciliacion, String municipioRowid, String coConcepto, String refObjetoTributario, boolean confirmada, boolean temporal, boolean baja, String sort, String dir, char modoAcceso, boolean orden) throws GadirServiceException {
		if (Utilidades.isEmpty(coCliente) && Utilidades.isEmpty(coClienteTitularCuenta) && Utilidades.isEmpty(cuenta) && Utilidades.isEmpty(refDomiciliacion) && Utilidades.isEmpty(municipioRowid) && Utilidades.isEmpty(coConcepto) && Utilidades.isEmpty(refObjetoTributario)) {
			throw new GadirServiceException("Búsqueda de domiciliaciones sin criterios de selección.");
		}
		
		final DetachedCriteria criteria = DetachedCriteria.forClass(DomiciliacionDTO.class);

		if(!Utilidades.isEmpty(coCliente)) {
			criteria.add(Restrictions.eq("clienteDTO.coCliente", Long.parseLong(coCliente)));
		}

		if(!Utilidades.isEmpty(coClienteTitularCuenta) || !Utilidades.isEmpty(coBanco)) {
			if(!Utilidades.isEmpty(coBanco)) {
				/*ClienteCuentaDTO cc = clienteCuentaBO.findFiltered(
					new String[]{"id.coBanco", "id.coBancoSucursal", "id.dc", "id.cuenta", "id.coCliente"}, 
					new Object[]{coBanco, coSucursal, dc, cuenta, Long.parseLong(coCliente)}).get(0);*/
				criteria.add(Restrictions.eq("clienteCuentaDTO.id.coBanco", coBanco));
				criteria.add(Restrictions.eq("clienteCuentaDTO.id.coBancoSucursal", coSucursal));
				criteria.add(Restrictions.eq("clienteCuentaDTO.id.dc", dc));
				criteria.add(Restrictions.eq("clienteCuentaDTO.id.cuenta", cuenta));
			}
			if(!Utilidades.isEmpty(coClienteTitularCuenta)) {
				criteria.add(Restrictions.eq("clienteCuentaDTO.id.coCliente", Long.parseLong(coClienteTitularCuenta)));
			}
		}else if (!Utilidades.isEmpty(refDomiciliacion)) {
			criteria.add(Restrictions.eq("refDomiciliacion", refDomiciliacion).ignoreCase());					
		}else{
			MunicipioDTO m = null;
			if(!Utilidades.isEmpty(municipioRowid)) {
				m = municipioBO.findByRowid(municipioRowid);
				if(!m.getId().getCoProvincia().equals("**") && !m.getId().getCoMunicipio().equals("***"))
					criteria.add(Restrictions.eq("municipioDTO.id", m.getId()));
			} else {
				List<MunicipioDTO> municipioDTOs = ControlTerritorial.getMunicipiosUsuario(false);
				criteria.add(Restrictions.in("municipioDTO", municipioDTOs));
			}

			if(!Utilidades.isEmpty(coConcepto) && !coConcepto.equals("****")) {
				criteria.add(Restrictions.eq("conceptoDTO.id",coConcepto));
			} else {
				List<ConceptoDTO> conceptoDTOs = ControlTerritorial.getConceptos(m==null?null:m.getCodigoCompleto(), null, (short)Utilidades.getAnoActual(), modoAcceso, "R");
				if (conceptoDTOs.isEmpty()) {
					criteria.add(Restrictions.sqlRestriction("1=2"));
				} else {
					criteria.add(Restrictions.in("conceptoDTO", conceptoDTOs));
				}
			}

			if(!Utilidades.isEmpty(refObjetoTributario)) {
				criteria.add(Restrictions.eq("refObjTributario", refObjetoTributario).ignoreCase());					
			}
		}
		
		//Control de acceso
		if(Utilidades.isEmpty(municipioRowid)) {
			criteria.add(Restrictions.sqlRestriction("CONTROL_ACCESOS.getModelos({alias}.co_provincia, {alias}.co_municipio, {alias}.co_concepto, null, " + Utilidades.getAnoActual() + ", '" + DatosSesion.getCodigoTerritorialGenerico() + "', " + (modoAcceso == ControlTerritorial.COMPLETO ? "null" : "'"+modoAcceso+"'") + ", 'R', '2') is not null"));
		}
		
		//Que no salgan PPP ni fraccionadas
		criteria.add(Restrictions.ne("tipo","P"));
		criteria.add(Restrictions.ne("tipo","F"));
		
		//Filtro
		if(temporal&& !baja && !confirmada)
			criteria.add(Restrictions.eq("tipo","T"));
		
		if(!temporal && baja && !confirmada)
			criteria.add(Restrictions.eq("tipo","B"));
		
		if(!temporal && !baja && confirmada)
			criteria.add(Restrictions.eq("tipo","C"));
		
		if(temporal && baja && !confirmada)
		{
			Criterion Ctemporal = Restrictions.eq("tipo","T");
			Criterion Cbaja = Restrictions.eq("tipo","B");
	        LogicalExpression orExp = Restrictions.or(Ctemporal,Cbaja);
	        criteria.add(orExp);
		}
			
		if(temporal && confirmada && !baja)
		{
			Criterion Ctemporal = Restrictions.eq("tipo","T");
			Criterion Cconfirmada = Restrictions.eq("tipo","C");
	        LogicalExpression orExp = Restrictions.or(Ctemporal,Cconfirmada);
            criteria.add(orExp);
		}
			
		if(baja && confirmada && !temporal)
		{
			Criterion Cconfirmada = Restrictions.eq("tipo","C");
			Criterion Cbaja = Restrictions.eq("tipo","B");
	        LogicalExpression orExp = Restrictions.or(Cconfirmada,Cbaja);
	        criteria.add(orExp);
		}
		
		criteria.setFetchMode("municipioDTO", FetchMode.JOIN);
		criteria.setFetchMode("conceptoDTO", FetchMode.JOIN);
		criteria.setFetchMode("clienteCuentaDTO", FetchMode.JOIN);
		criteria.setFetchMode("clienteCuentaDTO.clienteDTO", FetchMode.JOIN);
		//criteria.setFetchMode("clienteDTO", FetchMode.JOIN);
		criteria.createAlias("clienteDTO", "cli", DetachedCriteria.INNER_JOIN);
		
		if (orden)
		{
			if (Utilidades.isEmpty(sort)) {
				sort = "cliente";
				dir = GadirPaginatedList.ASCENDING;
			}
			
			if ("cliente".equals(sort)) {
				if (GadirPaginatedList.DESCENDING.equals(dir)) {
					criteria.addOrder(Order.desc("cli.razonSocial"));
					criteria.addOrder(Order.desc("refObjTributario"));
				} else {
					criteria.addOrder(Order.asc("cli.razonSocial"));
					criteria.addOrder(Order.asc("refObjTributario"));
				}
			}
			
			if ("municipio".equals(sort)) {
				if (GadirPaginatedList.DESCENDING.equals(dir)) {
					criteria.addOrder(Order.desc("municipioDTO.id.coMunicipio"));
				} else {
					criteria.addOrder(Order.asc("municipioDTO.id.coMunicipio"));
				}
			}
			
			if ("concepto".equals(sort)) {
				if (GadirPaginatedList.DESCENDING.equals(dir)) {
					criteria.addOrder(Order.desc("conceptoDTO.coConcepto"));
				} else {
					criteria.addOrder(Order.asc("conceptoDTO.coConcepto"));
				}
			}
			
			if ("refObj".equals(sort)) {
				if (GadirPaginatedList.DESCENDING.equals(dir)) {
					criteria.addOrder(Order.desc("refObjTributario"));
				} else {
					criteria.addOrder(Order.asc("refObjTributario"));
				}
			}
			
			if ("refDom".equals(sort)) {
				if (GadirPaginatedList.DESCENDING.equals(dir)) {
					criteria.addOrder(Order.desc("refDomiciliacion"));
				} else {
					criteria.addOrder(Order.asc("refDomiciliacion"));
				}
			}
			
			if ("cuenta".equals(sort)) {
				if (GadirPaginatedList.DESCENDING.equals(dir)) {
					criteria.addOrder(Order.desc("clienteCuentaDTO.id.coBanco"));
					criteria.addOrder(Order.desc("clienteCuentaDTO.id.coBancoSucursal"));
					criteria.addOrder(Order.desc("clienteCuentaDTO.id.dc"));
					criteria.addOrder(Order.desc("clienteCuentaDTO.id.cuenta"));
				} else {
					criteria.addOrder(Order.asc("clienteCuentaDTO.id.coBanco"));
					criteria.addOrder(Order.asc("clienteCuentaDTO.id.coBancoSucursal"));
					criteria.addOrder(Order.asc("clienteCuentaDTO.id.dc"));
					criteria.addOrder(Order.asc("clienteCuentaDTO.id.cuenta"));
				}
			}
			
			if ("titCuenta".equals(sort)) {
				if (GadirPaginatedList.DESCENDING.equals(dir)) {
					criteria.addOrder(Order.desc("clienteCuentaDTO.clienteDTO.identificador"));
				} else {
					criteria.addOrder(Order.asc("clienteCuentaDTO.clienteDTO.identificador"));
				}
			}
			
			if ("tipo".equals(sort)) {
				if (GadirPaginatedList.DESCENDING.equals(dir)) {
					criteria.addOrder(Order.desc("tipo"));
				} else {
					criteria.addOrder(Order.asc("tipo"));
				}
			}
			if ("fechaI".equals(sort)) {
				if (GadirPaginatedList.DESCENDING.equals(dir)) {
					criteria.addOrder(Order.desc("fxInicio"));
				} else {
					criteria.addOrder(Order.asc("fxInicio"));
				}
			}
			if ("fechaF".equals(sort)) {
				if (GadirPaginatedList.DESCENDING.equals(dir)) {
					criteria.addOrder(Order.desc("fxFinal"));
				} else {
					criteria.addOrder(Order.asc("fxFinal"));
				}
			}
//			if ("domicilioTrib".equals(sort)) {
//				if (GadirPaginatedList.DESCENDING.equals(dir)) {
//					criteria.addOrder(Order.desc("domicilioTrib"));
//				} else {
//					criteria.addOrder(Order.asc("domicilioTrib"));
//				}
//			}
		}

		return criteria;
	}

	public static DetachedCriteria getCriteriaTributosDomiciliados(String coCliente, String coClienteTitularCuenta, String coBanco, String coSucursal, String dc, String cuenta, String refDomiciliacion, String municipioRowid, String coConcepto, String refObjetoTributario, boolean confirmada, boolean temporal, boolean baja, String sort, String dir, boolean orden) throws GadirServiceException {
		return getCriteriaTributosDomiciliados(coCliente, coClienteTitularCuenta, coBanco, coSucursal, dc, cuenta, refDomiciliacion, municipioRowid, coConcepto, refObjetoTributario, confirmada, temporal, baja, sort, dir, ControlTerritorial.COMPLETO, orden);
	}

	
	public static String generarRefdom() throws GadirServiceException{
		Object o = domiciliacionBO.ejecutaQuerySelect("SELECT G535_ACTUALIZACION_CENSO.OBTENER_REF_DOMICILIACION FROM DUAL");
		String refDom=(String)((List<?>)o).get(0);
		return refDom;
	}
	
	public static String getCuentaDomiciliacion(Integer coDomiciliacion) {
		String result;
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(DomiciliacionDTO.class, "dom");
			criteria.createAlias("dom.clienteCuentaDTO", "ccli");
			criteria.createAlias("ccli.cuentaDTO", "cue");
			criteria.createAlias("cue.bancoSucursalDTO", "suc");
			criteria.createAlias("suc.bancoDTO", "ban");
			criteria.add(Restrictions.eq("coDomiciliacion", coDomiciliacion));
			List<DomiciliacionDTO> dtos = domiciliacionBO.findByCriteria(criteria);
			if (!dtos.isEmpty()) {
				DomiciliacionDTO domiciliacionDTO = dtos.get(0);
				result = domiciliacionDTO.getClienteCuentaDTO().getId().getCoBanco() + " " +
						domiciliacionDTO.getClienteCuentaDTO().getId().getCoBancoSucursal() + " " +
						domiciliacionDTO.getClienteCuentaDTO().getId().getDc() + " " +
						domiciliacionDTO.getClienteCuentaDTO().getId().getCuenta() + " " +
						domiciliacionDTO.getClienteCuentaDTO().getCuentaDTO().getBancoSucursalDTO().getBancoDTO().getNombre();
			} else {
				result = "n" + coDomiciliacion.toString();
			}
		} catch (GadirServiceException e) {
			LOG.error("Error recuperando cuenta domiciliación: " + e.getMensaje(), e);
			result = "e" + coDomiciliacion.toString();
		}
		return result;
	}

	public DomiciliacionBO getDomiciliacionBO() {
		return domiciliacionBO;
	}

	public void setDomiciliacionBO(DomiciliacionBO domiciliacionBO) {
		DomiciliacionUtil.domiciliacionBO = domiciliacionBO;
	}
	
	public MunicipioBO getMunicipioBO() {
		return municipioBO;
	}

	public void setMunicipioBO(MunicipioBO municipioBO) {
		DomiciliacionUtil.municipioBO = municipioBO;
	}
	
}
