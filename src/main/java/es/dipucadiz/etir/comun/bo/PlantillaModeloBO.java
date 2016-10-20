package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.PlantillaModeloDTO;
import es.dipucadiz.etir.comun.dto.PlantillaModeloDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;


public interface PlantillaModeloBO extends
        GenericBO<PlantillaModeloDTO, PlantillaModeloDTOId> {


	/**
	 * Método que se encarga de obtener las plantilla-modelo asociadas a la
	 * plantilla dada.
	 *
	 * @param coPlantilla
	 *            Código de la plantilla.
	 * @return Lista de plantilla-modelo asociadas a la plantilla.
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	List<PlantillaModeloDTO> findByPlantilla(final Long coPlantilla)
	        throws GadirServiceException;
}
