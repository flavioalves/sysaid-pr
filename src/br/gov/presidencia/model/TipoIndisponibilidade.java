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

import org.hibernate.annotations.Type;

@Entity
@SequenceGenerator(name = "tipoIndisponSequence", sequenceName = "mgd_tipo_indispon_seq", allocationSize = 1)
@Table(name="mgd_tipo_indispon")
@NamedQueries({
    @NamedQuery(name="TipoIndisponibilidade.findAllAtivo",
                query="SELECT t FROM TipoIndisponibilidade t where t.ativo = TRUE "),
    @NamedQuery(name="TipoIndisponibilidade.findByName",
                query="SELECT t FROM TipoIndisponibilidade t where t.nome = :nome "),
}) 
public class TipoIndisponibilidade extends GenericModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "tipoIndisponSequence")
	@Column
	private Integer id;
	
	@Column
	private String nome;
	
	@Type(type="yes_no")
	private Boolean ativo;
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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
		TipoIndisponibilidade other = (TipoIndisponibilidade) obj;
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
	private static final long serialVersionUID = -7729768548005135010L;

}
