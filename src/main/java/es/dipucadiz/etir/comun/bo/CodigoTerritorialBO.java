/**
 * 
 */
package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.CodigoTerritorialDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.dto.UnidadAdministrativaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface CodigoTerritorialBO extends GenericBO<CodigoTerritorialDTO, String>{

	List<CodigoTerritorialDTO> findByMunicipio(String provincia, String municipio);
	List<CodigoTerritorialDTO> findByNotMunicipio(String provincia, String municipio);
	List<String> etiquetasCodigosTerritoriales() throws GadirServiceException;
	List<String> etiquetasCodigoTerritorial(final String coCodigoTerritorial, final List<MunicipioDTO> listaMunicipios, final List<UnidadAdministrativaDTO> listaUnidadesAdministrativas) throws GadirServiceException;
	
}
