package es.dipucadiz.etir.comun.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class BOPDTO implements java.io.Serializable {
	private static final long serialVersionUID = 8911688284833527390L;

	private BOPDTOId id;
	private DocumentoDTO documentoDTO;
	private MunicipioDTO municipioDTO;
	private Date fxEnvio;
	private Date fxPublicacionBOP;
	private Date fxNotificacionBOP;
	private String nuBOP;
	private Integer nuDocumentosEnviados;
	private Integer nuDocumentosNotificados;
	private EstadoSituacionDTO estadoSituacionDTO;
	private BDDocumentalGrupoDTO bdDocumentalGrupoDTO;
	private String tipoBOP;
	private ExpedienteDTO expedienteDTO;
	private Date fhActualizacion;
	private String coUsuarioActualizacion;
	private Set<BOPDocumentoDTO> bopDocumentoDTOs = new HashSet<BOPDocumentoDTO>(0);
	private String rowid;

	 public BOPDTO() {		 
	 }

	 public BOPDTO(BOPDTOId id) {
		 this.id = id;
	 }

	public BOPDTOId getId() {
		return id;
	}

	public void setId(BOPDTOId id) {
		this.id = id;
	}

	public DocumentoDTO getDocumentoDTO() {
		return documentoDTO;
	}

	public void setDocumentoDTO(DocumentoDTO documentoDTO) {
		this.documentoDTO = documentoDTO;
	}

	public Date getFxEnvio() {
		return fxEnvio;
	}

	public void setFxEnvio(Date fxEnvio) {
		this.fxEnvio = fxEnvio;
	}

	public Date getFxPublicacionBOP() {
		return fxPublicacionBOP;
	}

	public void setFxPublicacionBOP(Date fxPublicacionBOP) {
		this.fxPublicacionBOP = fxPublicacionBOP;
	}

	public Date getFxNotificacionBOP() {
		return fxNotificacionBOP;
	}

	public void setFxNotificacionBOP(Date fxNotificacionBOP) {
		this.fxNotificacionBOP = fxNotificacionBOP;
	}

	public String getNuBOP() {
		return nuBOP;
	}

	public void setNuBOP(String nuBOP) {
		this.nuBOP = nuBOP;
	}

	public Integer getNuDocumentosEnviados() {
		return nuDocumentosEnviados;
	}

	public void setNuDocumentosEnviados(Integer nuDocumentosEnviados) {
		this.nuDocumentosEnviados = nuDocumentosEnviados;
	}

	public Integer getNuDocumentosNotificados() {
		return nuDocumentosNotificados;
	}

	public void setNuDocumentosNotificados(Integer nuDocumentosNotificados) {
		this.nuDocumentosNotificados = nuDocumentosNotificados;
	}

	public EstadoSituacionDTO getEstadoSituacionDTO() {
		return estadoSituacionDTO;
	}

	public void setEstadoSituacionDTO(EstadoSituacionDTO estadoSituacionDTO) {
		this.estadoSituacionDTO = estadoSituacionDTO;
	}

	public String getTipoBOP() {
		return tipoBOP;
	}

	public void setTipoBOP(String tipoBOP) {
		this.tipoBOP = tipoBOP;
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

	public Set<BOPDocumentoDTO> getBopDocumentoDTOs() {
		return bopDocumentoDTOs;
	}

	public void setBopDocumentoDTOs(Set<BOPDocumentoDTO> bopDocumentoDTOs) {
		this.bopDocumentoDTOs = bopDocumentoDTOs;
	}

	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}

	public ExpedienteDTO getExpedienteDTO() {
		return expedienteDTO;
	}

	public void setExpedienteDTO(ExpedienteDTO expedienteDTO) {
		this.expedienteDTO = expedienteDTO;
	}

	public BDDocumentalGrupoDTO getBdDocumentalGrupoDTO() {
		return bdDocumentalGrupoDTO;
	}

	public void setBdDocumentalGrupoDTO(BDDocumentalGrupoDTO bdDocumentalGrupoDTO) {
		this.bdDocumentalGrupoDTO = bdDocumentalGrupoDTO;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public MunicipioDTO getMunicipioDTO() {
		return municipioDTO;
	}

	public void setMunicipioDTO(MunicipioDTO municipioDTO) {
		this.municipioDTO = municipioDTO;
	}	 
}
