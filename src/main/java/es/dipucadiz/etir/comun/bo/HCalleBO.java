package es.dipucadiz.etir.comun.bo;


import es.dipucadiz.etir.comun.dto.CalleDTO;
import es.dipucadiz.etir.comun.dto.HCalleDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface HCalleBO extends GenericBO<HCalleDTO, Long>{

	public void guardarHCalle(CalleDTO calle) throws GadirServiceException;
	
	public void guardarHCalle(CalleDTO calle,String movimiento) throws GadirServiceException;
}