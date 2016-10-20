package es.dipucadiz.etir.comun.vo;

import java.io.Serializable;

public class ParteCajaMesVO implements Serializable{
	private static final long serialVersionUID = 4996599916310604137L;
	private String coParteCajaMes;
	private String coProvincia;
	private String coMunicipio;
	private String coOtr;
	private String coCargo;
	private String cargo;
	private String modelo;
	private String ejerper;
	private String imPendAnt;
	private String imCarPeriodo;
	private String imIngresos;
	private String imBajas;
	private String imPagCom;
	private String imPagOrg;
	private String impAnulIng;
	private String imAnulBaj;
	private String imAnulPagCom;
	private String imAnulPagOrg;
	private String imPendiente;
	private String imPasEjec;
	private String imRepuesto;
	private String imBonificado;
	//Tipo Blanco
	private String imPpal;
	private String imRecApr;
	private String imInDem;
	private String imCostas;
	//Tipo C
	private String imPpalC;
	private String imRecAprC;
	private String imInDemC;
	private String imCostasC;
	//Tipo O
	private String imPpalO;
	private String imRecAprO;
	private String imInDemO;
	private String imCostasO;
	
	private String imTotal;
	private String clase;
	private String tipo;
	private String tipCobrDescr;
	
	private String hayCobrAnul; 
	

	public ParteCajaMesVO (ParteCajaMesVO pCajaMesVO){
		
		this.coParteCajaMes = pCajaMesVO.getCoParteCajaMes();
		this.coProvincia= pCajaMesVO.getCoProvincia() ;
		this.coMunicipio = pCajaMesVO.getCoMunicipio();
		this.coOtr = pCajaMesVO.getCoOtr();
		this.coCargo = pCajaMesVO.getCoCargo();
		
		this.clase = pCajaMesVO.getClase();
		this.tipo = pCajaMesVO.getTipo();
		this.tipCobrDescr = pCajaMesVO.getTipCobrDescr();
		 
	}
	
	public ParteCajaMesVO() {
		// TODO Auto-generated constructor stub
	}

	public String getCoParteCajaMes() {
		return coParteCajaMes;
	}
	public void setCoParteCajaMes(String coParteCajaMes) {
		this.coParteCajaMes = coParteCajaMes;
	}
	
