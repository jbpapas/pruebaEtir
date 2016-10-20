package es.dipucadiz.etir.comun.dto;


import java.util.Date;

import es.dipucadiz.etir.comun.utilidades.Utilidades;


public class ReplicaDTO implements java.io.Serializable {

	private Long coReplica;
	private MunicipioDTO municipioDTO;
	private OtrDTO otrDTO;
	private Date fxDesde;
	private Date fxHasta;
	private String estado;
	private String coUsuarioActualizacion;
	private Date fhActualizacion;
	private EjecucionDTO ejecucionDTO;
//	private Set<ReplicaDocumentoDTO> replicaDocumentoDTOs = new HashSet<ReplicaDocumentoDTO>(
//			0);
	private String rowid;


	public ReplicaDTO() {
	}

	public ReplicaDTO(Long coReplica) {
		this.coReplica = coReplica;
	}

	public ReplicaDTO(Long coReplica, MunicipioDTO municipioDTO, OtrDTO otrDTO, Date fxDesde, Date fxHasta, 
			String estado, String coUsuarioActualizacion, Date fhActualizacion,
			EjecucionDTO ejecucionDTO) {
		this.coReplica = coReplica;
		this.municipioDTO = municipioDTO;
		this.otrDTO = otrDTO;
		this.fxDesde = fxDesde;
		this.fxHasta = fxHasta;
		this.estado = estado;
		this.coUsuarioActualizacion = coUsuarioActualizacion;
		this.fhActualizacion = fhActualizacion;
		this.ejecucionDTO = ejecucionDTO;
		//this.replicaDocumentoDTOs = replicaDocumentoDTOs;
	}

	public Long getCoReplica() {
		return coReplica;
	}

	public void setCoReplica(Long coReplica) {
		this.coReplica = coReplica;
	}

	public MunicipioDTO getMunicipioDTO() {
		return municipioDTO;
	}

	public void setMunicipioDTO(MunicipioDTO municipioDTO) {
		this.municipioDTO = municipioDTO;
	}

	public OtrDTO getOtrDTO() {
		return otrDTO;
	}

	public void setOtrDTO(OtrDTO otrDTO) {
		this.otrDTO = otrDTO;
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCoUsuarioActualizacion() {
		return coUsuarioActualizacion;
	}

	public void setCoUsuarioActualizacion(String coUsuarioActualizacion) {
		this.coUsuarioActualizacion = coUsuarioActualizacion;
	}

	public Date getFhActualizacion() {
		return fhActualizacion;
	}

	public void setFhActualizacion(Date fhActualizacion) {
		this.fhActualizacion = fhActualizacion;
	}

	public EjecucionDTO getEjecucionDTO() {
		return ejecucionDTO;
	}

	public void setEjecucionDTO(EjecucionDTO ejecucionDTO) {
		this.ejecucionDTO = ejecucionDTO;
	}

	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = Utilidades.codificarRowidFormatoSeguro(rowid);
	}

}
