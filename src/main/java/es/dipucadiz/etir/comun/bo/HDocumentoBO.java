package es.dipucadiz.etir.comun.bo;


import es.dipucadiz.etir.comun.dto.DocumentoDTO;
import es.dipucadiz.etir.comun.dto.HDocumentoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface HDocumentoBO extends GenericBO<HDocumentoDTO, Long>{

	public void guardarHDocumento(DocumentoDTO documento,String movimiento) throws GadirServiceException;


}