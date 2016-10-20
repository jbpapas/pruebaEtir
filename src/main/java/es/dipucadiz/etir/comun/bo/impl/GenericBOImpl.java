package es.dipucadiz.etir.comun.bo.impl;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.GenericBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.exception.GadirServiceException;


public class GenericBOImpl<T, Id extends Serializable> extends AbstractGenericBOImpl<T, Id> implements
		GenericBO<T, Id> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 978884538847561170L;


	protected final Log log = LogFactory.getLog(getClass());
	
	
	private Class<T> persistentClass;
	
	private DAOBase<T, Id> dao;

	
	public GenericBOImpl(Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}	
	
	
	public Class<T> getPersistentClass() {
		return persistentClass;
	}


	public void setPersistentClass(Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}


	public DAOBase<T, Id> getDao() {
		return dao;
	}


	public void setDao(DAOBase<T, Id> dao) {
		this.dao = dao;
	}


	public Log getLog() {
		return log;
	}

	
	public void save(final T transientObject) throws GadirServiceException {
		try {
			dao.save(transientObject);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al grabar el objeto " + transientObject,
			        e);
		}
	}

	public void saveOnly(final T transientObject) throws GadirServiceException {
		try {
			dao.saveOnly(transientObject);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al insertar el objeto " + transientObject,
			        e);
		}
	}


	public void auditorias(T transientObject, Boolean saveOnly)
			throws GadirServiceException {
		throw new GadirServiceException("No se puede usar el metodo auditorias en el GenericBoImpl");
	}
	
}
