package es.dipucadiz.etir.comun.vo;

import java.io.Serializable;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class OpcionIncidenciaVO implements Serializable {
	private static final long serialVersionUID = 6668586833770048123L;

	String coDocnotif;
	String coIncidencia;
	String coEstadoSituacion;
	String nombre;
	String plantilla;
	
	public String getCoDocnotif() {
		return coDocnotif;
	}
	public void setCoDocnotif(String coDocnotif) {
		this.coDocnotif = coDocnotif;
	}
	public String getCoIncidencia() {
		return coIncidencia;
	}
	public void setCoIncidencia(String coIncidencia) {
		this.coIncidencia = coIncidencia;
	}
	public String getCoEstadoSituacion() {
		return coEstadoSituacion;
	}
	public void setCoEstadoSituacion(String coEstadoSituacion) {
		this.coEstadoSituacion = coEstadoSituacion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPlantilla() {
		return plantilla;
	}
	public void setPlantilla(String plantilla) {
		this.plantilla = plantilla;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public boolean equals(Object object) {
		boolean result = false;
		if (object instanceof OpcionIncidenciaVO) {
			OpcionIncidenciaVO otro = (OpcionIncidenciaVO) object;
			String oThis = coIncidencia + coEstadoSituacion;
			String oOtro = otro.getCoIncidencia() + otro.coEstadoSituacion;
			result = oThis == null ? oOtro == null : oThis.equals(oOtro);
		}
		return result;
	}
	
	@Override
	public int hashCode() {
		String oThis = coIncidencia + coEstadoSituacion;
		return new HashCodeBuilder(17, 31).append(oThis).toHashCode();
	}
	
}
