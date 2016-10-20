package es.dipucadiz.etir.comun.boStoredProcedure;

import java.util.Date;

import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.vo.CalculoInteresesVO;





public interface InformarNotificacionResultadoBO {
	
 
	public void execute(Long nuPeticion) throws GadirServiceException;

}
