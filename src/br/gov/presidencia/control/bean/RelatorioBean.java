package br.gov.presidencia.control.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import br.gov.presidencia.facade.GrupoFacade;
import br.gov.presidencia.facade.ParametroControleFacade;
import br.gov.presidencia.facade.UsuarioFacade;
import br.gov.presidencia.model.Grupo;
import br.gov.presidencia.model.ParametroControle;
import br.gov.presidencia.model.Usuario;

@Named
@ViewScoped
public class RelatorioBean extends AbstractBean {
	
	@Inject
	private UsuarioFacade usuarioFacade;
	
	@Inject
	private ParametroControleFacade paramControleFacade;
	
	private List<ParametroControle> listaParam;
	
	private Usuario usuarioSelecionado;
	
	private Date dataInicio;
	
	private Date dataFim;
	
	private Integer interval = 5;
	
	@Inject
	private GrupoFacade grupoFacade;
	
	private List<SelectItem> listaGrupoSelect;
	
	private String grupoRel1;
	private String grupoRel2;
	private String grupoRel3;
	private String grupoRel4;
	private String grupoRel5;
	private String grupoRel6;
	private String grupoRel7;
	private String grupoRel8;
	private String grupoRel9;
	private String grupoRel10;

	
    @PostConstruct
    public void init() {
    	this.listaParam = paramControleFacade.listAll();
    	ParametroControle param = recuperaPorParam("intervalo");
    	if(param != null){
    		this.interval =  Integer.valueOf(param.getValor());
    		if(this.interval <= 2){
    			this.interval = 3;
    		}
    	}
    	
    	 param = recuperaPorParam("grupoRel1");
    	if(param != null){
    		this.grupoRel1 =  param.getValor();
    	}
    	 param = recuperaPorParam("grupoRel2");
    	if(param != null){
    		this.grupoRel2 =  param.getValor();
    	}
    	 param = recuperaPorParam("grupoRel3");
    	if(param != null){
    		this.grupoRel3 =  param.getValor();
    	}
    	 param = recuperaPorParam("grupoRel4");
    	if(param != null){
    		this.grupoRel4 =  param.getValor();
    	}
    	 param = recuperaPorParam("grupoRel5");
    	if(param != null){
    		this.grupoRel5 =  param.getValor();
    	}
	   	 param = recuperaPorParam("grupoRel6");
	   	if(param != null){
	   		this.grupoRel6 =  param.getValor();
	   	}
	   	
	   	param = recuperaPorParam("grupoRel7");
	   	if(param != null){
    		this.grupoRel7 =  param.getValor();
    	}
    	 param = recuperaPorParam("grupoRel8");
    	if(param != null){
    		this.grupoRel8 =  param.getValor();
    	}
    	 param = recuperaPorParam("grupoRel9");
    	if(param != null){
    		this.grupoRel9 =  param.getValor();
    	}
	   	 param = recuperaPorParam("grupoRel10");
	   	if(param != null){
	   		this.grupoRel10 =  param.getValor();
	   	}

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
    
    private ParametroControle recuperaPorParam(String parametro){
    	for(ParametroControle param : this.listaParam){
    		if(param.getParam().equalsIgnoreCase(parametro)){
    			return param;
    		}
    	}
    	return null;
    }
    
    private ParametroControle recuperaPorParamNovo(String parametro){ 
    	ParametroControle param = this.recuperaPorParam(parametro);
    	if(param == null){
    		param = new ParametroControle();
    		param.setParam(parametro);
    	}
    	return param;
    }
    
    public void atualizaPeriodo(){
    	this.getDataInicio();
    	this.getDataFim();
    }
    
    public void atualizaIntervalo(){
    	ParametroControle param  = this.recuperaPorParamNovo("intervalo");
    	if(getInterval() != null){
    		param.setValor(getInterval().toString());
    		try {
				paramControleFacade.save(param);
				this.displayInfoMessageToUser("Intervalo salvo com sucesso");
			} catch (Exception e) {
				this.displayErrorMessageToUser("Erro ao salvar o intervalo: "+e.getMessage());
				e.printStackTrace();
			}
    	}
    	
    }
    
	public void onGrupoChange(int id){
		 ParametroControle param;
		switch (id) {
		case 1:
			param = recuperaPorParamNovo("grupoRel1");
			 param.setValor(this.getGrupoRel1());
			 atualizaParamControle(param);
			break;
		case 2:
			 param = recuperaPorParamNovo("grupoRel2");
			 param.setValor(this.getGrupoRel2());
			 atualizaParamControle(param);
			break;
		case 3:
			param = recuperaPorParamNovo("grupoRel3");
			 param.setValor(this.getGrupoRel3());
			 atualizaParamControle(param);
			break;
		case 4:
			 param = recuperaPorParamNovo("grupoRel4");
			 param.setValor(this.getGrupoRel4());
			 atualizaParamControle(param);
			break;
		case 5:
			param = recuperaPorParamNovo("grupoRel5");
			 param.setValor(this.getGrupoRel5());
			 atualizaParamControle(param);
			break;
		case 6:
			 param = recuperaPorParamNovo("grupoRel6");
			 param.setValor(this.getGrupoRel6());
			 atualizaParamControle(param);
			break;			
		case 7:
			param = recuperaPorParamNovo("grupoRel7");
			 param.setValor(this.getGrupoRel7());
			 atualizaParamControle(param);
			break;
		case 8:
			 param = recuperaPorParamNovo("grupoRel8");
			 param.setValor(this.getGrupoRel8());
			 atualizaParamControle(param);
			break;
		case 9:
			param = recuperaPorParamNovo("grupoRel9");
			 param.setValor(this.getGrupoRel9());
			 atualizaParamControle(param);
			break;
		case 10:
			 param = recuperaPorParamNovo("grupoRel10");
			 param.setValor(this.getGrupoRel10());
			 atualizaParamControle(param);
			break;			
		}
		
		
	}

	private void atualizaParamControle(ParametroControle param) {
		try {
			this.paramControleFacade.save(param);
			this.displayInfoMessageToUser("Dados salvo com sucesso");
		} catch (Exception e) {
			this.displayErrorMessageToUser("Não foi possível dados. "+e.getMessage());
		}
	}
    
    public void atualizaRelatorios(){
    	
    }
    
    private int number;
    
    public int getNumber() {
        return number;
    }
 
    public void increment() {
        number++;
    }
	
	
	
	public List<Usuario> getListaUsuarioGrupoRel1(){
		return this.getUsuarioFacade().listAllPorGrupoRelatorioPerido(this.grupoRel1, this.getDataInicio(), this.getDataFim());
	}
	
	public List<Usuario> getListaUsuarioGrupoRel2(){
		return this.getUsuarioFacade().listAllPorGrupoRelatorioPerido(this.grupoRel2, this.getDataInicio(), this.getDataFim());
	}
	
	public List<Usuario> getListaUsuarioGrupoRel3(){
		return this.getUsuarioFacade().listAllPorGrupoRelatorioPerido(this.grupoRel3, this.getDataInicio(), this.getDataFim());
	}
	
	public List<Usuario> getListaUsuarioGrupoRel4(){
		return this.getUsuarioFacade().listAllPorGrupoRelatorioPerido(this.grupoRel4, this.getDataInicio(), this.getDataFim());
	}
	
	public List<Usuario> getListaUsuarioGrupoRel5(){
		return this.getUsuarioFacade().listAllPorGrupoRelatorioPerido(this.grupoRel5, this.getDataInicio(), this.getDataFim());
	}
	
	public List<Usuario> getListaUsuarioGrupoRel6(){
		return this.getUsuarioFacade().listAllPorGrupoRelatorioPerido(this.grupoRel6, this.getDataInicio(), this.getDataFim());
	}
	
	public List<Usuario> getListaUsuarioGrupoRel7(){
		return this.getUsuarioFacade().listAllPorGrupoRelatorioPerido(this.grupoRel7, this.getDataInicio(), this.getDataFim());
	}
	
	public List<Usuario> getListaUsuarioGrupoRel8(){
		return this.getUsuarioFacade().listAllPorGrupoRelatorioPerido(this.grupoRel8, this.getDataInicio(), this.getDataFim());
	}
	
	public List<Usuario> getListaUsuarioGrupoRel9(){
		return this.getUsuarioFacade().listAllPorGrupoRelatorioPerido(this.grupoRel9, this.getDataInicio(), this.getDataFim());
	}
	
	public List<Usuario> getListaUsuarioGrupoRel10(){
		return this.getUsuarioFacade().listAllPorGrupoRelatorioPerido(this.grupoRel10, this.getDataInicio(), this.getDataFim());
	}


	public UsuarioFacade getUsuarioFacade() {
		return usuarioFacade;
	}

	public void setUsuarioFacade(UsuarioFacade usuarioFacade) {
		this.usuarioFacade = usuarioFacade;
	}


	public Date getDataInicio() {
		if(dataInicio == null){
			dataInicio = new Date();
		}
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		if(dataFim == null){
			dataFim = new Date();
		}
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}


	public List<ParametroControle> getListaParam() {
		return listaParam;
	}


	public void setListaParam(List<ParametroControle> listaParam) {
		this.listaParam = listaParam;
	}


	
	public Integer getInterval() {
		return interval;
	}

	public void setInterval(Integer interval) {
		this.interval = interval;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}


	public void setListaGrupoSelect(List<SelectItem> listaGrupoSelect) {
		this.listaGrupoSelect = listaGrupoSelect;
	}

	public String getGrupoRel1() {
		return grupoRel1;
	}

	public void setGrupoRel1(String grupoRel1) {
		this.grupoRel1 = grupoRel1;
	}

	public String getGrupoRel2() {
		return grupoRel2;
	}

	public void setGrupoRel2(String grupoRel2) {
		this.grupoRel2 = grupoRel2;
	}

	public String getGrupoRel3() {
		return grupoRel3;
	}

	public void setGrupoRel3(String grupoRel3) {
		this.grupoRel3 = grupoRel3;
	}

	public String getGrupoRel4() {
		return grupoRel4;
	}

	public void setGrupoRel4(String grupoRel4) {
		this.grupoRel4 = grupoRel4;
	}

	public String getGrupoRel5() {
		return grupoRel5;
	}

	public void setGrupoRel5(String grupoRel5) {
		this.grupoRel5 = grupoRel5;
	}

	public String getGrupoRel6() {
		return grupoRel6;
	}

	public void setGrupoRel6(String grupoRel6) {
		this.grupoRel6 = grupoRel6;
	}
	
	

	public String getGrupoRel7() {
		return grupoRel7;
	}

	public void setGrupoRel7(String grupoRel7) {
		this.grupoRel7 = grupoRel7;
	}

	public String getGrupoRel9() {
		return grupoRel9;
	}

	public void setGrupoRel9(String grupoRel9) {
		this.grupoRel9 = grupoRel9;
	}

	public String getGrupoRel10() {
		return grupoRel10;
	}

	public void setGrupoRel10(String grupoRel10) {
		this.grupoRel10 = grupoRel10;
	}
	
	

	public String getGrupoRel8() {
		return grupoRel8;
	}

	public void setGrupoRel8(String grupoRel8) {
		this.grupoRel8 = grupoRel8;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 5983368142774783720L;

}
