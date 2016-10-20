package es.dipucadiz.etir.comun.bo.impl;

import java.util.Collections;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import es.dipucadiz.etir.comun.bo.SituacionBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.DAOConstant;
import es.dipucadiz.etir.comun.dto.IncidenciaSituacionDTO;
import es.dipucadiz.etir.comun.dto.SituacionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;



public class SituacionBOImpl extends AbstractGenericBOImpl<SituacionDTO, String>  implements SituacionBO {
	private static final long serialVersionUID = 1240847522855505177L;
	
	private DAOBase<SituacionDTO, String> dao;

	public DAOBase<SituacionDTO, String> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<SituacionDTO, String> dao) {
		this.dao = dao;
	}
	public void auditorias(SituacionDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}
	
	@Override
	public List<SituacionDTO> findAll() throws GadirServiceException {
		try {
			return getDao().findAll("coSituacion", DAOConstant.ASC_ORDER);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado.", e);
		}
	}

	public List<SituacionDTO> findByModelo(String coModelo) {
		List<SituacionDTO> result;
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(SituacionDTO.class, "sit");
			DetachedCriteria otro = DetachedCriteria.forClass(IncidenciaSituacionDTO.class, "incsit");
			otro.setProjection(Projections.property("coIncidenciaSituacion"));
			otro.add(Restrictions.or(
					Restrictions.eqProperty("sit.coSituacion", "incsit.situacionDTOByCoSituacionOrigen.coSituacion"), 
					Restrictions.eqProperty("sit.coSituacion", "incsit.situacionDTOByCoSituacionDestino.coSituacion")
			));
			otro.add(Restrictions.eq("incsit.modeloVersionDTO.id.coModelo", coModelo));
			criteria.add(Subqueries.exists(otro));
			criteria.addOrder(Order.asc("sit.coSituacion"));
			result = findByCriteria(criteria);
		} catch (GadirServiceException e) {
			log.error(e.getMensaje(), e);
			result = Collections.emptyList();
		}
		return result;
	}
	
}
