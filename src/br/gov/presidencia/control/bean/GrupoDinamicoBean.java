package br.gov.presidencia.control.bean;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import br.gov.presidencia.facade.GrupoFacade;
import br.gov.presidencia.model.Grupo;

@Named
@ViewScoped
public class GrupoDinamicoBean extends AbstractBean implements Serializable{
	
	@Inject
	private GrupoFacade grupoFacade;
	
	private List<Grupo> lista;
	
	public List<Grupo> getListaBase(){
			return this.getGrupoFacade().listAll();
	}
	
	
	
	public String salvar(){
		for (Grupo grupo : this.getLista()) {
			//System.out.println(grupo.getNome() +" - "+ grupo.getDinamico());
			try {
				this.grupoFacade.save(grupo);
			} catch (Exception e) {
				super.displayErrorMessageToUser("Erro ao Salvar o Grupo: "+grupo.getNome());
				e.printStackTrace();
			}
		}
		super.displayInfoMessageToUser("Salvo com sucesso");
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




	/**
	 * 
	 */
	private static final long serialVersionUID = -7114880885178732452L;

}
