package es.dipucadiz.etir.comun.bo;

import es.dipucadiz.etir.comun.dto.DocumentoCensoSeguimientoDTO;

/**
 * Interface responsable de definir la lógica de negocio asociada a la clase del
 * documentocensoseguimiento {@link DocumentoCensoSeguimientoDTO}.
 * 
 */
public interface DocumentoCensoSeguimientoBO extends GenericBO<DocumentoCensoSeguimientoDTO, Long> {
	DocumentoCensoSeguimientoDTO getUltimaIncidencia(String coModelo, String coVersion, String coDocumento);
}
