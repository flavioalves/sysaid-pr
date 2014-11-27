package br.gov.presidencia.dao;

import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.gov.presidencia.model.Grupo;

@Named
public class GrupoDao extends GenericDao<Grupo> {
	
	
	public GrupoDao() {
		super(Grupo.class);
	}
	
	public List<Grupo> findAllAtivo(){
		 TypedQuery<Grupo> query =
			      this.getEntityManager().createNamedQuery("Grupo.findAllAtivo", Grupo.class);
		return query.getResultList();
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 4770158458977556376L;


	public String carregaDadosPermissao(String nome) {
		
		String sql = "SELECT PERMISSION FROM USER_GROUPS WHERE group_name = :gruponome";
	
		Query query = getEntityManager().createNativeQuery(sql);
		query.setParameter("gruponome", nome);
		@SuppressWarnings("unchecked")
		byte[]retorno = (byte[])query.getSingleResult();
		
		return new String(retorno);
	}

}
