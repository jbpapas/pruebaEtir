package es.dipucadiz.etir.comun.vo;

import java.io.Serializable;

import es.dipucadiz.etir.comun.dto.ModeloVersionDTO;
import es.dipucadiz.etir.comun.dto.PlantillaModeloDTO;

/**
 *
 * 
 * @version 1.0 25/01/2010
 * @author SDS[CGMESA]
 */
public class ModeloVersionVO implements Serializable {

	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = -6129648636782859634L;

	private ModeloVersionDTO modeloVersion;

	private String id;
	
	private String idModelo;
	
	private String idVersion;
	

	
	
	
	public ModeloVersionVO(PlantillaModeloDTO plantillaModelo) {
		this.modeloVersion = plantillaModelo.getModeloVersionDTO();
		
	}

	/**
	 * @return the modeloVersion
	 */
	public ModeloVersionDTO getModeloVersion() {
		return modeloVersion;
	}

	/**
	 * @param modeloVersion the modeloVersion to set
	 */
	public void setModeloVersion(ModeloVersionDTO modeloVersion) {
		this.modeloVersion = modeloVersion;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return modeloVersion.getId().getCoModelo() 
		+ modeloVersion.getId().getCoVersion();
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the idModelo
	 */
	public String getIdModelo() {
		return modeloVersion.getId().getCoModelo();
	}

	/**
	 * @param idModelo the idModelo to set
	 */
	public void setIdModelo(String idModelo) {
		this.idModelo = idModelo;
	}

	/**
	 * @return the idVersion
	 */
	public String getIdVersion() {
		return modeloVersion.getId().getCoVersion();
	}

	/**
	 * @param idVersion the idVersion to set
	 */
	public void setIdVersion(String idVersion) {
		this.idVersion = idVersion;
	}

	

	
	
	

}
