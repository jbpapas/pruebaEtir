package es.dipucadiz.etir.comun.bo;

import es.dipucadiz.etir.comun.dto.DocumentoSeguimientoDTO;
import es.dipucadiz.etir.comun.dto.HDocumentoLiquidacionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface HDocumentoLiquidacionBO extends GenericBO<HDocumentoLiquidacionDTO, Long> {
	public HDocumentoLiquidacionDTO findBySeguimiento(DocumentoSeguimientoDTO documentoSeguimientoDTO) throws GadirServiceException;
}
