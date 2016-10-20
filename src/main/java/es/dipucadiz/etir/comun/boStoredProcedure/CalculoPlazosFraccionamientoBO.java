package es.dipucadiz.etir.comun.boStoredProcedure;

import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.sb07.G7D1Fraccionamiento.action.G7D1CalculoPlazosOImportesVO;





public interface CalculoPlazosFraccionamientoBO {
	
	
	
//	 function calculo_plazos(

//             e_tipo varchar2,                                -- Tipo del fraccionamiento (V o E)
//             e_importe_plazo in out number,            -- Si este campo viene relleno e_nu_plazos tiene que venir vacio. El importe a conseguir en cada plazo si viene relleno. Si no viene relleno devolvera el importe del primer plazo si no es prorrateado o el importe de cada plazo si es prorrateado
//             e_nu_plazos in out integer,                 -- Si este campo viene relleno e_importe_plazo vendra vacio. El numero de plazos que del fraccionamiento si viene relleno. Si no viene relleno devolvera el numero de plazos necesarios para conseguir las exigencias de e_importe_plazo
//             e_bo_prorrateado in integer,               -- Si el fraccionamiento va a ser prorrateado (0 o 1). Si e_importe_plazo viene relleno no importa el valor pero seria un fraccionamiento prorrateado. Si lo que viene relleno es e_nu_plazos este argumento indicará que importe se devuelve en e_importe_plazo
//             e_importe_fraccionamiento number,      -- La suma de los importes pendientes de los documentos a incluir en el fraccionamiento
//             e_dia_cobro integer,                          -- El dia que viene en las condiciones del fraccionamiento
//             e_mes_inicio integer,                         -- El mes de inicio del fraccionamiento (MM)
//             e_ano_inicio integer,                         -- El año de inicio del fraccionamiento (AAAA)
//             e_meses_periodicidad integer,             -- La periodicidad del fraccionamiento
//             e_docs varchar2,                             -- La lista de los documentos a incluir en el fraccionamiento separados por |(MMMVDDDDDDDDD|.......)
//             e_error_mensaje in out varchar2) return integer;
//
		public G7D1CalculoPlazosOImportesVO execute(String e_tipo, Float e_importe_plazo, Integer e_nu_plazos, Integer e_bo_prorrateado, 
		    Float e_importe_fraccionamiento, Short e_dia_cobro, Short e_mes_inicio, Short e_ano_inicio, Float e_meses_periodicidad,
			String e_docs, Float e_importe_plazo_u) throws GadirServiceException;

}
