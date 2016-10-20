package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.CruceCondicionDTO;
import es.dipucadiz.etir.comun.dto.CruceCondicionDTOId;
import es.dipucadiz.etir.comun.dto.CruceDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface CruceCondicionBO extends GenericBO<CruceCondicionDTO, CruceCondicionDTOId> {
	List<CruceCondicionDTO> obtenerListado(CruceDTO cruce)throws GadirServiceException;
	Short obtenerUltimoID(CruceDTO cruce)throws GadirServiceException;
	CruceDTO obtenerArgumento(String rowidCondicion)throws GadirServiceException;
	Short obtenerPrimerID(CruceDTO cruce)throws GadirServiceException;
}