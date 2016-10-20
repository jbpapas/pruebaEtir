/**
 * 
 */
package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.CargaDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface MunicipioBO extends GenericBO<MunicipioDTO, MunicipioDTOId> {

	List<MunicipioDTO> findByProvincia(String provincia) throws GadirServiceException;
	
	List<MunicipioDTO> findMunicipiosByParam(CargaDTO filtro)
	        throws GadirServiceException;
	
	List<MunicipioDTO> findMunicipiosByCalle(final Long filtro) throws GadirServiceException;
	
	public List<MunicipioDTO> findMunicipiosCallejero(String idProvincia) throws GadirServiceException;
	
	public List<MunicipioDTO> findMunicipiosByProvincia(String idProvincia) throws GadirServiceException;
}
