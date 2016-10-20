package es.dipucadiz.etir.comun.boStoredProcedure;

import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.vo.DeudaVO;




public interface DeudaBO {
	
	public DeudaVO execute(String coModelo, String coVersion, String coDocumento) throws GadirServiceException;

}
