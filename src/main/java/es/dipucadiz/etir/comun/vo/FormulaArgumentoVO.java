package es.dipucadiz.etir.comun.vo;

import java.io.Serializable;

import es.dipucadiz.etir.comun.dto.FormulaArgumentoDTO;

public class FormulaArgumentoVO extends FormulaArgumentoDTO implements Serializable{
	private static final long serialVersionUID = 8726515383963913372L;

	private String descripcionTipo;
	private String descripcionValor;
	private Boolean obligatorio;
	
	public Boolean getObligatorio() {
		return obligatorio;
	}

	
	public String getDescObligatorio() {
		if(this.getObligatorio()!= null && this.getObligatorio()){
			return "SI";
		}else{
			return "NO";
		}
	}
	
	
	public void setObligatorio(Boolean obligatorio) {
		this.obligatorio = obligatorio;
	}

	public String getDescripcionTipo() {
		return descripcionTipo;
	}

	public void setDescripcionTipo(String descripcionTipo) {
		this.descripcionTipo = descripcionTipo;
	}
	
	
	public void setPosInicio(String posInicio) {
		try{
			super.setPosInicio(Integer.valueOf(posInicio));
		}catch (Exception e) {
			super.setPosInicio(null);
		}
	}

	public void setPosFin(String posFin) {
		try{
			super.setPosFin(Integer.valueOf(posFin));
		}catch (Exception e) {
			super.setPosFin(null);
		}
	}

	public String getDescripcionValor() {
		
	
		return descripcionValor;
	}

	public void setDescripcionValor(String descripcionValor) {
		
			this.descripcionValor = descripcionValor;
		
		
	}
	
	
	
}



