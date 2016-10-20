package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.ConceptoDTO;
import es.dipucadiz.etir.comun.dto.ConceptoModeloDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface ConceptoModeloBO extends GenericBO<ConceptoModeloDTO, Long>{
	List<ConceptoModeloDTO> findConceptoModelos(List<ConceptoDTO> listaConceptos, String codModelo) throws GadirServiceException;

	boolean isMultiConcepto(String coModelo) throws GadirServiceException;

}
