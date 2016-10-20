package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.AcmUsuarioBotonDTO;
import es.dipucadiz.etir.comun.dto.AcmUsuarioBotonDTOId;
import es.dipucadiz.etir.comun.dto.AcmUsuarioDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;



public interface AcmUsuarioBotonBO extends GenericBO<AcmUsuarioBotonDTO, AcmUsuarioBotonDTOId> {
	
	List<AcmUsuarioBotonDTO> findByUsuario(AcmUsuarioDTO acmUsuarioDTO) throws GadirServiceException;
	
}
