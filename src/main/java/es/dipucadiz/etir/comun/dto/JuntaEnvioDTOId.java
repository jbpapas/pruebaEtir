package es.dipucadiz.etir.comun.dto;

public class JuntaEnvioDTOId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2141113653231245637L;
	private Long nuEnvio;
	private String coDelegacion;

	public JuntaEnvioDTOId() {
	}

	public JuntaEnvioDTOId(Long nuEnvio, String coDelegacion) {
		this.nuEnvio = nuEnvio;
		this.coDelegacion = coDelegacion;
	}

	public Long getNuEnvio() {
		return nuEnvio;
	}

	public void setNuEnvio(Long nuEnvio) {
		this.nuEnvio = nuEnvio;
	}

	public String getCoDelegacion() {
		return coDelegacion;
	}

	public void setCoDelegacion(String coDelegacion) {
		this.coDelegacion = coDelegacion;
	}

	public boolean equals(Object other) {
		if ( (this == other ) ) return true;
		if ( (other == null ) ) return false;
		if ( !(other instanceof JuntaEnvioDTOId) ) return false;
		JuntaEnvioDTOId castOther = ( JuntaEnvioDTOId ) other; 
		return (this.getNuEnvio()==castOther.getNuEnvio()) && (this.getCoDelegacion()==castOther.getCoDelegacion());
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getNuEnvio() == null ? 0 : this.getNuEnvio()
						.hashCode());
		result = 37
				* result
				+ (getCoDelegacion() == null ? 0 : this.getCoDelegacion()
						.hashCode());
		return result;
	}
}
