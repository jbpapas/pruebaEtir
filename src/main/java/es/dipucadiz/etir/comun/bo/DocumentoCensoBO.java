package es.dipucadiz.etir.comun.bo;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import es.dipucadiz.etir.comun.dto.DocumentoCensoDTO;
import es.dipucadiz.etir.comun.dto.DocumentoCensoDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface DocumentoCensoBO extends GenericBO<DocumentoCensoDTO, DocumentoCensoDTOId> {
	
	List<DocumentoCensoDTO> findDocumentosCensoByModeloAndVersion(final String coModelo, final String coVersion)
    	throws GadirServiceException;
	
	List<DocumentoCensoDTO> findDocumentosCensoByMunicipioConceptoModeloVersion(final String coMunicipio, final String coConcepto, final String coModelo, final String coVersion)
		throws GadirServiceException;

	List<DocumentoCensoDTO> findDocumentosCensoByMunicipioConceptoModeloVersionSinConcepto(final String coMunicipio, final String coConcepto, final String coModelo, final String coVersion)
    	throws GadirServiceException;
	
	public int countByCriterioBusqueda(DetachedCriteria criterio) throws GadirServiceException;
}
