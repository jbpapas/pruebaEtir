package es.dipucadiz.etir.comun.vo;


public class CasillaMunicipioOperacionVO implements java.io.Serializable {

	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 1353735553358300979L;

    /**
     * Atributos 
     */
    private String orden;
	
    private String nuCasilla;
    
    private String descCasilla;
    
    private Boolean mantenible;
    
    private String atributo;
    
    private String descAtributo;
    
    private String funcion;
    
    private String argumento;
    
    private String parametro;
    
    private String masProcesos;
    
    private String coCasillaMunicipio;
    
    private String coMunicipio;
    
    private String coProvincia;
    
    private String coOperacion;
    
    
	public CasillaMunicipioOperacionVO() {
	}
	

	/**
	 * @return the orden
	 */
	public String getOrden() {
		return orden;
	}

	/**
	 * @param orden the orden to set
	 */
	public void setOrden(String orden) {
		this.orden = orden;
	}

	/**
	 * @return the nuCasilla
	 */
	public String getNuCasilla() {
		return nuCasilla;
	}

	/**
	 * @param nuCasilla the nuCasilla to set
	 */
	public void setNuCasilla(String nuCasilla) {
		this.nuCasilla = nuCasilla;
	}

	/**
	 * @return the descCasilla
	 */
	public String getDescCasilla() {
		return descCasilla;
	}

	/**
	 * @param descCasilla the descCasilla to set
	 */
	public void setDescCasilla(String descCasilla) {
		this.descCasilla = descCasilla;
	}

	/**
	 * @return the mantenible
	 */
	public Boolean getMantenible() {
		return mantenible;
	}

	/**
	 * @param mantenible the mantenible to set
	 */
	public void setMantenible(Boolean mantenible) {
		this.mantenible = mantenible;
	}

	/**
	 * @return the atributo
	 */
	public String getAtributo() {
		return atributo;
	}

	/**
	 * @param atributo the atributo to set
	 */
	public void setAtributo(String atributo) {
		this.atributo = atributo;
	}

	/**
	 * @return the descAtributo
	 */
	public String getDescAtributo() {
		return descAtributo;
	}

	/**
	 * @param descAtributo the descAtributo to set
	 */
	public void setDescAtributo(String descAtributo) {
		this.descAtributo = descAtributo;
	}

	/**
	 * @return the funcion
	 */
	public String getFuncion() {
		return funcion;
	}

	/**
	 * @param funcion the funcion to set
	 */
	public void setFuncion(String funcion) {
		this.funcion = funcion;
	}
	
	/**
	 * @return the argumento
	 */
	public String getArgumento() {
		return argumento;
	}

	/**
	 * @param argumento the argumento to set
	 */
	public void setArgumento(String argumento) {
		this.argumento = argumento;
	}

	/**
	 * @return the parametro
	 */
	public String getParametro() {
		return parametro;
	}

	/**
	 * @param parametro the parametro to set
	 */
	public void setParametro(String parametro) {
		this.parametro = parametro;
	}

	/**
	 * @return the masProcesos
	 */
	public String getMasProcesos() {
		return masProcesos;
	}

	/**
	 * @param masProcesos the masProcesos to set
	 */
	public void setMasProcesos(String masProcesos) {
		this.masProcesos = masProcesos;
	}


	/**
	 * @return the coCasillaMunicipio
	 */
	public String getCoCasillaMunicipio() {
		return coCasillaMunicipio;
	}


	/**
	 * @param coCasillaMunicipio the coCasillaMunicipio to set
	 */
	public void setCoCasillaMunicipio(String coCasillaMunicipio) {
		this.coCasillaMunicipio = coCasillaMunicipio;
	}


	/**
	 * @return the coMunicipio
	 */
	public String getCoMunicipio() {
		return coMunicipio;
	}


	/**
	 * @param coMunicipio the coMunicipio to set
	 */
	public void setCoMunicipio(String coMunicipio) {
		this.coMunicipio = coMunicipio;
	}


	/**
	 * @return the coProvincia
	 */
	public String getCoProvincia() {
		return coProvincia;
	}


	/**
	 * @param coProvincia the coProvincia to set
	 */
	public void setCoProvincia(String coProvincia) {
		this.coProvincia = coProvincia;
	}


	/**
	 * @return the coOperacion
	 */
	public String getCoOperacion() {
		return coOperacion;
	}


	/**
	 * @param coOperacion the coOperacion to set
	 */
	public void setCoOperacion(String coOperacion) {
		this.coOperacion = coOperacion;
	}

}
