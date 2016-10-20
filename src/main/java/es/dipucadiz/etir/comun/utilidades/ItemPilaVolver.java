package es.dipucadiz.etir.comun.utilidades;

import java.io.Serializable;
import java.util.Map;

public class ItemPilaVolver implements Serializable {
	private static final long serialVersionUID = 6370130473505158091L;
	
	String actionVolvible;
	//String actionVolvedor;
	Map<String,Object> parametros;
	
	
	
	public ItemPilaVolver() {
		super();
	
	}



	public ItemPilaVolver(String actionVolvible, Map<String, Object> parametros) {
		super();
		this.actionVolvible = actionVolvible;
		this.parametros = parametros;
	}




	public String getActionVolvible() {
		return actionVolvible;
	}



	public void setActionVolvible(String actionVolvible) {
		this.actionVolvible = actionVolvible;
	}



	public Map<String, Object> getParametros() {
		return parametros;
	}



	public void setParametros(Map<String, Object> parametros) {
		this.parametros = parametros;
	}
	
	
	
}
