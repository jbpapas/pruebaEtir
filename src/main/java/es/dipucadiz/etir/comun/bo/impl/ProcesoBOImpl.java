package es.dipucadiz.etir.comun.bo.impl;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.ProcesoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.ProcesoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class ProcesoBOImpl extends AbstractGenericBOImpl<ProcesoDTO, String>
        implements ProcesoBO {


	private static final long serialVersionUID = -2340497702134956775L;

	private DAOBase<ProcesoDTO, String> dao;

	
	public ProcesoDTO findByUrlAndTipoMP(String url) throws GadirServiceException{
		ProcesoDTO procesoDTO=null;
		
		Criterion criterions[] = { Restrictions.eq("url", url), Restrictions.in("tipo", new String[]{"M", "P"}) };
		String orderPropertys[] = null;
		int orderTypes[] = null;
		int firstResult = -1; 
		int maxResults = 1; 
		
		List<ProcesoDTO> listaProcesos=dao.findFiltered(criterions, orderPropertys, orderTypes, firstResult, maxResults);
		
		if (listaProcesos!=null && !listaProcesos.isEmpty())procesoDTO=listaProcesos.get(0);
		
		return procesoDTO;
	}
	
	

	// GETTERS AND SETTERS

	@Override
	public DAOBase<ProcesoDTO, String> getDao() {
		return this.dao;
	}

	/**
	 * Método que establece el atributo dao.
	 * 
	 * @param dao
	 *            El procesoDao.
	 */
	public void setDao(final DAOBase<ProcesoDTO, String> procesoDao) {
		this.dao = procesoDao;
	}
	
	public void auditorias(ProcesoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
