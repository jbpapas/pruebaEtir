package es.dipucadiz.etir.comun.boStoredProcedure;

import java.util.Map;

import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface DescargaFormulaBO {
	public Map<String, Object> execute(String coProvincia, String coMunicipio, String coConcepto, String coModelo, String coVersion, String coFecha, String msg) throws GadirServiceException;
}
