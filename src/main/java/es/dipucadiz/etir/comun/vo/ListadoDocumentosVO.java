package es.dipucadiz.etir.comun.vo;


public class ListadoDocumentosVO implements java.io.Serializable {

	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
    private static final long serialVersionUID = -4296106805035584786L;
    
    /**
     * Atributos 
     */
    private String co_documento;
	
    private String co_modelo;
    
    private String co_version;
    
    private String municipio;
    
    private String estado;
    
    private String ref_obj_tributario;
    
    private String NIF;
    
    private String nombre;
    
	

	public ListadoDocumentosVO() {
	}



	public String getCo_documento() {
		return co_documento;
	}



	public void setCo_documento(String co_documento) {
		this.co_documento = co_documento;
	}



	public String getCo_modelo() {
		return co_modelo;
	}



	public void setCo_modelo(String co_modelo) {
		this.co_modelo = co_modelo;
	}



	public String getCo_version() {
		return co_version;
	}



	public void setCo_version(String co_version) {
		this.co_version = co_version;
	}



	public String getMunicipio() {
		return municipio;
	}



	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}



	public String getEstado() {
		return estado;
	}



	public void setEstado(String estado) {
		this.estado = estado;
	}



	public String getRef_obj_tributario() {
		return ref_obj_tributario;
	}



	public void setRef_obj_tributario(String ref_obj_tributario) {
		this.ref_obj_tributario = ref_obj_tributario;
	}



	public String getNIF() {
		return NIF;
	}



	public void setNIF(String nif) {
		NIF = nif;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



}
