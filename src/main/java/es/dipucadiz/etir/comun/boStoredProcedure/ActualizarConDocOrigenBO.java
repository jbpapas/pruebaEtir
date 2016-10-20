package es.dipucadiz.etir.comun.boStoredProcedure;

import java.util.Map;



public interface ActualizarConDocOrigenBO {
	
	public Map<String, Object> execute(String coModelo, String coVersion, String coDocumento, String coModeloOrigen, String coVersionOrigen, String coDocumentoOrigen, String coProcesoActual);

}
