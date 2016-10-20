package es.dipucadiz.etir.comun.bo;

import es.dipucadiz.etir.comun.dto.CargoSubcargoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface CargoSubcargoBO extends GenericBO<CargoSubcargoDTO, Long>{

	CargoSubcargoDTO findByIdInitialized(Long coCargoSubcargo) throws GadirServiceException;
		
	
	
}