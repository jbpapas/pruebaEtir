package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.ValidacionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;


public interface ValidacionBO extends GenericBO<ValidacionDTO, Long> {

	/**
	 * Método que se encarga de buscar el listado de validaciones que cumplen el
	 * filtro establecido.
	 * 
	 * @param filtro
	 *            {@link ValidacionDTO} que sirve como filtro.
	 * @return Lista de objetos poblados, trae informada la casilla para moder
	 *         usarla posteriormente.
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	List<ValidacionDTO> findValidaciones(final ValidacionDTO filtro)
	        throws GadirServiceException;
	
	List<ValidacionDTO> findValidacionesTipo(final ValidacionDTO filtro)
    throws GadirServiceException;

	/**
	 * Método que se encarga de buscar el listado de validaciones que cumplen el
	 * filtro establecido.
	 * 
	 * @param filtro
	 *            {@link ValidacionDTO} que sirve como filtro.
	 * @return Lista de objetos poblados, trae informada la casilla para moder
	 *         usarla posteriormente.
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	List<ValidacionDTO> findValidacionesFunciones(final ValidacionDTO filtro)
	        throws GadirServiceException;

	/**
	 * Método que se encarga de recuperar una validación con las casillas
	 * asociadas (evitando así el uso de lazy).
	 * 
	 * @param coValidacion
	 *            Identificador de la validación a recuperar.
	 * @return ValidacionDTO El objeto poblado asociado al identificador
	 *         recibido como parámetro.
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	ValidacionDTO findByIdWithCasillas(final Long coValidacion)
	        throws GadirServiceException;

	/**
	 * Método que se encarga de borrar la validación dada y todos sus
	 * argumentos.
	 * 
	 * @param id
	 *            Id de la validacion a eliminar
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	void deleteWithArgumentos(final Long id) throws GadirServiceException;

	/**
	 * Método que se encarga de guardar una validacion y los argumentos de
	 * validacion asociados a la funcion seleccionada.
	 * 
	 * @param val
	 *            ValidacionDTO Objeto con los datos a persistir.
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	void saveWithArgumentos(final ValidacionDTO val)
	        throws GadirServiceException;

	/**
	 * Método que se encarga de actualizar una validacion eliminando los
	 * argumentos de la funcion antigua y estableciendo los de la nueva función.
	 * 
	 * @param val
	 *            El objeto a persistir.
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	void updateWithArgumentos(final ValidacionDTO val)
	        throws GadirServiceException;

	/**
	 * Método que se encarga de obtener el orden para la validación. <br/>
	 * El orden va en funcion de:
	 * <ul>
	 * <li>Municipio</li>
	 * <li>Concepto</li>
	 * <li>Modelo</li>
	 * <li>Version</li>
	 * <li>Casilla</li>
	 * </ul>
	 * 
	 * @param validacion
	 *            Validación con los datos.
	 * @return Orden para la valdacion.
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	Short getNextOrden(final ValidacionDTO validacion)
	        throws GadirServiceException;
	       
}
