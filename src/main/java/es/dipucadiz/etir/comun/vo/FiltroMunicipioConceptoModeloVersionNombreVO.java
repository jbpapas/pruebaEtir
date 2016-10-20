package es.dipucadiz.etir.comun.vo;

import java.io.Serializable;

/**
 * Clase que representa el filtro de Municipio-Concepto-Modelo-Version.
 *
 * @version 1.0 11/02/2010
 * @author SDS[FJTORRES]
 */
public class FiltroMunicipioConceptoModeloVersionNombreVO implements Serializable {
	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 1L;

	private String coProvincia;
	private String coMunicipio;
	private String coConcepto;
	private String coModelo;
	private String coVersion;
	private String Nombre;

	/**
	 * Constructor de la clase.
	 *
	 */
	public FiltroMunicipioConceptoModeloVersionNombreVO(){}

	/**
	 * Constructor de la clase.
	 *
	 * @param coProvincia
	 * @param coMunicipio
	 * @param coConcepto
	 * @param coModelo
	 * @param coVersion
	 */
	public FiltroMunicipioConceptoModeloVersionNombreVO(final String coProvincia,
	        final String coMunicipio, final String coConcepto,
	        final String coModelo, final String coVersion, final String Nombre) {
		super();
		this.coProvincia = coProvincia;
		this.coMunicipio = coMunicipio;
		this.coConcepto = coConcepto;
		this.coModelo = coModelo;
		this.coVersion = coVersion;
		this.Nombre = Nombre;
	}

	/**
	 * Método que devuelve el atributo coMunicipio.
	 *
	 * @return coMunicipio.
	 */
	public String getCoMunicipio() {
		return coMunicipio;
	}

	/**
	 * Método que establece el atributo coMunicipio.
	 *
	 * @param coMunicipio
	 *            El coMunicipio.
	 */
	public void setCoMunicipio(final String coMunicipio) {
		this.coMunicipio = coMunicipio;
	}

	/**
	 * Método que devuelve el atributo coConcepto.
	 *
	 * @return coConcepto.
	 */
	public String getCoConcepto() {
		return coConcepto;
	}

	/**
	 * Método que establece el atributo coConcepto.
	 *
	 * @param coConcepto
	 *            El coConcepto.
	 */
	public void setCoConcepto(final String coConcepto) {
		this.coConcepto = coConcepto;
	}

	/**
	 * Método que devuelve el atributo coModelo.
	 *
	 * @return coModelo.
	 */
	public String getCoModelo() {
		return coModelo;
	}

	/**
	 * Método que establece el atributo coModelo.
	 *
	 * @param coModelo
	 *            El coModelo.
	 */
	public void setCoModelo(final String coModelo) {
		this.coModelo = coModelo;
	}

	/**
	 * Método que devuelve el atributo coVersion.
	 *
	 * @return coVersion.
	 */
	public String getCoVersion() {
		return coVersion;
	}

	/**
	 * Método que establece el atributo coVersion.
	 *
	 * @param coVersion
	 *            El coVersion.
	 */
	public void setCoVersion(final String coVersion) {
		this.coVersion = coVersion;
	}

	/**
	 * Método que devuelve el atributo coProvincia.
	 *
	 * @return coProvincia.
	 */
	public String getCoProvincia() {
		return coProvincia;
	}

	/**
	 * Método que establece el atributo coProvincia.
	 *
	 * @param coProvincia
	 *            El coProvincia.
	 */
	public void setCoProvincia(final String coProvincia) {
		this.coProvincia = coProvincia;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}
}
