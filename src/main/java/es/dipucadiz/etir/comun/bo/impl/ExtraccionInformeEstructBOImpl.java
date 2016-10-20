package es.dipucadiz.etir.comun.bo.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.ExtraccionInformeEstructBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.ExtraccionInformeEstructDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;


public class ExtraccionInformeEstructBOImpl
		extends
		AbstractGenericBOImpl<ExtraccionInformeEstructDTO, Long>
		implements ExtraccionInformeEstructBO {
	
	private DAOBase<ExtraccionInformeEstructDTO, Long> dao;
	
	private static final Log LOG = LogFactory.getLog(ExtraccionInformeEstructBOImpl.class);
	
	public DAOBase<ExtraccionInformeEstructDTO, Long> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<ExtraccionInformeEstructDTO, Long> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}	
	
	public void auditorias(ExtraccionInformeEstructDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}
	
	public List<ExtraccionInformeEstructDTO> findByInforme(String coExtraccion, short coExtraccionInforme) throws GadirServiceException {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ExtraccionInformeEstructDTO.class);
		criteria.add(Restrictions.eq("extraccionInformeDTO.id.coExtraccion", coExtraccion));
		criteria.add(Restrictions.eq("extraccionInformeDTO.id.coExtraccionInforme", coExtraccionInforme));
		criteria.addOrder(Order.asc("linea"));
		criteria.addOrder(Order.asc("posIniInforme"));
		return (this.getDao().findByCriteria(criteria));		
	}
	
	public List<ExtraccionInformeEstructDTO> findByInformeLineaPosicion(String coExtraccion, short coExtraccionInforme, short linea, short posicion) throws GadirServiceException {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ExtraccionInformeEstructDTO.class);
		criteria.add(Restrictions.eq("extraccionInformeDTO.id.coExtraccion", coExtraccion));
		criteria.add(Restrictions.eq("extraccionInformeDTO.id.coExtraccionInforme", coExtraccionInforme));
		criteria.add(Restrictions.eq("linea", linea));
		criteria.add(Restrictions.eq("posIniInforme", posicion));
		return (this.getDao().findByCriteria(criteria));		
	}
	
	public List<ExtraccionInformeEstructDTO> findByInformeAndLinea(String coExtraccion, short coExtraccionInforme, short linea) throws GadirServiceException {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ExtraccionInformeEstructDTO.class);
		criteria.add(Restrictions.eq("extraccionInformeDTO.id.coExtraccion", coExtraccion));
		criteria.add(Restrictions.eq("extraccionInformeDTO.id.coExtraccionInforme", coExtraccionInforme));
		criteria.add(Restrictions.eq("linea", linea));
		criteria.addOrder(Order.asc("posIniInforme"));
		return (this.getDao().findByCriteria(criteria));		
	}
	
}