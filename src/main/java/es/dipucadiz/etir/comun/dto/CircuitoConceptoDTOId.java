package es.dipucadiz.etir.comun.dto;

public class CircuitoConceptoDTOId  implements java.io.Serializable {
	private static final long serialVersionUID = -387974676252339699L;

	private String coCircuito;
	private String coProvincia;
	private String coMunicipio;
	private String coConcepto;

	public CircuitoConceptoDTOId() {
	}

	public CircuitoConceptoDTOId(String coCircuito, String coProvincia, String coMunicipio, String coConcepto) {
		this.coCircuito = coCircuito;
		this.coProvincia = coProvincia;
		this.coMunicipio = coMunicipio;
		this.coConcepto = coConcepto;
	}

	public String getCoCircuito() {
		return coCircuito;
	}

	public void setCoCircuito(String coCircuito) {
		this.coCircuito = coCircuito;
	}

	public String getCoProvincia() {
		return coProvincia;
	}

	public void setCoProvincia(String coProvincia) {
		this.coProvincia = coProvincia;
	}

	public String getCoMunicipio() {
		return coMunicipio;
	}

	public void setCoMunicipio(String coMunicipio) {
		this.coMunicipio = coMunicipio;
	}

	public String getCoConcepto() {
		return coConcepto;
	}

	public void setCoConcepto(String coConcepto) {
		this.coConcepto = coConcepto;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CircuitoConceptoDTOId))
			return false;
		CircuitoConceptoDTOId castOther = (CircuitoConceptoDTOId) other;

		return ((this.getCoCircuito() == castOther.getCoCircuito()) || (this
					.getCoCircuito() != null
					&& castOther.getCoCircuito() != null && this
					.getCoCircuito().equals(castOther.getCoCircuito())))
				&& ((this.getCoProvincia() == castOther.getCoProvincia()) || (this
						.getCoProvincia() != null
						&& castOther.getCoProvincia() != null && this
						.getCoProvincia().equals(castOther.getCoProvincia())))
				&& ((this.getCoMunicipio() == castOther.getCoMunicipio()) || (this
						.getCoMunicipio() != null
						&& castOther.getCoMunicipio() != null && this
						.getCoMunicipio().equals(castOther.getCoMunicipio())))
				&& ((this.getCoConcepto() == castOther.getCoConcepto()) || (this
						.getCoConcepto() != null
						&& castOther.getCoConcepto() != null && this
						.getCoConcepto().equals(castOther.getCoConcepto())));
	}
	
	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getCoCircuito() == null ? 0 : this.getCoCircuito().hashCode());
		result = 37 * result
				+ (getCoProvincia() == null ? 0 : this.getCoProvincia().hashCode());
		result = 37 * result
				+ (getCoMunicipio() == null ? 0 : this.getCoMunicipio().hashCode());
		result = 37 * result
				+ (getCoConcepto() == null ? 0 : this.getCoConcepto().hashCode());
		return result;
	}

}


