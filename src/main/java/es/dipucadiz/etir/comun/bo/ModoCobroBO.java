package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.ModoCobroDTO;
import es.dipucadiz.etir.comun.dto.SituacionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

/**
 * Interface responsable de definir la lógica de negocio asociada a la clase del
 */
public interface ModoCobroBO extends GenericBO<ModoCobroDTO, String> {
	
	public List<ModoCobroDTO> findByEstadoOrigen(String coEstado ) throws GadirServiceException;
	public List<ModoCobroDTO> findByEstadoDestino(String coEstado ) throws GadirServiceException;
	public List<ModoCobroDTO> findAll() throws GadirServiceException;
}
