package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.AcmBotonDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;



public interface AcmBotonBO extends GenericBO<AcmBotonDTO, Long>{
	
	
	public List<AcmBotonDTO> findByAcmPerfil(String coAcmPerfil) throws GadirServiceException;
	
	public List<AcmBotonDTO> findByAcmUsuario(String coAcmUsuario) throws GadirServiceException;

	
}
