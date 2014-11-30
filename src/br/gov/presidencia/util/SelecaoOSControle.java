package br.gov.presidencia.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SelecaoOSControle implements Serializable{


	private String userName;
	
	private List<String> listaOS;
	
	private Date inicio;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<String> getListaOS() {
		if(listaOS == null){
			listaOS = new ArrayList<String>();
		}
		return listaOS;
	}

	public void setListaOS(List<String> listaOS) {
		this.listaOS = listaOS;
	}
	
	
	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = -197208611570366378L;	
	
	

}
