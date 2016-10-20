package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.CargaEstructuraDTO;
import es.dipucadiz.etir.comun.dto.CargaEstructuraDTOId;
import es.dipucadiz.etir.comun.dto.CargaTipoRegistroDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

/**
 * Interface responsable de definir la lógica de negocio asociada a la clase del
 * modelo {@link CargaEstructuraDTO}.
 * 
 * @version 1.0 26/11/2009
 * @author SDS[FJTORRES]
 */
public interface CargaEstructuraBO extends
        GenericBO<CargaEstructuraDTO, CargaEstructuraDTOId> {

	/**
	 * Método que se encarga de obtener el listado de asociaciones de posiciones
	 * a casillas asociadas al tipo de registro dado.
	 * 
	 * @param idTipReg
	 *            Id del tipo de registro.
	 * @return Lista de objetos poblados.
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	List<CargaEstructuraDTO> findListadoByTipoRegistro(
	        final CargaTipoRegistroDTOId idTipReg) throws GadirServiceException;

	/**
	 * Método que se encarga de borrar una asociacion para el numero de casilla
	 * dado, obteniendo la estructura y tipo de registro de la nueva asociacion
	 * que se va crear.
	 * 
	 * @param transientObject
	 *            Asociacion a guardar.
	 * @param nuCasilla
	 *            Numero de casilla de la asociacion a borrar.
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	void saveAndDelete(final CargaEstructuraDTO transientObject,
	        final Short nuCasilla) throws GadirServiceException;
}
