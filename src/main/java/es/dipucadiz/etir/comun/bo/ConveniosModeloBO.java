package es.dipucadiz.etir.comun.bo;

import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.ConveniosModeloDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface ConveniosModeloBO extends GenericBO<ConveniosModeloDTO, Long>{
	
	void setDao(DAOBase<ConveniosModeloDTO, Long> dao);
	DAOBase<ConveniosModeloDTO, Long> getDao();
	public ConveniosModeloDTO findByIdLazy(final Long coConveniosModelo) throws GadirServiceException;
}