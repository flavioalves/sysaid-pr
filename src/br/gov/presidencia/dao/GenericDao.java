package br.gov.presidencia.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;

public abstract class GenericDao<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	
	@Inject
	private EntityManager em;

	private Class<T> entityClass;

	public void beginTransaction() {
		getEntityManager().getTransaction().begin();
	}

	public void commit() {
		getEntityManager().getTransaction().commit();
	}

	public void rollback() {
		getEntityManager().getTransaction().rollback();
	}

	public void flush() {
		getEntityManager().flush();
	}

	//public void joinTransaction(EntityManager newEm) {
	//	em = newEm;
	//}

	public GenericDao(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public void save(T entity) {
		getEntityManager().persist(entity);
	}
	
	public void delete(Object id, Class<T> classe) {
		T entityToBeRemoved = getEntityManager().getReference(classe, id);
		 
        getEntityManager().remove(entityToBeRemoved);
	}
	
	public void delete(T entityClass) {
        getEntityManager().remove(entityClass);
	}

	public T update(T entity) {
		return getEntityManager().merge(entity);
	}

	public void refresh(T entity) {
		getEntityManager().refresh(entity);
	}
	
	public T find(Object id) {
		return this.getEntityManager().find(entityClass, id);
	}

	public T findReferenceOnly(int entityID) {
		return getEntityManager().getReference(entityClass, entityID);
	}

	public List<T> findAll() {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> c = cb.createQuery(entityClass);
        @SuppressWarnings("unused")
		Root<T> rootCriteria = c.from(entityClass);
/*        Predicate clause = clausulaWhere(cb, rootCriteria);
        if(clause != null) {
        	c.where(clause);
        }*/
        TypedQuery<T> q = getEntityManager().createQuery(c);
        return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	protected T findOneResult(String namedQuery, Map<String, Object> parameters) {
		T result = null;

		try {
			Query query = getEntityManager().createNamedQuery(namedQuery);

			// Method that will populate parameters if they are passed not null and empty
			if (parameters != null && !parameters.isEmpty()) {
				populateQueryParameters(query, parameters);
			}

			result = (T) query.getSingleResult();

		} catch (NoResultException e) {
			System.out.println("Nenhum resultado retornado pela query: " + namedQuery);  
		} catch (Exception e) {
			System.out.println("Erro durante a execu��o da query: " + e.getMessage() + "\n" + namedQuery);  
			e.printStackTrace();
		}

		return result;
	}
	
    @SuppressWarnings("unchecked")  
    protected List<T> findManyResults(String namedQuery, Map<String, Object> parameters){  
        List<T> result = null;  
  
        try{  
        	Query query = getEntityManager().createNamedQuery(namedQuery); 
  
            if (parameters != null && !parameters.isEmpty()) {  
                populateQueryParameters(query, parameters);  
            }
            
            result = query.getResultList();  
  
        } catch (NoResultException e) {  
            System.out.println("Nenhum resultado retornado pela query: " + namedQuery);  
        } catch (Exception e) {  
            System.out.println("Erro durante a execu��o da query: " + e.getMessage() + "\n" + namedQuery);  
            e.printStackTrace();  
        }  
  
        return result;  
    }
	

	private void populateQueryParameters(Query query, Map<String, Object> parameters) {
		for (Entry<String, Object> entry : parameters.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}

	public EntityManager getEntityManager() {
		/*
		if(em == null) {
			em = emf.createEntityManager();
		} else {
			if(!em.isOpen()) {
				em = emf.createEntityManager();
			}
		}
		*/
		//System.out.println(em.toString());
		return em;
	}
	
	public Predicate clausulaWhere(CriteriaBuilder cb, Root<T> rootCriteria) {
		return cb.equal(rootCriteria.get("ativo"), 1);
	}
	
	
	/**
	 * WorkAround para resolver o problema de tabela dinamica do Sysaid
	 * @return
	 * @throws SQLException
	 */
	protected  Connection getConnection() throws SQLException{

		Session session = (Session) em.getDelegate();
		SessionFactoryImplementor sfi = (SessionFactoryImplementor) session.getSessionFactory();
		ConnectionProvider cp = sfi.getConnectionProvider();
		Connection connection = cp.getConnection();

		return connection;

	}

	
}
