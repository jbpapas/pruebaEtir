package es.dipucadiz.etir.comun.dto;

import java.sql.Blob;
import java.util.Date;

public class BDDocumentalDTO implements java.io.Serializable {

	private static final long serialVersionUID = -8657851118892283976L;
	private Long coBDDocumental;
    private BDDocumentalGrupoDTO bdDocumentalGrupoDTO;
	private DocumentoDTO documentoDTO;
    private String observaciones;
	private String nombre;
	private String tipo;
	private Date fhPublicado;
	private String idAlfrescoFirmado;
    private Blob fichero;
    private Date fhActualizacion;
    private String coUsuarioActualizacion;
	private String rowid;

	public BDDocumentalDTO() {}

	public Long getCoBDDocumental() {
		return coBDDocumental;
	}

	public void setCoBDDocumental(Long coBDDocumental) {
		this.coBDDocumental = coBDDocumental;
	}

	public DocumentoDTO getDocumentoDTO() {
		return documentoDTO;
	}

	public void setDocumentoDTO(DocumentoDTO documentoDTO) {
		this.documentoDTO = documentoDTO;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Blob getFichero() {
		return fichero;
	}

	public void setFichero(Blob fichero) {
		this.fichero = fichero;
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

	public BDDocumentalGrupoDTO getBdDocumentalGrupoDTO() {
		return bdDocumentalGrupoDTO;
	}

	public void setBdDocumentalGrupoDTO(BDDocumentalGrupoDTO bdDocumentalGrupoDTO) {
		this.bdDocumentalGrupoDTO = bdDocumentalGrupoDTO;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Date getFhPublicado() {
		return fhPublicado;
	}

	public void setFhPublicado(Date fhPublicado) {
		this.fhPublicado = fhPublicado;
	}

	public String getIdAlfrescoFirmado() {
		return idAlfrescoFirmado;
	}

	public void setIdAlfrescoFirmado(String idAlfrescoFirmado) {
		this.idAlfrescoFirmado = idAlfrescoFirmado;
	}

}
