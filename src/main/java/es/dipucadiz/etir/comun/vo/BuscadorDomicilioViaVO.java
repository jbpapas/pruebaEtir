package es.dipucadiz.etir.comun.vo;

import java.io.Serializable;

public class BuscadorDomicilioViaVO  implements Serializable{
	
	private static final long serialVersionUID = 4503239561265040868L;
	private String sigla;
	private String nombreVia;
	private String ubicacion;
	private Integer cp;
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getNombreVia() {
		return nombreVia;
	}
	public void setNombreVia(String nombreVia) {
		this.nombreVia = nombreVia;
	}
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	public Integer getCp() {
		return cp;
	}
	public void setCp(Integer cp) {
		this.cp = cp;
	}
	

}