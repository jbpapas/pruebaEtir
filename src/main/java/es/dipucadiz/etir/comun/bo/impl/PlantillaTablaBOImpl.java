package es.dipucadiz.etir.comun.bo.impl;

import java.util.Collections;
import java.util.List;

import es.dipucadiz.etir.comun.bo.PlantillaTablaBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.PlantillaTablaDTO;
import es.dipucadiz.etir.comun.dto.PlantillaTablaDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;


public class PlantillaTablaBOImpl extends
        AbstractGenericBOImpl<PlantillaTablaDTO, PlantillaTablaDTOId> implements
        PlantillaTablaBO {

	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 6879318165367306079L;

	/**
	 * Atributo que almacena el DAO asociado a modelo.
	 */
	private DAOBase<PlantillaTablaDTO, PlantillaTablaDTOId> plantillaTablaDao;


	/**
	 * {@inheritDoc}
	 */
	public List<PlantillaTablaDTO> findByPlantilla(final Long coPlantilla)
	        throws GadirServiceException {
		List<PlantillaTablaDTO> lista = null;
		if (coPlantilla == null) {
			lista = Collections.emptyList();
		} else {
			try {
				lista = this.findFiltered("id.coPlantilla", coPlantilla);
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Ocurrio un error al obtener las tablas asociadas a la plantilla "
				                + coPlantilla, e);
			}
		}
		return lista;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DAOBase<PlantillaTablaDTO, PlantillaTablaDTOId> getDao() {
		return this.getPlantillaTablaDao();

	}

	// GETTERS AND SETTERS

	public DAOBase<PlantillaTablaDTO, PlantillaTablaDTOId> getPlantillaTablaDao() {
		return plantillaTablaDao;
	}

	public void setPlantillaTablaDao(
	        final DAOBase<PlantillaTablaDTO, PlantillaTablaDTOId> plantillaTablaDao) {
		this.plantillaTablaDao = plantillaTablaDao;
	}
	
	public void auditorias(PlantillaTablaDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
