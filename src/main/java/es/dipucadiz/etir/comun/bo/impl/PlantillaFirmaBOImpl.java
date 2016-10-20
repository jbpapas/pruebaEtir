package es.dipucadiz.etir.comun.bo.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.PlantillaBO;
import es.dipucadiz.etir.comun.bo.PlantillaFirmaBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.PlantillaFirmaDTO;
import es.dipucadiz.etir.comun.dto.PlantillaFirmaDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class PlantillaFirmaBOImpl extends AbstractGenericBOImpl<PlantillaFirmaDTO, PlantillaFirmaDTOId>
		implements PlantillaFirmaBO {
	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 6879318165367306079L;

	/**
	 * Atributo que almacena el DAO asociado a modelo.
	 */
	private DAOBase<PlantillaFirmaDTO, PlantillaFirmaDTOId> plantillaFirmaDao;
	
	private PlantillaBO plantillaBO;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DAOBase<PlantillaFirmaDTO, PlantillaFirmaDTOId> getDao() {
		return this.getPlantillaFirmaDao();
	}
	

	// GETTERS AND SETTERS

	/**
	 * Método que devuelve el atributo modeloDao.
	 * 
	 * @return modeloDao.
	 */
	public DAOBase<PlantillaFirmaDTO, PlantillaFirmaDTOId> getPlantillaFirmaDao() {
		return plantillaFirmaDao;
	}

	/**
	 * Método que establece el atributo modeloDao.
	 * 
	 * @param modeloDao
	 *            El modeloDao.
	 */
	public void setPlantillaFirmaDao(final DAOBase<PlantillaFirmaDTO, PlantillaFirmaDTOId> modeloDao) {
		this.plantillaFirmaDao = modeloDao;
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

	public void auditorias(PlantillaFirmaDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
		transientObject.setPlantillaDTO(plantillaBO.findById(transientObject.getId().getCoPlantilla()));
		plantillaBO.auditorias(transientObject.getPlantillaDTO(), false);
	}

	public List<PlantillaFirmaDTO> findByPlantillaLazy(Long coPlantilla) throws GadirServiceException {
		List<PlantillaFirmaDTO> plantillaFirmaDTOs = findFiltered("id.coPlantilla", coPlantilla);
		for (PlantillaFirmaDTO plantillaFirmaDTO : plantillaFirmaDTOs) {
			Hibernate.initialize(plantillaFirmaDTO.getUnidadAdministrativaDTO());
		}
		return plantillaFirmaDTOs;
	}

	public PlantillaFirmaDTO findByRowidLazy(String firmaRowid) throws GadirServiceException {
		PlantillaFirmaDTO result = findByRowid(firmaRowid);
		Hibernate.initialize(result.getUnidadAdministrativaDTO());
		return result;
	}

	public String findAdvertencia(PlantillaFirmaDTO plantillaFirmaDTO) throws GadirServiceException {
		String result = "";
		DetachedCriteria criterio = DetachedCriteria.forClass(PlantillaFirmaDTO.class);
		criterio.add(Restrictions.eq("id.coPlantilla", plantillaFirmaDTO.getId().getCoPlantilla()));
		criterio.add(Restrictions.eq("id.coUnidadAdministrativa", plantillaFirmaDTO.getId().getCoUnidadAdministrativa()));
		criterio.add(Restrictions.eq("id.ordenFirma", plantillaFirmaDTO.getId().getOrdenFirma()));
		criterio.add(Restrictions.ge("fxHasta", plantillaFirmaDTO.getId().getFxDesde()));
		criterio.add(Restrictions.le("id.fxDesde", plantillaFirmaDTO.getFxHasta()));
		int solapados = countByCriteria(criterio);
		if (solapados > 1) {
			result = "Existen " + solapados + " firmas solapadas.";
		}
		return result;
	}
}
