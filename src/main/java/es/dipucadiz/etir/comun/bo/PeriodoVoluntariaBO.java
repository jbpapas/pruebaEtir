package es.dipucadiz.etir.comun.bo;

import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.PeriodoVoluntariaDTO;

public interface PeriodoVoluntariaBO extends GenericBO<PeriodoVoluntariaDTO, Long>{
	
	void setDao(DAOBase<PeriodoVoluntariaDTO, Long> dao);
	DAOBase<PeriodoVoluntariaDTO, Long> getDao();
}