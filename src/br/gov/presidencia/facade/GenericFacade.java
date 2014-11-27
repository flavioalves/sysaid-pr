package br.gov.presidencia.facade;

import java.io.Serializable;
import java.util.List;

import br.gov.presidencia.dao.GenericDao;

public abstract class GenericFacade<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4392915011052684066L;
	
	public abstract GenericDao<T> getDao();
	
	public void save(T entity) throws Exception {
		try {
			getDao().beginTransaction();
			getDao().save(entity);
			getDao().commit();
		} catch (Exception e) {
			getDao().rollback();
			throw e;
		}
	}

	public void update(T entity) throws Exception {
		try {
			getDao().beginTransaction();
			getDao().update(entity);
			getDao().commit();
		} catch (Exception e) {
			getDao().rollback();
			throw e;
		}
	}

	public void delete(T entity) throws Exception {
		try {
			getDao().beginTransaction();
			getDao().delete(entity);
			getDao().commit();
		} catch (Exception e) {
			getDao().rollback();
			throw e;
		}
	}

	public T find(Object id) {
		T entity = getDao().find(id);
		return entity;
	}

	public List<T> listAll() {
		List<T> result = getDao().findAll();
		return result;
	}

}
