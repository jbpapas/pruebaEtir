package es.dipucadiz.etir.comun.boStoredProcedure;

import java.math.BigDecimal;
import java.util.Map;

public interface ComprobarDomicilioBO {
	
	public Map<String, Object> execute(Long coCliente, String refCatastral, String coConcepto, String nombreProvincia, String nombreMunicipio, String sigla, String nombreCalle, Integer numero, String letra, String escalera, String planta, BigDecimal km, String puerta, String bloque, Integer cp, String procedencia, Integer boTributario);

}
