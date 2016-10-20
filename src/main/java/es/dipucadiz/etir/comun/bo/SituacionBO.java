package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.SituacionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface SituacionBO extends GenericBO<SituacionDTO, String>{
	
	public List<SituacionDTO> findAll() throws GadirServiceException; 
	public List<SituacionDTO> findByModelo(String coModelo);
	
}
