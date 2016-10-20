package es.dipucadiz.etir.comun.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;

public interface DAOBase<T, Id extends Serializable> extends Serializable {
	
	List<T> findAll();

	List<T> findAll(final String orderProperty, final int orderType);

	List<T> findAll(final String[] orderProperties, final int[] orderTypes);

	T findById(final Id id);

	List<T> findFiltered(final String propName, final Object filter);

	List<T> findFiltered(final String propName, final Object filter,
	        final int firstResult, final int maxResults);

	List<T> findFiltered(final String propName, final Object filter,
	        final String orderProperty, final int orderType);

	List<T> findFiltered(final String propName, final Object filter,
	        final String orderProperty, final int orderType,
	        final int firstResult, final int maxResults);

	List<T> findFiltered(final String propNames[], final Object filters[]);

	List<T> findFiltered(final String propNames[], final Object filters[],
	        final int firstResult, final int maxResults);

	List<T> findFiltered(final String propNames[], final Object filters[],
	        final String orderProperty, final int orderType);

	List<T> findFiltered(final String propNames[], final Object filters[],
	        final String orderProperty, final int orderType,
	        final int firstResult, final int maxResults);

	List<T> findFiltered(final String[] propNames, final Object[] filters,
	        String[] orderPropertys, int[] orderTypes);

	List<T> findFiltered(final Criterion[] criterions,
			final String[] orderPropertys, int[] orderTypes,
			final int firstResult, final int maxResults);


	List<T> findByCriteria(final DetachedCriteria criteria);
	List findByCriteriaGenerico(final DetachedCriteria criteria);
	List<T> findByCriteria(final DetachedCriteria criteria, int firstResult, int maxResults);
	List findByCriteriaGenerico(final DetachedCriteria criteria, int firstResult, int maxResults);
	
	void save(final T transientObject);

	Id saveOnly(final T transientObject);

	void delete(final Id id);
	
	void deleteAll(Collection<T> collection);
	
	List<T> findByQuery(String query);
	List<T> findByQuery(String query, Map<String, Object> params);
	List<T> findByQuery(String query, int firstResult, int maxResults);
	List<T> findByQuery(String query, Map<String, Object> params, int firstResult, int maxResults);
	
	
	List<T> findByNamedQuery(String queryName);
	List<T> findByNamedQuery(String queryName, Map<String, Object> params);
	List<T> findByNamedQuery(String queryName, String[] propNames, Object[] filters);
	List<T> findByNamedQuery(String queryName, int firstResult, int maxResults);
	List<T> findByNamedQuery(String queryName, Map<String, Object> params, int firstResult, int maxResults);
	
	
	T findByQueryUniqueResult(String query);
	T findByQueryUniqueResult(String query, Map<String, Object> params);
	T findByQueryUniqueResult(String query, int firstResult, int maxResults);
	T findByQueryUniqueResult(String query, Map<String, Object> params, int firstResult, int maxResults);
	
	
	T findByNamedQueryUniqueResult(String queryName);
	T findByNamedQueryUniqueResult(String queryName, Map<String, Object> params);
	T findByNamedQueryUniqueResult(String queryName, int firstResult, int maxResults);
	T findByNamedQueryUniqueResult(String queryName, Map<String, Object> params, int firstResult, int maxResults);
	
	int ejecutaSQLQueryUpdate(String query);
	int ejecutaSQLQueryUpdate(String query, Map<String, Object> params);
	
	Object ejecutaSQLQuerySelect(String query);
	Object ejecutaSQLQuerySelect(String query, Map<String, Object> params);
	
	Object ejecutaNamedQuerySelect(String query);
	Object ejecutaNamedQuerySelect(String query, Map<String, Object> params);

	public int countByCriteria(final DetachedCriteria criteria);
	public int countByCriteriaColumnaDistinct(final DetachedCriteria criteria, final String propertyName);

	public List<Object[]> findVariasTablasByNamedQuery(final String queryName, final Map<String, Object> params);
	
	
	Class<T> getPersistentClass();
	void setPersistentClass(Class<T> persistentClass);
}
