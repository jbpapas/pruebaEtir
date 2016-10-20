package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.CorrespondenciaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

/**
 * Interface responsable de definir la lógica de negocio asociada a la clase del
 * modelo {@link CorrespondenciaDTO}.
 * 
 * @version 1.0 09/11/2009
 * @author SDS[FJTORRES]
 */
public interface CorrespondenciaBO extends GenericBO<CorrespondenciaDTO, Long> {

	public List<CorrespondenciaDTO> findCorrespondenciaByParam(
	        final CorrespondenciaDTO filtro) throws GadirServiceException;
	
	
	public List<CorrespondenciaDTO> findBoActivaCoDocumento(
	        final String coDocumento) throws GadirServiceException;

	/**
	 * Método que se encarga de borrar la correspondencia dada y todos sus
	 * argumentos.
	 * 
	 * @param id
	 *            Id de la correspondencia a eliminar
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	void deleteWithArgumentos(final Long id) throws GadirServiceException;

	/**
	 * Método que se encarga de guardar una correspondencia y los argumentos de
	 * correspondencia asociados a la funcion seleccionada.
	 * 
	 * @param corr
	 *            CorrespondenciaDTO Objeto con los datos a persistir.
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	void saveWithArgumentos(final CorrespondenciaDTO corr)
	        throws GadirServiceException;

	/**
	 * Método que se encarga de actualizar una correspondencia eliminando los
	 * argumentos de la funcion antigua y estableciendo los de la nueva función.
	 * 
	 * @param corr
	 *            El objeto a persistir.
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	void updateWithArgumentos(final CorrespondenciaDTO corr)
	        throws GadirServiceException;

	/**
	 * Método que se encarga de obtener la correspondencia con la funcion
	 * asociada rellena.
	 * 
	 * @param codigo
	 *            Codigo de la correspondencia buscada
	 * @return Correspondencia con funcion rellena.
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	CorrespondenciaDTO findByIdWithFuncion(final Long codigo)
	        throws GadirServiceException;
}
