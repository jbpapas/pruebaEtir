package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.DocumentoNotDgcDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.sb07.comun.vo.DocumentoNotDgcVO;
import es.dipucadiz.etir.sb07.comun.vo.ResolucionCatastralSeleccionadaVO;
import es.dipucadiz.etir.sb07.comun.vo.ResolucionCatastralVO;

public interface DocumentoNotDgcBO extends GenericBO<DocumentoNotDgcDTO, Long>{
	
	
	
	Boolean findDocumentoCatastral(String tDocumento, String coProvincia, String coMunicipio, 
			String coModelo, String coConcepto, String coVersion, Short ejercicio, String coPeriodo)
			throws GadirServiceException;
	
	
	List<DocumentoNotDgcVO> findEnviosBop(String publicados, String noPublicados) throws GadirServiceException;
	
	List<ResolucionCatastralVO> findEnviosGrupoBop(Long grupoBop) throws GadirServiceException;
	
	List<ResolucionCatastralVO> findDocumentosEnvio(Long grupoBop, String fechaEnvio, String fechaPublicacion, String fechaNotificacion) throws GadirServiceException;

	
	
	List<ResolucionCatastralVO> findResolucionCatastralVO(String anoExpediente, String numExpediente, String docExpediente, 
			Boolean liquidados, Boolean noLiquidados, Boolean liquidables, Boolean noLiquidables) throws GadirServiceException;
	
	List<ResolucionCatastralVO> findResolucionCatastralVOByDocumento(String tDocumento, String coProvincia, String coMunicipio, 
			String coModelo, String coConcepto, String coVersion, Short ejercicio, String coPeriodo, Boolean liquidados, 
			Boolean noLiquidados, Boolean liquidables, Boolean noLiquidables) throws GadirServiceException;
	
	List<ResolucionCatastralVO> findResolucionCatastralVOByRefCatastral(String refCatastral, Boolean liquidados, 
			Boolean noLiquidados, Boolean liquidables, Boolean noLiquidables) throws GadirServiceException;
	
	List<ResolucionCatastralVO> findResolucionCatastralVOByNumDocumento(String codModelo, String codVersion, String codDocumento, 
			Boolean liquidados, Boolean noLiquidados, Boolean liquidables, Boolean noLiquidables) throws GadirServiceException;
	
	public ResolucionCatastralSeleccionadaVO buscaResolucionCatastralSeleccionadaVO(final Long coDocumentoNotDgc)
    throws GadirServiceException;
	
	DocumentoNotDgcDTO findByIdFetch(final Long coDocumentoNotDgc) throws GadirServiceException;
	
	public DocumentoNotDgcDTO findDocNotDgcByModeloVersionDocumento(String codModelo, String
			codVersion, String codDocumento) throws GadirServiceException;
	
	void modificarNotificacion(ResolucionCatastralSeleccionadaVO notificacion) throws GadirServiceException;
	
	void eliminarNotificacion(Long idNotificacion) throws GadirServiceException;

	List<ResolucionCatastralVO> findResolucionCatastralVOByCoCliente(String coCliente, boolean liquidados, boolean noLiquidados, boolean liquidables, boolean noLiquidables) throws GadirServiceException;
}