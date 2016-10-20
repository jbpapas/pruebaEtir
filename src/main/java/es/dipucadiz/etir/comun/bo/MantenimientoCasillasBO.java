package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.action.mantenimientoCasillas.MCDocumentoVO;
import es.dipucadiz.etir.comun.dto.DocumentoDTO;
import es.dipucadiz.etir.comun.dto.ValidacionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;



public interface MantenimientoCasillasBO{
	
	public MCDocumentoVO getDocumento(String coModelo, String coVersion, String coDocumento, String operacion) throws GadirServiceException;

	public List<Short> findCasillasLigadas(String coModelo, String coVersion, short nuCasilla) throws GadirServiceException;
	
	public boolean ejecutaValidacion(MCDocumentoVO mcDocumentoVO, List<ValidacionDTO> validacionDTOs, short hoja) throws GadirServiceException;
	
	public void saveDocumentoCompleto(MCDocumentoVO mcDocumentoVO, String coProcesoActual) throws GadirServiceException;
	
	public DocumentoDTO findDocumento(String coModelo, String coVersion, String coDocumento) throws GadirServiceException;
	
	public int borraDocumentoCompleto(MCDocumentoVO mcDocumentoVO, String coProcesoActual) throws GadirServiceException;

	public void cargarHojasRepeticion(MCDocumentoVO mcDocumentoVO) throws GadirServiceException;
}
