package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.HDomicilioNotificacionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.sb07.comun.vo.HGlobalDomiciliosVO;


public interface HConceptoDomicilioNotificacionBO extends
        GenericBO<HDomicilioNotificacionDTO, Long> {

	
	/**
	 * Método encargado de obtener los historicos de domicilios de notificación
	 * 
	 * @param coCliente
	 * @param codMunicipio
	 * @param codCalle
	 * @param codConcepto
	 * @param fecha
	 * @return
	 * @throws GadirServiceException
	 */
	List<HGlobalDomiciliosVO> findByClienteAndOrMunicipioCalleConceptoFecha(final String coCliente, final String codProvincia, final String codMunicipio, final String codCalle, final String codConcepto, final String fecha) throws GadirServiceException;
	
	/**
	 * Método encargado de obtener los historicos de domicilios de notificación
	 * 
	 * @param coCliente
	 * @param codMunicipio
	 * @param codCalle
	 * @param nombreConcepto
	 * @param fecha
	 * @return
	 * @throws GadirServiceException
	 */
	List<HGlobalDomiciliosVO> findByClienteAndOrMunicipioCalleNombreConceptoFecha(final String coCliente, final String codMunicipio, final String codCalle, final String nombreConcepto, final String fecha) throws GadirServiceException;
	
	/**
	 * Método encargado de obtener el historico de domicilio de notificación seleccionado
	 * 
	 * @param rowid
	 * @return
	 * @throws GadirServiceException
	 */
	HGlobalDomiciliosVO findByRowId(String rowid) throws GadirServiceException;
}
