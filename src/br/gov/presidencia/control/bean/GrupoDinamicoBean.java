package br.gov.presidencia.control.bean;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import br.gov.presidencia.facade.GrupoFacade;
import br.gov.presidencia.model.Grupo;
import br.gov.presidencia.model.GrupoDinamico;
import br.gov.presidencia.model.Usuario;

@Named
@ViewScoped
public class GrupoDinamicoBean extends AbstractBean implements Serializable{
	
	@Inject
	private GrupoFacade grupoFacade;
	
	private List<Grupo> lista;
	
	private Grupo grupoSelecionado;

	
	public List<Grupo> getListaBase(){
			return this.getGrupoFacade().listAll();
	}
	
	public String salvar(){
		for (Grupo grupo : this.getLista()) {
			salvar(grupo);
		}
		return null;
	}

	private void salvar(Grupo grupo) {
		try {
			this.grupoFacade.save(grupo);
		} catch (Exception e) {
			super.displayErrorMessageToUser("Erro ao Salvar o Grupo: "+grupo.getNome());
			e.printStackTrace();
		}
		super.displayInfoMessageToUser("Salvo com sucesso");
	}
		
	public void selecionarTecnico(Usuario tec){
		this.grupoSelecionado.getDinamico().setResponsavel(tec);
		this.salvar(this.grupoSelecionado);
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



	/**
	 * 
	 */
	private static final long serialVersionUID = -7114880885178732452L;

}
