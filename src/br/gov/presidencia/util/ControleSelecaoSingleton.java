package br.gov.presidencia.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class ControleSelecaoSingleton {
	
	private List<SelecaoOSControle> controle;

    private static volatile ControleSelecaoSingleton instance = null;
    private ControleSelecaoSingleton() { }
 
    public static ControleSelecaoSingleton getInstance() {
        if (instance == null) {
            synchronized (ControleSelecaoSingleton.class) {
                if (instance == null) {
                    instance = new ControleSelecaoSingleton();
                    instance.setControle(new ArrayList<SelecaoOSControle>());
                }
            }
        }
 
        return instance;
    }

	public List<SelecaoOSControle> getControle() {
		return controle;
	}

	public void setControle(List<SelecaoOSControle> controle) {
		this.controle = controle;
	}
	
	public List<String> getListaOSSelecionadas(){
		List<SelecaoOSControle> lista = this.getControle();
		List<String> osSelecionadas = new ArrayList<String>();
		for(SelecaoOSControle os : lista){
			osSelecionadas.addAll(os.getListaOS());
		}
		return osSelecionadas;
	}

	public void saveOSSelecionada(String userName, String numeroOS){
		List<SelecaoOSControle> lista = this.getControle();
		for(SelecaoOSControle co : lista){
			if(co.getUserName().equalsIgnoreCase(userName)){
				co.getListaOS().add(numeroOS);
				return;
			}
		}
		
		SelecaoOSControle controle = new SelecaoOSControle();
		controle.setInicio(new Date());
		controle.setUserName(userName);
		controle.getListaOS().add(numeroOS);
		ControleSelecaoSingleton.getInstance().getControle().add(controle);
	}
	
	public void removeOSSelecionada(String userName){
		Iterator<SelecaoOSControle> lista = this.getControle().iterator();
		while (lista.hasNext()) {
			SelecaoOSControle so = lista.next(); // must be called before you can call i.remove()
			if(so.getUserName().equalsIgnoreCase(userName) ){
				lista.remove();
			}
		}
	}
	
	public Boolean isOSSelecionada(String numeroOS){
		final long ONE_MINUTE_IN_MILLIS=60000;//millisecs
		
		List<SelecaoOSControle> lista = this.getControle();
		for(SelecaoOSControle co : lista){
			if(co.getListaOS().contains(numeroOS) ){
				long t=co.getInicio().getTime();
				Date afterAddingOneMin=new Date(t + (2 * ONE_MINUTE_IN_MILLIS));
				if(afterAddingOneMin.after(new Date())){
					return true;
				}else{
					co.getListaOS().remove(numeroOS);
				}
			}
		}
		return false;
	}

}