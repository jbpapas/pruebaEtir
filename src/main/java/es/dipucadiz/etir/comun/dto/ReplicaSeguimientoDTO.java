package es.dipucadiz.etir.comun.dto;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import es.dipucadiz.etir.comun.utilidades.Utilidades;


public class ReplicaSeguimientoDTO implements java.io.Serializable {

	private String coReplicaSeguimiento;
	private ReplicaDTO replicaDTO;
	private DocumentoDTO documentoDTO;
	private DocumentoSeguimientoDTO documentoSeguimientoDTO;
	private boolean boEnviado;
	private String coUsuarioActualizacion;
	private Date fhActualizacion;
	private EjecucionDTO ejecucionDTO;
	private String rowid;


	public ReplicaSeguimientoDTO() {
	}

	public ReplicaSeguimientoDTO(ReplicaDTO replicaDTO) {
		this.replicaDTO = replicaDTO;
	}

	public ReplicaSeguimientoDTO(String coReplicaSeguimiento, ReplicaDTO replicaDTO, 
			DocumentoDTO documentoDTO, DocumentoSeguimientoDTO documentoSeguimientoDTO, 
			boolean boEnviado, String coUsuarioActualizacion, Date fhActualizacion,
			EjecucionDTO ejecucionDTO) {
		this.coReplicaSeguimiento = coReplicaSeguimiento;
		this.documentoDTO = documentoDTO;
		this.boEnviado = boEnviado;
		this.coUsuarioActualizacion = coUsuarioActualizacion;
		this.fhActualizacion = fhActualizacion;
		this.ejecucionDTO = ejecucionDTO;
	}

	public String getCoReplicaSeguimiento() {
		return coReplicaSeguimiento;
	}

	public void setCoReplicaSeguimiento(String coReplicaSeguimiento) {
		this.coReplicaSeguimiento = coReplicaSeguimiento;
	}

	public DocumentoSeguimientoDTO getDocumentoSeguimientoDTO() {
		return documentoSeguimientoDTO;
	}

	public void setDocumentoSeguimientoDTO(
			DocumentoSeguimientoDTO documentoSeguimientoDTO) {
		this.documentoSeguimientoDTO = documentoSeguimientoDTO;
	}

	public DocumentoDTO getDocumentoDTO() {
		return documentoDTO;
	}

	public void setDocumentoDTO(DocumentoDTO documentoDTO) {
		this.documentoDTO = documentoDTO;
	}

	public ReplicaDTO getReplicaDTO() {
		return replicaDTO;
	}

	public void setReplicaDTO(ReplicaDTO replicaDTO) {
		this.replicaDTO = replicaDTO;
	}

	public boolean isBoEnviado() {
		return boEnviado;
	}

	public void setBoEnviado(boolean boEnviado) {
		this.boEnviado = boEnviado;
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
