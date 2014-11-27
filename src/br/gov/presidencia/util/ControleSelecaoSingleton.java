package br.gov.presidencia.util;

import java.util.HashMap;
import java.util.Map;

public class ControleSelecaoSingleton {
	
	private Map<String,Object> lista = new HashMap<String, Object>();

    private static volatile ControleSelecaoSingleton instance = null;
    private ControleSelecaoSingleton() { }
 
    public static ControleSelecaoSingleton getInstance() {
        if (instance == null) {
            synchronized (ControleSelecaoSingleton.class) {
                if (instance == null) {
                    instance = new ControleSelecaoSingleton();
                }
            }
        }
 
        return instance;
    }
    
    public void saveObjectInAppContext(String key, Object value){
    	synchronized (value) {
    		lista.put(key, value);
		}
    	
    }
    
	public Boolean isObjectInAppContext(String chave){
		return lista.containsKey(chave);
	}
	
	public Object getObjectInAppContext(String chave){
    		return lista.get(chave);
		
	}
	
	public Object removeObjectInAppContext(String chave){
		return lista.remove(chave);
	
	}
    
    
    
}