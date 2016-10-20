package es.dipucadiz.etir.comun.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class BDDocumentalGrupoDTO implements java.io.Serializable {

	private static final long serialVersionUID = -1136087288570664041L;
	private Long coBDDocumentalGrupo;
    private Date fhActualizacion;
    private String coUsuarioActualizacion;
    private Set<ExpedienteSeguimientoDTO> expedienteSeguimientoDTOs = new HashSet<ExpedienteSeguimientoDTO>(0);
    private Set<CostasDTO> costasDTOs = new HashSet<CostasDTO>(0);
	private String rowid;

	public BDDocumentalGrupoDTO() {}

	public BDDocumentalGrupoDTO(Long coBDDocumentalGrupo) {
		this.coBDDocumentalGrupo = coBDDocumentalGrupo;
	}

	public Long getCoBDDocumentalGrupo() {
		return coBDDocumentalGrupo;
	}

	public void setCoBDDocumentalGrupo(Long coBDDocumentalGrupo) {
		this.coBDDocumentalGrupo = coBDDocumentalGrupo;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Set<ExpedienteSeguimientoDTO> getExpedienteSeguimientoDTOs() {
		return expedienteSeguimientoDTOs;
	}

	public void setExpedienteSeguimientoDTOs(
			Set<ExpedienteSeguimientoDTO> expedienteSeguimientoDTOs) {
		this.expedienteSeguimientoDTOs = expedienteSeguimientoDTOs;
	}

	public void setCostasDTOs(Set<CostasDTO> costasDTOs) {
		this.costasDTOs = costasDTOs;
	}

	public Set<CostasDTO> getCostasDTOs() {
		return costasDTOs;
	}


}
