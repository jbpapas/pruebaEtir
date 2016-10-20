package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.AcmMenuDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;



public interface AcmMenuBO extends GenericBO<AcmMenuDTO, String>{
	
	
	public List<AcmMenuDTO> findByAcmPerfil(String coAcmPerfil) throws GadirServiceException;
	public List<AcmMenuDTO> findByAcmPerfilSinOrden(String coAcmPerfil) throws GadirServiceException;
	
}
