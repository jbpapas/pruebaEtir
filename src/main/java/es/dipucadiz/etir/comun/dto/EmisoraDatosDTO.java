package es.dipucadiz.etir.comun.dto;

// Generated 09-oct-2015 13:34:00 by prodriguez 

import java.math.BigDecimal;
import java.util.Date;

/**
 *  prodriguez 
 */
public class EmisoraDatosDTO implements java.io.Serializable {
	private static final long serialVersionUID = -3180317549647412965L;

	private EmisoraDatosDTOId id;
	private String ficheroDestino;
	private Integer numCobros;
	private BigDecimal imCobros;
	private Date fhActualizacion;
	private String coUsuarioActualizacion;
	private String rowid;
		
	public EmisoraDatosDTO() {
	}
	
	public EmisoraDatosDTO(EmisoraDatosDTOId id) {
		this.id = id;
	}
	
	public EmisoraDatosDTO (EmisoraDatosDTOId id, String ficheroDestino, Integer numCobros, BigDecimal imCobros, TablaGtDTO tablaGtDTO){
		this.id = id;
		this.ficheroDestino = ficheroDestino;
		this.numCobros = numCobros;
		this.imCobros = imCobros;
	}

	public EmisoraDatosDTOId getId() {
		return id;
	}

	public void setId(EmisoraDatosDTOId id) {
		this.id = id;
	}

	public String getFicheroDestino() {
		return ficheroDestino;
	}

	public void setFicheroDestino(String ficheroDestino) {
		this.ficheroDestino = ficheroDestino;
	}

	public Integer getNumCobros() {
		return numCobros;
	}

	public void setNumCobros(Integer numCobros) {
		this.numCobros = numCobros;
	}

	public BigDecimal getImCobros() {
		return imCobros;
	}

	public void setImCobros(BigDecimal imCobros) {
		this.imCobros = imCobros;
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

	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}

}
