package es.dipucadiz.etir.comun.boStoredProcedure;

import java.util.Map;

import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface CargaCircuitosCompletaBO {
	public Map<String, Object> execute(String nombreFichero) throws GadirServiceException;
}
