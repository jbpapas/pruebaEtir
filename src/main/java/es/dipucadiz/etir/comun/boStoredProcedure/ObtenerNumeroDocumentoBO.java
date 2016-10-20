package es.dipucadiz.etir.comun.boStoredProcedure;

import es.dipucadiz.etir.comun.exception.GadirServiceException;




public interface ObtenerNumeroDocumentoBO {
	
	public String execute(String coModelo, String coVersion) throws GadirServiceException;

}
