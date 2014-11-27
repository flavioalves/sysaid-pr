package br.gov.presidencia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@SequenceGenerator(name = "paramcontroleSequence", sequenceName = "MGD_PARAM_CONTROLE_SEQ", allocationSize = 1)
@Table(name="mgd_controle_param")
public class ParametroControle extends GenericModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "paramcontroleSequence")
	@Column
	private Integer id;
	
	@Column(name="param")
	private String param;
	
	@Column(name="value")
	private String valor;
	
	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8240744657581292446L;

	public ParametroControle() {
	}

}
