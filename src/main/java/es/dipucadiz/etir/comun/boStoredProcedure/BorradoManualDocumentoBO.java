package es.dipucadiz.etir.comun.boStoredProcedure;

import es.dipucadiz.etir.comun.exception.GadirServiceException;




public interface BorradoManualDocumentoBO {
	
	public int execute(String coModelo, String coVersion, String coDocumento, String tipoBorrado, String coProcesoActual) throws GadirServiceException;

}
