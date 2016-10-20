package es.dipucadiz.etir.comun.vo;


public class CampoTipoValor {
	private String tipo;
	private String valor;
	private Short posicionIni;
	private Short posicionFin;
	private String modeloAdicional;
	private String versionAdicional;
	
	
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
	public Short getPosicionIni() {
		return posicionIni;
	}
	public void setPosicionIni(Short posicionIni) {
		if (posicionIni != null && posicionIni > 0) {
			this.posicionIni = posicionIni;
		}
	}
	public Short getPosicionFin() {
		return posicionFin;
	}
	public void setPosicionFin(Short posicionFin) {
		if (posicionFin != null && posicionFin > 0) {
			this.posicionFin = posicionFin;
		}
	}
	
	public void setModeloAdicional(String modeloAdicional) {
		this.modeloAdicional = modeloAdicional;
	}
	public String getModeloAdicional() {
		return modeloAdicional;
	}
	public void setVersionAdicional(String versionAdicional) {
		this.versionAdicional = versionAdicional;
	}
	public String getVersionAdicional() {
		return versionAdicional;
	}

}
