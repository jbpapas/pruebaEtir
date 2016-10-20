package es.dipucadiz.etir.comun.boStoredProcedure;

import java.util.Map;



public interface CrearHojasBO {
	
	public Map<String, Object> execute(String coModelo, String coVersion, String coDocumento, String inicio, String fin, String coProcesoActual);

}
