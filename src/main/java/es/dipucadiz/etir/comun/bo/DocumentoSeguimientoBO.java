package es.dipucadiz.etir.comun.bo;

import es.dipucadiz.etir.comun.dto.DocumentoSeguimientoDTO;

public interface DocumentoSeguimientoBO extends GenericBO<DocumentoSeguimientoDTO, Long>{
	DocumentoSeguimientoDTO getUltimaIncidencia(String coModelo, String coVersion, String coDocumento);
}