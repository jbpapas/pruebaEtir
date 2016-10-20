package es.dipucadiz.etir.comun.bo;


import es.dipucadiz.etir.comun.dto.HDepositoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface HDepositoBO extends GenericBO<HDepositoDTO, Long>{
	
	public String obtenerTipoAnterior( Long coDeposito) throws GadirServiceException;
}