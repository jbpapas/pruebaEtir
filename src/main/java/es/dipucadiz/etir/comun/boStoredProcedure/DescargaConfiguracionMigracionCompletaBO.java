package es.dipucadiz.etir.comun.boStoredProcedure;

import java.util.Map;

import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface DescargaConfiguracionMigracionCompletaBO {
	public Map<String, Object> execute(String fechaActual) throws GadirServiceException;
}
