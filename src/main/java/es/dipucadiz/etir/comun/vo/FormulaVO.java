package es.dipucadiz.etir.comun.vo;

import java.io.Serializable;

import es.dipucadiz.etir.comun.dto.FormulaDTO;

public class FormulaVO extends FormulaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	private String correcta;


	public String getCorrecta() {
		if(this.getBoCorrecto()){
			return "SI";
		}else{
			return "NO";
		}
	}


	public void setCorrecta(String correcta) {
		this.correcta = correcta;
	}
	
	
	
}
