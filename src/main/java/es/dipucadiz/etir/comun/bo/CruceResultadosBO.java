package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.CruceDTO;
import es.dipucadiz.etir.comun.dto.CruceResultadoDTO;
import es.dipucadiz.etir.comun.dto.CruceResultadoDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;


public interface CruceResultadosBO extends GenericBO<CruceResultadoDTO, CruceResultadoDTOId> {
	
	 List<CruceResultadoDTO> obtenerListado(CruceDTO cruce)throws GadirServiceException;
	 public Byte obtenerUltimoID(CruceDTO argumento) throws GadirServiceException;
}