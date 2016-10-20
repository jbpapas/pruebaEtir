package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.ModeloVersionDTO;
import es.dipucadiz.etir.comun.dto.ModeloVersionDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;


public interface ModeloVersionBO extends
        GenericBO<ModeloVersionDTO, ModeloVersionDTOId> {

	/**
	 * Método que se encarga de obtener las versiones de un modelo dado, si el
	 * modelo es null o vacio devuelve una lista vacia.
	 * 
	 * @param coModelo
	 *            Código del modelo.
	 * @return Lista de versiones.
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	List<ModeloVersionDTO> findVersionesByModelo(final String coModelo)
	        throws GadirServiceException;

	/**
	 * Método que se encarga de obtener una lista de versiones que cumplan el
	 * filtro de los parametros dados.
	 * 
	 * @param codModelo
	 *            - Codigo del modelo de filtro.
	 * @param codVersion
	 *            - Codigo de version de filtro.
	 * @return Lista con las versiones que cumplan el filtro.
	 * @throws GadirServiceException
	 *             - Si ocurre cualquier error.
	 */
	List<ModeloVersionDTO> findVersiones(String codModelo, String codVersion)
	        throws GadirServiceException;


	/**
	 * Método que se encarga de dar de alta el modelo asociado a la version
	 * previamente a grabar la version. Ademas crea las casillas iniciales que
	 * se definan.
	 * 
	 * @param modeloVersion
	 *            ModeloVersionDTO con los datos a guardar.
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	void saveConNuevoModelo(final ModeloVersionDTO modeloVersion)
	        throws GadirServiceException;

	/**
	 * Método que se encarga de actualizar los datos del modelo y la version
	 * dados.
	 * 
	 * @param modeloVersion
	 *            ModeloVersionDTO con los datos de la version y el modelo.
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	void saveVersionAndModelo(final ModeloVersionDTO modeloVersion)
	        throws GadirServiceException;

	/**
	 * Método que se encarga de crear una nueva versión y las casillas iniciales
	 * definidas.
	 * 
	 * @param transientObject
	 *            {@link ModeloVersionDTO} con los datos de la nueva versión.
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	void saveOnlyConCasillas(final ModeloVersionDTO transientObject)
	        throws GadirServiceException;

	/**
	 * Método que se encarga de duplicar la información del modelo-version
	 * origen al destino. Solo copia las casillas.
	 * 
	 * @param idOrigen
	 *            Id del modelo-version origen.
	 * @param idDestino
	 *            Id del modelo-version destino.
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	void saveDuplicarVersion(final ModeloVersionDTOId idOrigen,
	        final ModeloVersionDTOId idDestino) throws GadirServiceException;

	/**
	 * Método que se encarga de obtener un modelo-version concreto segun su id,
	 * el modelo-version traera los datos del modelo cargados.
	 * 
	 * @param id
	 *            Id del modelo-version que se quiere obtener.
	 * @return ModeloVersion que coincida con los parametros dados.
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	ModeloVersionDTO findByIdLazy(final ModeloVersionDTOId id)
	        throws GadirServiceException;

	/**
	 * Método que se encarga de obtener las versiones existentes en
	 * GA_DOCUMENTOS para el modelo seleccionado.
	 */
	List<ModeloVersionDTO> findAllVersionByModelo(final String coModelo,
	        final String estado) throws GadirServiceException;

	ModeloVersionDTO findInitializedById(final ModeloVersionDTOId id) throws GadirServiceException;
	ModeloVersionDTO findVersionVigente(final String coModelo) throws GadirServiceException;
	/**
	 * {@inheritDoc}
	 */
	List<Integer> tieneRelacionesNoEliminables(final ModeloVersionDTOId id) throws GadirServiceException;
	
	/**
	 * {@inheritDoc}
	 */
	Boolean eliminarModeloVersion(final ModeloVersionDTO modeloVersion) throws GadirServiceException;

	ModeloVersionDTO findVersionVigenteByModelo(final String coModelo) throws GadirServiceException;
	
	List<ModeloVersionDTO> buscaModelosVersionesDeDocumentos(final String coModelo) throws GadirServiceException;

	List<Integer> comprobarEliminarModeloVersion(ModeloVersionDTO modeloVersion) throws GadirServiceException;
}
