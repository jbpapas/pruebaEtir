package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.FuncionArgumentoDTO;
import es.dipucadiz.etir.comun.dto.FuncionArgumentoDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

/**
 * Interface responsable de definir la lógica de negocio asociada a la clase del
 * modelo {@link FuncionArgumentoDTO}.
 * 
 * @version 1.0 17/12/2009
 * @author SDS[FJTORRES]
 */
public interface FuncionArgumentoBO extends
        GenericBO<FuncionArgumentoDTO, FuncionArgumentoDTOId> {

	/**
	 * Método que se encarga de obtener la lista de argumentos de la funcion
	 * dada.
	 * 
	 * @param coFuncion
	 *            Código de la funcion.
	 * @param salida
	 *            Indica si queremos argumentos de salida.
	 * @return Lista de argumentos.
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	List<FuncionArgumentoDTO> findArgumentosFuncion(String coFuncion,
	        boolean salida) throws GadirServiceException;
	        
	/**
	 * Método que se encarga de obtener la lista de argumentos de la funcion
	 * dada.
	 * 
	 * @param coFuncion
	 *            Código de la funcion.
	 * @return Lista de argumentos.
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	List<FuncionArgumentoDTO> findArgumentosFuncion(String coFuncion)
	        throws GadirServiceException;
}
