package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.PlantillaImgCondCampoDTO;
import es.dipucadiz.etir.comun.dto.PlantillaImgCondCampoDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;


public interface PlantillaImgCondCampoBO extends
        GenericBO<PlantillaImgCondCampoDTO, PlantillaImgCondCampoDTOId> {

	/**
	 * Método que se encarga de recuperar los campos asociados a una determinada
	 * imagen y condición.
	 * 
	 * @param coPlantillaImagen
	 *            Imagen a la que está asociada la condición y asimismo el
	 *            campo.
	 * @param ordenImgCondicional
	 *            Condición a la que se asocia el campo.
	 * @return List Listado de objetos poblados.
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	List<PlantillaImgCondCampoDTO> findCampoByCondicionImagen(
	        final Long coPlantillaImagen, final Short ordenImgCondicional)
	        throws GadirServiceException;

	

}
