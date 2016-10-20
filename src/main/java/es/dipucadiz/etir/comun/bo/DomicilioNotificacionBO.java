/**
 * 
 */
package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.DomicilioDTO;
import es.dipucadiz.etir.comun.dto.DomicilioNotificacionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface DomicilioNotificacionBO extends GenericBO<DomicilioNotificacionDTO, Long>{
	
	DomicilioNotificacionDTO findByIdFetch(final String coDomicilioNotificacion) throws GadirServiceException;
	List<DomicilioNotificacionDTO> findByClienteAndDomicilio(final String coCliente, final String coDomicilio, final boolean isAyuntamiento) throws GadirServiceException;
	
	/**
	 * Da de alta el domicilio de notificacion.
	 * 
	 * @param domicilioNotificacion
	 * @throws GadirServiceException
	 */
	void altaDomicilioNotificacion(DomicilioNotificacionDTO domicilioNotificacion) throws GadirServiceException;
	
	/**
	 * Actualiza el domicilio de notificacion.
	 * Los cambios realizados son guardados en el historico.
	 * 
	 * @param domicilioNotificacion
	 * @throws GadirServiceException
	 */
	void actualizarDomicilioNotificacion(DomicilioNotificacionDTO domicilioNotificacion) throws GadirServiceException;
	
	/**
	 * Método encargado de eliminar un concepto asociado a un domicilio de notificación.
	 * En el caso de no tener más conceptos asociados, el domicilio deja de ser un domicilio
	 * de notificación.
	 * 
	 * @param domicilioNotificacion
	 * @throws GadirServiceException
	 */
	void eliminarDomicilioNotificacion(DomicilioNotificacionDTO domicilioNotificacion) throws GadirServiceException;
	
	/**
	 * Método encargado de eliminar un domicilio de notificación junto a todos
	 * sus conceptos asociados.
	 * 
	 * @param domicilioNotificacion
	 * @throws GadirServiceException
	 */
	void eliminarDomicilioNotificacionConceptos(DomicilioDTO domicilioNotificacion, final boolean isAyuntamiento)	throws GadirServiceException;
}
