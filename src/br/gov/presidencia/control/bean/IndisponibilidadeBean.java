package br.gov.presidencia.control.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.mail.EmailException;
import org.omnifaces.cdi.ViewScoped;

import br.gov.presidencia.facade.GrupoFacade;
import br.gov.presidencia.facade.IndisponibilidadeFacade;
import br.gov.presidencia.facade.TipoIndisponibilidadeFacade;
import br.gov.presidencia.facade.UsuarioFacade;
import br.gov.presidencia.model.Grupo;
import br.gov.presidencia.model.Indisponibilidade;
import br.gov.presidencia.model.TipoIndisponibilidade;
import br.gov.presidencia.model.Usuario;
import br.gov.presidencia.util.EmailNotification;


@Named
@ViewScoped
public class IndisponibilidadeBean extends AbstractBean {
	
	@Inject
	private IndisponibilidadeFacade indisponibilidadeFacade;
	
	@Inject
	private TipoIndisponibilidadeFacade tipoFacade;
	
	@Inject
	private UsuarioFacade tecnicoFacade;
	
	@Inject
	private GrupoFacade grupoFacade;
	
	private Indisponibilidade indisponibilidade;
	
	private TipoIndisponibilidade tipoIndisponibilidade;
	
	private List<TipoIndisponibilidade> listaTipoIndisponibilidade;
	
	private List<Usuario> listaUsuarios;
	
	private List<SelectItem> listaTipoSelect;
	
	private List<SelectItem> listaGrupoSelect;
	
	private Usuario tecnico;
	
	private Usuario responsavelGrupo;
	
	private String grupoId;
	
    @PostConstruct
    public void init() {
    	tipoIndisponibilidade = new TipoIndisponibilidade(); 
    	indisponibilidade = new Indisponibilidade();
    }
    
/*    public Usuario recuperaTecnicoSelecionado(){
    	this.tecnico = (Usuario) super.getObjectInSession("tecnico");
    	return this.tecnico; 
    }*/
    
    public String limparTipo(){
    	tipoIndisponibilidade = new TipoIndisponibilidade();  
    	return null;
    }
    
    public String salvarTipo(){
    	if(this.getListaTipoIndisponibilidade()!= null && this.getListaTipoIndisponibilidade().contains(this.getTipoIndisponibilidade())){
    		this.displayErrorMessageToUser("Tipo jÃ¡ cadastrado");
    		return null;
    	}
    	
    	try {
    		this.getTipoIndisponibilidade().setAtivo(true);
			this.tipoFacade.save(this.getTipoIndisponibilidade());
			this.setTipoIndisponibilidade(new TipoIndisponibilidade());
			this.displayInfoMessageToUser("Salvo com sucesso");
		} catch (Exception e) {
			this.displayErrorMessageToUser("Erro ao cadastrar Tipo");
			e.printStackTrace();
		}
    	
    	return null;
    }
    
	public void onGrupoChange(){
		if(grupoId != null){
			Grupo grupo = this.grupoFacade.find(grupoId);
			if(grupo.getDinamico() != null && grupo.getDinamico().getResponsavel() != null){
				setResponsavelGrupo(grupo.getDinamico().getResponsavel());	
			}else{
				super.displayErrorMessageToUser("Grupo sem Responsável, defina na opção de Grupo Dinâmico.");
			}
			
			this.listaUsuarios = grupo.getTecnicos();
		}
	}
    
    public String removerTipo(){
    	this.getTipoIndisponibilidade().setAtivo(false);
    	try {
			this.tipoFacade.save(this.getTipoIndisponibilidade());
			this.setTipoIndisponibilidade(new TipoIndisponibilidade());
		} catch (Exception e) {
			this.displayErrorMessageToUser("Erro ao remover Tipo");
			e.printStackTrace();
		}
    	this.displayInfoMessageToUser("Removido com sucesso");
    	
    	return null;
    }
    
    public List<TipoIndisponibilidade> getListaTipoIndisponibilidadeBase(){
    	return this.tipoFacade.listAll();
    }
    
	public List<SelectItem> getListaTipoSelect() {
		if(listaTipoSelect == null){
			listaTipoSelect = new ArrayList<SelectItem>();
			List<TipoIndisponibilidade> lista = this.getTipoFacade().listAll();
			for(TipoIndisponibilidade t :lista){
				listaTipoSelect.add(new SelectItem(t, t.getNome()));
			}
		}
		return listaTipoSelect;
	}
	
