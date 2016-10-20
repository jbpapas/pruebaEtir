package es.dipucadiz.etir.comun.boStoredProcedure;

import java.util.Map;

import es.dipucadiz.etir.comun.exception.GadirServiceException;



public interface CalculoManualDocumentoBO {
	
	public Map<String, Object> execute(String coModelo, String coVersion, String coDocumento, String tipo, String coProcesoActual) throws GadirServiceException;

}
