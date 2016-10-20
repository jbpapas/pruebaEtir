package es.dipucadiz.etir.comun.dto;

import java.util.Date;

public class ObservacionesGrupoDTO implements java.io.Serializable {
	private static final long serialVersionUID = 1055543629467212510L;

	private Long coObservacionesGrupo;
    private Date fhActualizacion;
    private String coUsuarioActualizacion;
	private String rowid;

	public ObservacionesGrupoDTO() {}

	public ObservacionesGrupoDTO(Long coObservacionesGrupo) {
		this.coObservacionesGrupo = coObservacionesGrupo;
	}

	public Long getCoObservacionesGrupo() {
		return coObservacionesGrupo;
	}

	public void setCoObservacionesGrupo(Long coObservacionesGrupo) {
		this.coObservacionesGrupo = coObservacionesGrupo;
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
