package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.PlantillaFirmaDTO;
import es.dipucadiz.etir.comun.dto.PlantillaFirmaDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface PlantillaFirmaBO extends GenericBO<PlantillaFirmaDTO, PlantillaFirmaDTOId> {
	

	
	List<PlantillaFirmaDTO> findByPlantillaLazy(Long coPlantilla) throws GadirServiceException;

	PlantillaFirmaDTO findByRowidLazy(String firmaRowid) throws GadirServiceException;

	String findAdvertencia(PlantillaFirmaDTO plantillaFirmaDTO) throws GadirServiceException;

}
