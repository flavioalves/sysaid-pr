package br.gov.presidencia.control.bean;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
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
import br.gov.presidencia.facade.ParametroControleFacade;
import br.gov.presidencia.facade.UsuarioFacade;
import br.gov.presidencia.model.CustValue;
import br.gov.presidencia.model.Grupo;
import br.gov.presidencia.model.OrdemServico;
import br.gov.presidencia.model.ParametroControle;
import br.gov.presidencia.model.Usuario;
import br.gov.presidencia.util.ControleSelecaoSingleton;

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
	
	@Inject
	private ParametroControleFacade paramControleFacade;
	
	private Grupo grupo;
	
	private List<String> permissoes;
	
	private List<String> filtros;
	
	private List<OrdemServico> listaOS;
	
	private List<Usuario> listaTecnicos;
	
	private List<SelectItem> listaGrupoSelect;
	
	private String grupoId;
	
	private Usuario tecnicoSelecionado;
	
	private Boolean bloqueaBotao;
	
	private int tipoSeguranca = 0;
	
	
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

    	//Bloqueia o botao Carregar
    	this.setBloqueaBotao(true);
    	
    	// Lista com filtros por Status
    	List<ParametroControle> listaParam = null;
    	
    	
    	// Verifica se consegui carregar o usuario 
    	if(getUsuarioLogadoCookie() != null){
    		//Ler permissoes do Usuario Logado Por Grupo
    		permissoes = getGrupoFacade().lerPermissaoUsuarioPorGrupo(getUsuarioLogadoCookie());
    		if(permissoes != null){
	    		for(String per : permissoes){
	    			if(per.equalsIgnoreCase("All")){
	    				tipoSeguranca = Usuario.All;
	    				break;
	    			}else if(per.equalsIgnoreCase("AssigendGroupAndAssignedOnly")){
	    				tipoSeguranca = Usuario.AssigendGroupAndAssignedOnly;
	    			}else if(per.equalsIgnoreCase("AssignedOnly") && tipoSeguranca == 0){
	    				tipoSeguranca = Usuario.AssignedOnly;
	    			}
	    		}
    		}
    		
    		
    		//Ler filtros por status
    		listaParam = getParamControleFacade().findPorParam(getUsuarioLogadoCookie().getUserName()+"-status",  ParametroControle.PARAM_STATUS);
    		filtros = new ArrayList<String>();
    		if(listaParam != null){
	    		for(ParametroControle pr : listaParam){
	    			filtros.add(pr.getValor());
	    		}
    		}else{
    			filtros.add("1");
    		}
    	}
    	
		//Prepara list de OS no modelo Lazy Load
		this.modelList = new LazyDataModel<OrdemServico>() {

			private static final long serialVersionUID = 1L;

			@Override
	        public List<OrdemServico> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
				String sort = "DESC";
				List<OrdemServico> listaRetorno = null;
				if(sortOrder != null && SortOrder.ASCENDING.name().equalsIgnoreCase(sortOrder.name())){
					sort = "ASC";
				}
				
				
				try {
					//Estou pasando os dados da segunranca na busca por OS
					listaRetorno =  getOrdemServicoFacade().listAll(first,pageSize,sortField,sort,getFiltrosInt(), tipoSeguranca,getUsuarioLogadoCookie());
					} catch (SQLException e) {
						displayErrorMessageToUser("Erro SQL ao recuperar a descrição da OS");
						e.printStackTrace();
					} catch (IOException e) {
						displayErrorMessageToUser("Erro de IO ao recuperar a descrição da OS");
						e.printStackTrace();
					}
				
				if(listaRetorno != null ){
				Iterator<OrdemServico> lista =	listaRetorno.iterator();

					while (lista.hasNext()) {
						OrdemServico os = lista.next(); // must be called before you can call i.remove()
						if(ControleSelecaoSingleton.getInstance().isOSSelecionada(os.getId().toString(),getUsuarioLogadoCookie()) ){
							lista.remove();
						}
					}
				
				}
				return listaRetorno;
	        	} 
	        };

	        //Nao estou utilizando essa informacao
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
					this.listaTecnicos = this.usuarioFacade.listAllDisponiveisPorGrupo(grupoId, true);
					//Lista estah cheia limpa a Fila set Ativo = False
					if(this.listaTecnicos == null || this.listaTecnicos.isEmpty()){
						this.ordemServicoFacade.limpaFilaPorGrupoHoje(grupoId);
					}
					//Verifica fila novamente
					this.listaTecnicos = this.usuarioFacade.listAllDisponiveisPorGrupo(grupoId, true);
				}else{
					//this.listaUsuarios = this.grupo.getTecnicos();
					this.listaTecnicos = this.usuarioFacade.listAllDisponiveisPorGrupo(grupoId, false);
				}
			}
			
		}
	}
	
	public void onFiltroChange(){
		
		if (getUsuarioLogadoCookie() != null) {
			
			String id = getUsuarioLogadoCookie().getUserName() + "-status";
			
			try {
				this.paramControleFacade.limpaFiltro(id);
			} catch (Exception e1) {
				this.displayErrorMessageToUser("Erro ao excluir filtro. " + e1.getMessage());
				e1.printStackTrace();
				return;
			}

			for (String valor : getFiltros()) {

				ParametroControle param = new ParametroControle();
				param.setParam(id);
				param.setValor(valor);
				param.setTipo(1);
				try {
					this.paramControleFacade.save(param);
				} catch (Exception e) {
					this.displayErrorMessageToUser("Erro ao configurar filtro. " + e.getMessage());
					e.printStackTrace();
					return;
				}
			}
			this.displayInfoMessageToUser("Filtro salvo.");
		}

	}
	
	public String salvarTecnico(){
		try {
			this.ordemServicoFacade.salvarOSComRegraDeNegocio(this.grupo, this.getListaOS(), this.getTecnicoSelecionado(), super.getUsuarioLogadoCookie());
			this.removeOSSelecionada(super.getUsuarioLogadoCookie().getUserName());
			super.displayInfoMessageToUser("Salvo com sucesso");
		} catch (Exception e) {
			super.displayErrorMessageToUser("Erro ao Salvar OS com regras de negocio "+e.getMessage());
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	
    public void resetGrupoTecnicos() {
    	this.grupoId = null;
    	this.listaTecnicos = null;
    	
    	if(getUsuarioLogadoCookie() != null){
    		saveOSSelecionada(getUsuarioLogadoCookie().getUserName(), getListaOS());
    	}

    }
    
    public void onRowSelect(SelectEvent event) {  
    	//OrdemServico os = (OrdemServico)event.getObject();
    	if(getListaOS() == null || getListaOS().isEmpty()){
    		this.setBloqueaBotao(true);
    	}else{
    		this.setBloqueaBotao(false);
    	}
    }
    
    public void onRowUnselect(UnselectEvent  event) {
    	//OrdemServico os = (OrdemServico)event.getObject();
    	if(getListaOS() == null || getListaOS().isEmpty()){
    		this.setBloqueaBotao(true);
    	}else{
    		this.setBloqueaBotao(false);
    	}
    }
    
    public void liberarOSSelecionadas(){
    	if(getUsuarioLogadoCookie() != null){
    		removeOSSelecionada(getUsuarioLogadoCookie().getUserName());	
    	}
    	
    }
    
	public void saveOSSelecionada(String userName, List<OrdemServico> listaNumeroOS){
		for(OrdemServico numeroOS : listaNumeroOS){
			 ControleSelecaoSingleton.getInstance().saveOSSelecionada(userName, numeroOS.getId().toString());
		}
		ControleSelecaoSingleton.getInstance();
	}
	
	public Boolean isOSSelecionada(String numeroOS){	
		return ControleSelecaoSingleton.getInstance().isOSSelecionada(numeroOS,getUsuarioLogadoCookie());
	
	}
	
	
	public void removeOSSelecionada(String userName){
		ControleSelecaoSingleton.getInstance().removeOSSelecionada(userName);
		ControleSelecaoSingleton.getInstance();
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
		return  this.listaOS;
	}

	public void setListaOS(List<OrdemServico> listaOS) {
		this.listaOS = listaOS;
	}

	
	public List<SelectItem> getListaStatus() {
		List<SelectItem> listaStatus = new ArrayList<SelectItem>();
		
		List<CustValue> lista = this.ordemServicoFacade.listaCustValuesporTipo("status");
		for(CustValue c :lista){
			listaStatus.add(new SelectItem(c.getValueKey(), c.getValueCaption()));
		}

		return listaStatus;
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
	
	public List<String> getListaGrupoLista() {
		List<String> listaGrupo = new ArrayList<String>();
		List<Grupo> lista = this.getGrupoFacade().listAll();
		for(Grupo g :lista){
			listaGrupo.add(g.getNome());
		}
		return listaGrupo;
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




	public List<Usuario> getListaTecnicos() {
		return listaTecnicos;
	}

	public void setListaUsuarios(List<Usuario> listaTecnicos) {
		this.listaTecnicos = listaTecnicos;
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

	public List<String> getFiltros() {
		if(filtros == null ){
			filtros = new ArrayList<String>();
			filtros.add("1");
		}
		return filtros;
	}
	
	public List<Integer> getFiltrosInt(){
		List<Integer> lista = new ArrayList<Integer>();
		for(String v : getFiltros()){
			lista.add(new Integer(v));
		}
		return lista;
	}

	public void setFiltros(List<String> filtros) {
		this.filtros = filtros;
	}




	public ParametroControleFacade getParamControleFacade() {
		return paramControleFacade;
	}

	public void setParamControleFacade(ParametroControleFacade paramControleFacade) {
		this.paramControleFacade = paramControleFacade;
	}

	public List<String> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<String> permissoes) {
		this.permissoes = permissoes;
	}




	/**
	 * 
	 */
	private static final long serialVersionUID = 6988553902681606276L;

}