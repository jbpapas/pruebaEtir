package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.CargaCriterioCondicionDTO;
import es.dipucadiz.etir.comun.dto.CargaCriterioCondicionDTOId;
import es.dipucadiz.etir.comun.dto.CargaCriterioDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

/**
 * Interface responsable de definir la lógica de negocio asociada a la clase del
 * modelo {@link CargaCriterioCondicionDTO}.
 * 
 * @version 1.0 01/12/2009
 * @author SDS[FJTORRES]
 */
public interface CargaCriterioCondicionBO extends
        GenericBO<CargaCriterioCondicionDTO, CargaCriterioCondicionDTOId> {

	/**
	 * Método que se encarga de grabar la condicion del criterio y el criterio
	 * al que esta asociada.
	 * 
	 * @param condicion
	 *            Condicion a guardar, contiene en su id el criterio a guardar.
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	void saveWithCargaCriterio(final CargaCriterioCondicionDTO condicion)
	        throws GadirServiceException;

	/**
	 * Método que se encarga de obtener el siguiente valor del campo clave de la
	 * condición, de un determinado criterio.
	 * 
	 * @param idCriterio
	 *            Id del criterio del que queremos obtener el siguiente valor
	 * @return Numero entero con el siguiente valor.
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	byte getNextClave(final CargaCriterioDTOId idCriterio)
	        throws GadirServiceException;

	/**
	 * Método que se encarga de obtener las condiciones iniciales de las
	 * expresiones filtrandolas por tipo de registro o por criterio de carga,
	 * segun lo indicado en el parametro filtroCriterio.
	 * 
	 * @param idCriterio
	 *            Id del criterio de carga del que queremos obtener las
	 *            condiciones.
	 * @param filtroCriterio
	 *            Indica si se filtra por el criterio de carga.
	 * @return Listado de objetos poblados.
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	List<CargaCriterioCondicionDTO> findCondicionesIniciales(
	        final CargaCriterioDTOId idCriterio, final boolean filtroCriterio)
	        throws GadirServiceException;

	/**
	 * Método que se encarga de obtener todas las condiciones de un criterio
	 * dado.
	 * 
	 * @param idCriterio
	 *            Id del criterio.
	 * @return Lista de condiciones asociadas al criterio.
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	List<CargaCriterioCondicionDTO> findListaCondicioneCriterio(
	        final CargaCriterioDTOId idCriterio) throws GadirServiceException;

}
