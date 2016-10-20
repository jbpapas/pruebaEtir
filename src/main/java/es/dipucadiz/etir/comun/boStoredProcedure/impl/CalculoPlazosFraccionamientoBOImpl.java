package es.dipucadiz.etir.comun.boStoredProcedure.impl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
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

import es.dipucadiz.etir.comun.boStoredProcedure.CalculoPlazosFraccionamientoBO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.Mensaje;
import es.dipucadiz.etir.sb07.G7D1Fraccionamiento.action.G7D1CalculoPlazoVO;
import es.dipucadiz.etir.sb07.G7D1Fraccionamiento.action.G7D1CalculoPlazosOImportesVO;
import es.dipucadiz.etir.sb07.G7D1Fraccionamiento.action.G7D1CalculoVO;



public class CalculoPlazosFraccionamientoBOImpl extends StoredProcedure implements CalculoPlazosFraccionamientoBO {

	private static final Log LOG = LogFactory.getLog(CalculoPlazosFraccionamientoBOImpl.class);

	public CalculoPlazosFraccionamientoBOImpl(final DataSource dataSource) {
		
		
//      e_tipo varchar2,                                -- Tipo del fraccionamiento (V o E)
//      e_importe_plazo in out number,            -- Si este campo viene relleno e_nu_plazos tiene que venir vacio. El importe a conseguir en cada plazo si viene relleno. Si no viene relleno devolvera el importe del primer plazo si no es prorrateado o el importe de cada plazo si es prorrateado
//     e_nu_plazos in out integer,                 -- Si este campo viene relleno e_importe_plazo vendra vacio. El numero de plazos que del fraccionamiento si viene relleno. Si no viene relleno devolvera el numero de plazos necesarios para conseguir las exigencias de e_importe_plazo
//      e_bo_prorrateado in integer,               -- Si el fraccionamiento va a ser prorrateado (0 o 1). Si e_importe_plazo viene relleno no importa el valor pero seria un fraccionamiento prorrateado. Si lo que viene relleno es e_nu_plazos este argumento indicará que importe se devuelve en e_importe_plazo
//      e_importe_fraccionamiento number,      -- La suma de los importes pendientes de los documentos a incluir en el fraccionamiento
//      e_dia_cobro integer,                          -- El dia que viene en las condiciones del fraccionamiento
//      e_mes_inicio integer,                         -- El mes de inicio del fraccionamiento (MM)
//      e_ano_inicio integer,                         -- El año de inicio del fraccionamiento (AAAA)
//      e_meses_periodicidad integer,             -- La periodicidad del fraccionamiento
//      e_docs varchar2,                             -- La lista de los documentos a incluir en el fraccionamiento separados por |(MMMVDDDDDDDDD|.......)
//      e_error_mensaje in out varchar2) return integer;
//


		super(dataSource, "G7D1_GESTION_FRACCIONAMIENTO.calculo_plazos_java");
		LOG.info("G7D1_GESTION_FRACCIONAMIENTO.calculo_plazos_java");
		
		setFunction(true);	
		declareParameter(new SqlOutParameter("resultado", Types.NUMERIC));		
		declareParameter(new SqlOutParameter("e_tipo", Types.VARCHAR));		
		declareParameter(new SqlOutParameter("e_importe_plazo", Types.NUMERIC));
		declareParameter(new SqlOutParameter("e_nu_plazos", Types.NUMERIC));		 
		declareParameter(new SqlOutParameter("e_bo_prorrateado", Types.NUMERIC));
		declareParameter(new SqlOutParameter("e_importe_fraccionamiento", Types.NUMERIC));
		declareParameter(new SqlOutParameter("e_dia_cobro", Types.NUMERIC));		
		declareParameter(new SqlOutParameter("e_mes_inicio", Types.NUMERIC));	
		declareParameter(new SqlOutParameter("e_ano_inicio", Types.NUMERIC));			
		declareParameter(new SqlOutParameter("e_meses_periodicidad", Types.NUMERIC));	
		declareParameter(new SqlOutParameter("e_docs", Types.VARCHAR));	
		declareParameter(new SqlOutParameter("e_importe_plazo_u", Types.NUMERIC));			
		declareParameter(new SqlOutParameter("e_error_mensaje", Types.VARCHAR));	
		compile();
	}

	@SuppressWarnings("unchecked")
	public G7D1CalculoPlazosOImportesVO execute(String e_tipo, Float e_importe_plazo, Integer e_nu_plazos, Integer e_bo_prorrateado, 
			Float e_importe_fraccionamiento, Short e_dia_cobro, Short e_mes_inicio, Short e_ano_inicio, Float e_meses_periodicidad,
			String e_docs, Float e_importe_plazo_u) throws GadirServiceException {
		final Map<String, Object> params = new HashMap<String, Object>();
		 
		
		 
		params.put("e_tipo", e_tipo);		
		params.put("e_importe_plazo", e_importe_plazo);
		params.put("e_nu_plazos", e_nu_plazos);		 
		params.put("e_bo_prorrateado", e_bo_prorrateado);
		params.put("e_importe_fraccionamiento", e_importe_fraccionamiento);
		params.put("e_dia_cobro", e_dia_cobro);		
		params.put("e_mes_inicio",e_mes_inicio);	
		params.put("e_ano_inicio", e_ano_inicio);			
		params.put("e_meses_periodicidad", e_meses_periodicidad);	
		params.put("e_docs",e_docs);		
		 		
		 
		 
		Map<String, Object> resultsList = new HashMap<String, Object>();
		G7D1CalculoPlazosOImportesVO calculoVO = new G7D1CalculoPlazosOImportesVO();
		try { 
			resultsList = execute(params);
			if (resultsList == null) {
				throw new GadirServiceException("G7D1_GESTION_FRACCIONAMIENTO.calculo_plazos_java devuelve null.");
			} else {
				if (!resultsList.containsKey("resultado")) {
					throw new GadirServiceException("G7D1_GESTION_FRACCIONAMIENTO.calculo_plazos_java no contiene resultado.");
				} else {
					int resultado = ((BigDecimal) resultsList.get("resultado")).intValue();
					if (resultado != 0) {
						throw new GadirServiceException(Mensaje.getTexto(resultado));
					} else {
						//1º ver como tratamos el error 
 
//				      e_importe_plazo in out number,            -- Si este campo viene relleno e_nu_plazos tiene que venir vacio. El importe a conseguir en cada plazo si viene relleno. Si no viene relleno devolvera el importe del primer plazo si no es prorrateado o el importe de cada plazo si es prorrateado
//				      e_nu_plazos in out integer,                 -- Si este campo viene relleno e_importe_plazo vendra vacio. El numero de plazos que del fraccionamiento si viene relleno. Si no viene relleno devolvera el numero de plazos necesarios para conseguir las exigencias de e_importe_plazo
//				      e_error_mensaje in out varchar2) return integer;
				//

						
						
						BigDecimal importePlazo = (BigDecimal)resultsList.get("e_importe_plazo") ;
						 
						BigDecimal  nuPlazos = (BigDecimal)resultsList.get("e_nu_plazos") ;
						
						BigDecimal importePlazoUltimo = (BigDecimal)resultsList.get("e_importe_plazo_u") ;
					 
						
						calculoVO.setImporteUltPlazo(importePlazoUltimo);
						calculoVO.setImportePlazo(importePlazo) ;
						calculoVO.setNuPlazos(nuPlazos.intValue());
							 
						}
						
						 
						
					}
				}
			 
		} catch (Exception e) {
			throw new GadirServiceException(e);
		}
		return calculoVO;
	}
	
 

}
