package es.dipucadiz.etir.comun.dto;


import java.math.BigDecimal;
import java.util.Date;

import es.dipucadiz.etir.comun.utilidades.Utilidades;


public class ReplicaPendienteDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8137869720773462879L;
	private Long coReplicaPendiente;
	private DocumentoDTO documentoDTO;
	private BigDecimal imPrincipal;
	private BigDecimal imRecApremio;
	private BigDecimal imIntereses;
	private BigDecimal imCostas;
	private String observaciones;
	private String coUsuarioActualizacion;
	private Date fhActualizacion;
	private EjecucionDTO ejecucionDTO;
	private ReplicaDTO replicaDTO;
	private String tipo;
	private String rowid;


	public ReplicaPendienteDTO() {
	}

	public ReplicaPendienteDTO(Long coReplicaPendiente) {
		this.coReplicaPendiente = coReplicaPendiente;
	}

	public ReplicaPendienteDTO(Long coReplicaPendiente, DocumentoDTO documentoDTO, String observaciones, 
			BigDecimal imPrincipal, BigDecimal imRecApremio, BigDecimal imIntereses, BigDecimal imCostas, String tipo,
			String coUsuarioActualizacion, Date fhActualizacion, EjecucionDTO ejecucionDTO, ReplicaDTO replicaDTO) {
		this.coReplicaPendiente = coReplicaPendiente;
		this.documentoDTO = documentoDTO;
		this.observaciones = observaciones;
		this.coUsuarioActualizacion = coUsuarioActualizacion;
		this.fhActualizacion = fhActualizacion;
		this.ejecucionDTO = ejecucionDTO;
		this.replicaDTO = replicaDTO;
		this.imPrincipal = imPrincipal;
		this.imRecApremio = imRecApremio;
		this.imIntereses = imIntereses;
		this.imCostas = imCostas;
		this.tipo = tipo;
	}


	public Long getCoReplicaPendiente() {
		return coReplicaPendiente;
	}

	public void setCoReplicaPendiente(Long coReplicaPendiente) {
		this.coReplicaPendiente = coReplicaPendiente;
	}

	public DocumentoDTO getDocumentoDTO() {
		return documentoDTO;
	}

	public void setDocumentoDTO(DocumentoDTO documentoDTO) {
		this.documentoDTO = documentoDTO;
	}

	public BigDecimal getImPrincipal() {
		return imPrincipal;
	}

	public void setImPrincipal(BigDecimal imPrincipal) {
		this.imPrincipal = imPrincipal;
	}

	public BigDecimal getImRecApremio() {
		return imRecApremio;
	}

	public void setImRecApremio(BigDecimal imRecApremio) {
		this.imRecApremio = imRecApremio;
	}

	public BigDecimal getImIntereses() {
		return imIntereses;
	}

	public void setImIntereses(BigDecimal imIntereses) {
		this.imIntereses = imIntereses;
	}

	public BigDecimal getImCostas() {
		return imCostas;
	}

	public void setImCostas(BigDecimal imCostas) {
		this.imCostas = imCostas;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = Utilidades.codificarRowidFormatoSeguro(rowid);
	}

}
