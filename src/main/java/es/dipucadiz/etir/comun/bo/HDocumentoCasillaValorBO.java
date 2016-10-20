package es.dipucadiz.etir.comun.bo;

import es.dipucadiz.etir.comun.dto.DocumentoCasillaValorDTO;
import es.dipucadiz.etir.comun.dto.HDocumentoCasillaValorDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

/**
 * Interface responsable de definir la lógica de negocio asociada a la clase del
 * modelo {@link HDocumentoCasillaValorDTO}.
 * 
 * @version 1.0 25/01/2010
 * @author SDS[agonzalez]
 */
public interface HDocumentoCasillaValorBO extends
        GenericBO<HDocumentoCasillaValorDTO, Long> {

	/**
	 * Método encargado de actualizar el histórico correspondiente a documento
	 * casilla valor.
	 * 
	 * @param casilla
	 *            - Objeto de tipo DocumentoCasillaValorDTO
	 * @param proceso
	 *            - Codigo del proceso que lo genera.
	 * @throws GadirServiceException
	 *             - Si ocurre cualquier error.
	 */
	void actualizarHistorico(DocumentoCasillaValorDTO casilla, String proceso)
	        throws GadirServiceException;

}
