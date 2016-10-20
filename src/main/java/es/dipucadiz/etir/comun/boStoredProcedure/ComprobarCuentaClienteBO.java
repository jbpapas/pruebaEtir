package es.dipucadiz.etir.comun.boStoredProcedure;

import java.util.Map;

public interface ComprobarCuentaClienteBO {
	
	public Map<String, Object> execute(String coBanco, String coBancoSucursal, String dc, String cuenta, Long coCliente, String procedencia, String coModelo, String coVersion, String coDocumento);

}