package es.dipucadiz.etir.comun.dto;

import java.util.Date;

import es.dipucadiz.etir.comun.utilidades.MunicipioConceptoModeloUtil;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class CircuitoConceptoDTO  implements java.io.Serializable {
	private static final long serialVersionUID = 8435707704867324971L;
	
	private CircuitoConceptoDTOId id;
	private MunicipioDTO municipioDTO;
	private ConceptoDTO conceptoDTO;
	private CircuitoDTO circuitoDTO;
	private Date fhActualizacion;
	private String coUsuarioActualizacion;
	private String rowid;

    public CircuitoConceptoDTO() {
    }

	
    public CircuitoConceptoDTO(CircuitoConceptoDTOId id, CircuitoDTO circuitoDTO) {
        this.id = id;
        this.circuitoDTO = circuitoDTO;
    }
    public CircuitoConceptoDTO(CircuitoConceptoDTOId id, CircuitoDTO circuitoDTO, Date fhActualizacion, String coUsuarioActualizacion) {
       this.id = id;
       this.circuitoDTO = circuitoDTO;
       this.fhActualizacion = fhActualizacion;
       this.coUsuarioActualizacion = coUsuarioActualizacion;
    }
   
    public CircuitoConceptoDTOId getId() {
        return this.id;
    }
    
    public void setId(CircuitoConceptoDTOId id) {
        this.id = id;
    }
    public CircuitoDTO getCircuitoDTO() {
        return this.circuitoDTO;
    }
    
    public void setCircuitoDTO(CircuitoDTO circuitoDTO) {
        this.circuitoDTO = circuitoDTO;
    }
    public Date getFhActualizacion() {
        return this.fhActualizacion;
    }
    
    public void setFhActualizacion(Date fhActualizacion) {
        this.fhActualizacion = fhActualizacion;
    }
    public String getCoUsuarioActualizacion() {
        return this.coUsuarioActualizacion;
    }
    
    public void setCoUsuarioActualizacion(String coUsuarioActualizacion) {
        this.coUsuarioActualizacion = coUsuarioActualizacion;
    }
	
	public String getRowid() {
		return rowid;
	}
	
	public void setRowid(String rowid) {
		this.rowid = Utilidades.codificarRowidFormatoSeguro(rowid);
	}


	public MunicipioDTO getMunicipioDTO() {
		return municipioDTO;
	}


	public void setMunicipioDTO(MunicipioDTO municipioDTO) {
		this.municipioDTO = municipioDTO;
	}


	public ConceptoDTO getConceptoDTO() {
		return conceptoDTO;
	}


	public void setConceptoDTO(ConceptoDTO conceptoDTO) {
		this.conceptoDTO = conceptoDTO;
	}


	public String getCodigoDescripcionMunicipio(){
		MunicipioDTO m = MunicipioConceptoModeloUtil.getMunicipioDTO(this.municipioDTO.getId().getCoProvincia(), this.municipioDTO.getId().getCoMunicipio());
		return m.getCodigoDescripcion();
	}
	
	public String getCodigoDescripcionConcepto(){
		ConceptoDTO c = MunicipioConceptoModeloUtil.getConceptoDTO(this.conceptoDTO.getCoConcepto());
		return c.getCodigoDescripcion();
	}


}


