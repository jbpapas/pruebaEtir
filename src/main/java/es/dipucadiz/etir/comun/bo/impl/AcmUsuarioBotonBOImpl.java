package es.dipucadiz.etir.comun.bo.impl;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.AcmUsuarioBotonBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.DAOConstant;
import es.dipucadiz.etir.comun.dto.AcmUsuarioBotonDTO;
import es.dipucadiz.etir.comun.dto.AcmUsuarioBotonDTOId;
import es.dipucadiz.etir.comun.dto.AcmUsuarioDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;



public class AcmUsuarioBotonBOImpl extends AbstractGenericBOImpl<AcmUsuarioBotonDTO, AcmUsuarioBotonDTOId> implements AcmUsuarioBotonBO {

	private DAOBase<AcmUsuarioBotonDTO, AcmUsuarioBotonDTOId> dao;

	public void setDao(DAOBase<AcmUsuarioBotonDTO, AcmUsuarioBotonDTOId> dao) {
		this.dao = dao;
	}

	public DAOBase<AcmUsuarioBotonDTO, AcmUsuarioBotonDTOId> getDao() {
		return dao;
	}

	public List<AcmUsuarioBotonDTO> findByUsuario(AcmUsuarioDTO acmUsuarioDTO) throws GadirServiceException {
		Criterion criterions[] = { Restrictions.eq("acmUsuarioDTO", acmUsuarioDTO) };
		String orderPropertys[] = { "id.coAcmBoton" };
		int orderTypes[] = { DAOConstant.ASC_ORDER };
		int firstResult = -1; 
		int maxResults = -1; 
		return dao.findFiltered(criterions, orderPropertys, orderTypes, firstResult, maxResults);
	}

	public void auditorias(AcmUsuarioBotonDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
