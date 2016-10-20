package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.CruceResultadoCampoDTO;
import es.dipucadiz.etir.comun.dto.CruceResultadoCampoDTOId;
import es.dipucadiz.etir.comun.dto.CruceResultadoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;


public interface CruceResultadosCampoBO extends GenericBO<CruceResultadoCampoDTO, CruceResultadoCampoDTOId> {
	List<CruceResultadoCampoDTO> obtenerListado(CruceResultadoDTO resultado)throws GadirServiceException;
	Short obtenerUltimoID(CruceResultadoDTO cruceResultado) throws GadirServiceException;

}