package es.dipucadiz.etir.comun.dto;

import java.util.Date;

public class PortafirmasFirmanteDTO {
	
	private Long coPortafirmasFirmante;
	private String coCircuito;
	private Date fxDesde;
	private Date fxHasta;
	private String nif;
	private String nombre;
	private String tipoFirmante;
	private Date fhActualizacion;
	private String coUsuarioActualizacion;
	private String rowid;
	
	public PortafirmasFirmanteDTO() {
	}
	
	public PortafirmasFirmanteDTO(Long coPortafirmasFirmante) {
		this.coPortafirmasFirmante = coPortafirmasFirmante;
	}
	
	// GETTERS AND SETTERS
	
	public Long getCoPortafirmasFirmante() {
		return coPortafirmasFirmante;
	}
	
	public void setCoPortafirmasFirmante(Long coPortafirmasFirmante) {
		this.coPortafirmasFirmante = coPortafirmasFirmante;
	}
	
	public String getCoCircuito() {
		return coCircuito;
	}

	public void setCoCircuito(String coCircuito) {
		this.coCircuito = coCircuito;
	}

	public Date getFxDesde() {
		return fxDesde;
	}
	
	public void setFxDesde(Date fxDesde) {
		this.fxDesde = fxDesde;
	}
	
	public Date getFxHasta() {
		return fxHasta;
	}
	
	public void setFxHasta(Date fxHasta) {
		this.fxHasta = fxHasta;
	}
	
	public String getNif() {
		return nif;
	}
	
	public void setNif(String nif) {
		this.nif = nif;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getTipoFirmante() {
		return tipoFirmante;
	}
	
	public void setTipoFirmante(String tipoFirmante) {
		this.tipoFirmante = tipoFirmante;
	}
	
	public Date getFhActualizacion() {
		return fhActualizacion;
	}
	
	public void setFhActualizacion(Date fhActualizacion) {
		this.fhActualizacion = fhActualizacion;
	}
	
	public String getCoUsuarioActualizacion() {
		return coUsuarioActualizacion;
	}
	
	public void setCoUsuarioActualizacion(String coUsuarioActualizacion) {
		this.coUsuarioActualizacion = coUsuarioActualizacion;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}

	public String getRowid() {
		return rowid;
	}
}
