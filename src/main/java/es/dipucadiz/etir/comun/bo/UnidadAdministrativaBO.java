/**
 * 
 */
package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.AcmUsuarioDTO;
import es.dipucadiz.etir.comun.dto.UnidadAdministrativaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.KeyValue;

public interface UnidadAdministrativaBO extends GenericBO<UnidadAdministrativaDTO, String> {

	
	List<KeyValue> findUsuariosRecaudacion() throws GadirServiceException;
	KeyValue findUsuarioRecaudacion(final String coUnidadAdministrativa) throws GadirServiceException;
	
	void validarCoUnidadAdministrativa(final String coUnidadAdministrativa, final boolean isAlta) throws GadirServiceException;
	void validarNombre(final String coUnidadAdministrativa, final String nombre) throws GadirServiceException;
	/**
	 * 
	 * @param unidadRegistral
	 * @param coUnidadAdministrativa
	 * @param unidadRegistralQuePertenece
	 * @param unidadAdministrativaQueDepende
	 * @return Devuelve en posición 0 el DTO de unidadRegistralQuePertenece y en posición 1 el DTO de unidadAdministrativaQueDepende
	 * @throws GadirServiceException
	 */
	UnidadAdministrativaDTO validarUnidadRegistralQuePertenece(String unidadRegistral, String coUnidadAdministrativa, String unidadRegistralQuePertenece) throws GadirServiceException;
	UnidadAdministrativaDTO validarUnidadAdministrativaQueDepende(String unidadRegistral, String coUnidadAdministrativa, String unidadAdministrativaQueDepende) throws GadirServiceException;
	AcmUsuarioDTO validarUsuarioRecaudacion(String usuarioRecaudacion) throws GadirServiceException;
	Integer validarTelefono(String telefono) throws GadirServiceException;
	void validarPrincipal(String principal) throws GadirServiceException;
	void validarEmail(String email) throws GadirServiceException;
	
	public UnidadAdministrativaDTO findByIdInitialized(String codigoUnidadAdministrativa);

	


	
}
