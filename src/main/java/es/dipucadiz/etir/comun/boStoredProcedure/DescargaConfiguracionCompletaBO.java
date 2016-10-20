package es.dipucadiz.etir.comun.boStoredProcedure;

import java.util.Map;

import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface DescargaConfiguracionCompletaBO {
	public Map<String, Object> execute(String coProvincia, String coMunicipio, String coConcepto) throws GadirServiceException;
}
