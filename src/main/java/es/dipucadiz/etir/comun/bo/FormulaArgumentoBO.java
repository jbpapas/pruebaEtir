package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.FormulaArgumentoDTO;
import es.dipucadiz.etir.comun.dto.FormulaArgumentoDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface FormulaArgumentoBO extends
GenericBO<FormulaArgumentoDTO, FormulaArgumentoDTOId> {
	List<FormulaArgumentoDTO> obtenerArgumentosFromPaso(String codFormula, Byte codPaso) throws GadirServiceException;
	List<FormulaArgumentoDTO> getFormulaArgumentoByFormula(String coFormula, Byte coPaso);
}
