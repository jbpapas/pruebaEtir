package es.dipucadiz.etir.comun.bo.impl;

import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;

import es.dipucadiz.etir.comun.bo.ProcesoAccionBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.DAOConstant;
import es.dipucadiz.etir.comun.dto.PlantillaDTO;
import es.dipucadiz.etir.comun.dto.ProcesoAccionDTO;
import es.dipucadiz.etir.comun.dto.ProcesoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;



public class ProcesoAccionBOImpl extends AbstractGenericBOImpl<ProcesoAccionDTO, Long>  implements ProcesoAccionBO {

//	private static final Log LOG = LogFactory.getLog(ProcesoAccionBOImpl.class);

	private DAOBase<ProcesoAccionDTO, Long> dao;


	public DAOBase<ProcesoAccionDTO, Long> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<ProcesoAccionDTO, Long> dao) {
		this.dao = dao;
	}

	public List<ProcesoAccionDTO> findByProcesoInitialized(ProcesoDTO procesoDTO) throws GadirServiceException {
		List<ProcesoAccionDTO> procesoAccionDTOs = findFiltered("procesoDTO", procesoDTO, "coProcesoAccion", DAOConstant.ASC_ORDER);
		for (ProcesoAccionDTO procesoAccionDTO : procesoAccionDTOs) {
			Hibernate.initialize(procesoAccionDTO.getPlantillaDTOs());
			Set<PlantillaDTO> plantillaDTOs = procesoAccionDTO.getPlantillaDTOs();
			for (PlantillaDTO plantillaDTO : plantillaDTOs) {
				Hibernate.initialize(plantillaDTO.getPlantillaOdtDTO());
			}
		}
		return procesoAccionDTOs;
	}

	public ProcesoAccionDTO findByProcesoAccionInitialized(String coProcesoActual, String accion) throws GadirServiceException {
		ProcesoAccionDTO result = null;
		String[] propNames = new String[2];
		Object[] filters = new Object[2];
		propNames[0] = "procesoDTO.coProceso";
		filters[0] = coProcesoActual;
		propNames[1] = "accion";
		filters[1] = accion;
		List<ProcesoAccionDTO> procesoAccionDTOs = dao.findFiltered(propNames, filters, 0, 1);
		
		if (!procesoAccionDTOs.isEmpty()) {
			Hibernate.initialize(procesoAccionDTOs.get(0).getPlantillaDTOs());
			for (PlantillaDTO plantillaDTO : procesoAccionDTOs.get(0).getPlantillaDTOs()) {
				Hibernate.initialize(plantillaDTO.getPlantillaOdtDTO());
			}
			result = procesoAccionDTOs.get(0);
		}
		
		return result;
	}

	public void auditorias(ProcesoAccionDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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








