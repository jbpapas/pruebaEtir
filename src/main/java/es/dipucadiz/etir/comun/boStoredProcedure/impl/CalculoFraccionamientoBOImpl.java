package es.dipucadiz.etir.comun.boStoredProcedure.impl;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import es.dipucadiz.etir.comun.boStoredProcedure.CalculoFraccionamientoBO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.Mensaje;
import es.dipucadiz.etir.sb07.G7D1Fraccionamiento.action.G7D1CalculoPlazoVO;
import es.dipucadiz.etir.sb07.G7D1Fraccionamiento.action.G7D1CalculoVO;



public class CalculoFraccionamientoBOImpl extends StoredProcedure implements CalculoFraccionamientoBO {

	private static final Log LOG = LogFactory.getLog(CalculoFraccionamientoBOImpl.class);

	public CalculoFraccionamientoBOImpl(final DataSource dataSource) {
		
		
//		function calculo_fraccionamiento_java(e_co_fraccionamiento in ga_fraccionamiento.co_fraccionamiento%type,
//                e_plazos in out varchar2, 
//                e_fechas in out varchar2,
//                e_garantizado in out varchar2,
//                e_im_ppal in out varchar2, 
//                e_im_recargo in out varchar2,
//                e_im_costas in out varchar2,
//                e_im_demora in out varchar2,
//                e_im_int_fracc in out varchar2,
//                e_im_int_fracc_sp in out varchar2) return integer;

		super(dataSource, "G7D1_GESTION_FRACCIONAMIENTO.calculo_fraccionamiento_java");
		LOG.info("G7D1_GESTION_FRACCIONAMIENTO.calculo_fraccionamiento_java");
		
		setFunction(true);	
		declareParameter(new SqlOutParameter("resultado", Types.NUMERIC));		
		declareParameter(new SqlParameter("e_coFraccionamiento", Types.NUMERIC));
		declareParameter(new SqlParameter("e_importe_plazo", Types.NUMERIC));
		 
		declareParameter(new SqlOutParameter("e_plazos", Types.VARCHAR));
		declareParameter(new SqlOutParameter("e_fechas", Types.VARCHAR));
		declareParameter(new SqlOutParameter("e_garantizado", Types.VARCHAR));		
 
			
		declareParameter(new SqlOutParameter("e_im_ppal", Types.VARCHAR));	
		declareParameter(new SqlOutParameter("e_im_recargo", Types.VARCHAR));			
		declareParameter(new SqlOutParameter("e_im_costas", Types.VARCHAR));	

		declareParameter(new SqlOutParameter("e_im_demora", Types.VARCHAR));			
		declareParameter(new SqlOutParameter("e_im_int_fracc", Types.VARCHAR));		
		declareParameter(new SqlOutParameter("e_im_int_fracc_sp", Types.VARCHAR));		
				
		declareParameter(new SqlOutParameter("e_error_mensaje", Types.VARCHAR));	
		compile();
	}

