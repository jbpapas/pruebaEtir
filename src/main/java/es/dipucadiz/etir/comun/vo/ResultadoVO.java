package es.dipucadiz.etir.comun.vo;

import java.io.Serializable;

import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class ResultadoVO  implements Serializable{
	private static final long serialVersionUID = 4210501945215734835L;

	private Integer rowid;
	private Long casillaModificar;
	private String descripcionCasillaModificar;
	private String descripcionTipo;
	private String tipo;
	private String valor;
	private String descripcionValor;
	private String descripcionValorAdicional;
	private String valorAdicional;
	
	private Short posInicio;
	private Short posFin;
	
	public Integer getRowid() {
		return rowid;
	}
	public void setRowid(Integer rowid) {
		this.rowid = rowid;
	}
	public Long getCasillaModificar() {
		return casillaModificar;
	}
	public void setCasillaModificar(Long casillaModificar) {
		this.casillaModificar = casillaModificar;
	}
	public String getDescripcionCasillaModificar() {
		return descripcionCasillaModificar;
	}
	public void setDescripcionCasillaModificar(String descripcionCasillaModificar) {
		this.descripcionCasillaModificar = descripcionCasillaModificar;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getValorAdicional() {
		return valorAdicional;
	}
	public void setValorAdicional(String valorAdicional) {
		this.valorAdicional = valorAdicional;
	}
	
	
	public String getDescripcionTipo() {
		return descripcionTipo;
	}
	public void setDescripcionTipo(String descripcionTipo) {
		this.descripcionTipo = descripcionTipo;
	}
	public Short getPosInicio() {
		return posInicio;
	}
	
	
	public void setPosInicio(String posInicio){
		if(Utilidades.isNumeric(posInicio)){
			this.posInicio=Short.valueOf(posInicio);
		}
	}
	
	public void setPosFin(String posFin){
		if(Utilidades.isNumeric(posFin)){
			this.posFin=Short.valueOf(posFin);
		}
	}
	public Short getPosFin() {
		return posFin;
	}
//	public void setPosInicio(Short posInicio) {
//		this.posInicio = posInicio;
//	}
//	public void setPosFin(Short posFin) {
//		this.posFin = posFin;
//	}
	public String getDescripcionValor() {
		return descripcionValor;
	}
	public void setDescripcionValor(String descripcionValor) {
		this.descripcionValor = descripcionValor;
	}
	public String getDescripcionValorAdicional() {
		return descripcionValorAdicional;
	}
	public void setDescripcionValorAdicional(String descripcionValorAdicional) {
		this.descripcionValorAdicional = descripcionValorAdicional;
	}
	
	
	





}