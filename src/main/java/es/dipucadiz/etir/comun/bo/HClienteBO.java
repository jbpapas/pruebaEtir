/**
 * 
 */
package es.dipucadiz.etir.comun.bo;

import java.util.Date;
import java.util.List;

import es.dipucadiz.etir.comun.dto.ClienteDTO;
import es.dipucadiz.etir.comun.dto.HClienteDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface HClienteBO extends GenericBO<HClienteDTO, Long>{

	/**
	 * Método usado para buscar los clientes históricos de un id dado
	 * 
	 * @param id
	 * @return
	 */
	List<HClienteDTO> findHClienteById(Long id) throws GadirServiceException;
	
	/**
	 * Método usado para buscar los clientes históricos de un id dado y fecha
	 * 
	 * @param id
	 * @return
	 */
	List<HClienteDTO> findHClienteByIdYFecha(Long id, Date fecha) throws GadirServiceException;
	
	/**
	 * Método usado para buscar los clientes históricos de un rowid dado
	 * 
	 * @param id
	 * @return
	 */
	List<HClienteDTO> findHClienteByRowid(String rowid) throws GadirServiceException;
	
	/**
	 * Método usado para almacenar un histórico de cliente.
	 * 
	 * @param cliente
	 * @param tipoMovimiento
	 * @return
	 */
	void guardarHCliente(ClienteDTO cliente, String tipoMovimiento) throws GadirServiceException;
}
