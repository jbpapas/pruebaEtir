package es.dipucadiz.etir.comun.bo.impl;

import java.util.List;

import es.dipucadiz.etir.comun.bo.FraccionamientoPlazoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.FraccionamientoPlazoDTO;
import es.dipucadiz.etir.comun.dto.FraccionamientoPlazoDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public class FraccionamientoPlazoBOImpl extends
        AbstractGenericBOImpl<FraccionamientoPlazoDTO, FraccionamientoPlazoDTOId> implements
        FraccionamientoPlazoBO {


	
	private DAOBase<FraccionamientoPlazoDTO, FraccionamientoPlazoDTOId> dao;

	public void setDao(DAOBase<FraccionamientoPlazoDTO, FraccionamientoPlazoDTOId> dao) {
		this.dao = dao;
	}
	
	public DAOBase<FraccionamientoPlazoDTO, FraccionamientoPlazoDTOId> getDao() {
		return dao;
	}
	
	public void auditorias(FraccionamientoPlazoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		//transientObject.setFhActualizacion(Utilidades.getDateActual());
		//transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
	}

	public boolean existePlazo(Long coFraccionamiento, short anyo, byte mes) throws GadirServiceException {
		String[] propNames = {"id.coFraccionamiento", "ano", "mes"};
		Object[] filters = {coFraccionamiento, anyo, (short)mes};
		List<FraccionamientoPlazoDTO> fraccionamientoPlazoDTOs = findFiltered(propNames, filters, 0, 1);
		return fraccionamientoPlazoDTOs.size() > 0;
	}

}
