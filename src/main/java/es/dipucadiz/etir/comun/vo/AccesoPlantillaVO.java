package es.dipucadiz.etir.comun.vo;

import java.io.Serializable;



public class AccesoPlantillaVO implements Serializable {
	private static final long serialVersionUID = -5541579605767258850L;

	private String coProvincia = "**";
	private String coMunicipio = "***";
	private String coConcepto = "****";
	private String coModelo = "***";
	private String coVersion = "*";
	private String accion = "I";
	
	public String getCoProvincia() {
		return coProvincia;
	}

	public void setCoProvincia(final String coProvincia) {
		this.coProvincia = coProvincia;
	}

	public String getCoMunicipio() {
		return coMunicipio;
	}

	public void setCoMunicipio(final String coMunicipio) {
		this.coMunicipio = coMunicipio;
	}

	public String getCoConcepto() {
		return coConcepto;
	}

	public void setCoConcepto(final String coConcepto) {
		this.coConcepto = coConcepto;
	}

	public String getCoModelo() {
		return coModelo;
	}

	public void setCoModelo(final String coModelo) {
		this.coModelo = coModelo;
	}

	public String getCoVersion() {
		return coVersion;
	}

	public void setCoVersion(final String coVersion) {
		this.coVersion = coVersion;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(final String accion) {
		this.accion = accion;
	}
	
	@Override
	public int hashCode() {
		return (coProvincia + coMunicipio + coConcepto + coModelo + coVersion + accion).hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (!(o instanceof AccesoPlantillaVO)) {
			return false;
		}
		AccesoPlantillaVO otro = (AccesoPlantillaVO) o;
		return this.coProvincia.equals(otro.getCoProvincia()) &&
				this.coMunicipio.equals(otro.getCoMunicipio()) &&
				this.coConcepto.equals(otro.getCoConcepto()) &&
				this.coModelo.equals(otro.getCoModelo()) &&
				this.coVersion.equals(otro.getCoVersion()) &&
				this.accion.equals(otro.getAccion());
	}
	
}
