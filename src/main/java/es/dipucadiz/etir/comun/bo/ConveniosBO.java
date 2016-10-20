package es.dipucadiz.etir.comun.bo;

import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.ConveniosDTO;
import es.dipucadiz.etir.comun.dto.ConveniosDTOId;

public interface ConveniosBO extends GenericBO<ConveniosDTO, ConveniosDTOId>{
	
	void setDao(DAOBase<ConveniosDTO, ConveniosDTOId> dao);
	DAOBase<ConveniosDTO, ConveniosDTOId> getDao();
}