package es.dipucadiz.etir.comun.dto;

import java.util.Date;

public class BOPDocumentoDTO implements java.io.Serializable {

     private Long coBOPDocumento;
     private DocumentoDTO documentoDTO;
     private BOPDTO bopDTO;
     private Date fhActualizacion;
     private String coUsuarioActualizacion;
	 private String rowid;

	 public BOPDocumentoDTO() {		 
	 }

	public Long getCoBOPDocumento() {
		return coBOPDocumento;
	}

	public void setCoBOPDocumento(Long coBOPDocumento) {
		this.coBOPDocumento = coBOPDocumento;
	}

	public DocumentoDTO getDocumentoDTO() {
		return documentoDTO;
	}

	public void setDocumentoDTO(DocumentoDTO documentoDTO) {
		this.documentoDTO = documentoDTO;
	}

	public BOPDTO getBopDTO() {
		return bopDTO;
	}

	public void setBopDTO(BOPDTO bopDTO) {
		this.bopDTO = bopDTO;
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
