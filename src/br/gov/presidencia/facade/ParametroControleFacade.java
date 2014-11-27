package br.gov.presidencia.facade;

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

}
