package es.dipucadiz.etir.comun.bo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface GenericBO<T, Id extends Serializable> extends Serializable {

	List<T> findAll() throws GadirServiceException;

	List<T> findAll(final String orderProperty, final int orderType)
	        throws GadirServiceException;

	List<T> findAll(final String[] orderProperties, final int[] orderTypes)
	        throws GadirServiceException;

	T findById(final Id id) throws GadirServiceException;
	
	T findByIdInitialized(final Id id, String[] propertiesToInitialize) throws GadirServiceException;

	List<T> findFiltered(final String propName, final Object filter)
	        throws GadirServiceException;

	List<T> findFilteredInitialized(final String propName, final Object filter, String[] propertiesToInitialize)
    	throws GadirServiceException;
	
	List<T> findFilteredInitialized(final String propName, final Object filter, final String orderProperty, final int orderType, String[] propertiesToInitialize)
	throws GadirServiceException;
	
	List<T> findFiltered(final String propName, final Object filter,
	        final int firstResult, final int maxResults)
	        throws GadirServiceException;

	List<T> findFiltered(final String propName, final Object filter,
	        final String orderProperty, final int orderType)
	        throws GadirServiceException;

	List<T> findFiltered(final String propName, final Object filter,
	        final String orderProperty, final int orderType,
	        final int firstResult, final int maxResults)
	        throws GadirServiceException;

	List<T> findFiltered(final String propNames[], final Object filters[])
	        throws GadirServiceException;

	List<T> findFiltered(final String propNames[], final Object filters[],
	        final int firstResult, final int maxResults)
	        throws GadirServiceException;

	List<T> findFiltered(final String propNames[], final Object filters[],
	        final String orderProperty, final int orderType)
	        throws GadirServiceException;

	List<T> findFiltered(final String propNames[], final Object filters[],
	        final String orderProperty, final int orderType,
	        final int firstResult, final int maxResults)
	        throws GadirServiceException;

	List<T> findFiltered(final String[] propNames,
	        final Object[] filters, final String[] orderProperty,
	        final int[] orderType)
	        throws GadirServiceException;
	
	List<T> findByCriteria(final DetachedCriteria criterio) throws GadirServiceException;
	List<T> findByCriteria(final DetachedCriteria criterio, int firstResult, int maxResults) throws GadirServiceException;
	List findByCriteriaGenerico(final DetachedCriteria criterio) throws GadirServiceException;
	List findByCriteriaGenerico(final DetachedCriteria criterio, int firstResult, int maxResults) throws GadirServiceException;

	void save(final T transientObject) throws GadirServiceException;
	void saveOnly(final T transientObject) throws GadirServiceException;

	void delete(final Id id) throws GadirServiceException;
	
	public List<Object[]> findVariasTablasByNamedQuery(final String queryName, final Map<String, Object> params) throws GadirServiceException;
	
	T findByRowid(String rowid) throws GadirServiceException;
	
	abstract void auditorias(final T transientObject, final Boolean saveOnly) throws GadirServiceException;
	
	
	List<T> findByQuery(String query) throws GadirServiceException;
	
	List<T> findByQuery(String query, Map<String, Object> params) throws GadirServiceException;
	
	List<T> findByQuery(String query, int firstResult, int maxResults) throws GadirServiceException;
	
	List<T> findByQuery(String query, Map<String, Object> params, int firstResult, int maxResults) throws GadirServiceException;
	
	
	List<T> findByNamedQuery(String queryName) throws GadirServiceException;

	List<T> findByNamedQuery(String queryName, Map<String, Object> params) throws GadirServiceException;
	
	List<T> findByNamedQuery(String queryName, String[] propNames, Object[] filters) throws GadirServiceException;
	
	List<T> findByNamedQuery(String queryName, int firstResult, int maxResults) throws GadirServiceException;
	
	List<T> findByNamedQuery(String queryName, Map<String, Object> params, int firstResult, int maxResults) throws GadirServiceException;
	
	
	T findByQueryUniqueResult(String query) throws GadirServiceException;
	
	T findByQueryUniqueResult(String query, Map<String, Object> params) throws GadirServiceException;
	
	T findByQueryUniqueResult(String query, int firstResult, int maxResults) throws GadirServiceException;
	
	T findByQueryUniqueResult(String query, Map<String, Object> params, int firstResult, int maxResults) throws GadirServiceException;
	
	
	T findByNamedQueryUniqueResult(String queryName) throws GadirServiceException;

	T findByNamedQueryUniqueResult(String queryName, Map<String, Object> params) throws GadirServiceException;
	
	T findByNamedQueryUniqueResult(String queryName, int firstResult, int maxResults) throws GadirServiceException;
	
	T findByNamedQueryUniqueResult(String queryName, Map<String, Object> params, int firstResult, int maxResults) throws GadirServiceException;
	
	public int ejecutaQueryUpdate(String query) throws GadirServiceException;
	public int ejecutaQueryUpdate(String query, Map<String, Object> params) throws GadirServiceException;
	public Object ejecutaQuerySelect(String query) throws GadirServiceException;
	public Object ejecutaQuerySelect(String query, Map<String, Object> params) throws GadirServiceException;
	public Object ejecutaNamedQuerySelect(String query) throws GadirServiceException;
	public Object ejecutaNamedQuerySelect(String query, Map<String, Object> params) throws GadirServiceException;
	
	int countByCriteria(DetachedCriteria criterio) throws GadirServiceException;
	int countByCriteriaColumnaDistinct(final DetachedCriteria criteria, final String propertyName) throws GadirServiceException;
	
	@SuppressWarnings("unchecked")
	List findByNamedQueryGeneric(String queryName) throws GadirServiceException;

	@SuppressWarnings("unchecked")	
	List findByNamedQueryGeneric(String queryName, Map<String, Object> params) throws GadirServiceException;
	
	@SuppressWarnings("unchecked")	
	List findByNamedQueryGeneric(String queryName, String[] propNames, Object[] filters) throws GadirServiceException;
	
	@SuppressWarnings("unchecked")	
	List findByNamedQueryGeneric(String queryName, int firstResult, int maxResults) throws GadirServiceException;
	
	@SuppressWarnings("unchecked")	
	List findByNamedQueryGeneric(String queryName, Map<String, Object> params, int firstResult, int maxResults) throws GadirServiceException;
	
}
	
	