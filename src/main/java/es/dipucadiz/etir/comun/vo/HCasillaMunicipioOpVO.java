package es.dipucadiz.etir.comun.vo;

import java.io.Serializable;

import es.dipucadiz.etir.comun.dto.HCasillaMunicipioOperaciDTO;

public class HCasillaMunicipioOpVO extends HCasillaMunicipioOperaciDTO implements Serializable {
	
	/**
     * Atributo que almacena el serialVersionUID de la clase.
     */
    private static final long serialVersionUID = -8805613133647903423L;
    
    private String funcionDescripcion;
    private String argumentoDescripcion;
    
    
	public String getFuncionDescripcion() {
		return funcionDescripcion;
	}

	public void setFuncionDescripcion(String funcionDescripcion) {
		this.funcionDescripcion = funcionDescripcion;
	}

	public String getArgumentoDescripcion() {
		return argumentoDescripcion;
	}

	public void setArgumentoDescripcion(String argumentoDescripcion) {
		this.argumentoDescripcion = argumentoDescripcion;
	}
    
    
    
}
	
	
