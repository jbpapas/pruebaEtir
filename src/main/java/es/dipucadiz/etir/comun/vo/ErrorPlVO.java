package es.dipucadiz.etir.comun.vo;

public class ErrorPlVO {
	private String fechaHora;
	private String error;
	
	public ErrorPlVO(String fechaHora, String error) {
		this.fechaHora = fechaHora;
		this.error = error;
	}

	public String getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(String fechaHora) {
		this.fechaHora = fechaHora;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	
}
