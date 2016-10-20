/**
 * 
 */
package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.MunicipioCodterDTO;
import es.dipucadiz.etir.comun.dto.MunicipioCodterDTOId;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface MunicipioCodterBO extends GenericBO<MunicipioCodterDTO, MunicipioCodterDTOId> {

	List<MunicipioDTO> findByCodter(String coCodigoTerritorial) throws GadirServiceException;
	MunicipioCodterDTO findById(final String codigoTerritorial, final String provincia, final String municipio) throws GadirServiceException;
//	List<CodigoTerritorialDTO> findByMunicipio(String provincia, String municipio) throws GadirServiceException;
	
}
