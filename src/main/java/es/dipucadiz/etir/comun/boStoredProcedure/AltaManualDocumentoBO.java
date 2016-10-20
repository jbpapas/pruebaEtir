package es.dipucadiz.etir.comun.boStoredProcedure;

import java.util.Map;



public interface AltaManualDocumentoBO {
	
	public Map<String, Object> execute(String coProvincia, String coMunicipio, String coConcepto, String coModelo, String coVersion, String coModeloOrigen, String coVersionOrigen, String coDocumentoOrigen, Long coCliente, String refCatastral, Long coDomicilio, String fxInicio, String fxFin, String coProcesoActual);
	public Map<String, Object> execute(String coProvincia, String coMunicipio, String coConcepto, String coModelo, String coVersion, String coModeloOrigen, String coVersionOrigen, String coDocumentoOrigen, Long coCliente, String refCatastral, Long coDomicilio, String fxInicio, String fxFin, boolean todasCasillas, String coProcesoActual);

}
