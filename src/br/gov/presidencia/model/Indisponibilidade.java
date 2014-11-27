package br.gov.presidencia.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;


@Entity
@SequenceGenerator(name = "indisponibilidadeSequence", sequenceName = "mgd_indisponibilidade_seq", allocationSize = 1)
@Table(name="mgd_indisponibilidade")
public class Indisponibilidade extends GenericModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "indisponibilidadeSequence")
	@Column
	private Integer id;
	
	@ManyToOne(optional = false, targetEntity=Usuario.class)
	@JoinColumn(name="user_name", referencedColumnName="user_name")
	private Usuario usuario;
	
	@OneToOne(cascade = CascadeType.ALL, optional = true, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name="id_tipo", referencedColumnName="id")
	private TipoIndisponibilidade tipo;
	
	@Column(name="dt_inicio")
	private Date inicio;
	
	@Column(name="dt_fim")
	private Date fim;
	
	@Column
	private String justificativa;
	
	@Column
	@Type(type="yes_no")
	private Boolean ativo;
	
	
	

	public Integer getId() {
		return id;
	}




	public void setId(Integer id) {
		this.id = id;
	}




	public Usuario getUsuario() {
		return usuario;
	}




	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}




	public TipoIndisponibilidade getTipo() {
		return tipo;
	}




	public void setTipo(TipoIndisponibilidade tipo) {
		this.tipo = tipo;
	}




	public Date getInicio() {
		return inicio;
	}




	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}




	public Date getFim() {
		return fim;
	}




	public void setFim(Date fim) {
		this.fim = fim;
	}




	public String getJustificativa() {
		return justificativa;
	}




	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}




	public Boolean getAtivo() {
		return ativo;
	}




	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}




	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Indisponibilidade other = (Indisponibilidade) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}




	/**
	 * 
	 */
	private static final long serialVersionUID = 6836440494249353370L;


}
