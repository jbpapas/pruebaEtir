package es.dipucadiz.etir.comun.boStoredProcedure;

import java.util.Date;
import java.util.Map;



public interface ResultadoNotificacionBO {
	
	public Map<String, Object> execute(String coModelo, String coVersion, String coDocumento, Date fecha, String resultado, String coBarras, String observaciones);

}
