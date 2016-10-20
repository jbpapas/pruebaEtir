package es.dipucadiz.etir.comun.vo;

import java.io.Serializable;

import es.dipucadiz.etir.comun.dto.CasillaModeloDTO;

/**
 * Clase responsable de mapear una relacion entre {@link CasillaModeloDTO} y una
 * estructura de salida definida con modelo de salida. <br/>
 * Almacena los datos de la casilla y los datos relevantes de la estructura.
 *
 * @version 1.0 20/01/2010
 * @author SDS[FJTORRES]
 */
public class CasillaModeloEstructuraVO implements Serializable {

	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = -6129648636782859634L;

	private CasillaModeloDTO casilla;

	private Short orden;
	private String tipo;
	private Short posInicio;
	private Short posFin;

	/**
	 * Constructor de la clase.
	 *
	 * @param vCasilla
	 */
	public CasillaModeloEstructuraVO(final CasillaModeloDTO vCasilla) {
		this.casilla = vCasilla;
	}

	/**
	 * Constructor de la clase.
	 *
	 * @param vCasillla
	 * @param vOrden
	 * @param vTipo
	 * @param vPosInicio
	 * @param vPosFin
	 */
	public CasillaModeloEstructuraVO(final CasillaModeloDTO vCasillla,
	        final Short vOrden, final String vTipo, final Short vPosInicio,
	        final Short vPosFin) {
		this.casilla = vCasillla;
		this.orden = vOrden;
		this.tipo = vTipo;
		this.posInicio = vPosInicio;
		this.posFin = vPosFin;
	}

	/**
	 * Método que devuelve el atributo casilla.
	 *
	 * @return casilla.
	 */
	public CasillaModeloDTO getCasilla() {
		return casilla;
	}

	/**
	 * Método que establece el atributo casilla.
	 *
	 * @param casilla
	 *            El casilla.
	 */
	public void setCasilla(final CasillaModeloDTO casilla) {
		this.casilla = casilla;
	}

	/**
	 * Método que devuelve el atributo orden.
	 *
	 * @return orden.
	 */
	public Short getOrden() {
		return orden;
	}

	/**
	 * Método que establece el atributo orden.
	 *
	 * @param orden
	 *            El orden.
	 */
	public void setOrden(final Short orden) {
		this.orden = orden;
	}

	/**
	 * Método que devuelve el atributo tipo.
	 *
	 * @return tipo.
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Método que establece el atributo tipo.
	 *
	 * @param tipo
	 *            El tipo.
	 */
	public void setTipo(final String tipo) {
		this.tipo = tipo;
	}

	/**
     * Método que devuelve el atributo posInicio.
     *
     * @return posInicio.
     */
    public Short getPosInicio() {
    	return posInicio;
    }

	/**
     * Método que establece el atributo posInicio.
     *
     * @param posInicio
     *            El posInicio.
     */
    public void setPosInicio(final Short posInicio) {
    	this.posInicio = posInicio;
    }

	/**
     * Método que devuelve el atributo posFin.
     *
     * @return posFin.
     */
    public Short getPosFin() {
    	return posFin;
    }

	/**
     * Método que establece el atributo posFin.
     *
     * @param posFin
     *            El posFin.
     */
    public void setPosFin(final Short posFin) {
    	this.posFin = posFin;
    }
}
