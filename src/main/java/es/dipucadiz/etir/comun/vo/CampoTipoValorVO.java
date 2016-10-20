package es.dipucadiz.etir.comun.vo;

public class CampoTipoValorVO {

	private String coModelo;
	private String coVersion;
	private String tipo;
	private String valor;
	private String valorAdicional;
	private String modeloAdicional;
	private String versionAdicional;
	
	
	
	public CampoTipoValorVO() {
	}

	

	public CampoTipoValorVO(String tipo, String valor, String valorAdicional) {
		super();
		this.tipo = tipo;
		this.valor = valor;
		this.valorAdicional = valorAdicional;
	}

	
	public CampoTipoValorVO(String tipo, String valor, String valorAdicional, String coModelo, String coVersion) {
		super();
		this.tipo = tipo;
		this.valor = valor;
		this.valorAdicional = valorAdicional;
		this.coModelo = coModelo;
		this.coVersion = coVersion;
	}

	public CampoTipoValorVO(String tipo, String valor, String valorAdicional, String coModelo, String coVersion, String modeloAdicional, String versionAdicional) {
		super();
		this.tipo = tipo;
		this.valor = valor;
		this.valorAdicional = valorAdicional;
		this.coModelo = coModelo;
		this.coVersion = coVersion;
		this.setModeloAdicional(modeloAdicional);
		this.setVersionAdicional(versionAdicional);
	
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



	public void setCoVersion(String coVersion) {
		this.coVersion = coVersion;
	}



	public String getCoVersion() {
		return coVersion;
	}



	public void setCoModelo(String coModelo) {
		this.coModelo = coModelo;
	}



	public String getCoModelo() {
		return coModelo;
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
