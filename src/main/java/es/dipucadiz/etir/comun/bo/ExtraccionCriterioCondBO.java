package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.ExtraccionCriterioCondDTO;
import es.dipucadiz.etir.comun.dto.ExtraccionCriterioCondDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;


public interface ExtraccionCriterioCondBO extends GenericBO<ExtraccionCriterioCondDTO, ExtraccionCriterioCondDTOId> {

	public List<ExtraccionCriterioCondDTO> findByCriterio(String coExtraccion, String tipoRegistro, short coExtraccionCriterio) throws GadirServiceException;
	
	}
