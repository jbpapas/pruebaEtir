package es.dipucadiz.etir.comun.vo;

import java.io.Serializable;

import es.dipucadiz.etir.comun.dto.HDomicilioNotificacionDTO;

public class HConceptoDomicilioNotificacionVO implements Serializable {

	/**
     * Atributo que almacena el serialVersionUID de la clase.
     */
    private static final long serialVersionUID = -8805613133647903424L;
    
    private HDomicilioNotificacionDTO domicilio;
    
    private String provincia;
    
    private String municipio;
    
    private String cp;
    
    private String sigla;
    
    private String calle;
    
    private String numero;
    
    private String km;

    private String letra;
    
    private String bloque;
    
    private String escalera;
    
    private String planta;
    
    private String puerta;
    
    private String ubicacion;
    
    private Boolean boAEAT;
    
    private Boolean boDomTributario;
    
	public HDomicilioNotificacionDTO getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(HDomicilioNotificacionDTO domicilio) {
		this.domicilio = domicilio;
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

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getKm() {
		return km;
	}

	public void setKm(String km) {
		this.km = km;
	}

	public String getLetra() {
		return letra;
	}

	public void setLetra(String letra) {
		this.letra = letra;
	}

	public String getBloque() {
		return bloque;
	}

	public void setBloque(String bloque) {
		this.bloque = bloque;
	}

	public String getEscalera() {
		return escalera;
	}

	public void setEscalera(String escalera) {
		this.escalera = escalera;
	}

	public String getPlanta() {
		return planta;
	}

	public void setPlanta(String planta) {
		this.planta = planta;
	}

	public String getPuerta() {
		return puerta;
	}

	public void setPuerta(String puerta) {
		this.puerta = puerta;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public Boolean getBoAEAT() {
		return boAEAT;
	}

	public void setBoAEAT(Boolean boAEAT) {
		this.boAEAT = boAEAT;
	}

	public Boolean getBoDomTributario() {
		return boDomTributario;
	}

	public void setBoDomTributario(Boolean boDomTributario) {
		this.boDomTributario = boDomTributario;
	}

}
