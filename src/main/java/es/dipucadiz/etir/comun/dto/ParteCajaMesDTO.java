package es.dipucadiz.etir.comun.dto;


import java.math.BigDecimal;
import java.util.Date;

import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class ParteCajaMesDTO implements java.io.Serializable {
	private static final long serialVersionUID = 3985090509087450615L;
	
	private Long coParteCajaMes;
	private EjecucionDTO ejecucionDTO;
	private Short ano;
	private Short mes;
	private MunicipioDTO municipioDTO;
	private OtrDTO otrDTO;
	private EstadoSituacionDTO estadoSituacionDTO;
	private ModeloDTO modeloDTO;
	private Short ejercicio;
	private String periodo;
	private BigDecimal imPrincipal;
	private BigDecimal imRecApremio;
	private BigDecimal imIntereses;
	private BigDecimal imCostas;
	private Date fhActualizacion;
	private String coUsuarioActualizacion;
	private CargoDTO cargoDTO;
	private String rowid;
	private String clase;
	private String tipo;

	public ParteCajaMesDTO() {
	}

	public ParteCajaMesDTO(Long coParteCajaMes) {
		this.coParteCajaMes = coParteCajaMes;
	}

	public ParteCajaMesDTO(Long coParteCajaMes, EjecucionDTO ejecucionDTO, Short ano, Short mes, MunicipioDTO municipioDTO, 
			OtrDTO otrDTO, EstadoSituacionDTO estadoSituacionDTO, ModeloDTO modeloDTO, Short ejercicio, String periodo,
			BigDecimal imPrincipal, BigDecimal imRecApremio, BigDecimal imIntereses, BigDecimal imCostas, 
			Date fhActualizacion, String coUsuarioActualizacion, CargoDTO cargoDTO) {
		this.coParteCajaMes = coParteCajaMes;
		this.ejecucionDTO = ejecucionDTO;
		this.ano = ano;
		this.mes = mes;
		this.municipioDTO = municipioDTO;
		this.otrDTO = otrDTO;
		this.estadoSituacionDTO = estadoSituacionDTO;
		this.modeloDTO = modeloDTO;
		this.ejercicio = ejercicio;
		this.periodo = periodo;
		this.imPrincipal = imPrincipal;
		this.imRecApremio = imRecApremio;
		this.imIntereses = imIntereses;
		this.imCostas = imCostas;
		this.fhActualizacion = fhActualizacion;
		this.coUsuarioActualizacion = coUsuarioActualizacion;
		this.cargoDTO = cargoDTO;
	}
	
	public Long getCoParteCajaMes() {
		return coParteCajaMes;
	}

	public void setCoParteCajaMes(Long coParteCajaMes) {
		this.coParteCajaMes = coParteCajaMes;
	}

	public EjecucionDTO getEjecucionDTO() {
		return ejecucionDTO;
	}

	public void setEjecucionDTO(EjecucionDTO ejecucionDTO) {
		this.ejecucionDTO = ejecucionDTO;
	}

	public Short getAno() {
		return ano;
	}

	public void setAno(Short ano) {
		this.ano = ano;
	}

	public Short getMes() {
		return mes;
	}

	public void setMes(Short mes) {
		this.mes = mes;
	}

	public MunicipioDTO getMunicipioDTO() {
		return municipioDTO;
	}

	public void setMunicipioDTO(MunicipioDTO municipioDTO) {
		this.municipioDTO = municipioDTO;
	}

	public ModeloDTO getModeloDTO() {
		return modeloDTO;
	}

	public void setModeloDTO(ModeloDTO modeloDTO) {
		this.modeloDTO = modeloDTO;
	}

	public Short getEjercicio() {
		return ejercicio;
	}

	public void setEjercicio(Short ejercicio) {
		this.ejercicio = ejercicio;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public BigDecimal getImIntereses() {
		return imIntereses;
	}

	public void setImIntereses(BigDecimal imIntereses) {
		this.imIntereses = imIntereses;
	}

	public CargoDTO getCargoDTO() {
		return cargoDTO;
	}

	public void setCargoDTO(CargoDTO cargoDTO) {
		this.cargoDTO = cargoDTO;
	}

	public OtrDTO getOtrDTO() {
		return this.otrDTO;
	}

	public void setOtrDTO(OtrDTO otrDTO) {
		this.otrDTO = otrDTO;
	}

	public EstadoSituacionDTO getEstadoSituacionDTO() {
		return this.estadoSituacionDTO;
	}

	public void setEstadoSituacionDTO(EstadoSituacionDTO estadoSituacionDTO) {
		this.estadoSituacionDTO = estadoSituacionDTO;
	}

	public BigDecimal getImPrincipal() {
		return this.imPrincipal;
	}

	public void setImPrincipal(BigDecimal imPrincipal) {
		this.imPrincipal = imPrincipal;
	}

	public BigDecimal getImRecApremio() {
		return this.imRecApremio;
	}

	public void setImRecApremio(BigDecimal imRecApremio) {
		this.imRecApremio = imRecApremio;
	}

	public BigDecimal getImCostas() {
		return this.imCostas;
	}

	public void setImCostas(BigDecimal imCostas) {
		this.imCostas = imCostas;
	}

	public Date getFhActualizacion() {
		return this.fhActualizacion;
	}

	public void setFhActualizacion(Date fhActualizacion) {
		this.fhActualizacion = fhActualizacion;
	}

	public String getCoUsuarioActualizacion() {
		return this.coUsuarioActualizacion;
	}

	public void setCoUsuarioActualizacion(String coUsuarioActualizacion) {
		this.coUsuarioActualizacion = coUsuarioActualizacion;
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

	public String getRowid() {
		return rowid;
	}
	
	public void setRowid(String rowid) {
		this.rowid = Utilidades.codificarRowidFormatoSeguro(rowid);
	}

	
}
