package es.dipucadiz.etir.comun.bo;

import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.DomiciliacionBonifDTO;

public interface DomiciliacionBonifBO extends GenericBO<DomiciliacionBonifDTO, Long>{
	
	void setDao(DAOBase<DomiciliacionBonifDTO, Long> dao);
	DAOBase<DomiciliacionBonifDTO, Long> getDao();
}