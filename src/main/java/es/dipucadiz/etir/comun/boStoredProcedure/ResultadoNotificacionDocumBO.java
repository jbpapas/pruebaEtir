package es.dipucadiz.etir.comun.boStoredProcedure;

import java.util.Map;



public interface ResultadoNotificacionDocumBO {
	
	public Map<String, Object> execute(String documento, String tipo, String resultado, String fecha);

}
