/**
 * 
 */
package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.CodigoTerritorialDTO;
import es.dipucadiz.etir.comun.dto.CodterrUniadmDTO;
import es.dipucadiz.etir.comun.dto.CodterrUniadmDTOId;
import es.dipucadiz.etir.comun.dto.UnidadAdministrativaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface CodterrUniadmBO extends GenericBO<CodterrUniadmDTO, CodterrUniadmDTOId>{

	List<UnidadAdministrativaDTO> findByCodter(String coCodigoTerritorial) throws GadirServiceException;
	List<CodigoTerritorialDTO> findByUniadm(String coUnidadAdministrativa) throws GadirServiceException;
	
}
