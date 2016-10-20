package es.dipucadiz.etir.comun.dto;


import java.math.BigDecimal;
import java.util.Date;

public class HDepositoDTO implements java.io.Serializable {

	private Long id;
	private Long coDeposito;
	private Long coCliente;
	private String coModeloOrigen;
	private String coVersionOrigen;
	private String coDocumentoOrigen;
	private String tipo;
	private BigDecimal importeInicial;
	private BigDecimal importeDisponible;
	private Date fhConstitucion;
	private Date fhFinalizacion;
	private Date fhActualizacion;
	private String coUsuarioActualizacion;
	private String hTipoMovimiento;
	private Integer coDocumentoSegOrigen;
	private Integer nuDocumentoSigre;
	private Boolean boTransferido;
	private String rowid;

	public HDepositoDTO() {
	}

	public HDepositoDTO(Long coDeposito, Long coCliente) {
		this.coDeposito = coDeposito;
		this.coCliente = coCliente;
	}

	public HDepositoDTO(Long coDeposito, Long coCliente,
			String coModeloOrigen, String coVersionOrigen, String coDocumentoOrigen,
			String tipo, BigDecimal importeInicial, BigDecimal importeDisponible,
			Date fhConstitucion, Date fhFinalizacion, Date fhActualizacion,
			String coUsuarioActualizacion, String hTipoMovimiento,
			Integer coDocumentoSegOrigen, Integer nuDocumentoSigre,  Boolean boTransferido) {
		this.coDeposito = coDeposito;
		this.coCliente = coCliente;
		this.coModeloOrigen = coModeloOrigen;
		this.coVersionOrigen = coVersionOrigen;
		this.coDocumentoOrigen = coDocumentoOrigen;
		this.tipo = tipo;
		this.importeInicial = importeInicial;
		this.importeDisponible = importeDisponible;
		this.fhConstitucion = fhConstitucion;
		this.fhFinalizacion = fhFinalizacion;
		this.fhActualizacion = fhActualizacion;
		this.coUsuarioActualizacion = coUsuarioActualizacion;
		this.hTipoMovimiento = hTipoMovimiento;
		this.coDocumentoSegOrigen = coDocumentoSegOrigen;
		this.nuDocumentoSigre = nuDocumentoSigre;
		this.boTransferido = boTransferido;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCoDeposito() {
		return coDeposito;
	}

	public void setCoDeposito(Long coDeposito) {
		this.coDeposito = coDeposito;
	}

	public Long getCoCliente() {
		return coCliente;
	}

	public void setCoCliente(Long coCliente) {
		this.coCliente = coCliente;
	}

	public String getCoModeloOrigen() {
		return coModeloOrigen;
	}

	public void setCoModeloOrigen(String coModeloOrigen) {
		this.coModeloOrigen = coModeloOrigen;
	}

	public String getCoVersionOrigen() {
		return coVersionOrigen;
	}

	public void setCoVersionOrigen(String coVersionOrigen) {
		this.coVersionOrigen = coVersionOrigen;
	}

	public String getCoDocumentoOrigen() {
		return coDocumentoOrigen;
	}

	public void setCoDocumentoOrigen(String coDocumentoOrigen) {
		this.coDocumentoOrigen = coDocumentoOrigen;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getImporteInicial() {
		return importeInicial;
	}

	public void setImporteInicial(BigDecimal importeInicial) {
		this.importeInicial = importeInicial;
	}

	public BigDecimal getImporteDisponible() {
		return importeDisponible;
	}

	public void setImporteDisponible(BigDecimal importeDisponible) {
		this.importeDisponible = importeDisponible;
	}

	public Date getFhConstitucion() {
		return fhConstitucion;
	}

	public void setFhConstitucion(Date fhConstitucion) {
		this.fhConstitucion = fhConstitucion;
	}

	public Date getFhFinalizacion() {
		return fhFinalizacion;
	}

	public void setFhFinalizacion(Date fhFinalizacion) {
		this.fhFinalizacion = fhFinalizacion;
	}

	public Date getFhActualizacion() {
		return fhActualizacion;
	}

	public void setFhActualizacion(Date fhActualizacion) {
		this.fhActualizacion = fhActualizacion;
	}

	public String getCoUsuarioActualizacion() {
		return coUsuarioActualizacion;
	}

	public void setCoUsuarioActualizacion(String coUsuarioActualizacion) {
		this.coUsuarioActualizacion = coUsuarioActualizacion;
	}

	public String gethTipoMovimiento() {
		return hTipoMovimiento;
	}

	public void sethTipoMovimiento(String hTipoMovimiento) {
		this.hTipoMovimiento = hTipoMovimiento;
	}

	public Integer getCoDocumentoSegOrigen() {
		return coDocumentoSegOrigen;
	}

	public void setCoDocumentoSegOrigen(Integer coDocumentoSegOrigen) {
		this.coDocumentoSegOrigen = coDocumentoSegOrigen;
	}

	public Integer getNuDocumentoSigre() {
		return nuDocumentoSigre;
	}

	public void setNuDocumentoSigre(Integer nuDocumentoSigre) {
		this.nuDocumentoSigre = nuDocumentoSigre;
	}

	public Boolean getBoTransferido() {
		return boTransferido;
	}

	public void setBoTransferido(Boolean boTransferido) {
		this.boTransferido = boTransferido;
	}

	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}


}
