package es.dipucadiz.etir.comun.dto;

// Generated 02-feb-2010 13:28:20 by Hibernate Tools 3.2.4.GA

public class JuntaCargaErrorDTOId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3309556304529979907L;
	private long coJuntaCarga;
	private String claveDeuda;
	
	public JuntaCargaErrorDTOId() {
	}

	public JuntaCargaErrorDTOId(long coJuntaCarga, String claveDeuda) {
		this.coJuntaCarga = coJuntaCarga;
		this.claveDeuda = claveDeuda;
	}

	public long getCoJuntaCarga() {
		return coJuntaCarga;
	}

	public void setCoJuntaCarga(long coJuntaCarga) {
		this.coJuntaCarga = coJuntaCarga;
	}

	public String getClaveDeuda() {
		return claveDeuda;
	}

	public void setClaveDeuda(String claveDeuda) {
		this.claveDeuda = claveDeuda;
	}
	
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof JuntaCargaErrorDTOId))
			return false;
		JuntaCargaErrorDTOId castOther = (JuntaCargaErrorDTOId) other;

		return ((this.getCoJuntaCarga() == castOther.getCoJuntaCarga())&& 
				(this.getClaveDeuda() == castOther.getClaveDeuda()) || (this
				.getClaveDeuda() != null
				&& castOther.getClaveDeuda() != null && this
				.getClaveDeuda().equals(castOther.getClaveDeuda())));
	}

	public int hashCode() {
		int result = 17;
		
		result = 37 * result + (int) this.getCoJuntaCarga();
		
		result = 37
				* result
				+ (getClaveDeuda() == null ? 0 : this.getClaveDeuda()
						.hashCode());
		return result;
	}

}
