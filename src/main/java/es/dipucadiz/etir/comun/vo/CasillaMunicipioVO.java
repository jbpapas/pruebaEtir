package es.dipucadiz.etir.comun.vo;

import java.io.Serializable;

import es.dipucadiz.etir.comun.dto.CasillaMunicipioDTO;

public class CasillaMunicipioVO extends CasillaMunicipioDTO implements Serializable {
	
	/**
     * Atributo que almacena el serialVersionUID de la clase.
     */
    private static final long serialVersionUID = -8805613133647903423L;
    
    private String nuCasillaDescripcion;
    private String modeloDescripcion;

	public String getNuCasillaDescripcion() {
		return nuCasillaDescripcion;
	}

	public void setNuCasillaDescripcion(String nuCasillaDescripcion) {
		this.nuCasillaDescripcion = nuCasillaDescripcion;
	}

	public String getModeloDescripcion() {
		return modeloDescripcion;
	}

	public void setModeloDescripcion(String modeloDescripcion) {
		this.modeloDescripcion = modeloDescripcion;
	}
   
    
   
}
	
    