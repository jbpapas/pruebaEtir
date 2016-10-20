package es.dipucadiz.etir.comun.vo;

import java.io.Serializable;

public class ReplicaExcluidoVO implements Serializable{
	private static final long serialVersionUID = 4996599916310604137L;
	private String coReplicaExcluido;
	private String coModelo;
	private String coVersion;
	private String coDocumento;
	private String documento;
	private String observaciones;
	private String contribuyente;
	private String ejercicio;
	private String periodo;
	private String impPendCobro;
	private String refObjTrib;
	private String casilla306;
	
	public String getCoReplicaExcluido() {
		return coReplicaExcluido;
	}
	public void setCoReplicaExcluido(String coReplicaExcluido) {
		this.coReplicaExcluido = coReplicaExcluido;
	}
	public String getCoModelo() {
		return coModelo;
	}
	public void setCoModelo(String coModelo) {
		this.coModelo = coModelo;
	}
	public String getCoVersion() {
		return coVersion;
	}
	public void setCoVersion(String coVersion) {
		this.coVersion = coVersion;
	}
	public String getCoDocumento() {
		return coDocumento;
	}
	
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String coModelo, String coVersion, String coDocumento) {
		this.documento = coModelo + coVersion + coDocumento;
	}
	public void setCoDocumento(String coDocumento) {
		this.coDocumento = coDocumento;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getContribuyente() {
		return contribuyente;
	}
	public void setContribuyente(String contribuyente) {
		this.contribuyente = contribuyente;
	}
	public String getEjercicio() {
		return ejercicio;
	}
	public void setEjercicio(String ejercicio) {
		this.ejercicio = ejercicio;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	public String getImpPendCobro() {
		return impPendCobro;
	}
	public void setImpPendCobro(String impPendCobro) {
		this.impPendCobro = impPendCobro;
	}
	public String getRefObjTrib() {
		return refObjTrib;
	}
	public void setRefObjTrib(String refObjTrib) {
		this.refObjTrib = refObjTrib;
	}
	public String getCasilla306() {
		return casilla306;
	}
	public void setCasilla306(String casilla306) {
		this.casilla306 = casilla306;
	}
	
}