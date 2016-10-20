package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.ModeloVersionDTOId;
import es.dipucadiz.etir.comun.dto.MunicipioDTOId;
import es.dipucadiz.etir.comun.dto.ParametroMunicipioDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;



public interface ParametroMunicipioBO extends GenericBO<ParametroMunicipioDTO, Long> {
	
	
	/**
	 * Metodo para obtener un ParametroMunicipio por su id.
	 * 
	 * @param codParametro
	 * @return ParametroMunicipioDTO
	 * @throws GadirServiceException
	 */
	public ParametroMunicipioDTO findParametroMunicipioById(Long codParametro) throws GadirServiceException;
	
	/**
	 * Metodo para eliminar un ParametroMunicipio junto a sus valores por su id.
	 * 
	 * @param codParametroMunicipio
	 * @return void
	 * @throws GadirServiceException
	 */
	public void deleteParametroMunicipioAndValores(Long codParametroMunicipio) throws GadirServiceException;
	
	
	List<Object[]> findByMunicipioConceptoModeloVersion(MunicipioDTOId idMunicipio,
			String codigoConcepto, ModeloVersionDTOId idModeloVersion) throws GadirServiceException;

}
