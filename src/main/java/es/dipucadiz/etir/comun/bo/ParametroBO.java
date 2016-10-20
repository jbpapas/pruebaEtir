package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.ParametroDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;



public interface ParametroBO extends GenericBO<ParametroDTO, String> {

	/**
	 * Método que comprueba si existe un parámetro con el mismo codigo y nombre.
	 * 
	 * @param codParametro
	 * @param nombre
	 * @return Boolean
	 * @throws GadirServiceException
	 */
	public Boolean findParametro(String codParametro)
	throws GadirServiceException;
	
	public List<ParametroDTO> listarTodos() throws GadirServiceException;
	
}
