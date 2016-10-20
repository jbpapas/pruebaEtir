package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.ConceptoDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface ConceptoBO extends GenericBO<ConceptoDTO, String>{
	
	
	/*public List<ConceptoDTO> findConceptosDistintosByMunicipio(MunicipioDTO municipioDTO)
	throws GadirServiceException;
	
	public List<ConceptoDTO> findConceptosDistintosByMunicipioEjercicio(MunicipioDTO municipioDTO, short ejercicio)
	throws GadirServiceException;*/
	
	public List<ConceptoDTO> findConceptosByTasa(String coTasa) throws GadirServiceException;
	
	public List<ConceptoDTO> findConceptoByModelo(String coModelo) throws GadirServiceException;
}
