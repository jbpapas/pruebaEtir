package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.ParametroMunicipioValorDTO;
import es.dipucadiz.etir.comun.dto.ParametroMunicipioValorDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;



public interface ParametroMunicipioValorBO extends GenericBO<ParametroMunicipioValorDTO, ParametroMunicipioValorDTOId> {
	
	
	List<ParametroMunicipioValorDTO> findByParametroMunicipio(Long codParametroMunicipio) throws GadirServiceException;
	
}
