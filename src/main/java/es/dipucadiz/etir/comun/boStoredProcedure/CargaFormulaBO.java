package es.dipucadiz.etir.comun.boStoredProcedure;

import java.util.Map;

import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface CargaFormulaBO {
	public Map<String, Object> execute(String NombreZIP, String msg) throws GadirServiceException;
}
