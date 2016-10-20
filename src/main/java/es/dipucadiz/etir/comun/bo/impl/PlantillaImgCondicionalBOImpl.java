package es.dipucadiz.etir.comun.bo.impl;

import java.util.Collections;
import java.util.List;

import es.dipucadiz.etir.comun.bo.PlantillaImagenBO;
import es.dipucadiz.etir.comun.bo.PlantillaImgCondicionalBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.CasillaModeloDTO;
import es.dipucadiz.etir.comun.dto.PlantillaImgCondCampoDTO;
import es.dipucadiz.etir.comun.dto.PlantillaImgCondCampoDTOId;
import es.dipucadiz.etir.comun.dto.PlantillaImgCondicionalDTO;
import es.dipucadiz.etir.comun.dto.PlantillaImgCondicionalDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class PlantillaImgCondicionalBOImpl
        extends
        AbstractGenericBOImpl<PlantillaImgCondicionalDTO, PlantillaImgCondicionalDTOId>
        implements PlantillaImgCondicionalBO {

	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 3065384971565826647L;

	/**
	 * Atributo que almacena el DAO asociado a {@link CasillaModeloDTO}.
	 */
	private DAOBase<PlantillaImgCondicionalDTO, PlantillaImgCondicionalDTOId> plantillaImgCondDao;

	/**
	 * Atributo que almacena el plantillaImgCondCampoDao de la clase.
	 */
	private DAOBase<PlantillaImgCondCampoDTO, PlantillaImgCondCampoDTOId> plantillaImgCondCampoDao;
	
	private PlantillaImagenBO plantillaImagenBO;

	

	/**
	 * {@inheritDoc}
	 */
	public List<PlantillaImgCondicionalDTO> findByImagen(final Long coImagen)
	        throws GadirServiceException {
		List<PlantillaImgCondicionalDTO> lista = null;
		if (coImagen == null) {
			lista = Collections.emptyList();
		} else {
			try {
				lista = this.getDao().findFiltered("id.coPlantillaImagen",
				        coImagen);
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Ocurrio un error obteniendo las imagenes condicionales de la imagen: "
				                + coImagen, e);
			}
		}
		return lista;
	}

	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DAOBase<PlantillaImgCondicionalDTO, PlantillaImgCondicionalDTOId> getDao() {
		return this.getPlantillaImgCondDao();
	}

	/**
	 * Método que devuelve el atributo plantillaImgCondDao.
	 * 
	 * @return plantillaImgCondDao.
	 */
	public DAOBase<PlantillaImgCondicionalDTO, PlantillaImgCondicionalDTOId> getPlantillaImgCondDao() {
		return plantillaImgCondDao;
	}

	/**
	 * Método que establece el atributo plantillaImgCondDao.
	 * 
	 * @param plantillaImgCondDao
	 *            El plantillaImgCondDao.
	 */
	public void setPlantillaImgCondDao(
	        final DAOBase<PlantillaImgCondicionalDTO, PlantillaImgCondicionalDTOId> plantillaImgCondDao) {
		this.plantillaImgCondDao = plantillaImgCondDao;
	}

	/**
	 * Método que devuelve el atributo plantillaImgCondCampoDao.
	 * 
	 * @return plantillaImgCondCampoDao.
	 */
	public DAOBase<PlantillaImgCondCampoDTO, PlantillaImgCondCampoDTOId> getPlantillaImgCondCampoDao() {
		return plantillaImgCondCampoDao;
	}

	/**
	 * Método que establece el atributo plantillaImgCondCampoDao.
	 * 
	 * @param plantillaImgCondCampoDao
	 *            El plantillaImgCondCampoDao.
	 */
	public void setPlantillaImgCondCampoDao(
	        DAOBase<PlantillaImgCondCampoDTO, PlantillaImgCondCampoDTOId> plantillaImgCondCampoDao) {
		this.plantillaImgCondCampoDao = plantillaImgCondCampoDao;
	}
	
	public void auditorias(PlantillaImgCondicionalDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
		transientObject.setPlantillaImagenDTO(plantillaImagenBO.findById(transientObject.getId().getCoPlantillaImagen()));
		plantillaImagenBO.auditorias(transientObject.getPlantillaImagenDTO(), false);
	}

	/**
	 * @return the plantillaImagenBO
	 */
	public PlantillaImagenBO getPlantillaImagenBO() {
		return plantillaImagenBO;
	}

	/**
	 * @param plantillaImagenBO the plantillaImagenBO to set
	 */
	public void setPlantillaImagenBO(PlantillaImagenBO plantillaImagenBO) {
		this.plantillaImagenBO = plantillaImagenBO;
	}
	
	
}
