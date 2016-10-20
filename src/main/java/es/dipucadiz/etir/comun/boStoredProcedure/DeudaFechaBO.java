package es.dipucadiz.etir.comun.boStoredProcedure;

import java.util.Date;

import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.vo.DeudaVO;




public interface DeudaFechaBO {
	
	public DeudaVO execute(String coModelo, String coVersion, String coDocumento, Date fecha) throws GadirServiceException;

}
