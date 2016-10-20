package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.AyudaCampoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;



public interface AyudaCampoBO extends GenericBO<AyudaCampoDTO, Long>{
	
	public List<AyudaCampoDTO> findByProcesoAndCampo(String coProceso, String campo) throws GadirServiceException ;
	
	
}
