package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.AcmUsuarioDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;



public interface AcmUsuarioBO extends GenericBO<AcmUsuarioDTO, String>{
	
	public AcmUsuarioDTO findByIdInitialized(String id) throws GadirServiceException;
	
	public List<AcmUsuarioDTO> findByCodigoTerritorialStartsWith(String prefijoCodigoTerritorial) throws GadirServiceException;
	public List<AcmUsuarioDTO> findByCodigoTerritorialStartsWithInitialized(String prefijoCodigoTerritorial) throws GadirServiceException;
	
	public List<AcmUsuarioDTO> findByUnidadAdminAndCodigoTerritorialStartsWith(String prefijoCodigoTerritorial, String coUnidadAdministrativa) throws GadirServiceException;
}
