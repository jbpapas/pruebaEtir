package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.ExtraccionCriterioDTO;
import es.dipucadiz.etir.comun.dto.ExtraccionCriterioDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface ExtraccionCriterioBO extends
        GenericBO<ExtraccionCriterioDTO, ExtraccionCriterioDTOId> {

	public List<ExtraccionCriterioDTO> findByTipoRegistro(String coExtraccion, String tipoRegistro) throws GadirServiceException;
	
	}
