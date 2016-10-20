package es.dipucadiz.etir.comun.vo;

import java.io.Serializable;

public class AyudaCasillaVO implements Serializable {
	
	private String coModelo;
	private String coVersion;
	private String coDocumento;
	/*private String coConcepto;
	private String coMunicipio;
	private String coProvincia;
	private Long coCliente;*/
	
	protected Long coValidacion;
	
	/*protected String actionVolver;*/
	
    private String[] casillas;
    
    
    
    public AyudaCasillaVO(String coModelo, String coVersion, String coDocumento, Long coValidacion) {
		
    	super();
		this.coModelo = coModelo;
		this.coVersion = coVersion;
		this.coDocumento = coDocumento;
		this.coValidacion = coValidacion;
		
		casillas = new String[300];
    	casillas[0]="NO USAR";
	}

	public AyudaCasillaVO(){
		casillas = new String[400];
    	casillas[0]="NO USAR";
    }
    
    public void setCasilla (int numero, String valor){
    	try{
    		casillas[numero] = valor;
    	}catch(Exception e){
    		
    	}
    }
    
    public String getCasilla (int numero){
    	String casilla=null;
    	try{
    		casilla = casillas[numero];
    	}catch(Exception e){
    		
    	}
    	return casilla;
    }

	public String getCoModelo() {
		return coModelo;
	}

	public void setCoModelo(String coModelo) {
		this.coModelo = coModelo;
	}

	public String getCoVersion() {
		return coVersion;
	}

	public void setCoVersion(String coVersion) {
		this.coVersion = coVersion;
	}

	public String getCoDocumento() {
		return coDocumento;
	}

	public void setCoDocumento(String coDocumento) {
		this.coDocumento = coDocumento;
	}

	public Long getCoValidacion() {
		return coValidacion;
	}

	public void setCoValidacion(Long coValidacion) {
		this.coValidacion = coValidacion;
	}

    
}
