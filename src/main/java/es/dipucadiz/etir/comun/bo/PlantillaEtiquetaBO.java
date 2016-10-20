package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.PlantillaEtiquetaCampoDTO;
import es.dipucadiz.etir.comun.dto.PlantillaEtiquetaDTO;
import es.dipucadiz.etir.comun.dto.PlantillaEtiquetaDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.sb05.vo.PlantillaEtiquetaVO;


public interface PlantillaEtiquetaBO extends
        GenericBO<PlantillaEtiquetaDTO, PlantillaEtiquetaDTOId> {

	
	/**
	 * Método que se encarga de guardar una nueva etiqueta con el campo
	 * asociado.
	 *
	 * @param campo
	 *            El objeto con los datos a guardar (Etiqueta y Campo).
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	void saveEtiquetaWithCampo(final PlantillaEtiquetaCampoDTO campo)
	        throws GadirServiceException;

	
	/**
	 * Método que se encarga de eliminar una etiqueta y sus campos asociados.
	 *
	 * @param id
	 *            Identificador del elemento a eliminar.
	 * @throws GadirServiceException
	 *             Si ocurre algún error.
	 */
	void deleteEtiquetaWithCampos(final PlantillaEtiquetaDTOId id)
	        throws GadirServiceException;

	/**
	 * Método que se encarga de obtener el la lista de PlantillaEtiquetaDTO de
	 * una tabla.
	 *
	 * @param coPlantilla
	 *            Plantilla a las que está asociada la etiqueta.
	 * @param coTabla
	 *            tabla a la que está asociada la etiqueta.
	 * @return List lista de etiquetas que cumplen el filtro.
	 * @throws GadirServiceException
	 *             Si ocurre algún error.
	 */
	List<PlantillaEtiquetaDTO> getEtiquetasByTabla(final Long coPlantilla,
	        final Short coTabla) throws GadirServiceException;

	/**
	 * Método que se encarga de obtener la lista de imagenes asociadas a una
	 * plantilla.
	 *
	 * @param coPlantilla
	 *            Código de la plantilla.
	 * @return Lista de etiquetas asociadas a la plantilla.
	 * @throws GadirServiceException
	 *             Si ocurre algún error.
	 */
	List<PlantillaEtiquetaVO> findEtiquetasByPlantilla(Long coPlantilla)
	        throws GadirServiceException;
}
