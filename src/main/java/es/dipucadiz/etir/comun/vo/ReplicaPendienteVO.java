package es.dipucadiz.etir.comun.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class ReplicaPendienteVO implements Serializable{
	private static final long serialVersionUID = 4996599916310604137L;
	private String coReplicaPendiente;
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
	private String imPrincipal;
	private String imRecApremio;
	private String imIntereses;
	private String imCostas;
	private String tipo;
	
	public String getCoReplicaPendiente() {
		return coReplicaPendiente;
	}
	public void setCoReplicaPendiente(String coReplicaPendiente) {
		this.coReplicaPendiente = coReplicaPendiente;
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
	public String getImPrincipal() {
		return imPrincipal;
	}
	public void setImPrincipal(String imPrincipal) {
		this.imPrincipal = imPrincipal;
	}
	public String getImRecApremio() {
		return imRecApremio;
	}
	public void setImRecApremio(String imRecApremio) {
		this.imRecApremio = imRecApremio;
	}
	public String getImIntereses() {
		return imIntereses;
	}
	public void setImIntereses(String imIntereses) {
		this.imIntereses = imIntereses;
	}
	public String getImCostas() {
		return imCostas;
	}
	public void setImCostas(String imCostas) {
		this.imCostas = imCostas;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}