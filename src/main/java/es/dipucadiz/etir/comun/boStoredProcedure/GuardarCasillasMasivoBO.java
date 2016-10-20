package es.dipucadiz.etir.comun.boStoredProcedure;

import java.util.Date;
import java.util.Map;



public interface GuardarCasillasMasivoBO {
	
	public Map<String, Object> execute(String coModelo, String coVersion, String coDocumento, String hojas, String casillas, String valores, String errores, Date fhActualizacion, String coProcesoActual);

}
