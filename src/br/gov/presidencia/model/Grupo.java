package br.gov.presidencia.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="user_groups")
@NamedQueries({
    @NamedQuery(name="Grupo.findAllAtivo",
                query="SELECT g FROM Grupo g where g.ativo = TRUE "),
    @NamedQuery(name="Grupo.findByName",
                query="SELECT g FROM Grupo g where g.nome = :nome "),
}) 
public class Grupo extends GenericModel implements Serializable {
	
	@Id
	@Column(name="group_name", updatable = false, insertable= false)
	private String nome;
	
	@OneToOne(cascade = CascadeType.ALL, optional = true, fetch = FetchType.EAGER, orphanRemoval = true)
	@PrimaryKeyJoinColumn
	private GrupoDinamico dinamico;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name="user2group", 
		joinColumns =@JoinColumn(name="group_name"), 
		inverseJoinColumns = @JoinColumn(name="USER_NAME"))
	private List<Usuario> tecnicos;
	

	@Column(name="display_group", updatable = false, insertable= false)
	@Type(type="yes_no")
	private Boolean ativo;

	

	public List<Usuario> getTecnicos() {
		return tecnicos;
	}

	public void setTecnicos(List<Usuario> tecnicos) {
		this.tecnicos = tecnicos;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public GrupoDinamico getDinamico() {
		return dinamico;
	}

	public void setDinamico(GrupoDinamico dinamico) {
		this.dinamico = dinamico;
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
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Grupo other = (Grupo) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}






	/**
	 * 
	 */
	private static final long serialVersionUID = 3696730897260207239L;

}
