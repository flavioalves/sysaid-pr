package br.gov.presidencia.facade;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.gov.presidencia.dao.GenericDao;
import br.gov.presidencia.dao.UsuarioDao;
import br.gov.presidencia.model.Usuario;

@Named
public class UsuarioFacade extends GenericFacade<Usuario> {
	
	@Inject
	private UsuarioDao usuarioDao;
	
	@Override
	public GenericDao<Usuario> getDao() {
		return usuarioDao;
	}
	
	public List<Usuario> listAllDisponiveisPorGrupo(String grupoId, Boolean verificaFila){
		getDao().beginTransaction();
		List<Usuario> lista = this.getUsuarioDao().listAllDisponiveisPorGrupo(grupoId, verificaFila);
		getDao().commit();
		
		return lista;
	}
	
	public List<Usuario> listAllPorGrupoRelatorioPerido(String grupoId, Date inicio, Date fim ){
		List<Usuario> lista = this.getUsuarioDao().listAllPorGrupoRelatorioPerido(grupoId, inicio, fim);
		
		return lista;
	}
	
	public List<Usuario> findUsuarioByNome(String nome){
		return this.getUsuarioDao().findUsuarioByNome(nome);
	}
	
	public Usuario find(String userName) {
		return usuarioDao.find(userName);
	}
	
	

	public UsuarioDao getUsuarioDao() {
		return usuarioDao;
	}

	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 7419336992415458594L;

}
