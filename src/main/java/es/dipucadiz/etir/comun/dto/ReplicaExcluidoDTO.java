package es.dipucadiz.etir.comun.dto;


import java.util.Date;

import es.dipucadiz.etir.comun.utilidades.Utilidades;


public class ReplicaExcluidoDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8137869720773462879L;
	private Long coReplicaExcluido;
	private DocumentoDTO documentoDTO;
	private String observaciones;
	private String coUsuarioActualizacion;
	private Date fhActualizacion;
	private EjecucionDTO ejecucionDTO;
	private ReplicaDTO replicaDTO;
	private String rowid;


	public ReplicaExcluidoDTO() {
	}

	public ReplicaExcluidoDTO(Long coReplicaExcluido) {
		this.coReplicaExcluido = coReplicaExcluido;
	}

	public ReplicaExcluidoDTO(Long coReplicaExcluido, DocumentoDTO documentoDTO, String observaciones, 
			String coUsuarioActualizacion, Date fhActualizacion, EjecucionDTO ejecucionDTO, ReplicaDTO replicaDTO) {
		this.coReplicaExcluido = coReplicaExcluido;
		this.documentoDTO = documentoDTO;
		this.observaciones = observaciones;
		this.coUsuarioActualizacion = coUsuarioActualizacion;
		this.fhActualizacion = fhActualizacion;
		this.ejecucionDTO = ejecucionDTO;
		this.replicaDTO = replicaDTO;
	}


	public Long getCoReplicaExcluido() {
		return coReplicaExcluido;
	}

	public void setCoReplicaExcluido(Long coReplicaExcluido) {
		this.coReplicaExcluido = coReplicaExcluido;
	}

	public DocumentoDTO getDocumentoDTO() {
		return documentoDTO;
	}

	public void setDocumentoDTO(DocumentoDTO documentoDTO) {
		this.documentoDTO = documentoDTO;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public ReplicaDTO getReplicaDTO() {
		return replicaDTO;
	}

	public void setReplicaDTO(ReplicaDTO replicaDTO) {
		this.replicaDTO = replicaDTO;
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