	public void selecionarTecnico(Usuario tecnico){
		this.tecnico = tecnicoFacade.find(tecnico.getUserName());
		tecnico.getIndisponibilidades();
		this.setTecnico(tecnico);
	}
	
	
	public void salvarIndisponibilidade(){
		getIndisponibilidade().setAtivo(true);
		getIndisponibilidade().setUsuario(this.getTecnico());
		
		try {
			this.indisponibilidadeFacade.save(getIndisponibilidade());
			tecnicoFacade.getDao().refresh(this.getTecnico());
			super.displayInfoMessageToUser("Salvo com sucesso");
		} catch (Exception e) {
			super.displayErrorMessageToUser("Erro ao salvar Indiponibilidade.");
			e.printStackTrace();
		}
		
		EmailNotification notitifacao = new EmailNotification();
		try {
			notitifacao.envarEmailUsuario(getResponsavelGrupo(), this.getTecnico(), getIndisponibilidade());
		} catch (EmailException e) {
			super.displayErrorMessageToUser("Erro ao enviar e-mail de Notificação. "+e.getMessage());
			e.printStackTrace();
		}
		setIndisponibilidade(new Indisponibilidade());
	}
	
    public void removerIndisponibilidade(){
    	this.getIndisponibilidade().setAtivo(false);
    	try {
			this.indisponibilidadeFacade.save(this.getIndisponibilidade());
			this.setIndisponibilidade(new Indisponibilidade());
			tecnicoFacade.getDao().refresh(this.getTecnico());
			this.displayInfoMessageToUser("Removido com sucesso");
		} catch (Exception e) {
			this.displayErrorMessageToUser("Erro ao remover Tipo");
			e.printStackTrace();
		}
    	
    }

	public IndisponibilidadeFacade getIndisponibilidadeFacade() {
		return indisponibilidadeFacade;
	}


	public String getGrupoId() {
		return grupoId;
	}

	public void setGrupoId(String grupoId) {
		this.grupoId = grupoId;
	}

	public void setIndisponibilidadeFacade(
			IndisponibilidadeFacade indisponibilidadeFacade) {
		this.indisponibilidadeFacade = indisponibilidadeFacade;
	}


	public List<SelectItem> getListaGrupoSelect() {
		if(listaGrupoSelect == null){
			listaGrupoSelect = new ArrayList<SelectItem>();
			List<Grupo> lista = this.grupoFacade.listAll();
			for(Grupo g :lista){
				listaGrupoSelect.add(new SelectItem(g.getNome(), g.getNome()));
			}
		}
		return listaGrupoSelect;
	}

	public void setListaGrupoSelect(List<SelectItem> listaGrupoSelect) {
		this.listaGrupoSelect = listaGrupoSelect;
	}

	public TipoIndisponibilidadeFacade getTipoFacade() {
		return tipoFacade;
	}



	public void setTipoFacade(TipoIndisponibilidadeFacade tipoFacade) {
		this.tipoFacade = tipoFacade;
	}



	public Indisponibilidade getIndisponibilidade() {
		return indisponibilidade;
	}



	public void setIndisponibilidade(Indisponibilidade indisponibilidade) {
		this.indisponibilidade = indisponibilidade;
	}



	public TipoIndisponibilidade getTipoIndisponibilidade() {
		return tipoIndisponibilidade;
	}



	public void setTipoIndisponibilidade(TipoIndisponibilidade tipoIndisponibilidade) {
		this.tipoIndisponibilidade = tipoIndisponibilidade;
	}



	public List<TipoIndisponibilidade> getListaTipoIndisponibilidade() {
		return listaTipoIndisponibilidade;
	}



	public void setListaTipoIndisponibilidade(
			List<TipoIndisponibilidade> listaTipoIndisponibilidade) {
		this.listaTipoIndisponibilidade = listaTipoIndisponibilidade;
	}


	public Usuario getTecnico() {
		return tecnico;
	}

	public void setTecnico(Usuario tecnico) {
		this.tecnico = tecnico;
	}

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public Usuario getResponsavelGrupo() {
		return responsavelGrupo;
	}

	public void setResponsavelGrupo(Usuario responsavelGrupo) {
		this.responsavelGrupo = responsavelGrupo;
	}





	/**
	 * 
	 */
	private static final long serialVersionUID = 2334600022606852387L;

}
