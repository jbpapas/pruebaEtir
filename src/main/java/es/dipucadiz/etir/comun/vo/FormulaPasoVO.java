package es.dipucadiz.etir.comun.vo;

import java.io.Serializable;

public class FormulaPasoVO implements Serializable {

	/**
     * Atributo que almacena el serialVersionUID de la clase.
     */
    private static final long serialVersionUID = -8805613133647903424L;
    
    /**
     * Atributo que almacena el paso de la clase.
     */
    private String paso;
    
    /**
     * Atributo que almacena el orden del paso de la clase.
     */
    private String ordenPaso;
    
    /**
     * Atributo que almacena el rowid de la clase.
     */
    private String rowid;
    
    /**
     * Atributo que almacena el tipoDestino de la clase.
     */
    private String operacion;
    
    /**
     * Atributo que almacena el valorDestino de la clase.
     */
    private String argumentos;
    

	/**
	 * @return the paso
	 */
	public String getPaso() {
		return paso;
	}

	/**
	 * @param paso the paso to set
	 */
	public void setPaso(String paso) {
		this.paso = paso;
	}

	/**
	 * @return the rowid
	 */
	public String getRowid() {
		return rowid;
	}

	/**
	 * @param rowid the rowid to set
	 */
	public void setRowid(String rowid) {
		this.rowid = rowid;
	}

	/**
	 * @return the operacion
	 */
	public String getOperacion() {
		return operacion;
	}

	/**
	 * @param operacion the operacion to set
	 */
	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	/**
	 * @return the argumentos
	 */
	public String getArgumentos() {
		return argumentos;
	}

	/**
	 * @param argumentos the argumentos to set
	 */
	public void setArgumentos(String argumentos) {
		this.argumentos = argumentos;
	}

	/**
	 * @return the ordenPaso
	 */
	public String getOrdenPaso() {
		return ordenPaso;
	}

	/**
	 * @param ordenPaso the ordenPaso to set
	 */
	public void setOrdenPaso(String ordenPaso) {
		this.ordenPaso = ordenPaso;
	}
    
   
}
