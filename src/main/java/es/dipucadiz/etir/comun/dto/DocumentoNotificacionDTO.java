package es.dipucadiz.etir.comun.dto;

// Generated 06-feb-2012 08:11:20 by Hibernate Tools 3.2.4.GA

import java.math.BigDecimal;
import java.util.Date;

public class DocumentoNotificacionDTO implements java.io.Serializable {	
	private static final long serialVersionUID = 686462165690650478L;

	private Long coDocumentoNotificacion;
	private DocumentoDTO documentoDTO;
	private DocumentoSeguimientoDTO docSeguimientoEnvioDTO;
	private DocumentoSeguimientoDTO docSeguimientoResultadoDTO;
	private String coResultado;
	private Date fxResultado;
	private ExpedienteDTO expedienteDTO;
    private BDDocumentalGrupoDTO bdDocumentalGrupoDTO;
    private DocumentoDTO documentoResultadoDTO;
    private String codigoBarras;
	private String tipoNotificacion;
	private String codigoBarrasNot;
	private RemesaCargoCanalResDTO remesaCargoCanalResDTO;	
	private UnidadUrbanaDTO unidadUrbanaDTO;
	private BigDecimal imNotificado;
	private BigDecimal imNotificadoDocumento;
	private String coUsuarioActualizacion;
	private Date fhActualizacion;
	private String rowid;
	
	
	public String getCodigoBarrasNot() {
		return codigoBarrasNot;
	}

	public void setCodigoBarrasNot(String codigoBarrasNot) {
		this.codigoBarrasNot = codigoBarrasNot;
	}

	public RemesaCargoCanalResDTO getRemesaCargoCanalResDTO() {
		return remesaCargoCanalResDTO;
	}

	public void setRemesaCargoCanalResDTO(
			RemesaCargoCanalResDTO remesaCargoCanalResDTO) {
		this.remesaCargoCanalResDTO = remesaCargoCanalResDTO;
	}

	public UnidadUrbanaDTO getUnidadUrbanaDTO() {
		return unidadUrbanaDTO;
	}

	public void setUnidadUrbanaDTO(UnidadUrbanaDTO unidadUrbanaDTO) {
		this.unidadUrbanaDTO = unidadUrbanaDTO;
	}


	
	public DocumentoNotificacionDTO() {
	}
	
	public Long getCoDocumentoNotificacion() {
		return coDocumentoNotificacion;
	}
	public void setCoDocumentoNotificacion(Long coDocumentoNotificacion) {
		this.coDocumentoNotificacion = coDocumentoNotificacion;
	}
	public DocumentoDTO getDocumentoDTO() {
		return documentoDTO;
	}
	public void setDocumentoDTO(DocumentoDTO documentoDTO) {
		this.documentoDTO = documentoDTO;
	}
	public DocumentoSeguimientoDTO getDocSeguimientoEnvioDTO() {
		return docSeguimientoEnvioDTO;
	}
	public void setDocSeguimientoEnvioDTO(DocumentoSeguimientoDTO docSeguimientoEnvioDTO) {
		this.docSeguimientoEnvioDTO = docSeguimientoEnvioDTO;
	}
	public DocumentoSeguimientoDTO getDocSeguimientoResultadoDTO() {
		return docSeguimientoResultadoDTO;
	}
	public void setDocSeguimientoResultadoDTO(DocumentoSeguimientoDTO docSeguimientoResultadoDTO) {
		this.docSeguimientoResultadoDTO = docSeguimientoResultadoDTO;
	}
	public String getCoResultado() {
		return coResultado;
	}
	public void setCoResultado(String coResultado) {
		this.coResultado = coResultado;
	}
	public Date getFxResultado() {
		return fxResultado;
	}
	public void setFxResultado(Date fxResultado) {
		this.fxResultado = fxResultado;
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

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}

	public String getRowid() {
		return rowid;
	}

	public ExpedienteDTO getExpedienteDTO() {
		return expedienteDTO;
	}

	public void setExpedienteDTO(ExpedienteDTO expedienteDTO) {
		this.expedienteDTO = expedienteDTO;
	}

	public String getTipoNotificacion() {
		return tipoNotificacion;
	}

	public void setTipoNotificacion(String tipoNotificacion) {
		this.tipoNotificacion = tipoNotificacion;
	}

	public BDDocumentalGrupoDTO getBdDocumentalGrupoDTO() {
		return bdDocumentalGrupoDTO;
	}

	public void setBdDocumentalGrupoDTO(BDDocumentalGrupoDTO bdDocumentalGrupoDTO) {
		this.bdDocumentalGrupoDTO = bdDocumentalGrupoDTO;
	}

	public DocumentoDTO getDocumentoResultadoDTO() {
		return documentoResultadoDTO;
	}

	public void setDocumentoResultadoDTO(DocumentoDTO documentoResultadoDTO) {
		this.documentoResultadoDTO = documentoResultadoDTO;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public String getCodigoBarras() {
		return codigoBarras;
	}

	public BigDecimal getImNotificado() {
		return imNotificado;
	}

	public void setImNotificado(BigDecimal imNotificado) {
		this.imNotificado = imNotificado;
	}

	public BigDecimal getImNotificadoDocumento() {
		return imNotificadoDocumento;
	}

	public void setImNotificadoDocumento(BigDecimal imNotificadoDocumento) {
		this.imNotificadoDocumento = imNotificadoDocumento;
	}
}