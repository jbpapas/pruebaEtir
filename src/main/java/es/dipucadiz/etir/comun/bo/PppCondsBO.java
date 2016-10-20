package es.dipucadiz.etir.comun.bo;

import java.util.Date;

import es.dipucadiz.etir.comun.dto.PppCondsDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;


public interface PppCondsBO extends GenericBO<PppCondsDTO, Long> {

	boolean existePlazoFx(Short anyo, Short nuPlazos, Date fxPlazo) throws GadirServiceException;

}
