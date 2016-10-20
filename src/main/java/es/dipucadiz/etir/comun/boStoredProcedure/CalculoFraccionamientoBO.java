package es.dipucadiz.etir.comun.boStoredProcedure;

import java.math.BigDecimal;

import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.sb07.G7D1Fraccionamiento.action.G7D1CalculoVO;





public interface CalculoFraccionamientoBO {
	
	public G7D1CalculoVO execute(String coFraccionamiento, BigDecimal importeDelPlazo) throws GadirServiceException;

}
