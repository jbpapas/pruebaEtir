package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.CasillaModeloDTOId;
import es.dipucadiz.etir.comun.dto.CasillasLigadasDTO;
import es.dipucadiz.etir.comun.dto.ModeloVersionDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface CasillasLigadasBO extends GenericBO<CasillasLigadasDTO, Long> {

	List<CasillasLigadasDTO> findCasillasLigadasACasilla(
	        final CasillaModeloDTOId idCasilla) throws GadirServiceException;
	
	
	List<CasillasLigadasDTO> findCasillasLigadasByModeloVersion(
	        final ModeloVersionDTOId idModeloVersion) throws GadirServiceException;

	
}