	public String getCoProvincia() {
		return coProvincia;
	}
	public void setCoProvincia(String coProvincia) {
		this.coProvincia = coProvincia;
	}
	public String getCoMunicipio() {
		return coMunicipio;
	}
	public void setCoMunicipio(String coMunicipio) {
		this.coMunicipio = coMunicipio;
	}
	public String getCoOtr() {
		return coOtr;
	}
	public void setCoOtr(String coOtr) {
		this.coOtr = coOtr;
	}
	public String getCoCargo() {
		return coCargo;
	}
	public void setCoCargo(String coCargo) {
		this.coCargo = coCargo;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargoDTO(String cargo) {
		this.cargo = cargo;
	}
	public String getImCarPeriodo() {
		return imCarPeriodo;
	}
	public void setImCarPeriodo(String imCarPeriodo) {
		this.imCarPeriodo = imCarPeriodo;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getEjerper() {
		return ejerper;
	}
	public void setEjerper(String ejerper) {
		this.ejerper = ejerper;
	}
	public String getImPendAnt() {
		return imPendAnt;
	}
	public void setImPendAnt(String imPendAnt) {
		this.imPendAnt = imPendAnt;
	}
	public String getImIngresos() {
		return imIngresos;
	}
	public void setImIngresos(String imIngresos) {
		this.imIngresos = imIngresos;
	}
	public String getImBajas() {
		return imBajas;
	}
	public void setImBajas(String imBajas) {
		this.imBajas = imBajas;
	}
	public String getImPagCom() {
		return imPagCom;
	}
	public void setImPagCom(String imPagCom) {
		this.imPagCom = imPagCom;
	}
	public String getImPagOrg() {
		return imPagOrg;
	}
	public void setImPagOrg(String imPagOrg) {
		this.imPagOrg = imPagOrg;
	}
	public String getImpAnulIng() {
		return impAnulIng;
	}
	public void setImpAnulIng(String impAnulIng) {
		this.impAnulIng = impAnulIng;
	}
	public String getImAnulBaj() {
		return imAnulBaj;
	}
	public void setImAnulBaj(String imAnulBaj) {
		this.imAnulBaj = imAnulBaj;
	}
	public String getImAnulPagCom() {
		return imAnulPagCom;
	}
	public void setImAnulPagCom(String imAnulPagCom) {
		this.imAnulPagCom = imAnulPagCom;
	}
	public String getImAnulPagOrg() {
		return imAnulPagOrg;
	}
	public void setImAnulPagOrg(String imAnulPagOrg) {
		this.imAnulPagOrg = imAnulPagOrg;
	}
	public String getImPendiente() {
		return imPendiente;
	}
	public void setImPendiente(String imPendiente) {
		this.imPendiente = imPendiente;
	}
	public String getImPasEjec() {
		return imPasEjec;
	}
	public void setImPasEjec(String imPasEjec) {
		this.imPasEjec = imPasEjec;
	}
	public String getImRepuesto() {
		return imRepuesto;
	}
	public void setImRepuesto(String imRepuesto) {
		this.imRepuesto = imRepuesto;
	}
	public String getImBonificado() {
		return imBonificado;
	}
	public void setImBonificado(String imBonificado) {
		this.imBonificado = imBonificado;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public String getImPpal() {
		return imPpal;
	}
	public void setImPpal(String imPpal) {
		this.imPpal = imPpal;
	}
	public String getImRecApr() {
		return imRecApr;
	}
	public void setImRecApr(String imRecApr) {
		this.imRecApr = imRecApr;
	}
	public String getImInDem() {
		return imInDem;
	}
	public void setImInDem(String imInDem) {
		this.imInDem = imInDem;
	}
	public String getImCostas() {
		return imCostas;
	}
	public void setImCostas(String imCostas) {
		this.imCostas = imCostas;
	}
	public String getImPpalC() {
		return imPpalC;
	}
	public void setImPpalC(String imPpalC) {
		this.imPpalC = imPpalC;
	}
	public String getImRecAprC() {
		return imRecAprC;
	}
	public void setImRecAprC(String imRecAprC) {
		this.imRecAprC = imRecAprC;
	}
	public String getImInDemC() {
		return imInDemC;
	}
	public void setImInDemC(String imInDemC) {
		this.imInDemC = imInDemC;
	}
	public String getImCostasC() {
		return imCostasC;
	}
	public void setImCostasC(String imCostasC) {
		this.imCostasC = imCostasC;
	}
	public String getImPpalO() {
		return imPpalO;
	}
	public void setImPpalO(String imPpalO) {
		this.imPpalO = imPpalO;
	}
	public String getImRecAprO() {
		return imRecAprO;
	}
	public void setImRecAprO(String imRecAprO) {
		this.imRecAprO = imRecAprO;
	}
	public String getImInDemO() {
		return imInDemO;
	}
	public void setImInDemO(String imInDemO) {
		this.imInDemO = imInDemO;
	}
	public String getImCostasO() {
		return imCostasO;
	}
	public void setImCostasO(String imCostasO) {
		this.imCostasO = imCostasO;
	}
	public String getImTotal() {
		return imTotal;
	}
	public void setImTotal(String imTotal) {
		this.imTotal = imTotal;
	}
	public String getClase() {
		return clase;
	}
	public void setClase(String clase) {
		this.clase = clase;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getTipCobrDescr() {
		return tipCobrDescr;
	}
	public void setTipCobrDescr(String tipCobrDescr) {
		this.tipCobrDescr = tipCobrDescr;
	}
	public String getHayCobrAnul() {
		return hayCobrAnul;
	}
	public void setHayCobrAnul(String hayCobrAnul) {
		this.hayCobrAnul = hayCobrAnul;
	}
	
}