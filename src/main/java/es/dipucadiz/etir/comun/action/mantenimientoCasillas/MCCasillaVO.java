package es.dipucadiz.etir.comun.action.mantenimientoCasillas;

import java.io.Serializable;



public class MCCasillaVO implements Serializable {
	private static final long serialVersionUID = -6615651819771065819L;

	protected short nuCasilla;
	protected String valor;
	protected boolean error;
	
	public short getNuCasilla() {
		return nuCasilla;
	}
	public void setNuCasilla(short nuCasilla) {
		this.nuCasilla = nuCasilla;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public boolean getError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	
	
	
}
