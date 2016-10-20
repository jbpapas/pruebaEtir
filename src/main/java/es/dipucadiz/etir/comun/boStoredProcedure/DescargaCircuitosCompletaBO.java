package es.dipucadiz.etir.comun.boStoredProcedure;

import java.util.Map;

import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface DescargaCircuitosCompletaBO {
	public Map<String, Object> execute(String fechaActual, String coCircuitos) throws GadirServiceException;
}
