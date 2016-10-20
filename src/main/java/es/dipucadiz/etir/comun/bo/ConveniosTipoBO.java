package es.dipucadiz.etir.comun.bo;

import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.ConveniosTipoDTO;
import es.dipucadiz.etir.comun.dto.ConveniosTipoDTOId;

public interface ConveniosTipoBO extends GenericBO<ConveniosTipoDTO, ConveniosTipoDTOId>{
	
	void setDao(DAOBase<ConveniosTipoDTO, ConveniosTipoDTOId> dao);
	DAOBase<ConveniosTipoDTO, ConveniosTipoDTOId> getDao();
}