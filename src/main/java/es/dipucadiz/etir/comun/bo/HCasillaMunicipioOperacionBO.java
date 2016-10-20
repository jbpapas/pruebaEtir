package es.dipucadiz.etir.comun.bo;

import java.util.Date;
import java.util.List;

import es.dipucadiz.etir.comun.dto.HCasillaMunicipioOperaciDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;


/**
 * Interface responsable de definir la lógica de negocio asociada a la clase del HCasillaMunicipioOperacionBO
 * 
 * 
 * @version 1.0 03/12/2009
 * @author SDS[DSOLIS]
 */
public interface HCasillaMunicipioOperacionBO extends GenericBO<HCasillaMunicipioOperaciDTO, Long> {
	
	List<HCasillaMunicipioOperaciDTO> obtenerHCasillasMunicipioOpe( Long cocasillaMunicipio,
															Date fecha)
															throws GadirServiceException;
	
	
	
}

