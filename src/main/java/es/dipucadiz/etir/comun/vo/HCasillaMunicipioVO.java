package es.dipucadiz.etir.comun.vo;

import java.io.Serializable;

import es.dipucadiz.etir.comun.dto.HCasillaMunicipioDTO;

public class HCasillaMunicipioVO extends HCasillaMunicipioDTO implements Serializable {
	
	/**
     * Atributo que almacena el serialVersionUID de la clase.
     */
    private static final long serialVersionUID = -8805613133647903423L;
	
	
    
    private String descripcionCasilla;



	public String getDescripcionCasilla() {
		return descripcionCasilla;
	}



	public void setDescripcionCasilla(String descripcionCasilla) {
		this.descripcionCasilla = descripcionCasilla;
	}
    
    public String getMantenible(){
    	if(this.getBoMantenible()){
    		return "SI";
    	}else{
    		return "NO";
    	}
    }
    
    
    
    
    
    
}