/**
 * 
 */
package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.MunicipioSinonimoDTO;
import es.dipucadiz.etir.comun.dto.MunicipioSinonimoDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface MunicipioSinonimoBO extends GenericBO<MunicipioSinonimoDTO, MunicipioSinonimoDTOId> {

	List<MunicipioSinonimoDTO> findByMunicipio(String provincia, String municipio) throws GadirServiceException;
	List<MunicipioSinonimoDTO> findCodigoByProvinciaNombre(String coProvincia, String nombreMunicipio) throws GadirServiceException;

}
