package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.FuncionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;


public interface FuncionBO extends GenericBO<FuncionDTO, String> {

	
	List<FuncionDTO> getFuncionesPorTipo(String tipo) throws GadirServiceException;
	
	FuncionDTO getFuncionByNombre(String nombre) throws GadirServiceException;
	        
}
