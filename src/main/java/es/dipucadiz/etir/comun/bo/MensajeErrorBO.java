package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.MensajeErrorDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

/**
 * Interface responsable de definir la lógica de negocio asociada a la clase del
 * modelo {@link MensajeErrorDTO}.
 * 
 * @version 1.0 03/12/2009
 * @author SDS[FJTORRES]
 */
public interface MensajeErrorBO extends GenericBO<MensajeErrorDTO, Integer> {
	List<MensajeErrorDTO> buscarTextoError(String cadena) throws GadirServiceException;
}
