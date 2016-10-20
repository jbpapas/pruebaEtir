
package es.dipucadiz.etir.comun.bo.impl;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.CalendarioTareaMasivaBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.CalendarioTareaMasivaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class CalendarioTareaMasivaBOImpl extends AbstractGenericBOImpl<CalendarioTareaMasivaDTO, Long> implements CalendarioTareaMasivaBO {

	private static final long serialVersionUID = -6662193891573788258L;
	private static final Log LOG = LogFactory.getLog(CalendarioTareaMasivaBOImpl.class);
	
	private DAOBase<CalendarioTareaMasivaDTO, Long> dao;

	public DAOBase<CalendarioTareaMasivaDTO, Long> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<CalendarioTareaMasivaDTO, Long> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}
	
	
	public void auditorias(CalendarioTareaMasivaDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}	
	
	public List<CalendarioTareaMasivaDTO> findByAnoMes(int ano, int mes) throws GadirServiceException{
		DetachedCriteria criterio = DetachedCriteria.forClass(CalendarioTareaMasivaDTO.class);		
		
		GregorianCalendar gcInicio= new GregorianCalendar(ano, mes, 1, 0, 0, 0);
		Date fechaInicio = new Date(gcInicio.getTimeInMillis());
		
		GregorianCalendar gcFin= new GregorianCalendar(ano, mes, 31, 23, 59, 59);
		Date fechaFin = new Date(gcFin.getTimeInMillis());
		
		criterio.add(Restrictions.ge("fxTarea", fechaInicio));
		criterio.add(Restrictions.le("fxTarea",fechaFin));
				
		return findByCriteria(criterio);
	}
	public List<CalendarioTareaMasivaDTO> findByAno(int ano) throws GadirServiceException{
		DetachedCriteria criterio = DetachedCriteria.forClass(CalendarioTareaMasivaDTO.class);		
		
		GregorianCalendar gcInicio= new GregorianCalendar(ano, 0, 1, 0, 0, 0);
		Date fechaInicio = new Date(gcInicio.getTimeInMillis());
		
		GregorianCalendar gcFin= new GregorianCalendar(ano, 11, 31, 23, 59, 59);
		Date fechaFin = new Date(gcFin.getTimeInMillis());
		
		criterio.add(Restrictions.ge("fxTarea", fechaInicio));
		criterio.add(Restrictions.le("fxTarea",fechaFin));
				
		return findByCriteria(criterio);
	}
	
}