package br.gov.presidencia.facade;

import java.util.List;

import javax.inject.Inject;

import br.gov.presidencia.dao.GenericDao;
import br.gov.presidencia.dao.ParametroControleDao;
import br.gov.presidencia.model.ParametroControle;

public class ParametroControleFacade extends GenericFacade<ParametroControle> {
	
	@Inject
	private ParametroControleDao parametroControleDao;

	/**
	 * 
	 */
	private static final long serialVersionUID = 273215200505982239L;

	@Override
	public GenericDao<ParametroControle> getDao() {
		return this.parametroControleDao;
	}
	
	public List<ParametroControle> findPorParam(String param, int tipo){
		return getParametroControleDao().findPorParam(param, tipo);
	}

	public ParametroControleDao getParametroControleDao() {
		return parametroControleDao;
	}

	public void setParametroControleDao(ParametroControleDao parametroControleDao) {
		this.parametroControleDao = parametroControleDao;
	}


	public void limpaFiltro(String id) throws Exception {
		try {
			getDao().beginTransaction();
			getParametroControleDao().limpaFiltro(id);
			getDao().commit();
		} catch (Exception e) {
			getDao().rollback();
			throw e;
		}
		
	}
	
	

}
