/**
 * 
 */
package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.DomicilioDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.sb07.comun.vo.DomicilioVO;

public interface DomicilioBO extends GenericBO<DomicilioDTO, Long>{
	
	DomicilioDTO findByIdFetch(final String coDomicilio) throws GadirServiceException;
	List<DomicilioDTO> findByClienteAndUnidadUrbana(final String coCliente, final String coUnidadUrbana) throws GadirServiceException;
	DomicilioVO findByIdFetchConTributario(final String coDomicilio) throws GadirServiceException;
	
	List<DomicilioDTO> findByCliente(final String coCliente) throws GadirServiceException;
	List<DomicilioVO> findVOByClienteAndOrMunicipioCalle(final String coCliente, final String codProvincia, final String codMunicipio, final String codCalle) throws GadirServiceException;
	List<DomicilioVO> findByClienteMunicipioCalleConTributario(final String coCliente, final String codProvincia, final String codMunicipio, final String codCalle, int first, int max) throws GadirServiceException;
	List<DomicilioVO> findByClienteAndOrMunicipioCalleConcepto(final String coCliente, final String codProvincia, final String codMunicipio, 
			final String codCalle, final String codConcepto, final boolean isAyuntamiento) throws GadirServiceException;
	void actualizarDomicilio(DomicilioDTO domicilio, String Movimiento) throws GadirServiceException;
	
	/**
	 * Método encargado de eliminar un domicilio
	 * 
	 * @param domicilio
	 * @throws GadirServiceException
	 */
	public void eliminarDomicilio(DomicilioDTO domicilio) throws GadirServiceException; 
	
	/**
	 * Método que trae todos los domicilios que no son de notificación
	 * 
	 * @param coCliente
	 * @param codMunicipio
	 * @param codCalle
	 * @return
	 * @throws GadirServiceException
	 */
	List<DomicilioVO> findByClienteAndOrMunicipioCalleSinNotificacion(final String coCliente, final String codProvincia, final String codMunicipio, final String codCalle)throws GadirServiceException;

	public DomicilioDTO findDomicilioFiscalByCliente(final Long coCliente, final boolean conClienteDTO) throws GadirServiceException;
	public DomicilioDTO findDomicilioByIdInicializado(Long coDomicilio, final boolean conClienteDTO) throws GadirServiceException;
}
