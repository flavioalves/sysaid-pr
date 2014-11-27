package br.gov.presidencia.dao;

import javax.inject.Named;

import br.gov.presidencia.model.ParametroControle;

@Named
public class ParametroControleDao extends GenericDao<ParametroControle> {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -6478997078022327110L;
	
	public ParametroControleDao() {
		super(ParametroControle.class);
	}

}
