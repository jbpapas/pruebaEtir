package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.EstadoSituacionDTO;
import es.dipucadiz.etir.comun.dto.ModoCobroDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

/**
 * Interface responsable de definir la lógica de negocio asociada a la clase del
 */
public interface EstadoSituacionBO extends GenericBO<EstadoSituacionDTO, String> {
	
	public List<EstadoSituacionDTO> findAll() throws GadirServiceException;

}
