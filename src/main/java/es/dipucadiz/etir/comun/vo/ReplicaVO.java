package es.dipucadiz.etir.comun.vo;

import java.io.Serializable;

public class ReplicaVO implements Serializable{
	private static final long serialVersionUID = 4996599916310604137L;
	private String coReplica;
	private String provincia;
	private String municipio;
	private String otr;
	private String ano;
	private String mes;
    private String estado;
    
    
	public String getCoReplica() {
		return coReplica;
	}
	public void setCoReplica(String coReplica) {
		this.coReplica = coReplica;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getOtr() {
		return otr;
	}
	public void setOtr(String otr) {
		this.otr = otr;
	}
	public String getAno() {
		return ano;
	}
	public void setAno(String ano) {
		this.ano = ano;
	}
	public String getMes() {
		return mes;
	}
	public void setMes(String mes) {
		this.mes = mes;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
}