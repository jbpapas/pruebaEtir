package es.dipucadiz.etir.comun.vo;

public class InformeVO {

	private Long coInforme;
	private String coAcmUsuario;
	private String nombre;
	private String fecha;
	private String hora;
	private String tramite;
	private String proceso;
	private String rutaInforme;
	private Long coEjecucion;
	
	public Long getCoInforme() {
		return coInforme;
	}
	public void setCoInforme(Long coInforme) {
		this.coInforme = coInforme;
	}
	public String getCoAcmUsuario() {
		return coAcmUsuario;
	}
	public void setCoAcmUsuario(String coAcmUsuario) {
		this.coAcmUsuario = coAcmUsuario;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getTramite() {
		return tramite;
	}
	public void setTramite(String tramite) {
		this.tramite = tramite;
	}
	public String getProceso() {
		return proceso;
	}
	public void setProceso(String proceso) {
		this.proceso = proceso;
	}
	public String getRutaInforme() {
		return rutaInforme;
	}
	public void setRutaInforme(String rutaInforme) {
		this.rutaInforme = rutaInforme;
	}
	public Long getCoEjecucion() {
		return coEjecucion;
	}
	public void setCoEjecucion(Long coEjecucion) {
		this.coEjecucion = coEjecucion;
	}
}
