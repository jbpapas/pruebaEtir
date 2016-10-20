package es.dipucadiz.etir.comun.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.DAOConstant;


@SuppressWarnings("unchecked")
public class DAOBaseHibernate<T, Id extends Serializable> extends
        HibernateDaoSupport implements DAOBase<T, Id> {

	private static final long serialVersionUID = 6516663155114804684L;
	private static final Log log = LogFactory.getLog(DAOBaseHibernate.class);

	private Class<T> persistentClass;


	public DAOBaseHibernate(final Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	
	public List<T> findAll() {
		return super.getHibernateTemplate().loadAll(persistentClass);
	}

	public List<T> findAll(final String orderProperty, final int orderType) {
		final DetachedCriteria detachedCriteria = DetachedCriteria
		        .forClass(this.persistentClass);
		if (orderProperty != null) {
			Order defaultOrder = null;
			if (orderType == DAOConstant.DESC_ORDER) {
				defaultOrder = Order.desc(orderProperty);
			} else {
				defaultOrder = Order.asc(orderProperty);
			}
			detachedCriteria.addOrder(defaultOrder);
		}
		return super.getHibernateTemplate().findByCriteria(detachedCriteria);
	}

	public List<T> findAll(final String[] orderProperties,
	        final int[] orderTypes) {
		final DetachedCriteria detachedCriteria = DetachedCriteria
		        .forClass(this.persistentClass);
		if (orderProperties != null && orderTypes != null
		        && orderTypes.length == orderProperties.length) {
			final int lon = orderProperties.length;
			for (int i = 0; i < lon; i++) {
				if (orderTypes[i] == DAOConstant.DESC_ORDER) {
					detachedCriteria.addOrder(Order.desc(orderProperties[i]));
				} else {
					detachedCriteria.addOrder(Order.asc(orderProperties[i]));
				}
			}
		}
		return super.getHibernateTemplate().findByCriteria(detachedCriteria);
	}

	public T findById(final Id id) {
		return (T) super.getHibernateTemplate().get(persistentClass, id);
	}

	public List<T> findFiltered(final String propName, final Object filter) {
		final DetachedCriteria detachedCriteria = DetachedCriteria
		        .forClass(this.persistentClass);
		detachedCriteria.add(Restrictions.eq(propName, filter));
		return super.getHibernateTemplate().findByCriteria(detachedCriteria);
	}

	public List<T> findFiltered(final String propName, final Object filter,
	        final int firstResult, final int maxResults) {
		final DetachedCriteria detachedCriteria = DetachedCriteria
		        .forClass(this.persistentClass);
		detachedCriteria.add(Restrictions.eq(propName, filter));
		return super.getHibernateTemplate().findByCriteria(detachedCriteria,
		        firstResult, maxResults);
	}

	public List<T> findFiltered(final String propName, final Object filter,
	        final String orderProperty, final int orderType) {
		final DetachedCriteria detachedCriteria = DetachedCriteria
		        .forClass(this.persistentClass);
		detachedCriteria.add(Restrictions.eq(propName, filter));

		if (orderProperty != null) {
			Order defaultOrder = null;
			if (orderType == DAOConstant.DESC_ORDER) {
				defaultOrder = Order.desc(orderProperty);
			} else {
				defaultOrder = Order.asc(orderProperty);
			}
			detachedCriteria.addOrder(defaultOrder);
		}
		return super.getHibernateTemplate().findByCriteria(detachedCriteria);
	}

	public List<T> findFiltered(final String propName, final Object filter,
	        final String orderProperty, final int orderType,
	        final int firstResult, final int maxResults) {
		final DetachedCriteria detachedCriteria = DetachedCriteria
		        .forClass(this.persistentClass);
		detachedCriteria.add(Restrictions.eq(propName, filter));

		if (orderProperty != null) {
			Order defaultOrder = null;
			if (orderType == DAOConstant.DESC_ORDER) {
				defaultOrder = Order.desc(orderProperty);
			} else {
				defaultOrder = Order.asc(orderProperty);
			}
			detachedCriteria.addOrder(defaultOrder);
		}
		return super.getHibernateTemplate().findByCriteria(detachedCriteria,
		        firstResult, maxResults);
	}

	public List<T> findFiltered(final String propNames[],
	        final Object filters[]) {
		final DetachedCriteria detachedCriteria = DetachedCriteria
		        .forClass(this.persistentClass);
		final int lon = propNames.length;
		for (int i = 0; i < lon; i++) {
			detachedCriteria.add(Restrictions.eq(propNames[i], filters[i]));
		}
		return super.getHibernateTemplate().findByCriteria(detachedCriteria);
	}

	public List<T> findFiltered(final String propNames[],
	        final Object filters[], final int firstResult, final int maxResults) {
		final DetachedCriteria detachedCriteria = DetachedCriteria
		        .forClass(this.persistentClass);
		final int lon = propNames.length;
		for (int i = 0; i < lon; i++) {
			detachedCriteria.add(Restrictions.eq(propNames[i], filters[i]));
		}
		return super.getHibernateTemplate().findByCriteria(detachedCriteria,
		        firstResult, maxResults);
	}

	public List<T> findFiltered(final String[] propNames,
	        final Object[] filters, final String orderProperty,
	        final int orderType) {
		final DetachedCriteria detachedCriteria = DetachedCriteria
		        .forClass(this.persistentClass);
		final int lon = propNames.length;
		for (int i = 0; i < lon; i++) {
			detachedCriteria.add(Restrictions.eq(propNames[i], filters[i]));
		}
		if (orderProperty != null) {
			Order defaultOrder = null;
			if (orderType == DAOConstant.DESC_ORDER) {
				defaultOrder = Order.desc(orderProperty);
			} else {
				defaultOrder = Order.asc(orderProperty);
			}
			detachedCriteria.addOrder(defaultOrder);
		}
		return super.getHibernateTemplate().findByCriteria(detachedCriteria);
	}

	public List<T> findFiltered(final String[] propNames,
	        final Object[] filters, final String orderProperty,
	        final int orderType, final int firstResult, final int maxResults) {
		final DetachedCriteria detachedCriteria = DetachedCriteria
		        .forClass(this.persistentClass);
		final int lon = propNames.length;
		for (int i = 0; i < lon; i++) {
			detachedCriteria.add(Restrictions.eq(propNames[i], filters[i]));
		}
		if (orderProperty != null) {
			Order defaultOrder = null;
			if (orderType == DAOConstant.DESC_ORDER) {
				defaultOrder = Order.desc(orderProperty);
			} else {
				defaultOrder = Order.asc(orderProperty);
			}
			detachedCriteria.addOrder(defaultOrder);
		}
		return super.getHibernateTemplate().findByCriteria(detachedCriteria,
		        firstResult, maxResults);
	}

	public List<T> findFiltered(final String[] propNames,
	        final Object[] filters, final String[] orderPropertys,
	        final int[] orderTypes) {
		final DetachedCriteria detachedCriteria = DetachedCriteria
		        .forClass(this.persistentClass);
		final int lon = propNames.length;
		for (int i = 0; i < lon; i++) {
			detachedCriteria.add(Restrictions.eq(propNames[i], filters[i]));
		}
		if (orderPropertys != null) {
			Order order = null;
			for (int j = 0; j < orderPropertys.length; j++) {
				if (orderTypes == null) {
					order = Order.asc(orderPropertys[j]);
				} else {
					if (orderTypes[j] == DAOConstant.DESC_ORDER) {
						order = Order.desc(orderPropertys[j]);
					} else {
						order = Order.asc(orderPropertys[j]);
					}
				}
				detachedCriteria.addOrder(order);
			}
		}
		return super.getHibernateTemplate().findByCriteria(detachedCriteria);
	}

	public List<T> findFiltered(final Criterion[] criterions,
	        final String[] orderPropertys, final int[] orderTypes,
	        final int firstResult, final int maxResults) {
		final DetachedCriteria detachedCriteria = DetachedCriteria
		        .forClass(this.persistentClass);
		final int lon = criterions.length;
		for (int i = 0; i < lon; i++) {
			detachedCriteria.add(criterions[i]);
		}
		if (orderPropertys != null) {
			Order order = null;
			for (int j = 0; j < orderPropertys.length; j++) {
				if (orderTypes == null) {
					order = Order.asc(orderPropertys[j]);
				} else {
					if (orderTypes[j] == DAOConstant.DESC_ORDER) {
						order = Order.desc(orderPropertys[j]);
					} else {
						order = Order.asc(orderPropertys[j]);
					}
				}
				detachedCriteria.addOrder(order);
			}
		}
		return super.getHibernateTemplate().findByCriteria(detachedCriteria,
		        firstResult, maxResults);
	}

	public List<T> findByCriteria(final DetachedCriteria criteria) {
		return this.getHibernateTemplate().findByCriteria(criteria);
	}
	
	public List findByCriteriaGenerico(final DetachedCriteria criteria) {
		return this.getHibernateTemplate().findByCriteria(criteria);
	}

	public List<T> findByCriteria(final DetachedCriteria criteria, int firstResult, int maxResults) {
		return this.getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
	}
	
	public List findByCriteriaGenerico(final DetachedCriteria criteria, int firstResult, int maxResults) {
		return this.getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
	}

	public void save(final T transientObject) {
		super.getHibernateTemplate().saveOrUpdate(transientObject);
	}

	public Id saveOnly(final T transientObject) {
		return (Id) super.getHibernateTemplate().save(transientObject);
	}

	public void delete(final Id id) {
		super.getHibernateTemplate().delete(findById(id));
	}


	public void refresh(final T transientObject) {
		super.getHibernateTemplate().refresh(transientObject);
	}

	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	public void setPersistentClass(final Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	protected Query pasarParametros(final Query query,
	        final Map<String, Object> parametros) {
		log.debug("pasarParametros " + parametros.toString());
		for (final Map.Entry<String, Object> param : parametros.entrySet()) {
			if (param.getValue() instanceof String
			        && ((String)param.getValue()==null || ((String)param.getValue()).equals(""))) {
				log.debug("Cambiando cadena " + param.getKey()
				        + " vacía a asterisco (*)");
				param.setValue("*");
			}
			query.setParameter(param.getKey(), param.getValue());
		}
		return query;
	}

	public List<T> findByQuery(String query) {
		List<T> res;
		final Query q = getSession().createQuery(query);
		log.debug(q.getQueryString());
		res = q.list();
		return res;
	}

	public List<T> findByQuery(String query, Map<String, Object> params) {
		List<T> res;
		final Query q = getSession().getNamedQuery(query);
		log.debug(q.getQueryString());
		pasarParametros(q, params);
		res = q.list();
		return res;
	}

	public List<T> findByQuery(String query, int firstResult, int maxResults) {
		List<T> res;
		final Query q = getSession().createQuery(query);
		log.debug(q.getQueryString());
		q.setFirstResult(firstResult).setMaxResults(maxResults);
		res = q.list();
		return res;
	}

	public List<T> findByQuery(String query, Map<String, Object> params,
			int firstResult, int maxResults) {
		List<T> res;
		final Query q = getSession().createQuery(query);
		log.debug(q.getQueryString());
		pasarParametros(q, params);
		q.setFirstResult(firstResult).setMaxResults(maxResults);
		res = q.list();
		return res;
	}
	
	public List<T> findByNamedQuery(String queryName) {
		List<T> res;
		final Query q = getSession().getNamedQuery(queryName);
		log.debug(q.getQueryString());
		res = q.list();
		return res;
	}

	public List<T> findByNamedQuery(String queryName, Map<String, Object> params) {
		List<T> res;
		final Query q = getSession().getNamedQuery(queryName);
		log.debug(q.getQueryString());
		pasarParametros(q, params);
		res = q.list();
		return res;
	}
	
	public List<T> findByNamedQuery(String queryName, String[] propNames, Object[] filters) {
		List<T> res;
		Map params = new HashMap<String, Object>();
		for (int i = 0; i < propNames.length; i++) {
			params.put(propNames[i], filters[i]);
		}
		final Query q = getSession().getNamedQuery(queryName);
		log.debug(q.getQueryString());
		pasarParametros(q, params);
		res = q.list();
		return res;
	}

	public List<T> findByNamedQuery(String queryName, int firstResult, int maxResults) {
		List<T> res;
		final Query q = getSession().getNamedQuery(queryName);
		log.debug(q.getQueryString());
		q.setFirstResult(firstResult).setMaxResults(maxResults);
		res = q.list();
		return res;
	}

	public List<T> findByNamedQuery(String queryName, Map<String, Object> params,
			int firstResult, int maxResults) {
		List<T> res;
		final Query q = getSession().getNamedQuery(queryName);
		log.debug(q.getQueryString());
		pasarParametros(q, params);
		q.setFirstResult(firstResult).setMaxResults(maxResults);
		res = q.list();
		return res;
	}
	
	public T findByQueryUniqueResult(String query) {
		T res;
		final Query q = getSession().createQuery(query);
		log.debug(q.getQueryString());
		res = (T)q.uniqueResult();
		return res;
	}

	public T findByQueryUniqueResult(String query, Map<String, Object> params) {
		T res;
		final Query q = getSession().createQuery(query);
		log.debug(q.getQueryString());
		pasarParametros(q, params);
		res = (T)q.uniqueResult();
		return res;
	}

	public T findByQueryUniqueResult(String query, int firstResult, int maxResults) {
		T res;
		final Query q = getSession().createQuery(query);
		log.debug(q.getQueryString());
		q.setFirstResult(firstResult).setMaxResults(maxResults);
		res = (T)q.uniqueResult();
		return res;
	}

	public T findByQueryUniqueResult(String query, Map<String, Object> params,
			int firstResult, int maxResults) {
		T res;
		final Query q = getSession().createQuery(query);
		log.debug(q.getQueryString());
		pasarParametros(q, params);
		q.setFirstResult(firstResult).setMaxResults(maxResults);
		res = (T)q.uniqueResult();
		return res;
	}
	
	public T findByNamedQueryUniqueResult(String queryName) {
		T res;
		final Query q = getSession().getNamedQuery(queryName);
		log.debug(q.getQueryString());
		res = (T)q.uniqueResult();
		return res;
	}

	public T findByNamedQueryUniqueResult(String queryName, Map<String, Object> params) {
		T res;
		final Query q = getSession().getNamedQuery(queryName);
		log.debug(q.getQueryString());
		pasarParametros(q, params);
		res = (T)q.uniqueResult();
		return res;
	}

	public T findByNamedQueryUniqueResult(String queryName, int firstResult, int maxResults) {
		T res;
		final Query q = getSession().getNamedQuery(queryName);
		log.debug(q.getQueryString());
		q.setFirstResult(firstResult).setMaxResults(maxResults);
		res = (T)q.uniqueResult();
		return res;
	}

	public T findByNamedQueryUniqueResult(String queryName, Map<String, Object> params,
			int firstResult, int maxResults) {
		T res;
		final Query q = getSession().getNamedQuery(queryName);
		log.debug(q.getQueryString());
		pasarParametros(q, params);
		q.setFirstResult(firstResult).setMaxResults(maxResults);
		res = (T)q.uniqueResult();
		return res;
	}
	
	
	/* probando */
	public List<Object[]> findVariasTablasByNamedQuery(final String queryName,
	        final Map<String, Object> params) {
		final String[] paramNames = params.keySet().toArray(new String[] {});
		final Object[] values = params.values().toArray();
		return super.getHibernateTemplate().findByNamedQueryAndNamedParam(
		        queryName, paramNames, values);
	}


    public int countByCriteria(final DetachedCriteria criteria) throws DataAccessException {
        Integer count = (Integer)this.getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria executableCriteria = criteria.getExecutableCriteria(session);
                executableCriteria.setProjection(Projections.rowCount());
                for (Object result : executableCriteria.list()) {
                    if (result instanceof Integer) {
                        return (Integer) result;
                    }
                }
                return -1;
            }
        });

        return count.intValue();
    }
    
    public int countByCriteriaColumnaDistinct(final DetachedCriteria criteria, final String propertyName) throws DataAccessException {
        Integer count = (Integer)this.getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria executableCriteria = criteria.getExecutableCriteria(session);
                executableCriteria.setProjection(Projections.countDistinct(propertyName));
                for (Object result : executableCriteria.list()) {
                    if (result instanceof Integer) {
                        return (Integer) result;
                    }
                }
                return -1;
            }
        });

        return count.intValue();
    }

	public void deleteAll(Collection<T> collection) {
		super.getHibernateTemplate().deleteAll(collection);
	}
	
	
	public int ejecutaSQLQueryUpdate(String query){
		final SQLQuery q = getSession().createSQLQuery(query);
		log.debug(q.getQueryString());
	    int res = q.executeUpdate();
		return res;
	}
	
	public int ejecutaSQLQueryUpdate(String query, Map<String, Object> params){
		final SQLQuery q = getSession().createSQLQuery(query);
		log.debug(q.getQueryString());
	    pasarParametros(q, params);
	    int res = q.executeUpdate();
		return res;
	}
	
	public Object ejecutaSQLQuerySelect(String query){
		List<T> res;
		final SQLQuery q = getSession().createSQLQuery(query);
	    log.debug(q.getQueryString());
	    res = q.list();
	    return res;
	}
	
	public Object ejecutaSQLQuerySelect(String query, Map<String, Object> params){
		List<T> res;
		final SQLQuery q = getSession().createSQLQuery(query);
	    log.debug(q.getQueryString());
	    pasarParametros(q, params);
	    res = q.list();
	    return res;
	}
	
	public Object ejecutaNamedQuerySelect(String queryName){
		List<T> res;
		final Query q = getSession().getNamedQuery(queryName);
	    log.debug(q.getQueryString());
	    res = q.list();
	    return res;
	}
	public Object ejecutaNamedQuerySelect(String queryName, Map<String, Object> params){
		List<T> res;
		final Query q = getSession().getNamedQuery(queryName);
	    log.debug(q.getQueryString());
	    pasarParametros(q, params);
	    res = q.list();
	    return res;
	}
}
