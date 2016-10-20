package es.dipucadiz.etir.comun.bo;

import es.dipucadiz.etir.comun.dto.ConveniosConceptoDTO;
import es.dipucadiz.etir.comun.dto.ConveniosConceptoDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;


public interface ConveniosConceptoBO extends GenericBO<ConveniosConceptoDTO, ConveniosConceptoDTOId> {
	ConveniosConceptoDTO findByIdLazy(final ConveniosConceptoDTOId id) throws GadirServiceException;
}
