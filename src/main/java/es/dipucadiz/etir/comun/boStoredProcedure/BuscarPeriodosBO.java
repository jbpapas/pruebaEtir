package es.dipucadiz.etir.comun.boStoredProcedure;

import java.util.Map;

import es.dipucadiz.etir.comun.exception.GadirServiceException;



public interface BuscarPeriodosBO {
	
	public Map<String, Object> execute(String coProvincia, String coMunicipio, String coConcepto, String coModelo, short ejercicio, String coPeriodo) throws GadirServiceException;

}
