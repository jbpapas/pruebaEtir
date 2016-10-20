package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.PlantillaEtiquetaDTOId;
import es.dipucadiz.etir.comun.dto.PlantillaImagenDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface PlantillaImagenBO extends GenericBO<PlantillaImagenDTO, Long> {

	
	PlantillaImagenDTO findByIdLazy(final Long coPlantillaImagen)
	        throws GadirServiceException;

	List<PlantillaImagenDTO> findByPlantillaLazy(Long coPlantilla) throws GadirServiceException;

	PlantillaImagenDTO findByRowidLazy(String imagenRowid) throws GadirServiceException;
	
	/**
	 * Método que se encarga de eliminar una imagen y sus campos asociados.
	 *
	 * @param id
	 *            Identificador del elemento a eliminar.
	 * @throws GadirServiceException
	 *             Si ocurre algún error.
	 */
	void deleteImagenWithCampos(final long coPlantillaImagen)
	        throws GadirServiceException;

}
