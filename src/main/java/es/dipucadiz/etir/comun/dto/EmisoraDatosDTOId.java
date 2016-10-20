package es.dipucadiz.etir.comun.dto;

// Generated 09-oct-2015 13:34:00 by prodriguez 

import java.math.BigDecimal;
import java.util.Date;

/**
 *  prodriguez 
 */
public class EmisoraDatosDTOId implements java.io.Serializable {
	private static final long serialVersionUID = -3180317549647412965L;

	private String coEmisora;
	private Date fhDevengo;
	private String ficheroOrigen;

	public EmisoraDatosDTOId() {
	}

	public EmisoraDatosDTOId(String coEmisora, Date fhDevengo, String ficheroOrigen) {
		this.coEmisora = coEmisora;
		this.fhDevengo = fhDevengo;
		this.ficheroOrigen = ficheroOrigen;
	}
	
	public String getCoEmisora() {
		return coEmisora;
	}

	public void setCoEmisora(String coEmisora) {
		this.coEmisora = coEmisora;
	}

	public Date getFhDevengo() {
		return fhDevengo;
	}

	public void setFhDevengo(Date fhDevengo) {
		this.fhDevengo = fhDevengo;
	}

	public String getFicheroOrigen() {
		return ficheroOrigen;
	}

	public void setFicheroOrigen(String ficheroOrigen) {
		this.ficheroOrigen = ficheroOrigen;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof EmisoraDatosDTOId))
			return false;
		EmisoraDatosDTOId castOther = (EmisoraDatosDTOId) other;

		return ((this.getCoEmisora()== castOther.getCoEmisora()) || (this
				.getCoEmisora() != null
				&& castOther.getCoEmisora() != null && this.getCoEmisora()
				.equals(castOther.getCoEmisora())))
				&& ((this.getFhDevengo() == castOther.getFhDevengo()) || (this
						.getFhDevengo() != null
						&& castOther.getFhDevengo() != null && this
						.getFhDevengo().equals(castOther.getFhDevengo())))
				&& ((this.getFicheroOrigen() == castOther.getFicheroOrigen()) || (this
						.getFicheroOrigen() != null
						&& castOther.getFicheroOrigen() != null && this
						.getFicheroOrigen().equals(castOther.getFicheroOrigen())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getCoEmisora() == null ? 0 : this.getCoEmisora().hashCode());
		result = 37 * result
				+ (getFhDevengo() == null ? 0 : this.getFhDevengo().hashCode());
		result = 37
				* result
				+ (getFicheroOrigen() == null ? 0 : this.getFicheroOrigen()
						.hashCode());
		return result;
	}

	
}
