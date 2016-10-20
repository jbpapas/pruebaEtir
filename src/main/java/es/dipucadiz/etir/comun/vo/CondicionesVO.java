package es.dipucadiz.etir.comun.vo;

import java.io.Serializable;

public class CondicionesVO  implements Serializable{
	private static final long serialVersionUID = -6030265325955336937L;

	private Integer rowid;
	private Long casillaOrigen;
	private String descripcionCasillaOrigen;
	private String operador;
	private String descripcionOperador;
	private String valor;
	private String conector;
	private String descripcionConector;
	private Long codCasilla;
	
	
	public Long getCasillaOrigen() {
		return casillaOrigen;
	}
	public void setCasillaOrigen(Long casillaOrigen) {
		this.casillaOrigen = casillaOrigen;
	}
	public String getDescripcionCasillaOrigen() {
		return descripcionCasillaOrigen;
	}
	public void setDescripcionCasillaOrigen(String descripcionCasillaOrigen) {
		this.descripcionCasillaOrigen = descripcionCasillaOrigen;
	}
	public String getOperador() {
		return operador;
	}
	public void setOperador(String operador) {
		this.operador = operador;
	}
	public String getDescripcionOperador() {
		return descripcionOperador;
	}
	public void setDescripcionOperador(String descripcionOperador) {
		this.descripcionOperador = descripcionOperador;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getConector() {
		return conector;
	}
	public void setConector(String conector) {
		this.conector = conector;
	}
	public Integer getRowid() {
		return rowid;
	}
	public void setRowid(Integer rowid) {
		this.rowid = rowid;
	}
	public String getDescripcionConector() {
		return descripcionConector;
	}
	public void setDescripcionConector(String descripcionConector) {
		this.descripcionConector = descripcionConector;
	}
	public Long getCodCasilla() {
		return codCasilla;
	}
	public void setCodCasilla(Long codCasilla) {
		this.codCasilla = codCasilla;
	}
	





}