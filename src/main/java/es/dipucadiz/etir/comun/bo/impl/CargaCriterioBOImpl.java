package es.dipucadiz.etir.comun.bo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.CargaCriterioBO;
import es.dipucadiz.etir.comun.bo.TipoRegistroBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.QueryName;
import es.dipucadiz.etir.comun.dto.CargaCriterioCondicionDTO;
import es.dipucadiz.etir.comun.dto.CargaCriterioCondicionDTOId;
import es.dipucadiz.etir.comun.dto.CargaCriterioDTO;
import es.dipucadiz.etir.comun.dto.CargaCriterioDTOId;
import es.dipucadiz.etir.comun.dto.CargaTipoRegistroDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

/**
 * Clase responsable de implementar la lógica de negocio necesaria para el
 * mantenimiento de la clase del modelo {@link CargaCriterioDTO}.
 * 
 * @version 1.0 01/12/2009
 * @author SDS[FJTORRES]
 */
public class CargaCriterioBOImpl extends
        AbstractGenericBOImpl<CargaCriterioDTO, CargaCriterioDTOId> implements
        CargaCriterioBO {

	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = -658991429941156175L;

	/**
	 * Atributo que almacena el DAO asociado a {@link CargaCriterioDTO}.
	 */
	private DAOBase<CargaCriterioDTO, CargaCriterioDTOId> cargaCriterioDao;
	
	private TipoRegistroBO tipoRegistroBO;

	/**
	 * Atributo que almacena el DAO asociado a {@link CargaCriterioCondicionDTO}
	 */
	private DAOBase<CargaCriterioCondicionDTO, CargaCriterioCondicionDTOId> cargaCriterioCondicionDao;

	/**
	 * {@inheritDoc}
	 */
	public void deleteWithCondiciones(final CargaCriterioDTOId id)
	        throws GadirServiceException {
		try {

			final String[] propNames = new String[] { "id.coCargaCriterio",
			        "id.coCargaTipoRegistro", "id.coCarga" };
			final Object[] filters = new Object[] { id.getCoCargaCriterio(),
			        id.getCoCargaTipoRegistro(), id.getCoCarga() };

			// Obtenemos la lista de condiciones y las borramos.
			final List<CargaCriterioCondicionDTO> lista = this
			        .getCargaCriterioCondicionDao().findFiltered(propNames,
			                filters);
			for (final CargaCriterioCondicionDTO cond : lista) {
				this.getCargaCriterioCondicionDao().delete(cond.getId());
			}
			this.getCargaCriterioDao().delete(id);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al borrar el criterio"
			                + " de carga y sus condiciones.", e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public List<CargaCriterioDTO> findCriteriosById(final Long codCarga)
	        throws GadirServiceException {
		try {
			final Map<String, Object> params = new HashMap<String, Object>();
			if (codCarga != null) {
				params.put("coCarga", codCarga);
			}
			return this.findByNamedQuery(QueryName.CRITERIOS_BY_CARGA, params);
		} catch (final GadirServiceException ge) {
			throw ge;
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener los criterios", e);
		}
	}
	


	/**
	 * {@inheritDoc}
	 */
	@Override
	public DAOBase<CargaCriterioDTO, CargaCriterioDTOId> getDao() {
		return this.getCargaCriterioDao();
	}

	/**
	 * Método que devuelve el atributo cargaCriterioDao.
	 * 
	 * @return cargaCriterioDao.
	 */
	public DAOBase<CargaCriterioDTO, CargaCriterioDTOId> getCargaCriterioDao() {
		return cargaCriterioDao;
	}

	/**
	 * Método que establece el atributo cargaCriterioDao.
	 * 
	 * @param cargaCriterioDao
	 *            El cargaCriterioDao.
	 */
	public void setCargaCriterioDao(
	        final DAOBase<CargaCriterioDTO, CargaCriterioDTOId> cargaCriterioDao) {
		this.cargaCriterioDao = cargaCriterioDao;
	}

	/**
	 * Método que devuelve el atributo cargaCriterioCondicionDao.
	 * 
	 * @return cargaCriterioCondicionDao.
	 */
	public DAOBase<CargaCriterioCondicionDTO, CargaCriterioCondicionDTOId> getCargaCriterioCondicionDao() {
		return cargaCriterioCondicionDao;
	}

	/**
	 * Método que establece el atributo cargaCriterioCondicionDao.
	 * 
	 * @param cargaCriterioCondicionDao
	 *            El cargaCriterioCondicionDao.
	 */
	public void setCargaCriterioCondicionDao(
	        final DAOBase<CargaCriterioCondicionDTO, CargaCriterioCondicionDTOId> cargaCriterioCondicionDao) {
		this.cargaCriterioCondicionDao = cargaCriterioCondicionDao;
	}
	
	/**
	 * Método que obtiene el ultimo codigo de un criterio en base de datos para un tipo de registro dado.
	 * 
	 * @param cargaCriterioCondicionDao
	 *            El cargaCriterioCondicionDao.
	 */
	public int obtenerUltimoID(CargaCriterioDTOId id)throws GadirServiceException{
		final DetachedCriteria criteria = DetachedCriteria.forClass(CargaCriterioDTO.class);
		criteria.setProjection(Projections.max("id.coCargaCriterio"));
		
		criteria.setFetchMode("id",  FetchMode.JOIN);
		criteria.add(Restrictions.eq("id.coCarga",id.getCoCarga()));
		criteria.add(Restrictions.eq("id.coCargaTipoRegistro",id.getCoCargaTipoRegistro()));
	
		List lista=this.cargaCriterioDao.findByCriteria(criteria);

		if(lista!=null &&  lista.size()>0 && lista.get(0)!= null){
			
			return Integer.parseInt(lista.get(0).toString());
		}
		  
		return 0;
	            
	}
	
	
	public void auditorias(CargaCriterioDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
		transientObject.setCargaTipoRegistroDTO(tipoRegistroBO.findById(new CargaTipoRegistroDTOId(
				transientObject.getId().getCoCarga(),transientObject.getId().getCoCargaTipoRegistro())));
		
		tipoRegistroBO.auditorias(transientObject.getCargaTipoRegistroDTO(), false);
	}

	/**
	 * @return the tipoRegistroBO
	 */
	public TipoRegistroBO getTipoRegistroBO() {
		return tipoRegistroBO;
	}

	/**
	 * @param tipoRegistroBO the tipoRegistroBO to set
	 */
	public void setTipoRegistroBO(TipoRegistroBO tipoRegistroBO) {
		this.tipoRegistroBO = tipoRegistroBO;
	}
	
}
