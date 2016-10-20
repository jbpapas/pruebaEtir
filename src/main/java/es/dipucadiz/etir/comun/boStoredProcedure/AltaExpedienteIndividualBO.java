package es.dipucadiz.etir.comun.boStoredProcedure;

import java.math.BigDecimal;

import es.dipucadiz.etir.comun.exception.GadirServiceException;





public interface AltaExpedienteIndividualBO {
	
  
	public BigDecimal execute(
			String e_co_cliente, String e_co_provincia, String e_co_municipio, String e_co_circuito, String e_co_exp_segl) throws GadirServiceException;

}
