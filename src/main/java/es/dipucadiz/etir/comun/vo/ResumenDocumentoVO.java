package es.dipucadiz.etir.comun.vo;

import java.io.Serializable;


public class ResumenDocumentoVO implements Serializable {
	private static final long serialVersionUID = 6595337985306601283L;

	private String coProvincia;
	private String coMunicipio;
    private String coConcepto;
   
    private String coModelo;
    private String coVersion;
    private String ejercicio;
    private String coPeriodo;
    private String descMunicipio;
    private String descConcepto;
    private String descModelo;
    private String descPeriodo;
    private String cantidadN;
    private String cantidadR;
    private String cantidadI;
    private String cantidadF;
    private String cantidadC;
    private String cantidadL;
    private String cantidadB;
    private boolean notEmpty;
    
    public ResumenDocumentoVO() {
    	notEmpty = false;
    }
    public ResumenDocumentoVO(String coProvincia, String coMunicipio, String coConcepto, String coModelo, String coVersion, String ejercicio, String coPeriodo) {
    	this.coProvincia = coProvincia;
    	this.coMunicipio = coMunicipio;
    	this.coConcepto = coConcepto;
    	this.coModelo = coModelo;
    	this.coVersion = coVersion;
    	this.ejercicio = ejercicio;
    	this.coPeriodo = coPeriodo;
    	notEmpty = false;
    }
	public String getCantidadN() {
		return cantidadN;
	}
	public void setCantidadN(String cantidadN) {
		notEmpty = true;
		this.cantidadN = cantidadN;
	}
	public String getCantidadR() {
		return cantidadR;
	}
	public void setCantidadR(String cantidadR) {
		notEmpty = true;
		this.cantidadR = cantidadR;
	}
	public String getCantidadI() {
		return cantidadI;
	}
	public void setCantidadI(String cantidadI) {
		notEmpty = true;
		this.cantidadI = cantidadI;
	}
	public String getCantidadF() {
		return cantidadF;
	}
	public void setCantidadF(String cantidadF) {
		notEmpty = true;
		this.cantidadF = cantidadF;
	}
	public String getCantidadC() {
		return cantidadC;
	}
	public void setCantidadC(String cantidadC) {
		notEmpty = true;
		this.cantidadC = cantidadC;
	}
	public String getCantidadL() {
		return cantidadL;
	}
	public void setCantidadL(String cantidadL) {
		notEmpty = true;
		this.cantidadL = cantidadL;
	}
	public String getCantidadB() {
		return cantidadB;
	}
	public void setCantidadB(String cantidadB) {
		notEmpty = true;
		this.cantidadB = cantidadB;
	}
	public boolean isNotEmpty() {
		return notEmpty;
	}
	public String getCoMunicipio() {
		return coMunicipio;
	}
	public void setCoMunicipio(String coMunicipio) {
		this.coMunicipio = coMunicipio;
	}
	public String getCoConcepto() {
		return coConcepto;
	}
	public void setCoConcepto(String coConcepto) {
		this.coConcepto = coConcepto;
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
	public String getEjercicio() {
		return ejercicio;
	}
	public void setEjercicio(String ejercicio) {
		this.ejercicio = ejercicio;
	}
	public String getCoPeriodo() {
		return coPeriodo;
	}
	public void setCoPeriodo(String coPeriodo) {
		this.coPeriodo = coPeriodo;
	}
	public String getDescMunicipio() {
		return descMunicipio;
	}
	public void setDescMunicipio(String descMunicipio) {
		this.descMunicipio = descMunicipio;
	}
	public String getDescConcepto() {
		return descConcepto;
	}
	public void setDescConcepto(String descConcepto) {
		this.descConcepto = descConcepto;
	}
	public String getDescModelo() {
		return descModelo;
	}
	public void setDescModelo(String descModelo) {
		this.descModelo = descModelo;
	}
	public String getDescPeriodo() {
		return descPeriodo;
	}
	public void setDescPeriodo(String descPeriodo) {
		this.descPeriodo = descPeriodo;
	}
	public void setCoProvincia(String coProvincia) {
		this.coProvincia = coProvincia;
	}
	public String getCoProvincia() {
		return coProvincia;
	}
	public boolean isIgual(String coProvincia, String coMunicipio, String coConcepto, String coModelo, String coVersion, String ejercicio, String coPeriodo) {
		return
			this.coProvincia.equals(coProvincia) &&
			this.coMunicipio.equals(coMunicipio) &&
			this.coConcepto.equals(coConcepto) &&
			this.coModelo.equals(coModelo) &&
			this.coVersion.equals(coVersion) &&
			this.ejercicio.equals(ejercicio) &&
			this.coPeriodo.equals(coPeriodo);
	}
	
}
