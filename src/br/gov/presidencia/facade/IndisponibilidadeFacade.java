package br.gov.presidencia.facade;

import javax.inject.Inject;

import br.gov.presidencia.dao.GenericDao;
import br.gov.presidencia.dao.IndisponibilidadeDao;
import br.gov.presidencia.model.Indisponibilidade;

public class IndisponibilidadeFacade extends GenericFacade<Indisponibilidade> {

	@Inject
	private IndisponibilidadeDao indisponibilidadeDao;
	
	
	@Override
	public GenericDao<Indisponibilidade> getDao() {
		return indisponibilidadeDao;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7767970906695084372L;

}
