package es.dipucadiz.etir.comun.bo;

import java.math.BigDecimal;

import es.dipucadiz.etir.comun.dto.MovimientoContableDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;


public interface MovimientoContableBO extends GenericBO<MovimientoContableDTO, Long> {

	BigDecimal findImporteRestante(MovimientoContableDTO movimientoContableDTO) throws GadirServiceException;

}
