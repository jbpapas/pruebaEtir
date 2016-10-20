package es.dipucadiz.etir.comun.bo.impl;

import java.util.List;

import org.hibernate.Hibernate;

import es.dipucadiz.etir.comun.bo.PppBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.PppDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class PppBOImpl extends
        AbstractGenericBOImpl<PppDTO, Long> implements
        PppBO {


	
	private DAOBase<PppDTO, Long> dao;

	public void setDao(DAOBase<PppDTO, Long> dao) {
		this.dao = dao;
	}
	
	public DAOBase<PppDTO, Long> getDao() {
		return dao;
	}
	
	public void auditorias(PppDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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

	public PppDTO findByAnoNuPppInitialized(Short anyo, Integer nuPpp) throws GadirServiceException {
		PppDTO pppDTO = null;
		String[] propNames = {"ano", "nuPpp"};
		Object[] filters = {anyo, nuPpp};
		List<PppDTO> pppDTOs = findFiltered(propNames, filters);
		if (pppDTOs.size() == 1) {
			pppDTO = pppDTOs.get(0);
			Hibernate.initialize(pppDTO.getClienteDTOByCoCliente());
			Hibernate.initialize(pppDTO.getClienteDTOByCoClienteConyuge());
			Hibernate.initialize(pppDTO.getDomiciliacionDTO());
		}
		return pppDTO;
	}

}
