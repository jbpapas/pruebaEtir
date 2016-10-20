package es.dipucadiz.etir.comun.boStoredProcedure;

import java.util.Map;



public interface CandidatoAsignarBO {
	
	public Map<String, Object> execute(String coModelo, String coVersion, String coDocumento, Long coCliente, String coProcesoActual);

}
