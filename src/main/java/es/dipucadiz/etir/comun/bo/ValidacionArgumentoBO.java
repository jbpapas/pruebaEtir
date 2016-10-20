package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.ValidacionArgumentoDTO;
import es.dipucadiz.etir.comun.dto.ValidacionArgumentoDTOId;
import es.dipucadiz.etir.comun.dto.ValidacionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;


public interface ValidacionArgumentoBO extends
        GenericBO<ValidacionArgumentoDTO, ValidacionArgumentoDTOId> {

	/**
	 * Método que se encarga de obtener los argumentos de la validación dada.
	 * 
	 * @param coValidacion
	 *            Codigo de la validacion.
	 * @return Lista de argumentos de la validacion.
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	List<ValidacionArgumentoDTO> findArgumentos(final Long coValidacion)
	        throws GadirServiceException;

	/**
	 * Método que se encarga de obtener todos los argumentos asociados a una
	 * validacion.
	 * 
	 * @param val
	 *            Objeto con los valores por los que se va a filtrar.
	 * @return Lista con los argumentos asociados a dicha validacion.
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	List<ValidacionArgumentoDTO> findArgumentosValidacion(
	        final ValidacionDTO val) throws GadirServiceException;
	

	/**
	 * Método que se encarga de recuperar un objeto ValidacionArgumento con
	 * todos los datos de FuncionArgumentoDTO poblados.
	 * 
	 * @param id
	 *            Identificador del objeto a recuperar.
	 * @return El objeto poblado.
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	ValidacionArgumentoDTO findByIdWithFuncionArgumento(
	        final ValidacionArgumentoDTOId id) throws GadirServiceException;
}
