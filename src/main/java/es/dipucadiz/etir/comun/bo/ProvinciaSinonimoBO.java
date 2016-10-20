/**
 * 
 */
package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.ProvinciaSinonimoDTO;
import es.dipucadiz.etir.comun.dto.ProvinciaSinonimoDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface ProvinciaSinonimoBO extends GenericBO<ProvinciaSinonimoDTO, ProvinciaSinonimoDTOId> {

	List<ProvinciaSinonimoDTO> findByProvincia(String provincia) throws GadirServiceException;	
	public List<ProvinciaSinonimoDTO> findCodigoByNombre(String nombreProvincia) throws GadirServiceException;	
}
