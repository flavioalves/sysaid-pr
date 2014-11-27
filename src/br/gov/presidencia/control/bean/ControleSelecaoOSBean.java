package br.gov.presidencia.control.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Singleton;

import br.gov.presidencia.model.OrdemServico;
import br.gov.presidencia.model.Usuario;

@Named
@Singleton
public class ControleSelecaoOSBean extends AbstractBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 789523492902029601L;

	private OrdemServico os;
	
	private Map<OrdemServico,Usuario> listaOSSelecionadas;
	
	@PostConstruct
	public void init(){
		listaOSSelecionadas = new HashMap<OrdemServico,Usuario>();
	}
	

	public OrdemServico getOs() {
		return os;
	}

	public void setOs(OrdemServico os) {
		this.os = os;
	}

	public Map<OrdemServico,Usuario> getListaOSSelecionadas() {
		return listaOSSelecionadas;
	}

	public void setListaOSSelecionadas(Map<OrdemServico,Usuario> listaOSSelecionadas) {
		this.listaOSSelecionadas = listaOSSelecionadas;
	}
	
	

}
