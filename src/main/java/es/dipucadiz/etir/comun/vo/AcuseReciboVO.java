package es.dipucadiz.etir.comun.vo;

import java.io.Serializable;

import es.dipucadiz.etir.comun.dto.CruceDTO;


public class AcuseReciboVO extends CruceDTO implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	private String anio;
	private String codigoBarras;
	private String descripcion;
	private String dniContribuyente;
	private String empresaNotif;
	private String entidad;
	private String estado;
	private String fecha;
	private String nombreAcuseReciboPDF;
	private String municipio;
	private String nombreContribuyente;
	private String acuseReciboPDF;
	private String expediente;
	private String servicio;
	private String tipo;
	private String zona;
	private String uuidEspacioNotif;
	private String uuidFicheroPdf;
	
	
	public AcuseReciboVO() {		
	}
	
	public String getAnio() {
		return anio;
	}
	public void setAnio(String anio) {
		this.anio = anio;
	}
	public String getCodigoBarras() {
		return codigoBarras;
	}
	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getDniContribuyente() {
		return dniContribuyente;
	}
	public void setDniContribuyente(String dniContribuyente) {
		this.dniContribuyente = dniContribuyente;
	}
	public String getEmpresaNotif() {
		return empresaNotif;
	}
	public void setEmpresaNotif(String empresaNotif) {
		this.empresaNotif = empresaNotif;
	}
	public String getEntidad() {
		return entidad;
	}
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getNombreAcuseReciboPDF() {
		return nombreAcuseReciboPDF;
	}
	public void setNombreAcuseReciboPDF(String nombreAcuseReciboPDF) {
		this.nombreAcuseReciboPDF = nombreAcuseReciboPDF;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getNombreContribuyente() {
		return nombreContribuyente;
	}
	public void setNombreContribuyente(String nombreContribuyente) {
		this.nombreContribuyente = nombreContribuyente;
	}
	public String getAcuseReciboPDF() {
		return acuseReciboPDF;
	}
	public void setAcuseReciboPDF(String acuseReciboPDF) {
		this.acuseReciboPDF = acuseReciboPDF;
	}
	public String getExpediente() {
		return expediente;
	}
	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}
	public String getServicio() {
		return servicio;
	}
	public void setServicio(String servicio) {
		this.servicio = servicio;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getZona() {
		return zona;
	}
	public void setZona(String zona) {
		this.zona = zona;
	}
	public String getUuidEspacioNotif() {
		return uuidEspacioNotif;
	}
	public void setUuidEspacioNotif(String uuidEspacioNotif) {
		this.uuidEspacioNotif = uuidEspacioNotif;
	}
	public String getUuidFicheroPdf() {
		return uuidFicheroPdf;
	}
	public void setUuidFicheroPdf(String uuidFicheroPdf) {
		this.uuidFicheroPdf = uuidFicheroPdf;
	}
	
	
}
