package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.CircuitoRutaDTO;
import es.dipucadiz.etir.comun.dto.TramiteDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.sb06.vo.TramiteVO;

public interface TramiteBO extends GenericBO<TramiteDTO, Long> {

	List<TramiteVO> getTramitesByRuta(final CircuitoRutaDTO circuitoRutaDTO) throws GadirServiceException;
	List<TramiteVO> getTramitesByRutaYSinTipo(final CircuitoRutaDTO circuitoRutaDTO, final String tipo) throws GadirServiceException;
}
