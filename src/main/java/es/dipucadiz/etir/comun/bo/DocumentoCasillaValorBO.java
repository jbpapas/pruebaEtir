package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.DocumentoCasillaValorDTO;
import es.dipucadiz.etir.comun.dto.DocumentoCasillaValorDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;


public interface DocumentoCasillaValorBO extends GenericBO<DocumentoCasillaValorDTO, DocumentoCasillaValorDTOId> {
	
	List<DocumentoCasillaValorDTO> findDocumentosCasillaByDocumento(String coDocumento, String coModelo, String coVersion) throws GadirServiceException;

	List<DocumentoCasillaValorDTO> findDocumentosCasillaByDocumentoAndNuCasilla(String coDocumento, String coModelo, String coVersion, String nuCasilla) throws GadirServiceException;
	
	void actualizarCasilla(DocumentoCasillaValorDTO casilla) throws GadirServiceException;
	
}
