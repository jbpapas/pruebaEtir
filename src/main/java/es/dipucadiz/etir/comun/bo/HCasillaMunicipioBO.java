package es.dipucadiz.etir.comun.bo;

import java.util.Date;
import java.util.List;

import es.dipucadiz.etir.comun.dto.FuncionDTO;
import es.dipucadiz.etir.comun.dto.HCasillaMunicipioDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

/**
 * Interface responsable de definir la lógica de negocio asociada a la clase del
 * modelo {@link FuncionDTO}.
 * 
 * @version 1.0 03/12/2009
 * @author SDS[DSOLIS]
 */
public interface HCasillaMunicipioBO extends GenericBO<HCasillaMunicipioDTO, Long> {

	/**
	 * Método que se encarga de obtener todas los historicos de casilla municipio
	 * parametros de salida.
	 * 
	 * @param coMunicipio,String coConcepto,String coModelo,String coVersion
	 *           
	 * @return Lista de HCasillaMunicipio
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	
	
	List<HCasillaMunicipioDTO> obtenerHCasillasMunicipio( String coMunicipio,
															String coConcepto,
															String coModelo,
															String coVersion, Date fecha)
	        throws GadirServiceException;
	        
}
