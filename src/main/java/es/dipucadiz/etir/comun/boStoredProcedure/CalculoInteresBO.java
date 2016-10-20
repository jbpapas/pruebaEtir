package es.dipucadiz.etir.comun.boStoredProcedure;

import java.util.Date;

import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.vo.CalculoInteresesVO;





public interface CalculoInteresBO {
	
 
	public CalculoInteresesVO execute(Date e_fx_desde, Date e_fx_hasta, Float e_im_pendiente, Float e_im_intereses, Integer e_dias, Integer e_legal) throws GadirServiceException;

}
