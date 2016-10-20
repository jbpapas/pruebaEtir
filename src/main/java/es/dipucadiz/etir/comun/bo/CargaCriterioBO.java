package es.dipucadiz.etir.comun.bo;
import java.util.List;

import es.dipucadiz.etir.comun.dto.CargaCriterioDTO;
import es.dipucadiz.etir.comun.dto.CargaCriterioDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

/**
 * Interface responsable de definir la lógica de negocio asociada a la clase del
 * modelo {@link CargaCriterioDTO}.
 * 
 * @version 1.0 01/12/2009
 * @author SDS[FJTORRES]
 */
public interface CargaCriterioBO extends GenericBO<CargaCriterioDTO, CargaCriterioDTOId> {

	/**
	 * Método que se encarga de consultar los criterios existentes según el
	 * filtro establecido por el usuario y ordenadas por nombre.
	 * 
	 * @param filtro Filtro que contiene un codigo de Estructura de Entrada (coCarga).
	 * @return List<CargaCriterioDTO> Lista de objetos poblados.
	 * @throws GadirServiceException En el caso de que se produzca un error.
	 */
	List<CargaCriterioDTO> findCriteriosById(Long codCarga)
			throws GadirServiceException;
			
	/**
	 * Método que se encarga de borrar el criterio de carga y todas sus
	 * condiciones.
	 * 
	 * @param id
	 *            Id del criterio de carga a eliminar.
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	void deleteWithCondiciones(final CargaCriterioDTOId id)
			throws GadirServiceException;	
	

	/**
	 * Método que obtiene el ultimo codigo de un criterio en base de datos para un tipo de registro dado.
	 * 
	 * @param cargaCriterioCondicionDao
	 *            El cargaCriterioCondicionDao.
	 */
	public int obtenerUltimoID(CargaCriterioDTOId id)throws GadirServiceException;
}
