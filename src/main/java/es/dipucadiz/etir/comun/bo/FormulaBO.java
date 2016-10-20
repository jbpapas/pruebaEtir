package es.dipucadiz.etir.comun.bo;

import es.dipucadiz.etir.comun.dto.FormulaDTO;

public interface FormulaBO extends
GenericBO<FormulaDTO, String> {
		
	FormulaDTO findByIdFormula(final String coFormula);
	
	FormulaDTO findbyNombre(final String nombre);
}

