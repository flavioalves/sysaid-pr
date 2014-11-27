package br.gov.presidencia.util;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.omnifaces.cdi.ViewScoped;

public class Producers {
	
	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("sysaid");
	
	@Produces
	@ViewScoped
	public EntityManager criaEntityManager() {
		return entityManagerFactory.createEntityManager();
	}
	
	public void finaliza(@Disposes EntityManager entityManager) {
		if (entityManager.isOpen()) {
            entityManager.close();
        }
	}
	
}
