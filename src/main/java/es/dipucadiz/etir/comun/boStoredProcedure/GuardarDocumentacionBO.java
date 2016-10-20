package es.dipucadiz.etir.comun.boStoredProcedure;

import es.dipucadiz.etir.comun.exception.GadirServiceException;



public interface GuardarDocumentacionBO {

	public Long execute(String coModelo, String coVersion, String coDocumento, Long coBDDocumentalGrupo, String observaciones, String nombrePdfGadirNFS, Long coEjecucion, String coAcmUsuario) throws GadirServiceException;
	public Long execute(Long coBDDocumentalGrupo, String observaciones, String nombrePdfGadirNFS, Long coEjecucion, String coAcmUsuario) throws GadirServiceException;

}
