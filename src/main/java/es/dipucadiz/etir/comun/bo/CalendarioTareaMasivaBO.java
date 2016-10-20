package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.CalendarioTareaMasivaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface CalendarioTareaMasivaBO extends GenericBO<CalendarioTareaMasivaDTO, Long>{
	List<CalendarioTareaMasivaDTO> findByAnoMes(int ano, int mes) throws GadirServiceException;
	List<CalendarioTareaMasivaDTO> findByAno(int ano) throws GadirServiceException;
}