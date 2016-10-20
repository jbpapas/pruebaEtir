package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.CargaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

/**
 * Interface responsable de definir la lógica de negocio asociada a la clase del
 * modelo {@link CargaDTO}.
 * 
 * @version 2.0 18/11/2009
 * @author SDS[RMGARRIDO]
 */
public interface CargaBO extends GenericBO<CargaDTO, Long> {

	/**
	 * Método que se encarga de obtener la carga segun su id, hace fetch con
	 * Municipio, concepto y modelo-version.
	 * 
	 * @param coCarga
	 *            Código de la carga.
	 * @return Objeto {@link CargaDTO} con el municipio, concepto y
	 *         modelo-version informados.
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	CargaDTO findByIdLazy(final Long coCarga) throws GadirServiceException;

	/**
	 * Método que se encarga de obtener la carga según el número de registros en GA_CARGA ,
	 * se obtiene Municipio, concepto y modelo-version si solo hay un registro para ese municipio.
	 * 
	 * @param coMunicipio
	 *            Código de municipio.
	 * @return Objeto {@link CargaDTO} con el municipio, concepto y
	 *         modelo-version informados.
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	CargaDTO findNumeroEstructuras(final String coMunicipio) throws GadirServiceException;
	
	/**
	 * Método que se encarga de consultar las estructuras existentes según el
	 * filtro establecido por el usuario y ordenadas por nombre.
	 * 
	 * @param filtro
	 *            Filtro establecido por el usuario en el formulario de
	 *            búsqueda.
	 * @return List<CargaDTO> Lista de objetos poblados.
	 * @throws GadirServiceException
	 *             En el caso de que se produzca un error.
	 */
	List<CargaDTO> findEstructuraByParamString(String codModelo, String codVersion, String codMunicipio, String codConcepto)
	        throws GadirServiceException;
	
	/**
	 * Método que se encarga de consultar las estructuras existentes según el
	 * filtro establecido por el usuario y ordenadas por nombre.
	 * 
	 * @param filtro
	 *            Filtro establecido por el usuario en el formulario de
	 *            búsqueda.
	 * @return List<CargaDTO> Lista de objetos poblados.
	 * @throws GadirServiceException
	 *             En el caso de que se produzca un error.
	 */
	List<CargaDTO> findEstructuraByParam(CargaDTO filtro)
	        throws GadirServiceException;
	
	/**
	 * Método que se encarga de duplicar una estructura y sus elementos
	 * asociados.
	 * 
	 * @param carga
	 *            CargaDTO El elemento que se va a duplicar.
	 * @param presenta
	 *            Indica si la estructura presenta tipos de registro.
	 * @throws GadirServiceException
	 *             En el caso de que se produzca un error.
	 */
	void duplicar(CargaDTO carga, boolean presenta)
	        throws GadirServiceException;

	/**
	 * Método que se encarga de recuperar los elementos de la base de datos
	 * filtrados por una clave única.
	 * 
	 * @param filtro
	 *            Objeto con los datos que no deben repetirse (clave única).
	 * @return List<CargaDTO> Listado de objetos poblados.
	 * @throws GadirServiceException
	 *             En el caso de que se produzca un error.
	 */
	List<CargaDTO> findByUniqueKey(CargaDTO filtro)
	        throws GadirServiceException;

	/**
	 * Método que se encarga de guardar una nueva estructura en la base de
	 * datos.
	 * 
	 * @param carga
	 *            La nueva estructura a insertar
	 * @param presenta
	 *            Valor boolean que indica si la estructura presenta tipos de
	 *            registro.
	 * @throws GadirServiceException
	 *             En el caso de que se produzca un error.
	 */
	void saveOnly(CargaDTO carga, boolean presenta)
	        throws GadirServiceException;

	/**
	 * Método que se encarga de eliminar una estructura, eliminando previamente
	 * todos los elementos asociados a esta.
	 * 
	 * @param coCarga
	 *            Identificador de la estructura que se va a eliminar.
	 * @throws GadirServiceException
	 *             En el caso de que se produzca un error.
	 */
	void deleteEstructuraCascade(final Long coCarga)
	        throws GadirServiceException;

	/**
	 * Método que se encarga de duplicar una estructura y sus respectivos
	 * elementos asociados.
	 * 
	 * @param carga
	 *            La estructura que se va a actualizar.
	 * @param presentaTipos
	 *            Indicador de tipos de registro.
	 * @throws GadirServiceException
	 *             En el caso de que se produzca un error.
	 */
	void updateEstructura(CargaDTO carga, boolean presentaTipos)
	        throws GadirServiceException;
}
