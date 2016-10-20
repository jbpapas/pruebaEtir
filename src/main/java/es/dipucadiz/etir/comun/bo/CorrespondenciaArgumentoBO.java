package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.CorrespondenciaArgumentoDTO;
import es.dipucadiz.etir.comun.dto.CorrespondenciaArgumentoDTOId;
import es.dipucadiz.etir.comun.dto.CorrespondenciaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;


public interface CorrespondenciaArgumentoBO extends
        GenericBO<CorrespondenciaArgumentoDTO, CorrespondenciaArgumentoDTOId> {
	
	List<CorrespondenciaArgumentoDTO> findArgumentosCorrespondencia(final CorrespondenciaDTO corr)
	        throws GadirServiceException;
}
