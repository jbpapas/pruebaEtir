package es.dipucadiz.etir.comun.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import es.dipucadiz.etir.comun.dto.UnidadUrbanaDTO;

/**
 * Clase responsable de mapear un objeto de oracle de tipo REG_CANDIDATO.
 *  
 * @version 1.0 18/11/2009
 * @author SDS[AGONZALEZ]
 */
public class RegCandidatoVO implements Serializable {

	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = -3592006854362465696L;

	private String identificador;
	private String razonSocial;
	private BigDecimal indNif;
	private BigDecimal coMensaje;
	private BigDecimal unidadUrbana;
	private UnidadUrbanaDTO unidadUrbanaDTO;

	public RegCandidatoVO() {
	}

	public RegCandidatoVO(final String identificador, final String razonSocial,
			final BigDecimal indNif, final BigDecimal coMensaje, final BigDecimal unidadUrbana) {
		this.identificador = identificador;
		this.razonSocial = razonSocial;
		this.indNif = indNif;
		this.coMensaje = coMensaje;
		this.unidadUrbana = unidadUrbana;
	}
	
	/**
     * Método que devuelve el atributo identificador.
     * 
     * @return identificador.
     */
    public String getIdentificador() {
    	return identificador;
    }

	/**
     * Método que establece el atributo identificador.
     * 
     * @param identificador
     *            El identificador.
     */
    public void setIdentificador(String identificador) {
    	this.identificador = identificador;
    }

	/**
     * Método que devuelve el atributo razonSocial.
     * 
     * @return razonSocial.
     */
    public String getRazonSocial() {
    	return razonSocial;
    }

	/**
     * Método que establece el atributo razonSocial.
     * 
     * @param razonSocial
     *            El razonSocial.
     */
    public void setRazonSocial(String razonSocial) {
    	this.razonSocial = razonSocial;
    }

	/**
     * Método que devuelve el atributo indNif.
     * 
     * @return indNif.
     */
    public BigDecimal getIndNif() {
    	return indNif;
    }

	/**
     * Método que establece el atributo indNif.
     * 
     * @param indNif
     *            El indNif.
     */
    public void setIndNif(BigDecimal indNif) {
    	this.indNif = indNif;
    }

	/**
     * Método que devuelve el atributo coMensaje.
     * 
     * @return coMensaje.
     */
    public BigDecimal getCoMensaje() {
    	return coMensaje;
    }

	/**
     * Método que establece el atributo coMensaje.
     * 
     * @param coMensaje
     *            El coMensaje.
     */
    public void setCoMensaje(BigDecimal coMensaje) {
    	this.coMensaje = coMensaje;
    }

	/**
     * Método que devuelve el atributo unidadUrbana.
     * 
     * @return unidadUrbana.
     */
    public BigDecimal getUnidadUrbana() {
    	return unidadUrbana;
    }

	/**
     * Método que establece el atributo unidadUrbana.
     * 
     * @param unidadUrbana
     *            El unidadUrbana.
     */
    public void setUnidadUrbana(BigDecimal unidadUrbana) {
    	this.unidadUrbana = unidadUrbana;
    }

	/**
     * Método que devuelve el atributo unidadUrbanaDTO.
     * 
     * @return unidadUrbanaDTO.
     */
    public UnidadUrbanaDTO getUnidadUrbanaDTO() {
    	return unidadUrbanaDTO;
    }

	/**
     * Método que establece el atributo unidadUrbanaDTO.
     * 
     * @param unidadUrbanaDTO
     *            El unidadUrbanaDTO.
     */
    public void setUnidadUrbanaDTO(UnidadUrbanaDTO unidadUrbanaDTO) {
    	this.unidadUrbanaDTO = unidadUrbanaDTO;
    }
}
