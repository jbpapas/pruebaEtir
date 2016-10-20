/**
 * 
 */
package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.dto.ProvinciaDTO;
import es.dipucadiz.etir.comun.dto.ProvinciaSinonimoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.KeyValue;

public interface ProvinciaBO extends GenericBO<ProvinciaDTO, String> {

	List<String> etiquetasProvincias() throws GadirServiceException;
	List<KeyValue> etiquetasProvincia(String coProvincia, List<MunicipioDTO> listaMunicipios, List<ProvinciaSinonimoDTO> listaProvinciasSinonimos) throws GadirServiceException;
	ProvinciaDTO getProvinciaByMunicipio(String rowidMunicicpio)throws GadirServiceException;
}
