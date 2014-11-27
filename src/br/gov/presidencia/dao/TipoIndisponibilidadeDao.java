package br.gov.presidencia.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import br.gov.presidencia.model.TipoIndisponibilidade;

public class TipoIndisponibilidadeDao extends GenericDao<TipoIndisponibilidade> {
	

	public TipoIndisponibilidadeDao() {
		super(TipoIndisponibilidade.class);
	}
	
	public List<TipoIndisponibilidade> findAllAtivo(){
		 TypedQuery<TipoIndisponibilidade> query =
			      this.getEntityManager().createNamedQuery("TipoIndisponibilidade.findAllAtivo", TipoIndisponibilidade.class);
		return query.getResultList();
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 881430713450360801L;	

}
