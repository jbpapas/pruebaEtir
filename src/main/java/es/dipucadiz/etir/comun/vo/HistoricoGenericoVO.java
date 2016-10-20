package es.dipucadiz.etir.comun.vo;

import java.util.List;

public class HistoricoGenericoVO {
	private String usuario;
	private String fecha;
	private String hora;
	private String movimiento;
	private List<String> columnas;
	private String ejecucion;
	private List<Integer> columnasAlinearDerecha;
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public List<String> getColumnas() {
		return columnas;
	}
	public void setColumnas(List<String> columnas) {
		this.columnas = columnas;
	}
	public void setMovimiento(String movimiento) {
		this.movimiento = movimiento;
	}
	public String getMovimiento() {
		return movimiento;
	}
	public String getEjecucion() {
		return ejecucion;
	}
	public void setEjecucion(String ejecucion) {
		this.ejecucion = ejecucion;
	}
	public List<Integer> getColumnasAlinearDerecha() {
		return columnasAlinearDerecha;
	}
	public void setColumnasAlinearDerecha(List<Integer> columnasAlinearDerecha) {
		this.columnasAlinearDerecha = columnasAlinearDerecha;
	}

	
}
