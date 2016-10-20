package es.dipucadiz.etir.comun.vo;

import java.io.Serializable;

import es.dipucadiz.etir.comun.dto.CargoSubcargoDTO;

public class CargosSubcargoVO extends CargoSubcargoDTO  implements Serializable {
	
	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = -6129648636782859634L;

	
	private String codigoDescripcionModelo;
	private String codigoDescripcionConcepto;
	
	
	public String getCodigoDescripcionModelo() {
		return this.getCargoDTO().getModeloDTO().getCoModelo()+"-"+ this.getCargoDTO().getModeloDTO().getNombre();
	}
	public void setCodigoDescripcionModelo(String codigoDescripcionModelo) {
		this.codigoDescripcionModelo = codigoDescripcionModelo;
	}
	public String getCodigoDescripcionConcepto() {
		if(this.getConceptoDTO() != null){
			return this.getConceptoDTO().getCoConcepto()+"-"+this.getConceptoDTO().getNombre();
		}
		else{
			return "";
		}
	}
	public void setCodigoDescripcionConcepto(String codigoDescripcionConcepto) {
		this.codigoDescripcionConcepto = codigoDescripcionConcepto;
	}
	
	
	
}