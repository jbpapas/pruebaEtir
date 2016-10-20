package es.dipucadiz.etir.comun.boStoredProcedure;

import java.util.Map;

import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface CargaConfiguracionCompletaBO {
	public Map<String, Object> execute(String coProvincia, String coMunicipio, String coConcepto, String fecha) throws GadirServiceException;
}
