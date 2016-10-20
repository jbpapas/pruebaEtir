package es.dipucadiz.etir.comun.bo;

import es.dipucadiz.etir.comun.dto.FraccionamientoPlazoDTO;
import es.dipucadiz.etir.comun.dto.FraccionamientoPlazoDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;


public interface FraccionamientoPlazoBO extends GenericBO<FraccionamientoPlazoDTO, FraccionamientoPlazoDTOId> {

	boolean existePlazo(Long coFraccionamiento, short anyo, byte mes) throws GadirServiceException;

}
