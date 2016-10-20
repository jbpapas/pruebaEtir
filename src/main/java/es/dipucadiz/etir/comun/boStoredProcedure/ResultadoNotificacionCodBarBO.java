package es.dipucadiz.etir.comun.boStoredProcedure;

import java.util.Map;



public interface ResultadoNotificacionCodBarBO {
	
	public Map<String, Object> execute(String codigoBarras, String tipo, String fecha);

}
