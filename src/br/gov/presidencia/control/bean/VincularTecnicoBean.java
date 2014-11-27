package br.gov.presidencia.control.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.gov.presidencia.facade.GrupoFacade;
import br.gov.presidencia.facade.OrdemServicoFacade;
import br.gov.presidencia.facade.UsuarioFacade;
import br.gov.presidencia.model.Grupo;
import br.gov.presidencia.model.OrdemServico;
import br.gov.presidencia.model.Usuario;

/**
 * @author Alvaro
 *
 */
@Named
@ViewScoped
public class VincularTecnicoBean extends AbstractBean implements Serializable{
	
	@Inject
	private GrupoFacade grupoFacade;
	
	@Inject
	private OrdemServicoFacade ordemServicoFacade;
	
	@Inject
	private UsuarioFacade usuarioFacade;
	
	private Grupo grupo;
	
	private List<OrdemServico> listaOS;
	
	private List<Usuario> listaUsuarios;
	
	private List<SelectItem> listaGrupoSelect;
	
	private String grupoId;
	
	private Usuario tecnicoSelecionado;
	
	private Boolean bloqueaBotao;
	
	
/*	public String selecionarTecnico(){
		super.saveObjectInSession("tecnico", this.getTecnicoSelecionado());
		return null;
	}*/
	
	private LazyDataModel<OrdemServico> modelList;

    public LazyDataModel<OrdemServico> getModelList() {
          return this.modelList;
    }
    
    @PostConstruct
	public void init(){
		
		this.modelList = new LazyDataModel<OrdemServico>() {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
	        public List<OrdemServico> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
				String sort = "ASC";
				if(sortOrder != null && SortOrder.DESCENDING.name().equalsIgnoreCase(sortOrder.name())){
					sort = "DESC";
				}
				
				
				return getOrdemServicoFacade().listAll(first,pageSize,sortField,sort);
	        	} 
	        };

	        int totalRowCount = 100; //logical row count based on a count query
    		 this.modelList.setRowCount(totalRowCount);
	}

	
	public List<Grupo> getListaGrupoBase(){
		return this.getGrupoFacade().listAll();
	}
	
	public List<OrdemServico> getListaOSBase(){
		return this.getOrdemServicoFacade().listAll();
	}
	
	public void onGrupoChange(){
		if(grupoId != null){
			this.grupo = this.getGrupoFacade().find(grupoId);
			
			if(this.grupo != null){
				if(this.grupo.getDinamico() != null && this.grupo.getDinamico().getAtivo() != null && this.grupo.getDinamico().getAtivo()){
					//Verifica o fila de OS
					this.listaUsuarios = this.usuarioFacade.listAllDisponiveisPorGrupo(grupoId, true);
					//Lista estï¿½ï¿½ cheia limpa a Fila set Ativo = False
					if(this.listaUsuarios == null || this.listaUsuarios.isEmpty()){
						this.ordemServicoFacade.limpaFilaPorGrupoHoje(grupoId);
					}
					//Verifica fila novamente
					this.listaUsuarios = this.usuarioFacade.listAllDisponiveisPorGrupo(grupoId, true);
				}else{
					//this.listaUsuarios = this.grupo.getTecnicos();
					this.listaUsuarios = this.usuarioFacade.listAllDisponiveisPorGrupo(grupoId, false);
				}
			}
			
		}
	}
	
	public String salvarTecnico(){
		try {
			this.ordemServicoFacade.salvarOSComRegraDeNegocio(this.grupo, this.getListaOS(), this.getTecnicoSelecionado(), super.getUsuarioLogadoCookie());
			super.displayInfoMessageToUser("Salvo com sucesso");
		} catch (Exception e) {
			super.displayErrorMessageToUser("Erro ao Salvar os dados "+e.getMessage());
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	
    public void resetGrupoTecnicos() {
    	this.grupoId = null;
    	this.listaUsuarios = null;

    }
    
    public void onRowSelect(SelectEvent event) {
    	OrdemServico os = (OrdemServico)event.getObject();
    if( os != null && super.isObjectInAppContext(os.getId().toString())){
    		Usuario user = (Usuario)super.getObjectInAppContext(os.getId().toString());
    		this.displayErrorMessageToUser("OS - "+os.getId() +" marcada pelo usuário: "+user.getUserNameCalculado());
    		this.setBloqueaBotao(true);
    	}else{
    		super.saveObjectInAppContext(os.getId().toString(), getUsuarioLogadoCookie());
    		this.displayInfoMessageToUser("OS - "+os.getId() +" selecionada");
    		this.setBloqueaBotao(false);
    	}

    }
 
    public void onRowUnselect(UnselectEvent event) {
    	OrdemServico os = (OrdemServico)event.getObject();
    	if( os != null && super.isObjectInAppContext(os.getId().toString())){
    		Usuario user = (Usuario)super.getObjectInAppContext(os.getId().toString());
    		if(user != null && user.getUserName().equals(getUsuarioLogadoCookie().getUserName())){
    			super.removeObjectInAppContext(os.getId().toString());
    		}else{
    			this.displayErrorMessageToUser("OS - "+os.getId() +" marcada pelo usuário: "+user.getUserNameCalculado());
    		}
    		
    	}
    }
	

	public GrupoFacade getGrupoFacade() {
		return grupoFacade;
	}

	public void setGrupoFacade(GrupoFacade grupoFacade) {
		this.grupoFacade = grupoFacade;
	}

	public OrdemServicoFacade getOrdemServicoFacade() {
		return ordemServicoFacade;
	}

	public void setOrdemServicoFacade(OrdemServicoFacade ordemServicoFacade) {
		this.ordemServicoFacade = ordemServicoFacade;
	}

	public UsuarioFacade getUsuarioFacade() {
		return usuarioFacade;
	}

	public void setUsuarioFacade(UsuarioFacade usuarioFacade) {
		this.usuarioFacade = usuarioFacade;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}


	public List<OrdemServico> getListaOS() {
		return listaOS;
	}

	public void setListaOS(List<OrdemServico> listaOS) {
		this.listaOS = listaOS;
	}


	public List<SelectItem> getListaGrupoSelect() {
		if(listaGrupoSelect == null){
			listaGrupoSelect = new ArrayList<SelectItem>();
			List<Grupo> lista = this.getGrupoFacade().listAll();
			for(Grupo g :lista){
				listaGrupoSelect.add(new SelectItem(g.getNome(), g.getNome()));
			}
		}
		return listaGrupoSelect;
	}

	public void setListaGrupoSelect(List<SelectItem> listaGrupoSelect) {
		this.listaGrupoSelect = listaGrupoSelect;
	}

	public String getGrupoId() {
		return grupoId;
	}

	public void setGrupoId(String grupoId) {
		this.grupoId = grupoId;
	}




	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}


	public Usuario getTecnicoSelecionado() {
		return tecnicoSelecionado;
	}

	public void setTecnicoSelecionado(Usuario tecnicoSelecionado) {
		this.tecnicoSelecionado = tecnicoSelecionado;
	}

	public Boolean getBloqueaBotao() {
		return bloqueaBotao;
	}

	public void setBloqueaBotao(Boolean bloqueaBotao) {
		this.bloqueaBotao = bloqueaBotao;
	}




	/**
	 * 
	 */
	private static final long serialVersionUID = 6988553902681606276L;

}
