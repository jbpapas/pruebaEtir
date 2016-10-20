package es.dipucadiz.etir.comun.vo;

import java.math.BigDecimal;

public class DeudaVO {
	private BigDecimal imPrincipalPendiente;
	private BigDecimal imRecargoPendiente;
	private BigDecimal imDemoraPendiente;
	private BigDecimal imCostasPendiente;
	private BigDecimal imTotalPendiente;
	private String observaciones;
	
	public BigDecimal getImPrincipalPendiente() {
		return imPrincipalPendiente;
	}
	public void setImPrincipalPendiente(BigDecimal imPrincipalPendiente) {
		this.imPrincipalPendiente = imPrincipalPendiente;
	}
	public BigDecimal getImRecargoPendiente() {
		return imRecargoPendiente;
	}
	public void setImRecargoPendiente(BigDecimal imRecargoPendiente) {
		this.imRecargoPendiente = imRecargoPendiente;
	}
	public BigDecimal getImDemoraPendiente() {
		return imDemoraPendiente;
	}
	public void setImDemoraPendiente(BigDecimal imDemoraPendiente) {
		this.imDemoraPendiente = imDemoraPendiente;
	}
	public BigDecimal getImCostasPendiente() {
		return imCostasPendiente;
	}
	public void setImCostasPendiente(BigDecimal imCostasPendiente) {
		this.imCostasPendiente = imCostasPendiente;
	}
	public BigDecimal getImTotalPendiente() {
		return imTotalPendiente;
	}
	public void setImTotalPendiente(BigDecimal imTotalPendiente) {
		this.imTotalPendiente = imTotalPendiente;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
}