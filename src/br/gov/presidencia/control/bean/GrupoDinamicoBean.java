package br.gov.presidencia.control.bean;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import br.gov.presidencia.facade.GrupoFacade;
import br.gov.presidencia.facade.UsuarioFacade;
import br.gov.presidencia.model.Grupo;
import br.gov.presidencia.model.Usuario;

@Named
@ViewScoped
public class GrupoDinamicoBean extends AbstractBean implements Serializable{
	
	@Inject
	private GrupoFacade grupoFacade;
	
	@Inject
	private UsuarioFacade usuarioFacade;
	
	private List<Grupo> lista;
	
	private Usuario usuarioSelecionado;
	
	private Grupo grupoSelecionado;

	
	public List<Grupo> getListaBase(){
			return this.getGrupoFacade().listAll();
	}
	
	public String salvar(){
		for (Grupo grupo : this.getLista()) {
			try {
				this.grupoFacade.save(grupo);
			} catch (Exception e) {
				super.displayErrorMessageToUser("Erro ao Salvar Grupo. "+e.getMessage());
				e.printStackTrace();
				return null;
			}
		}
		super.displayInfoMessageToUser("Salvo com sucesso");
		return null;
	}

	public void selecionarTecnico(){
		this.selecionarTecnico(getUsuarioSelecionado());
	}
		
	public void selecionarTecnico(Usuario tec){
		this.grupoSelecionado.getDinamico().setResponsavel(tec);
		try {
			this.grupoFacade.save(this.grupoSelecionado);
		} catch (Exception e) {
			super.displayErrorMessageToUser("Erro ao Salvar Grupo. "+e.getMessage());
			e.printStackTrace();
			return;
		}
		super.displayInfoMessageToUser("Salvo com sucesso");
	}
	
	public void selecionarGrupo(Grupo grupo){
		this.grupoSelecionado = grupo;
	}
	
	public List<Usuario> getListaUsuarios() {
		if(this.grupoSelecionado != null){
			this.grupoSelecionado = this.grupoFacade.getDao().find(grupoSelecionado.getNome());
			return this.grupoSelecionado.getTecnicos();
		}
		return null;
	}
	
	public List<Usuario> findUsuarioByNome(String nome){
		return this.usuarioFacade.findUsuarioByNome(nome);
	}

	public GrupoFacade getGrupoFacade() {
		return grupoFacade;
	}


	public void setGrupoFacade(GrupoFacade grupoFacade) {
		this.grupoFacade = grupoFacade;
	}
	
	public void setLista(List<Grupo> lista) {
		this.lista = lista;
	}

	public List<Grupo> getLista(){
		if(this.lista == null){
			this.lista = this.getListaBase();
		}
		return this.lista;
	}


	public Grupo getGrupoSelecionado() {
		return grupoSelecionado;
	}



	public void setGrupoSelecionado(Grupo grupoSelecionado) {
		this.grupoSelecionado = grupoSelecionado;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = -7114880885178732452L;

}
