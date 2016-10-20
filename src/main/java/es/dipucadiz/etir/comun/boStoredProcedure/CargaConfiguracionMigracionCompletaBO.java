package es.dipucadiz.etir.comun.boStoredProcedure;

import java.util.Map;

import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface CargaConfiguracionMigracionCompletaBO {
	public Map<String, Object> execute(String fecha) throws GadirServiceException;
}
