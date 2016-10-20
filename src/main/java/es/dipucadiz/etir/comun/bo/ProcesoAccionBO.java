package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.ProcesoAccionDTO;
import es.dipucadiz.etir.comun.dto.ProcesoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;



public interface ProcesoAccionBO extends GenericBO<ProcesoAccionDTO, Long> {

	ProcesoAccionDTO findByProcesoAccionInitialized(String coProcesoActual, String string) throws GadirServiceException;
	List<ProcesoAccionDTO> findByProcesoInitialized(ProcesoDTO procesoDTO) throws GadirServiceException;

}
