package es.dipucadiz.etir.comun.bo;

import java.text.ParseException;
import java.util.List;

import es.dipucadiz.etir.comun.dto.CargaControlRecepcionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

/**
 * Interface responsable de definir la lógica de negocio asociada a la clase del
 * modelo {@link CargaControlRecepcionDTO}.
 * 
 * @version 1.0 09/11/2009
 * @author SDS[AGONZALEZ]
 */
public interface CargaControlRecepcionBO extends GenericBO<CargaControlRecepcionDTO, Long> {

	public List<CargaControlRecepcionDTO> getListadoCargaControlRecepcion(
	        String municipio, String concepto, String modelo, String version, String fxDesde, String fxHasta) throws GadirServiceException, ParseException; 
	
	public List<CargaControlRecepcionDTO> getListadoCargaControlRecepcionAceptadas(
	        String municipio, String concepto, String modelo, String version, String fxDesde, String fxHasta) throws GadirServiceException, ParseException; 
	
	
	
	
	
}
