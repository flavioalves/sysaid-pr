package br.gov.presidencia.dao;

import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

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
	
	public List<ParametroControle> findPorParam(String param, int tipo){
		
		String nomeQuery = null;
		switch (tipo) {
		case 1:
			nomeQuery = "ParametroControle.findPorParam";
			break;

		case 2:
			nomeQuery = "ParametroControle.findRel";
			break;
		}
			

		 TypedQuery<ParametroControle> query =
			      this.getEntityManager().createNamedQuery(nomeQuery, ParametroControle.class);
		 if(param != null){
			 query.setParameter("param", param);
		 }
		 
		return query.getResultList();
		
	}

	public void limpaFiltro(String id) {
		String sql = "delete from MGD_CONTROLE_PARAM where PARAM = :param";
		Query query = this.getEntityManager().createNativeQuery(sql);
		query.setParameter("param", id);
		query.executeUpdate();
		
	}

}
