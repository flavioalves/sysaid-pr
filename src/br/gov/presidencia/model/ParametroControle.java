package br.gov.presidencia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/***
 * O valor default para o Tipo eh o valor 2
 * @author User
 *
 */

@Entity
@SequenceGenerator(name = "paramcontroleSequence", sequenceName = "MGD_PARAM_CONTROLE_SEQ", allocationSize = 1)
@Table(name="mgd_controle_param")
@NamedQueries({
    @NamedQuery(name="ParametroControle.findRel",
                query="SELECT p FROM ParametroControle p where p.tipo = 2 "),
    @NamedQuery(name="ParametroControle.findPorParam",
                query="SELECT p FROM ParametroControle p where p.param = :param and p.tipo = 1 "),                
}) 
public class ParametroControle extends GenericModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "paramcontroleSequence")
	@Column
	private Integer id;
	
	@Column(name="param")
	private String param;
	
	@Column(name="value")
	private String valor;
	
	@Column(name="tipo")
	private Integer tipo;
	
	public static int PARAM_STATUS = 1;
	
	public static int PARAM_REL = 2;
	
	
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
	
	public Integer getTipo() {
		if(tipo == null){
			tipo = 2;
		}
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 8240744657581292446L;

	public ParametroControle() {
	}

}
