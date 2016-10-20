package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.PlantillaTablaDTO;
import es.dipucadiz.etir.comun.dto.PlantillaTablaDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;


public interface PlantillaTablaBO extends
        GenericBO<PlantillaTablaDTO, PlantillaTablaDTOId> {

	

	/**
	 * Método que se encarga de obtener las tablas asociadas a la plantilla
	 * dada.
	 *
	 * @param coPlantilla
	 *            Código de la plantilla.
	 * @return Lista de tablas.
	 * @throws GadirServiceException
	 *             Si ocurre algún error.
	 */
	List<PlantillaTablaDTO> findByPlantilla(Long coPlantilla)
	        throws GadirServiceException;
}
