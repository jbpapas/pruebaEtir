package es.dipucadiz.etir.comun.bo;

import es.dipucadiz.etir.comun.dto.ProcesoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;


public interface ProcesoBO extends GenericBO<ProcesoDTO, String> {

	
	ProcesoDTO findByUrlAndTipoMP(String url) throws GadirServiceException;


	
	
}