	@SuppressWarnings("unchecked")
	public G7D1CalculoVO execute(String coFraccionamiento, BigDecimal importeDelPlazo) throws GadirServiceException {
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("e_coFraccionamiento", coFraccionamiento);
		params.put("e_importe_plazo", importeDelPlazo);
		Map<String, Object> resultsList = new HashMap<String, Object>();
		G7D1CalculoVO calculoVO = new G7D1CalculoVO();
		try {
			resultsList = execute(params);
			if (resultsList == null) {
				throw new GadirServiceException("G7D1_GESTION_FRACCIONAMIENTO.calculo_fraccionamiento_java devuelve null.");
			} else {
				if (!resultsList.containsKey("resultado")) {
					throw new GadirServiceException("G7D1_GESTION_FRACCIONAMIENTO.calculo_fraccionamiento_java no contiene resultado.");
				} else {
					int resultado = ((BigDecimal) resultsList.get("resultado")).intValue();
					String mensaje = ((String) resultsList.get("e_error_mensaje"));
					if (resultado != 0) {
						throw new GadirServiceException(mensaje);
					} else {
						//1º ver como tratamos el error 
						//supongo que habra que incluir en G7D1CalculoVO un string para el error
						// sino hubiera error hacemos lo siguiente:
						
						List<G7D1CalculoPlazoVO> listaCalculoPlazo=new ArrayList<G7D1CalculoPlazoVO>();						
						//voy iterando el resultsList creando cada plazo y añadiendolo;
						//Lo primero sera coger cada uno de los Strings y parsearlos a String[]
//						declareParameter(new SqlOutParameter("e_plazos", Types.VARCHAR));
//						declareParameter(new SqlOutParameter("e_fechas", Types.VARCHAR));
//						declareParameter(new SqlOutParameter("e_garantizado", Types.VARCHAR));		
//				 
//							
//						declareParameter(new SqlOutParameter("e_im_ppal", Types.VARCHAR));	
//						declareParameter(new SqlOutParameter("e_im_recargo", Types.VARCHAR));			
//						declareParameter(new SqlOutParameter("e_im_costas", Types.VARCHAR));	
//
//						declareParameter(new SqlOutParameter("e_im_demora", Types.VARCHAR));			
//						declareParameter(new SqlOutParameter("e_im_int_fracc", Types.VARCHAR));		
//						declareParameter(new SqlOutParameter("e_im_int_fracc_sp", Types.VARCHAR));		
//								
//						declareParameter(new SqlOutParameter("e_error_mensaje", Types.VARCHAR));	
						
						
						String valoresNumPlazo = (String)resultsList.get("e_plazos") ;
						String[] arrayNumPlazo = valoresNumPlazo.split("@");
				 
						String valoresFechas = (String)resultsList.get("e_fechas") ;
						String[] arrayFechas = valoresFechas.split("@");


						String valoresGarantizado = (String)resultsList.get("e_garantizado") ;
						String[] arrayGarantizado = valoresGarantizado.split("@");						 			 						
						
						String valoresImpPrincipal = (String)resultsList.get("e_im_ppal") ;
						String[] arrayImpPrincipal = valoresImpPrincipal.split("@");	
						
						String valoresImpRecargo= (String)resultsList.get("e_im_recargo") ;
						String[] arrayImpRecargo = valoresImpRecargo.split("@");
						
						String valoresImpCostas = (String)resultsList.get("e_im_costas") ;
						String[] arrayImpCostas = valoresImpCostas.split("@");
						
						String valoresImpDemora= (String)resultsList.get("e_im_demora") ;
						String[] arrayImpDemora = valoresImpDemora.split("@");
					
						String valoresImpInteresPonderado = (String)resultsList.get("e_im_int_fracc") ;
						String[] arrayImpInteresPonderado = valoresImpInteresPonderado.split("@");
						
						String valoresImpInteres = (String)resultsList.get("e_im_int_fracc_sp") ;
						String[] arrayImpInteres = valoresImpInteres.split("@");
 		
						 


//						String valoresGarantiaAportada = (String)resultsList.get("s_garantiaAportada") ;
//						String[] arrayGarantiaAportada = valoresGarantiaAportada.split("@"); 
//						
						
						for(int i =0; i <arrayNumPlazo.length; i++){
							G7D1CalculoPlazoVO nuevo = new G7D1CalculoPlazoVO();
							nuevo.setCoFraccionamientoRecibo(coFraccionamiento);
							nuevo.setNuPlazo(Integer.parseInt(arrayNumPlazo[i]));			
							nuevo.setFxPlazo(arrayFechas[i]);
							nuevo.setGarantizado(arrayGarantizado[i]);						
 
							if(','== arrayImpPrincipal[i].charAt(0)){
								arrayImpPrincipal[i] = "0"+arrayImpPrincipal[i];
							}
							nuevo.setImportePrincipal( new BigDecimal(arrayImpPrincipal[i].replace(",", ".")));	
							
							if(','== arrayImpRecargo[i].charAt(0)){
								arrayImpRecargo[i] = "0"+arrayImpRecargo[i];
							}							
							nuevo.setImRecargo(new BigDecimal(arrayImpRecargo[i].replace(",", ".")));	
							
							 
							if(','== arrayImpCostas[i].charAt(0)){
								arrayImpCostas[i] = "0"+arrayImpCostas[i];
							}							
							nuevo.setImCostas(new BigDecimal(arrayImpCostas[i].replace(",", ".")));								
							
							
							if(','== arrayImpDemora[i].charAt(0)){
								arrayImpDemora[i] = "0"+arrayImpDemora[i];
							}							
							nuevo.setImInteresDemora(new BigDecimal(arrayImpDemora[i].replace(",", ".")));								
							
							
							if(','== arrayImpInteresPonderado[i].charAt(0)){
								arrayImpInteresPonderado[i] = "0"+arrayImpInteresPonderado[i];
							}
							nuevo.setImporteInteresPonderado(new BigDecimal(arrayImpInteresPonderado[i].replace(",", ".")));		
							  
 
							if(','== arrayImpInteres[i].charAt(0)){
								arrayImpInteres[i] = "0"+arrayImpInteres[i];
							}
							nuevo.setImporteInteres(new BigDecimal(arrayImpInteres[i].replace(",", ".")));	

							listaCalculoPlazo.add(nuevo);
						}
						
						calculoVO.setListaCalculoPlazo(listaCalculoPlazo);
						
					}
				}
			}
		} catch (Exception e) {
			throw new GadirServiceException(e);
		}
		return calculoVO;
	}
	
 

}
