package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.IncidenciaDTO;
import es.dipucadiz.etir.comun.dto.IncidenciaSituacionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface IncidenciaSituacionBO extends GenericBO<IncidenciaSituacionDTO, Long>{

	public List<IncidenciaDTO> findByClaves(String coProvincia, String coMunicipio, String coModelo, String coVersion ) throws GadirServiceException;

}
