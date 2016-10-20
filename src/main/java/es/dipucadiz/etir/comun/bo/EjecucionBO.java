package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.EjecucionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;



public interface EjecucionBO extends GenericBO<EjecucionDTO, Long> {
	
	public EjecucionDTO insert(String proceso, String usuario, String script, String cola, List<String> parametros)
		throws GadirServiceException;
	public EjecucionDTO findByCoEjecucionInitialized(int codigoEjecucion);

}
