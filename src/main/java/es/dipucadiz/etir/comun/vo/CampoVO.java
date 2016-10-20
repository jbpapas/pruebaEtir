package es.dipucadiz.etir.comun.vo;

import java.io.Serializable;

/**
 * @author aolivares
 *
 */
public class CampoVO implements Serializable {

	/**
     * Atributo que almacena el serialVersionUID de la clase.
     */
    private static final long serialVersionUID = -8805613133647903424L;
    
    /**
     * Atributo que almacena el tipoOrigen de la clase.
     */
    private String tipoOrigen;
    
    /**
     * Atributo que almacena el valorOrigen de la clase.
     */
    private String valorOrigen;
    
    /**
     * Atributo que almacena el valorAdicional de la clase.
     */
    private String valorAdicionalOrigen;
    
    /**
     * Atributo que almacena el valorAdicional de la clase.
     */
    private String valorAdicionalDestino;
    
    /**
     * Atributo que almacena el tipoDestino de la clase.
     */
    private String tipoDestino;
    
    /**
     * Atributo que almacena el valorDestino de la clase.
     */
    private String valorDestino;
    
    /**
     * Atributo que almacena el operador de la clase.
     */
    private String operador;
    
    /**
     * Atributo que almacena el ruta de la clase.
     */
    private String ruta;
    
    /**
     * Atributo que almacena el ordenImgCondicional de la clase.
     */
    private short ordenImgCondicional;
    
    /**
     * Atributo que almacena el orden de la clase.
     */
    private byte orden;

	/**
     * Método que devuelve el atributo tipoOrigen.
     * 
     * @return tipoOrigen.
     */
    public String getTipoOrigen() {
    	return tipoOrigen;
    }

	/**
     * Método que establece el atributo tipoOrigen.
     * 
     * @param tipoOrigen
     *            El tipoOrigen.
     */
    public void setTipoOrigen(String tipoOrigen) {
    	this.tipoOrigen = tipoOrigen;
    }

	/**
     * Método que devuelve el atributo valorOrigen.
     * 
     * @return valorOrigen.
     */
    public String getValorOrigen() {
    	return valorOrigen;
    }

	/**
     * Método que establece el atributo valorOrigen.
     * 
     * @param valorOrigen
     *            El valorOrigen.
     */
    public void setValorOrigen(String valorOrigen) {
    	this.valorOrigen = valorOrigen;
    }

	/**
     * Método que devuelve el atributo tipoDestino.
     * 
     * @return tipoDestino.
     */
    public String getTipoDestino() {
    	return tipoDestino;
    }

	/**
     * Método que establece el atributo tipoDestino.
     * 
     * @param tipoDestino
     *            El tipoDestino.
     */
    public void setTipoDestino(String tipoDestino) {
    	this.tipoDestino = tipoDestino;
    }

	/**
     * Método que devuelve el atributo valorDestino.
     * 
     * @return valorDestino.
     */
    public String getValorDestino() {
    	return valorDestino;
    }

	/**
     * Método que establece el atributo valorDestino.
     * 
     * @param valorDestino
     *            El valorDestino.
     */
    public void setValorDestino(String valorDestino) {
    	this.valorDestino = valorDestino;
    }

	/**
     * Método que devuelve el atributo operador.
     * 
     * @return operador.
     */
    public String getOperador() {
    	return operador;
    }

	/**
     * Método que establece el atributo operador.
     * 
     * @param operador
     *            El operador.
     */
    public void setOperador(String operador) {
    	this.operador = operador;
    }

	/**
     * Método que devuelve el atributo ruta.
     * 
     * @return ruta.
     */
    public String getRuta() {
    	return ruta;
    }

	/**
     * Método que establece el atributo ruta.
     * 
     * @param ruta
     *            El ruta.
     */
    public void setRuta(String ruta) {
    	this.ruta = ruta;
    }

	/**
     * Método que devuelve el atributo ordenImgCondicional.
     * 
     * @return ordenImgCondicional.
     */
    public short getOrdenImgCondicional() {
    	return ordenImgCondicional;
    }

	/**
     * Método que establece el atributo ordenImgCondicional.
     * 
     * @param ordenImgCondicional
     *            El ordenImgCondicional.
     */
    public void setOrdenImgCondicional(short ordenImgCondicional) {
    	this.ordenImgCondicional = ordenImgCondicional;
    }

	/**
     * Método que devuelve el atributo orden.
     * 
     * @return orden.
     */
    public byte getOrden() {
    	return orden;
    }

	/**
     * Método que establece el atributo orden.
     * 
     * @param orden
     *            El orden.
     */
    public void setOrden(byte orden) {
    	this.orden = orden;
    }

	/**
	 * @return the valorAdicionalOrigen
	 */
	public String getValorAdicionalOrigen() {
		return valorAdicionalOrigen;
	}

	/**
	 * @param valorAdicionalOrigen the valorAdicionalOrigen to set
	 */
	public void setValorAdicionalOrigen(String valorAdicionalOrigen) {
		this.valorAdicionalOrigen = valorAdicionalOrigen;
	}

	/**
	 * @return the valorAdicionalDestino
	 */
	public String getValorAdicionalDestino() {
		return valorAdicionalDestino;
	}

	/**
	 * @param valorAdicionalDestino the valorAdicionalDestino to set
	 */
	public void setValorAdicionalDestino(String valorAdicionalDestino) {
		this.valorAdicionalDestino = valorAdicionalDestino;
	}
    
    
    

}
