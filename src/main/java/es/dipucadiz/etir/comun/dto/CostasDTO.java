package es.dipucadiz.etir.comun.dto;

import java.math.BigDecimal;
import java.util.Date;

public class CostasDTO implements java.io.Serializable {
	private static final long serialVersionUID = -8246226966193682677L;
	
	private Long coCostas;
	private ClienteDTO clienteDTO;
	private UnidadAdministrativaDTO unidadAdministrativaDTO;
	private CodigoTerritorialDTO codigoTerritorialDTO;
	private String tipo;
	private String descripcion;
	private BigDecimal importe;
	private Date fxAlta;
	private DocumentoDTO documentoDTO;
	private DocumentoDTO documentoCostasDTO;
	private String estado;
	private BDDocumentalGrupoDTO bdDocumentalGrupoDTO;
	private BienEmbargableDTO bienEmbargableDTO;
	private String nuFactura;
	private String coUsuarioActualizacion;
	private Date fhActualizacion;
	private String rowid;
	
	public CostasDTO() {
	}
	
	public CostasDTO(Long coCostas) {
		this.coCostas = coCostas;
	}
	
	public Long getCoCostas() {
		return coCostas;
	}
	public void setCoCostas(Long coCostas) {
		this.coCostas = coCostas;
	}
	public ClienteDTO getClienteDTO() {
		return clienteDTO;
	}
	public void setClienteDTO(ClienteDTO clienteDTO) {
		this.clienteDTO = clienteDTO;
	}
	public UnidadAdministrativaDTO getUnidadAdministrativaDTO() {
		return unidadAdministrativaDTO;
	}
	public void setUnidadAdministrativaDTO(
			UnidadAdministrativaDTO unidadAdministrativaDTO) {
		this.unidadAdministrativaDTO = unidadAdministrativaDTO;
	}
	public CodigoTerritorialDTO getCodigoTerritorialDTO() {
		return codigoTerritorialDTO;
	}
	public void setCodigoTerritorialDTO(CodigoTerritorialDTO codigoTerritorialDTO) {
		this.codigoTerritorialDTO = codigoTerritorialDTO;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public BigDecimal getImporte() {
		return importe;
	}
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}
	public Date getFxAlta() {
		return fxAlta;
	}
	public void setFxAlta(Date fxAlta) {
		this.fxAlta = fxAlta;
	}
	public DocumentoDTO getDocumentoDTO() {
		return documentoDTO;
	}
	public void setDocumentoDTO(DocumentoDTO documentoDTO) {
		this.documentoDTO = documentoDTO;
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
	public String getRowid() {
		return rowid;
	}
	public void setRowid(String rowid) {
		this.rowid = rowid;
	}

	public void setBdDocumentalGrupoDTO(BDDocumentalGrupoDTO bdDocumentalGrupoDTO) {
		this.bdDocumentalGrupoDTO = bdDocumentalGrupoDTO;
	}

	public BDDocumentalGrupoDTO getBdDocumentalGrupoDTO() {
		return bdDocumentalGrupoDTO;
	}

	public DocumentoDTO getDocumentoCostasDTO() {
		return documentoCostasDTO;
	}

	public void setDocumentoCostasDTO(DocumentoDTO documentoCostasDTO) {
		this.documentoCostasDTO = documentoCostasDTO;
	}

	public BienEmbargableDTO getBienEmbargableDTO() {
		return bienEmbargableDTO;
	}

	public void setBienEmbargableDTO(BienEmbargableDTO bienEmbargableDTO) {
		this.bienEmbargableDTO = bienEmbargableDTO;
	}

	public String getNuFactura() {
		return nuFactura;
	}

	public void setNuFactura(String nuFactura) {
		this.nuFactura = nuFactura;
	}
}