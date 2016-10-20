package es.dipucadiz.etir.comun.vo;

public class CandidatoVO {
	private Long coCliente;
	private String identificador;
	private String nombre;
	private int coMensaje;
	private String mensaje;
	private Long coUnidadUrbana;
	private String direccion;
	public Long getCoCliente() {
		return coCliente;
	}
	public void setCoCliente(Long coCliente) {
		this.coCliente = coCliente;
	}
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public void setCoMensaje(int coMensaje) {
		this.coMensaje = coMensaje;
	}
	public int getCoMensaje() {
		return coMensaje;
	}
	public void setCoUnidadUrbana(Long coUnidadUrbana) {
		this.coUnidadUrbana = coUnidadUrbana;
	}
	public Long getCoUnidadUrbana() {
		return coUnidadUrbana;
	}
}
