package es.dipucadiz.etir.comun.bo.impl;

import java.util.Collections;
import java.util.List;

import es.dipucadiz.etir.comun.bo.PlantillaImgCondCampoBO;
import es.dipucadiz.etir.comun.bo.PlantillaImgCondicionalBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.DAOConstant;
import es.dipucadiz.etir.comun.dto.PlantillaImgCondCampoDTO;
import es.dipucadiz.etir.comun.dto.PlantillaImgCondCampoDTOId;
import es.dipucadiz.etir.comun.dto.PlantillaImgCondicionalDTO;
import es.dipucadiz.etir.comun.dto.PlantillaImgCondicionalDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

/**
 * Clase responsable de implementar los métodos definidos para la lógica de la
 * clase {@link PlantillaImgCondCampoDTO}.
 * 
 * @version 1.0 15/02/2010
 * @author SDS[RMGARRIDO]
 */
public class PlantillaImgCondCampoBOImpl
        extends
        AbstractGenericBOImpl<PlantillaImgCondCampoDTO, PlantillaImgCondCampoDTOId>
        implements PlantillaImgCondCampoBO {

	/**
	 * Atributo que almacena el DAO asociado a {@link PlantillaImgCondCampoDTO}.
	 */
	private DAOBase<PlantillaImgCondCampoDTO, PlantillaImgCondCampoDTOId> plantillaImgCondCampoDao;
	
	PlantillaImgCondicionalBO plantillaImgCondicionalBO;

	/**
	 *{@inheritDoc}
	 */
	public List<PlantillaImgCondCampoDTO> findCampoByCondicionImagen(
	        final Long coPlantillaImagen, final Short ordenImgCondicional)
	        throws GadirServiceException {
		if (Utilidades.isNull(coPlantillaImagen) || ordenImgCondicional == 0) {
			if (log.isDebugEnabled()) {
				log
				        .debug("Los datos de filtrado llegaron null, no se recupera nada del sistema");
			}
			return Collections.emptyList();
		} else {
			try {
				String[] propNames = { "id.coPlantillaImagen",
				        "id.ordenImgCondicional" };
				Object[] filters = { coPlantillaImagen, ordenImgCondicional };
				String[] orderProperties = { "id.orden", "id.boOrigen" };
				int[] orderTypes = { DAOConstant.ASC_ORDER,
				        DAOConstant.ASC_ORDER };
				return this.getPlantillaImgCondCampoDao().findFiltered(
				        propNames, filters, orderProperties, orderTypes);
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Ocurrio un error obteniendo los campos condicionales de la imagen: "
				                + coPlantillaImagen, e);
			}
		}
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DAOBase<PlantillaImgCondCampoDTO, PlantillaImgCondCampoDTOId> getDao() {
		return this.getPlantillaImgCondCampoDao();
	}
	
	public void auditorias(PlantillaImgCondCampoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
		PlantillaImgCondicionalDTO img = plantillaImgCondicionalBO.findById(new PlantillaImgCondicionalDTOId(
				transientObject.getId().getCoPlantillaImagen(), transientObject.getId().getOrdenImgCondicional()));
		
		plantillaImgCondicionalBO.auditorias(img, false);
	}

	/**
	 * @return the plantillaImgCondicionalBO
	 */
	public PlantillaImgCondicionalBO getPlantillaImgCondicionalBO() {
		return plantillaImgCondicionalBO;
	}

	/**
	 * @param plantillaImgCondicionalBO the plantillaImgCondicionalBO to set
	 */
	public void setPlantillaImgCondicionalBO(
			PlantillaImgCondicionalBO plantillaImgCondicionalBO) {
		this.plantillaImgCondicionalBO = plantillaImgCondicionalBO;
	}
	

}
