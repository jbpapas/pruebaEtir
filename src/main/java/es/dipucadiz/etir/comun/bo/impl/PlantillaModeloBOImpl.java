package es.dipucadiz.etir.comun.bo.impl;

import java.util.Collections;
import java.util.List;

import org.hibernate.Hibernate;

import es.dipucadiz.etir.comun.bo.PlantillaModeloBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.PlantillaDTO;
import es.dipucadiz.etir.comun.dto.PlantillaModeloDTO;
import es.dipucadiz.etir.comun.dto.PlantillaModeloDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

@SuppressWarnings("unchecked")
public class PlantillaModeloBOImpl extends
        AbstractGenericBOImpl<PlantillaModeloDTO, PlantillaModeloDTOId>
        implements PlantillaModeloBO {

	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = -8918691223804830817L;

	/**
	 * Atributo que almacena el dao asociadoa {@link PlantillaDTO}.
	 */
	private DAOBase<PlantillaModeloDTO, PlantillaModeloDTOId> plantillaModeloDao;

	@Override
	public DAOBase<PlantillaModeloDTO, PlantillaModeloDTOId> getDao() {
		return this.getPlantillaModeloDao();
	}

	

	/**
	 * {@inheritDoc}
	 */
	public List<PlantillaModeloDTO> findByPlantilla(final Long coPlantilla)
	        throws GadirServiceException {
		List<PlantillaModeloDTO> lista = null;
		if (coPlantilla == null) {
			lista = Collections.emptyList();
		} else {
			try {
				lista = this.getDao().findFiltered("id.coPlantilla",
				        coPlantilla);
				if(lista != null){
					for(PlantillaModeloDTO plant : lista){
						if(plant.getModeloVersionDTO() != null){
							Hibernate.initialize(plant.getModeloVersionDTO());
							if(plant.getModeloVersionDTO().getModeloDTO() != null){
								Hibernate.initialize(plant.getModeloVersionDTO().getModeloDTO());
							}
						}
					}
				}
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Ocurrio un error al obtener las plantillas-modelo asociadas a la plantilla: "
				                + coPlantilla, e);
			}
		}
		Collections.sort(lista);
		return lista;
	}

	// GETTERS AND SETTERS

	/**
	 * @return the plantillaModeloDao
	 */
	public DAOBase<PlantillaModeloDTO, PlantillaModeloDTOId> getPlantillaModeloDao() {
		return plantillaModeloDao;
	}

	/**
	 * @param plantillaModeloDao
	 *            the plantillaModeloDao to set
	 */
	public void setPlantillaModeloDao(
	        final DAOBase<PlantillaModeloDTO, PlantillaModeloDTOId> plantillaModeloDao) {
		this.plantillaModeloDao = plantillaModeloDao;
	}
	
	public void auditorias(PlantillaModeloDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
	}
}
