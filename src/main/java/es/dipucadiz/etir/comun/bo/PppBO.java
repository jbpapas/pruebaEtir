package es.dipucadiz.etir.comun.bo;

import es.dipucadiz.etir.comun.dto.PppDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;


public interface PppBO extends GenericBO<PppDTO, Long> {

	PppDTO findByAnoNuPppInitialized(Short anyo, Integer nuPpp) throws GadirServiceException;

}
