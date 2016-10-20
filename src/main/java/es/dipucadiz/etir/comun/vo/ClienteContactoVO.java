package es.dipucadiz.etir.comun.vo;


import java.util.Date;

import es.dipucadiz.etir.comun.dto.ClienteDTO;
import es.dipucadiz.etir.comun.utilidades.TablaGt;
import es.dipucadiz.etir.comun.utilidades.TablaGtConstants;


public class ClienteContactoVO implements java.io.Serializable {

	private static final long serialVersionUID = -7787795572188941169L;
	private Long coClienteContacto;
	private ClienteDTO clienteDTO;
	private String tipo;
	private String contacto;
	private boolean boPreferente;
	private String observaciones;
	private Date fhActualizacion;
	private String coUsuarioActualizacion;
	private String descripcionTipo;

	public ClienteContactoVO() {
	}

	public ClienteContactoVO(ClienteDTO clienteDTO, String tipo,
			String contacto, boolean boPreferente) {
		this.clienteDTO = clienteDTO;
		this.tipo = tipo;
		this.contacto = contacto;
		this.boPreferente = boPreferente;
	}

	public ClienteContactoVO(ClienteDTO clienteDTO, String tipo,
			String contacto, boolean boPreferente, String observaciones,
			Date fhActualizacion, String coUsuarioActualizacion) {
		this.clienteDTO = clienteDTO;
		this.tipo = tipo;
		this.contacto = contacto;
		this.boPreferente = boPreferente;
		this.observaciones = observaciones;
		this.fhActualizacion = fhActualizacion;
		this.coUsuarioActualizacion = coUsuarioActualizacion;
	}

	public Long getCoClienteContacto() {
		return this.coClienteContacto;
	}

	public void setCoClienteContacto(Long coClienteContacto) {
		this.coClienteContacto = coClienteContacto;
	}

	public ClienteDTO getClienteDTO() {
		return this.clienteDTO;
	}

	public void setClienteDTO(ClienteDTO clienteDTO) {
		this.clienteDTO = clienteDTO;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getContacto() {
		return this.contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public boolean isBoPreferente() {
		return this.boPreferente;
	}

	public void setBoPreferente(boolean boPreferente) {
		this.boPreferente = boPreferente;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Date getFhActualizacion() {
		return this.fhActualizacion;
	}

	public void setFhActualizacion(Date fhActualizacion) {
		this.fhActualizacion = fhActualizacion;
	}

	public String getCoUsuarioActualizacion() {
		return this.coUsuarioActualizacion;
	}

	public void setCoUsuarioActualizacion(String coUsuarioActualizacion) {
		this.coUsuarioActualizacion = coUsuarioActualizacion;
	}
	
	public String getDescripcionTipo(){
		if(descripcionTipo == null){
			descripcionTipo = TablaGt.getCodigoDescripcion(TablaGtConstants.TABLA_TIPOS_CONTACTOS, this.tipo).getValue();
			 
		}
		
		return descripcionTipo;		
	}

	public void setDescripcionTipo(String descripcionTipo) {
		this.descripcionTipo = descripcionTipo;
	}
	
}
