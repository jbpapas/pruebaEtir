package es.dipucadiz.etir.comun.bo.impl;

import java.util.Date;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.PppCondsBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.PppCondsDTO;
import es.dipucadiz.etir.comun.dto.PppCondsPlazosFxsDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class PppCondsBOImpl extends
        AbstractGenericBOImpl<PppCondsDTO, Long> implements
        PppCondsBO {


	
	private DAOBase<PppCondsDTO, Long> dao;

	public void setDao(DAOBase<PppCondsDTO, Long> dao) {
		this.dao = dao;
	}
	
	public DAOBase<PppCondsDTO, Long> getDao() {
		return this.dao;

	}
	
	public void auditorias(PppCondsDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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

	public boolean existePlazoFx(Short anyo, Short nuPlazos, Date fxPlazo) throws GadirServiceException {
		DetachedCriteria criteria = DetachedCriteria.forClass(PppCondsPlazosFxsDTO.class);
		criteria.add(Restrictions.eq("fxPlazo", fxPlazo));
		criteria.createAlias("pppCondsPlazosDTO", "plazos");
		criteria.add(Restrictions.eq("plazos.nuPlazos", Short.valueOf(nuPlazos)));
		criteria.createAlias("plazos.pppCondsDTO", "conds");
		criteria.add(Restrictions.eq("conds.ejercicio", anyo));
		int count = countByCriteria(criteria);
		return count > 0;
	}

}
