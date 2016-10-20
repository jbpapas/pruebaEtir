package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.FormulaDTO;
import es.dipucadiz.etir.comun.dto.FormulaPasoDTO;
import es.dipucadiz.etir.comun.dto.FormulaPasoDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.vo.FormulaPasoVO;

public interface FormulaPasoBO extends
GenericBO<FormulaPasoDTO, FormulaPasoDTOId> {
	
	
	FormulaPasoDTO findByIdFecthFuncion(FormulaPasoDTOId id) throws GadirServiceException;
	byte nuevoID(String coFormula) throws GadirServiceException;
	Integer nuevoOrden(String coFormula) throws GadirServiceException;
	List<FormulaPasoDTO> getFormulaPasoByFormula(String coFormula);
	void copiarPaso(FormulaDTO formulaOrigen, FormulaDTO formulaACopiar) throws GadirServiceException;
	List<FormulaPasoVO> obtenerListado(FormulaDTO formula);
	List<FormulaPasoDTO> obtenerPasosAnteriores(String coFormula, byte coPaso) throws GadirServiceException;
	FormulaPasoDTO findByRowIdLazy(String id);
}

