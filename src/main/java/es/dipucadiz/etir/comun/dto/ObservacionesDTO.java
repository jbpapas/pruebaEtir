package es.dipucadiz.etir.comun.dto;

import java.sql.Clob;
import java.util.Date;

public class ObservacionesDTO implements java.io.Serializable {
	private static final long serialVersionUID = -125653375053112690L;

	private Long coObservaciones;
    private ObservacionesGrupoDTO observacionesGrupoDTO;
    private String titulo;
    private Clob observaciones;
    private Date fhAlta;
    private String coUsuarioAlta;
    private BDDocumentalGrupoDTO bdDocumentalGrupoDTO;
    private Date fhActualizacion;
    private String coUsuarioActualizacion;
	private String rowid;

	public ObservacionesDTO() {}

	public Long getCoObservaciones() {
		return coObservaciones;
	}

	public void setCoObservaciones(Long coObservaciones) {
		this.coObservaciones = coObservaciones;
	}

	public ObservacionesGrupoDTO getObservacionesGrupoDTO() {
		return observacionesGrupoDTO;
	}

	public void setObservacionesGrupoDTO(ObservacionesGrupoDTO observacionesGrupoDTO) {
		this.observacionesGrupoDTO = observacionesGrupoDTO;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Clob getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(Clob observaciones) {
		this.observaciones = observaciones;
	}

	public Date getFhAlta() {
		return fhAlta;
	}

	public void setFhAlta(Date fhAlta) {
		this.fhAlta = fhAlta;
	}

	public String getCoUsuarioAlta() {
		return coUsuarioAlta;
	}

	public void setCoUsuarioAlta(String coUsuarioAlta) {
		this.coUsuarioAlta = coUsuarioAlta;
	}

	public BDDocumentalGrupoDTO getBdDocumentalGrupoDTO() {
		return bdDocumentalGrupoDTO;
	}

	public void setBdDocumentalGrupoDTO(BDDocumentalGrupoDTO bdDocumentalGrupoDTO) {
		this.bdDocumentalGrupoDTO = bdDocumentalGrupoDTO;
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
