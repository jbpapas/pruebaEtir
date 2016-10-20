package es.dipucadiz.etir.comun.bo;

import es.dipucadiz.etir.comun.dto.DocumentoDTOId;
import es.dipucadiz.etir.comun.dto.DocumentoLiquidacionDTO;
import es.dipucadiz.etir.comun.dto.DocumentoLiquidacionDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface DocumentoLiquidacionBO extends GenericBO<DocumentoLiquidacionDTO, DocumentoLiquidacionDTOId> {
	
	Boolean findDocumentoLiquidacion(DocumentoDTOId id) throws GadirServiceException;
	
	Boolean findDocumentoLiquidacionByMunicipioModeloVersionConceptoPeriodoEjercicio(String municipio, String provincia, String modelo, String version, String concepto, String periodo, String ejercicio) throws GadirServiceException;
	
}
