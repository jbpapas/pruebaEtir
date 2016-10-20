package es.dipucadiz.etir.comun.bo;

import java.util.Date;
import java.util.List;

import es.dipucadiz.etir.comun.dto.DiaFestivoDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface DiaFestivoBO extends GenericBO<DiaFestivoDTO, Long>{

	List<DiaFestivoDTO> findByMunicipioAno(MunicipioDTO municipioDTO, Short ano) throws GadirServiceException;
	List<DiaFestivoDTO> findByMunicipioAnoMes(MunicipioDTO municipioDTO, int ano, int mes) throws GadirServiceException;
	List<DiaFestivoDTO> findByMunicipioDiaTipo(MunicipioDTO municipioDTO, Date dia, String tipoDiaFestivo) throws GadirServiceException;

	void duplicarDiaFestivos(MunicipioDTO municipioDTO, Short ano, Short anoDestino) throws GadirServiceException;
}
