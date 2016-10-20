package es.dipucadiz.etir.comun.vo;

import java.util.List;


public class DatosScriptVO {
	
	private String ruta;
	private List<String> parametrosJava;
	
	public DatosScriptVO() {
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public String getRuta() {
		return ruta;
	}

	public void setParametrosJava(List<String> parametrosJava) {
		this.parametrosJava = parametrosJava;
	}

	public List<String> getParametrosJava() {
		return parametrosJava;
	}
	
}
