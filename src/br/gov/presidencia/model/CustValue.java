package br.gov.presidencia.model;

import java.io.Serializable;

public class CustValue implements Serializable{
	


	private String listName;
	
	private Integer valueKey;
	
	private String valueCaption;
	
	
	

	public String getListName() {
		return listName;
	}




	public void setListName(String listName) {
		this.listName = listName;
	}




	public Integer getValueKey() {
		return valueKey;
	}




	public void setValueKey(Integer valueKey) {
		this.valueKey = valueKey;
	}




	public String getValueCaption() {
		return valueCaption;
	}




	public void setValueCaption(String valueCaption) {
		this.valueCaption = valueCaption;
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 6890265606312771509L;

}
