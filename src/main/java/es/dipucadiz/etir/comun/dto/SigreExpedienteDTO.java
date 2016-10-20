package es.dipucadiz.etir.comun.dto;

public class SigreExpedienteDTO implements java.io.Serializable {
	private static final long serialVersionUID = 7514786528561570353L;
	
	private SigreExpedienteDTOId id;
	private String identificador;
	private String razonSocial;
	private ClienteDTO clienteDTO;

	public SigreExpedienteDTO() {
	}

	public SigreExpedienteDTOId getId() {
		return id;
	}

	public void setId(SigreExpedienteDTOId id) {
		this.id = id;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public ClienteDTO getClienteDTO() {
		return clienteDTO;
	}

	public void setClienteDTO(ClienteDTO clienteDTO) {
		this.clienteDTO = clienteDTO;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
