package es.dipucadiz.etir.comun.bo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;

import es.dipucadiz.etir.comun.bo.EjecucionBO;
import es.dipucadiz.etir.comun.bo.PlantillaBO;
import es.dipucadiz.etir.comun.bo.PlantillaImagenBO;
import es.dipucadiz.etir.comun.bo.PlantillaImgCondCampoBO;
import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.QueryName;
import es.dipucadiz.etir.comun.dto.PlantillaEtiquetaCampoDTO;
import es.dipucadiz.etir.comun.dto.PlantillaEtiquetaDTOId;
import es.dipucadiz.etir.comun.dto.PlantillaImagenDTO;
import es.dipucadiz.etir.comun.dto.PlantillaImgCondCampoDTO;
import es.dipucadiz.etir.comun.dto.PlantillaImgCondicionalDTO;
import es.dipucadiz.etir.comun.dto.PlantillaImgCondicionalDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class PlantillaImagenBOImpl extends
        AbstractGenericBOImpl<PlantillaImagenDTO, Long> implements
        PlantillaImagenBO {

	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = -2340497702134956775L;

	/**
	 * Atributo que almacena el dao asociadoa {@link PlantillaImagenDTO}.
	 */
	private DAOBase<PlantillaImagenDTO, Long> dao;
	
	private PlantillaBO plantillaBO;

	/**
	 * Atributo que almacena el DAO asociado a
	 * {@link PlantillaImgCondicionalDTO}.
	 */
	private DAOBase<PlantillaImgCondicionalDTO, PlantillaImgCondicionalDTOId> plantillaImgCondDao;

	

	/**
	 * {@inheritDoc}
	 */
	public PlantillaImagenDTO findByIdLazy(final Long coPlantillaImagen)
	        throws GadirServiceException {
		if (Utilidades.isNull(coPlantillaImagen)) {
			if (log.isDebugEnabled()) {
				log
				        .debug("El id del elemento a recuperar llegó null, no se recupera nada del sistema");
			}
			return null;
		} else {
			try {
				final Map<String, Object> params = new HashMap<String, Object>();
				params.put("codPlantillaImagen", coPlantillaImagen);
				return (PlantillaImagenDTO) this.getPlantillaImagenDao()
				        .findByNamedQueryUniqueResult(
				                QueryName.PLANTILLA_IMAGEN_FIND_BY_ID_LAZY,
				                params);
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Ocurrio un error al obtener la imagene filtrando por su identificador.",
				        e);
			}
		}
	}

	
	/**
	 * {@inheritDoc}
	 */
	public void deleteImagenWithCampos(final long coPlantillaImagen)
	        throws GadirServiceException {
		if (Utilidades.isEmpty(coPlantillaImagen)) {
			if (log.isDebugEnabled()) {
				log
				        .debug("El identificador recibido es null, no se puede recuperar el elemento a borrar");
			}
		} else {
			try {
				final List<PlantillaImgCondicionalDTO> listadoCampos = this
				        .getPlantillaImgCondDao()
				        .findFiltered(
				                new String[] { "id.coPlantillaImagen" },
				                new Object[] { coPlantillaImagen });
				
				if (listadoCampos != null && !listadoCampos.isEmpty()) {
						
					PlantillaImgCondCampoBO plantillaImgCondCampoBO = (PlantillaImgCondCampoBO) GadirConfig.getBean("plantillaImgCondCampoBO");
					
					for (int i = 0; i < listadoCampos.size(); i++) {
						
						final List<PlantillaImgCondCampoDTO> listadoCondCampos =  plantillaImgCondCampoBO.
							findFiltered(
									new String[] { "id.coPlantillaImagen", "id.ordenImgCondicional" },
					                new Object[] { coPlantillaImagen, listadoCampos.get(i).getId().getOrdenImgCondicional() });

						for(int j = 0; j < listadoCondCampos.size(); j++){
							//Borramos uno a uno los campos de las imágenes condicionales
							plantillaImgCondCampoBO.delete(listadoCondCampos.get(j).getId());
						}
						
						//Una vez borrados los campos, borramos la imagen condicional
						this.getPlantillaImgCondDao().delete(
						        listadoCampos.get(i).getId());
					}
				}
				
				//Por último, borramos la imagen
				this.getPlantillaImagenDao().delete(coPlantillaImagen);
				
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Error al eliminar la imagen con los campos asociados.",
				        e);
			}
		}
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public DAOBase<PlantillaImagenDTO, Long> getDao() {
		return this.dao;
	}

	/**
	 * Método que establece el atributo dao.
	 * 
	 * @param dao
	 *            El dao.
	 */
	public void setDao(final DAOBase<PlantillaImagenDTO, Long> dao) {
		this.dao = dao;
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

	// GETTERS AND SETTERS

	/**
	 * Método que devuelve el atributo plantillaImagenDao.
	 * 
	 * @return plantillaImagenDao.
	 */
	public DAOBase<PlantillaImagenDTO, Long> getPlantillaImagenDao() {
		return dao;
	}

	/**
	 * Método que establece el atributo plantillaImagenDao.
	 * 
	 * @param plantillaImagenDao
	 *            El plantillaImagenDao.
	 */
	public void setPlantillaImagenDao(
	        final DAOBase<PlantillaImagenDTO, Long> plantillaImagenDao) {
		this.dao = plantillaImagenDao;
	}
	
	public void auditorias(PlantillaImagenDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
		transientObject.setPlantillaDTO(plantillaBO.findById(transientObject.getPlantillaDTO().getCoPlantilla()));
		plantillaBO.auditorias(transientObject.getPlantillaDTO(), false);
	}

	/**
	 * @return the plantillaBO
	 */
	public PlantillaBO getPlantillaBO() {
		return plantillaBO;
	}

	/**
	 * @param plantillaBO the plantillaBO to set
	 */
	public void setPlantillaBO(PlantillaBO plantillaBO) {
		this.plantillaBO = plantillaBO;
	}

	public List<PlantillaImagenDTO> findByPlantillaLazy(Long coPlantilla) throws GadirServiceException {
		List<PlantillaImagenDTO> plantillaImagenDTOs = findFiltered("plantillaDTO.coPlantilla", coPlantilla);
		for (PlantillaImagenDTO plantillaImagenDTO : plantillaImagenDTOs) {
			Hibernate.initialize(plantillaImagenDTO.getUnidadAdministrativaDTO());
		}
		return plantillaImagenDTOs;
	}

	public PlantillaImagenDTO findByRowidLazy(String imagenRowid) throws GadirServiceException {
		PlantillaImagenDTO result = findByRowid(imagenRowid);
		Hibernate.initialize(result.getUnidadAdministrativaDTO());
		return result;
	}
	
}
