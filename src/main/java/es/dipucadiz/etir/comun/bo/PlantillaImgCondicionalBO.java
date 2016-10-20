package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.PlantillaImgCondicionalDTO;
import es.dipucadiz.etir.comun.dto.PlantillaImgCondicionalDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;


public interface PlantillaImgCondicionalBO extends
        GenericBO<PlantillaImgCondicionalDTO, PlantillaImgCondicionalDTOId> {

	
	
	/**
	 * Método que se encarga de obtener la lista de imagenes condicionales
	 * asociadas a una imagen.
	 * 
	 * @param coImagen
	 *            Código de la imagen.
	 * @return Lista con las imagenes condicionales.
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	List<PlantillaImgCondicionalDTO> findByImagen(Long coImagen)
	        throws GadirServiceException;

	

}
