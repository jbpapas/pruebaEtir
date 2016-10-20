package es.dipucadiz.etir.comun.bo;

import es.dipucadiz.etir.comun.dto.CalleTramoPostalDTO;
import es.dipucadiz.etir.comun.dto.CalleTramoPostalDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface CalleTramoPostalBO extends GenericBO<CalleTramoPostalDTO, CalleTramoPostalDTOId>{


	String findCodigoPostalAsString(Long coCalle, Integer numero);
	CalleTramoPostalDTO findTramo(Long coCalle,String numero) throws GadirServiceException;
	
}
