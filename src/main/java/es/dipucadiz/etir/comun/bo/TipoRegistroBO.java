package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.CargaCriterioCondicionDTO;
import es.dipucadiz.etir.comun.dto.CargaCriterioDTO;
import es.dipucadiz.etir.comun.dto.CargaEstructuraDTO;
import es.dipucadiz.etir.comun.dto.CargaTipoRegistroDTO;
import es.dipucadiz.etir.comun.dto.CargaTipoRegistroDTOId;
import es.dipucadiz.etir.comun.dto.CasillaModeloDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;


public interface TipoRegistroBO extends
        GenericBO<CargaTipoRegistroDTO, CargaTipoRegistroDTOId> {

	/**
	 * Método que se encarga de buscar los tipos de registros asociados a una
	 * estructura de entrada.
	 * 
	 * @param codEstructura
	 *            - código de la estructura de entrada.
	 * @return Lista de tipos de registros.
	 * @throws GadirServiceException
	 *             - Si ocurre cualquier error.
	 */
	List<CargaTipoRegistroDTO> findTiposRegistrosByEstructura(Long codEstructura)
	        throws GadirServiceException;

	/**
	 * Método que se encarga de buscar las Casillas Modelos con el campo
	 * Bo_Repeticion a true.
	 * 
	 * @param codModelo
	 *            - código de Modelo.
	 * @param codVersion
	 *            - código de Versión.
	 * @return Lista de Casillas.
	 * @throws GadirServiceException
	 *             - Si ocurre cualquier error.
	 */
	List<CasillaModeloDTO> findCasillasModelosBoRepeticion(String codModelo,
	        String codVersion) throws GadirServiceException;

	/**
	 * Método que se encarga de buscar los Tipos de Registros con el campo
	 * Bo_Hoja1 a true.
	 * 
	 * @param codCarga
	 *            - código de Carga.
	 * @return Lista de Tipos de Registros.
	 * @throws GadirServiceException
	 *             - Si ocurre cualquier error.
	 */
	List<CargaTipoRegistroDTO> findBoHoja1(Long codCarga)
	        throws GadirServiceException;

	/**
	 * Método que se encarga de buscar los Tipos de Registros con el campo
	 * Bo_HojaN a true.
	 * 
	 * @param codCarga
	 *            - código de Carga.
	 * @return Lista de Tipos de Registros.
	 * @throws GadirServiceException
	 *             - Si ocurre cualquier error.
	 */
	List<CargaTipoRegistroDTO> findBoHojaN(Long codCarga)
	        throws GadirServiceException;

	/**
	 * Método que se encarga de buscar los Criterios de Inclusion/Exclusion
	 * relacionados con la estructura de entrada y el tipo de registro.
	 * 
	 * @param codCarga
	 *            - código de Carga.
	 * @param codTipoRegistro
	 *            - código de Carga.
	 * @return Lista de Tipos de Criterios de Inclusión/Exclusión.
	 * @throws GadirServiceException
	 *             - Si ocurre cualquier error.
	 */
	List<CargaCriterioDTO> findBoCriteriosIncExc(Long codCarga,
	        String codTipoRegistro) throws GadirServiceException;

	/**
	 * Método que se encarga de buscar las Posiciones de Casillas relacionados
	 * con la estructura de entrada y el tipo de registro.
	 * 
	 * @param codCarga
	 *            - código de Carga.
	 * @param codTipoRegistro
	 *            - código de Carga.
	 * @return Lista de Tipos de Carga Estructuras.
	 * @throws GadirServiceException
	 *             - Si ocurre cualquier error.
	 */
	List<CargaEstructuraDTO> findBoPosicionesCasillasReg(Long codCarga,
	        String codTipoRegistro) throws GadirServiceException;

	
	
	/**
	 * Método que se encarga de borrar el tipo de registro dado y sus
	 * dependencias.
	 * 
	 * @param idTipoReg
	 *            Id del tipo de registro.
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	void deleteTipoRegistro(CargaTipoRegistroDTOId idTipoReg)
	        throws GadirServiceException;

	/**
	 * Método que se encarga de eliminar los criterios de Inclusión/Exclusión
	 * relacionados.
	 * 
	 * @throws GadirServiceException
	 *             - Si ocurre cualquier error.
	 */
	void deleteCriteriosIncExc(List<CargaCriterioDTO> listaCriterios)
	        throws GadirServiceException;

	/**
	 * Método que se encarga de eliminar las posiciones de casillas
	 * relacionadas.
	 * 
	 * @param listaCriterios
	 *            - Lista de Elementos a eliminar.
	 * @throws GadirServiceException
	 *             - Si ocurre cualquier error.
	 */
	void deletePosicionesCasillas(List<CargaEstructuraDTO> listaCriterios)
	        throws GadirServiceException;

	/**
	 * Método que se encarga de guardar los criterios de Inclusión/Exclusión
	 * relacionados.
	 * 
	 * @throws GadirServiceException
	 *             - Si ocurre cualquier error.
	 */
	void saveCriteriosIncExc(List<CargaCriterioDTO> listaCriterios)
	        throws GadirServiceException;

	/**
	 * Método que se encarga de guardar las posiciones de casillas relacionadas.
	 * 
	 * @param listaCriterios
	 *            - Lista de Elementos a eliminar.
	 * @throws GadirServiceException
	 *             - Si ocurre cualquier error.
	 */
	void savePosicionesCasillas(List<CargaEstructuraDTO> listaCriterios)
	        throws GadirServiceException;
	
	/**
	 * Método que obtiene la lista de criterios cond asociados a una carga.
	 * @param  CargaTipoRegistroDTOId
	 * 			Identificador tipo de registro.
	 */
	public List<CargaCriterioCondicionDTO> obtenerListaCriteriosCond(CargaTipoRegistroDTOId idTipoReg);
	
	/**
	 * Método que obtiene la lista de criterios asociados a una carga.
	 * @param  CargaTipoRegistroDTOId
	 * 			Identificador tipo de registro.
	 */
	public List<CargaCriterioDTO> obtenerListaCriterios(CargaTipoRegistroDTOId idTipoReg) throws GadirServiceException;
	
	/**
	 * Método que obtiene la lista de Estructuras asociados a una carga.
	 * @param  CargaTipoRegistroDTOId
	 * 			Identificador tipo de registro.
	 */
	public List<CargaEstructuraDTO> obtenerListaEstructuras(CargaTipoRegistroDTOId idTipoReg) throws GadirServiceException;
	
	/**
	 * Método que guarda una estructura con el registro por defecto.
	 * @param  CargaTipoRegistroDTO
	 * 			Registro a almacenar.
	 */
	public void guardarTipoRegistroPorDefecto(final CargaTipoRegistroDTO registroGuardar) throws GadirServiceException;
	
	/**
	 * Método que elimina una estructura creando el registro por defecto.
	 * @param  CargaTipoRegistroDTO
	 * 			Registro a eliminar.
	 */
	public void eliminarCreandoRegistroDefecto(final CargaTipoRegistroDTO registro) throws GadirServiceException;
}
