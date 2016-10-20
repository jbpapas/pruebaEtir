package es.dipucadiz.etir.comun.bo.impl;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;

import es.dipucadiz.etir.comun.bo.GenericBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.Utilidades;


public abstract class AbstractGenericBOImpl<T, Id extends Serializable> implements
		GenericBO<T, Id> {

	private static final long serialVersionUID = 6846541725492303622L;
	protected final Log log = LogFactory.getLog(getClass());

	
	public List<T> findAll() throws GadirServiceException {
		try {
			return getDao().findAll();
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado.", e);
		}
	}

	public List<T> findAll(final String orderProperty, final int orderType)
	        throws GadirServiceException {
		try {
			return this.getDao().findAll(orderProperty, orderType);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado, ordenando por la propiedad "
			                + orderProperty, e);
		}
	}

	public List<T> findAll(final String[] orderProperties,
	        final int[] orderTypes) throws GadirServiceException {
		try {
			return this.getDao().findAll(orderProperties, orderTypes);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener  el listado, ordenando por las propiedades"
			                + Arrays.toString(orderProperties), e);
		}
	}

	public T findById(final Id id) throws GadirServiceException {
		try {
			return this.getDao().findById(id);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el objeto con id " + id, e);
		}
	}
	
	public T findByIdInitialized(final Id id, String[] propertiesToInitialize) throws GadirServiceException {
		try {
			T res = this.getDao().findById(id);

			if(res!=null){
				try{

					Class c = res.getClass();
					if (propertiesToInitialize!=null){
						for (int i=0; i<propertiesToInitialize.length; i++){
							String metodo= "get";
							metodo += propertiesToInitialize[i].substring(0, 1).toUpperCase();
							metodo += propertiesToInitialize[i].substring(1, propertiesToInitialize[i].length());
							Method m = c.getMethod(metodo, null);
							Hibernate.initialize(m.invoke(res, null));
						}
					}

				}catch(Exception e){
					log.error("Error en findByIdInitialized", e);
				}
			}
			
			return res;
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el objeto con id " + id, e);
		}
	}

	public List<T> findFilteredInitialized(final String propName, final Object filter, String[] propertiesToInitialize)
    	throws GadirServiceException {
		try {
			List<T> res = this.getDao().findFiltered(propName, filter);
			
			try{
				for(int j=0;j<res.size(); j++) {
					Class c = res.get(j).getClass();
					if (propertiesToInitialize!=null){
						for (int i=0; i<propertiesToInitialize.length; i++){
							String metodo= "get";
							metodo += propertiesToInitialize[i].substring(0, 1).toUpperCase();
							metodo += propertiesToInitialize[i].substring(1, propertiesToInitialize[i].length());
							Method m = c.getMethod(metodo, null);
							Hibernate.initialize(m.invoke(res.get(j), null));
						}
					}
				}
			}catch(Exception e){
				log.error("Error en findFilteredInitialized", e);
			}			

			return res;
			
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado filtrando por la propiedad "
			                + propName + " con el valor " + filter, e);
		}
	}
	
	public List<T> findFilteredInitialized(final String propName, final Object filter, final String orderProperty, final int orderType, String[] propertiesToInitialize)
	throws GadirServiceException {
		try {
			List<T> res = this.getDao().findFiltered(propName, filter, orderProperty, orderType);
			
			try{
				for(int j=0;j<res.size(); j++) {
					Class c = res.get(j).getClass();
					if (propertiesToInitialize!=null){
						for (int i=0; i<propertiesToInitialize.length; i++){
							String metodo= "get";
							metodo += propertiesToInitialize[i].substring(0, 1).toUpperCase();
							metodo += propertiesToInitialize[i].substring(1, propertiesToInitialize[i].length());
							Method m = c.getMethod(metodo, null);
							Hibernate.initialize(m.invoke(res.get(j), null));
						}
					}
				}
			}catch(Exception e){
				log.error("Error en findFilteredInitialized", e);
			}			
	
			return res;
			
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado filtrando por la propiedad "
			                + propName + " con el valor " + filter, e);
		}
	}
	
	public List<T> findFiltered(final String propName, final Object filter)
	        throws GadirServiceException {
		try {
			return this.getDao().findFiltered(propName, filter);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado filtrando por la propiedad "
			                + propName + " con el valor " + filter, e);
		}
	}

	public List<T> findFiltered(final String propName, final Object filter,
	        final int firstResult, final int maxResults)
	        throws GadirServiceException {
		try {
			return this.getDao().findFiltered(propName, filter, firstResult,
			        maxResults);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado filtrando por la propiedad "
			                + propName + " con el valor " + filter, e);
		}
	}

	public List<T> findFiltered(final String propName, final Object filter,
	        final String orderProperty, final int orderType)
	        throws GadirServiceException {
		try {
			return this.getDao().findFiltered(propName, filter, orderProperty,
			        orderType);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado filtrando por la propiedad "
			                + propName + " con el valor " + filter
			                + " y ordenando por " + orderProperty, e);
		}
	}

	public List<T> findFiltered(final String propName, final Object filter,
	        final String orderProperty, final int orderType,
	        final int firstResult, final int maxResults)
	        throws GadirServiceException {
		try {
			return this.getDao().findFiltered(propName, filter, orderProperty,
			        orderType, firstResult, maxResults);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado filtrando por la propiedad "
			                + propName + " con el valor " + filter
			                + " y ordenando por " + orderProperty, e);
		}
	}

	public List<T> findFiltered(final String[] propNames, final Object[] filters)
	        throws GadirServiceException {
		try {
			return this.getDao().findFiltered(propNames, filters);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado filtrando por las propiedades "
			                + Arrays.toString(propNames) + " con los valores "
			                + Arrays.toString(filters), e);
		}
	}

	public List<T> findFiltered(final String[] propNames,
	        final Object[] filters, final int firstResult, final int maxResults)
	        throws GadirServiceException {
		try {
			return this.getDao().findFiltered(propNames, filters, firstResult,
			        maxResults);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado filtrando por las propiedades "
			                + Arrays.toString(propNames) + " con los valores "
			                + Arrays.toString(filters), e);
		}
	}

	public List<T> findFiltered(final String[] propNames,
	        final Object[] filters, final String orderProperty,
	        final int orderType) throws GadirServiceException {
		try {
			return this.getDao().findFiltered(propNames, filters,
			        orderProperty, orderType);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado filtrando por las propiedades "
			                + Arrays.toString(propNames) + " con los valores "
			                + Arrays.toString(filters) + " y ordenando por "
			                + orderProperty, e);
		}
	}

	public List<T> findFiltered(final String[] propNames,
	        final Object[] filters, final String orderProperty,
	        final int orderType, final int firstResult, final int maxResults)
	        throws GadirServiceException {
		try {
			return this.getDao().findFiltered(propNames, filters,
			        orderProperty, orderType, firstResult, maxResults);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado filtrando por las propiedades "
			                + Arrays.toString(propNames) + " con los valores "
			                + Arrays.toString(filters) + " y ordenando por "
			                + orderProperty, e);
		}
	}
	
	public List<T> findFiltered(final String[] propNames,
	        final Object[] filters, final String[] orderProperty,
	        final int[] orderType)
	        throws GadirServiceException {
		try {
			return this.getDao().findFiltered(propNames, filters, orderProperty, orderType);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado filtrando por las propiedades "
			                + Arrays.toString(propNames) + " con los valores "
			                + Arrays.toString(filters) + " y ordenando por "
			                + orderProperty, e);
		}
	}
	
	public List<T> findByCriteria(DetachedCriteria criterio) throws GadirServiceException {
		try {
		
			return getDao().findByCriteria(criterio);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado ejecutando la consulta ",
			        e);
		}	
	}
	
	@SuppressWarnings("unchecked")
	public List findByCriteriaGenerico(DetachedCriteria criterio) throws GadirServiceException {
		try {
		
			return getDao().findByCriteriaGenerico(criterio);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado ejecutando la consulta ",
			        e);
		}	
	}


	public void delete(final Id id) throws GadirServiceException {
		try {
			this.getDao().delete(id);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al borrar el objeto con id " + id, e);
		}
	}	
	
	
	
	public List<Object[]> findVariasTablasByNamedQuery(final String queryName,
	        final Map<String, Object> params) throws GadirServiceException {
		try {
			return this.getDao().findVariasTablasByNamedQuery(queryName, params);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado ejecutando la consulta "
			                + queryName + " con los parametros " + params, e);
		}
	}
	

	public T findByRowid(String rowid){

		T resultado = null;
		List<T> lista = this.getDao().findFiltered("rowid", Utilidades.decodificarRowidFormatoSeguro(rowid));
		if (lista!=null && lista.size()==1)
			resultado=lista.iterator().next();
		return resultado;
	}
	
	
	
	public List<T> findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults) throws GadirServiceException {
		try {
			return getDao().findByCriteria(criteria, firstResult, maxResults);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado ejecutando la consulta ",
			        e);
		}
	}
	
	public List findByCriteriaGenerico(DetachedCriteria criteria, int firstResult, int maxResults) throws GadirServiceException {
		try {
			return getDao().findByCriteriaGenerico(criteria, firstResult, maxResults);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado ejecutando la consulta ",
			        e);
		}
	}
	
	public int countByCriteria(DetachedCriteria criterio) throws GadirServiceException {
		try {
			return getDao().countByCriteria(criterio);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el contador ejecutando la consulta ",
			        e);
		}	
	}
	
	public int countByCriteriaColumnaDistinct(final DetachedCriteria criterio, final String propertyName) throws GadirServiceException {
		try {
			return getDao().countByCriteriaColumnaDistinct(criterio, propertyName);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el contador ejecutando la consulta ",
			        e);
		}	
	}


	public void save(final T transientObject) throws GadirServiceException {
		try {
			// Actualizamos los campos de auditoría del objeto pasado
			auditorias(transientObject, false);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al grabar el objeto " + transientObject,
			        e);
		}
	}


	public void saveOnly(final T transientObject) throws GadirServiceException {
		try {
			// Actualizamos los campos de auditoría del objeto pasado
			auditorias(transientObject, true);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al insertar el objeto " + transientObject,
			        e);
		}
	}
	
	
	public List<T> findByQuery(String query) throws GadirServiceException{
		try {
			return getDao().findByQuery(query);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado ejecutando la consulta ",
			        e);
		}
	}
	
	public List<T> findByQuery(String query, Map<String, Object> params) throws GadirServiceException{
		try {
			return getDao().findByQuery(query, params);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado ejecutando la consulta ",
			        e);
		}
	}
	
	public List<T> findByQuery(String query, int firstResult, int maxResults) throws GadirServiceException{
		try {
			return getDao().findByQuery(query, firstResult, maxResults);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado ejecutando la consulta ",
			        e);
		}
	}
	
	public List<T> findByQuery(String query, Map<String, Object> params, int firstResult, int maxResults) throws GadirServiceException{
		try {
			return getDao().findByQuery(query,params, firstResult, maxResults);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado ejecutando la consulta ",
			        e);
		}
	}
	
	
	public List<T> findByNamedQuery(String queryName) throws GadirServiceException{
		try {
			return getDao().findByNamedQuery(queryName);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado ejecutando la consulta ",
			        e);
		}
	}

	public List<T> findByNamedQuery(String queryName, Map<String, Object> params) throws GadirServiceException{
		try {
			return getDao().findByNamedQuery(queryName, params);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado ejecutando la consulta ",
			        e);
		}
	}
	
	public List<T> findByNamedQuery(String queryName, String[] propNames, Object[] filters) throws GadirServiceException{
		try {
			return getDao().findByNamedQuery(queryName, propNames, filters);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado ejecutando la consulta ",
			        e);
		}
	}
	
	public List<T> findByNamedQuery(String queryName, int firstResult, int maxResults) throws GadirServiceException{
		try {
			return getDao().findByNamedQuery(queryName, firstResult, maxResults);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado ejecutando la consulta ",
			        e);
		}
	}
	
	public List<T> findByNamedQuery(String queryName, Map<String, Object> params, int firstResult, int maxResults) throws GadirServiceException{
		try {
			return getDao().findByNamedQuery(queryName, params, firstResult, maxResults);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado ejecutando la consulta ",
			        e);
		}
	}
	
	
	public T findByQueryUniqueResult(String query) throws GadirServiceException{
		try {
			return getDao().findByQueryUniqueResult(query);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado ejecutando la consulta ",
			        e);
		}
	}
	
	public T findByQueryUniqueResult(String query, Map<String, Object> params) throws GadirServiceException{
		try {
			return getDao().findByQueryUniqueResult(query, params);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado ejecutando la consulta ",
			        e);
		}
	}
	
	public T findByQueryUniqueResult(String query, int firstResult, int maxResults) throws GadirServiceException{
		try {
			return getDao().findByQueryUniqueResult(query, firstResult, maxResults);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado ejecutando la consulta ",
			        e);
		}
	}
	
	public T findByQueryUniqueResult(String query, Map<String, Object> params, int firstResult, int maxResults) throws GadirServiceException{
		try {
			return getDao().findByQueryUniqueResult(query, params, firstResult, maxResults);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado ejecutando la consulta ",
			        e);
		}
	}
	
	
	public T findByNamedQueryUniqueResult(String queryName) throws GadirServiceException{
		try {
			return getDao().findByNamedQueryUniqueResult(queryName);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado ejecutando la consulta ",
			        e);
		}
	}

	public T findByNamedQueryUniqueResult(String queryName, Map<String, Object> params) throws GadirServiceException{
		try {
			return getDao().findByNamedQueryUniqueResult(queryName, params);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado ejecutando la consulta ",
			        e);
		}
	}
	
	public T findByNamedQueryUniqueResult(String queryName, int firstResult, int maxResults) throws GadirServiceException{
		try {
			return getDao().findByNamedQueryUniqueResult(queryName, firstResult, maxResults);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado ejecutando la consulta ",
			        e);
		}
	}
	
	public T findByNamedQueryUniqueResult(String queryName, Map<String, Object> params, int firstResult, int maxResults) throws GadirServiceException{
		try {
			return getDao().findByNamedQueryUniqueResult(queryName, params, firstResult, maxResults);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado ejecutando la consulta ",
			        e);
		}
	}
	
	public int ejecutaQueryUpdate(String query) throws GadirServiceException{
		try {
			return getDao().ejecutaSQLQueryUpdate(query);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException("Ocurrio un error al ejecutar la consulta ", e);
		}
	}
	public int ejecutaQueryUpdate(String query, Map<String, Object> params) throws GadirServiceException{
		try {
			return getDao().ejecutaSQLQueryUpdate(query, params);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException("Ocurrio un error al ejecutar la consulta ", e);
		}
	}
	
	public Object ejecutaQuerySelect(String query) throws GadirServiceException{
		try {
			return getDao().ejecutaSQLQuerySelect(query);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException("Ocurrio un error al ejecutar la consulta ", e);
		}
	}
	public Object ejecutaQuerySelect(String query, Map<String, Object> params) throws GadirServiceException{
		try {
			return getDao().ejecutaSQLQuerySelect(query, params);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException("Ocurrio un error al ejecutar la consulta ", e);
		}
	}
	
	public Object ejecutaNamedQuerySelect(String query) throws GadirServiceException{
		try {
			return getDao().ejecutaNamedQuerySelect(query);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException("Ocurrio un error al ejecutar la consulta ", e);
		}
	}
	public Object ejecutaNamedQuerySelect(String query, Map<String, Object> params) throws GadirServiceException{
		try {
			return getDao().ejecutaNamedQuerySelect(query, params);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException("Ocurrio un error al ejecutar la consulta ", e);
		}
	}


	public abstract DAOBase<T, Id> getDao();
	
	@SuppressWarnings("unchecked")
	public List findByNamedQueryGeneric(String queryName) throws GadirServiceException{
		try {
			return getDao().findByNamedQuery(queryName);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado ejecutando la consulta ",
			        e);
		}
	}

	@SuppressWarnings("unchecked")
	public List findByNamedQueryGeneric(String queryName, Map<String, Object> params) throws GadirServiceException{
		try {
			return getDao().findByNamedQuery(queryName, params);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado ejecutando la consulta ",
			        e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List findByNamedQueryGeneric(String queryName, String[] propNames, Object[] filters) throws GadirServiceException{
		try {
			return getDao().findByNamedQuery(queryName, propNames, filters);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado ejecutando la consulta ",
			        e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List findByNamedQueryGeneric(String queryName, int firstResult, int maxResults) throws GadirServiceException{
		try {
			return getDao().findByNamedQuery(queryName, firstResult, maxResults);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado ejecutando la consulta ",
			        e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List findByNamedQueryGeneric(String queryName, Map<String, Object> params, int firstResult, int maxResults) throws GadirServiceException{
		try {
			return getDao().findByNamedQuery(queryName, params, firstResult, maxResults);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado ejecutando la consulta ",
			        e);
		}
	}	
}
