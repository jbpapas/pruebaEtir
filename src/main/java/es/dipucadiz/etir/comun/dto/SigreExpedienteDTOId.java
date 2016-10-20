package es.dipucadiz.etir.comun.dto;

public class SigreExpedienteDTOId implements java.io.Serializable {
	private static final long serialVersionUID = 8233114588606800535L;

	private String libreria;
	private long expediente;

	public SigreExpedienteDTOId() {
	}

	public SigreExpedienteDTOId(String libreria, long expediente) {
		this.libreria = libreria;
		this.expediente = expediente;
	}

	public String getLibreria() {
		return libreria;
	}

	public void setLibreria(String libreria) {
		this.libreria = libreria;
	}

	public long getExpediente() {
		return expediente;
	}

	public void setExpediente(long expediente) {
		this.expediente = expediente;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SigreExpedienteDTOId))
			return false;
		SigreExpedienteDTOId castOther = (SigreExpedienteDTOId) other;

		return ((this.getLibreria() == castOther.getLibreria()) || (this
				.getLibreria() != null
				&& castOther.getLibreria() != null && this
				.getLibreria().equals(castOther.getLibreria())))
				&& (this.getExpediente() == castOther.getExpediente());
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getLibreria() == null ? 0 : this.getLibreria()
						.hashCode());
		result = 37 * result + (int) this.getExpediente();
		return result;
	}

}
