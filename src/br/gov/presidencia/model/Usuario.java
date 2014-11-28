package br.gov.presidencia.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "SYSAID_USER")
public class Usuario extends GenericModel implements Serializable{
	
	 @Transient
	 private Integer id;
	 
		@Id
		@Column(name="USER_NAME", updatable = false, insertable= false)
		private String userName;
		
		@Column(name = "FIRST_NAME", updatable = false, insertable= false)
		private String nome;
		
		@Column(name = "LAST_NAME", updatable = false, insertable= false)
		private String sobrenome;

		@Column(name = "CALCULATED_USER_NAME", updatable = false,  insertable= false)
		private String userNameCalculado;
		
		@Column(name="EMAIL_ADDRESS", updatable = false, insertable= false)
		private String email;
		
		@OneToMany(mappedBy ="usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
		@Where(clause = "ativo = 'Y'")
		@OrderBy("id DESC")
		private List<Indisponibilidade> indisponibilidades;
	  
	  
	  //@Formula(" (SELECT COUNT(*) from service_req os where os.sr_type = 1 and os.responsibility = USER_NAME)")
	
	  @OneToMany(mappedBy ="tecnico", fetch = FetchType.LAZY)
	  @Where(clause = " TRUNC(assigned_data) = TRUNC(SYSDATE) ")
	  private List<FilaOrdemServico> listaFilaOrdemServico;
	  
	  @OneToMany(mappedBy ="usuario", fetch = FetchType.LAZY)
	  @Where(clause = "sr_type = 1 and close_time is null")
	  private List<ResumoOrdemServico> osTotalAberta;
	  
	  @Transient
	  private Boolean indisponivelNoPeriodo;
	  
	  @Transient
	  private List<ResumoOrdemServico> osTotalRelatorio;
	  
	  

	public List<ResumoOrdemServico> getOsHoje() {
		List<ResumoOrdemServico> osHoje = null;
		
		if(listaFilaOrdemServico != null ){
			osHoje = new ArrayList<ResumoOrdemServico>();
			for(FilaOrdemServico fila: listaFilaOrdemServico){
				osHoje.add(fila.getResumoOS());
			}
		}
		return osHoje;
	}


	public List<ResumoOrdemServico> getOsTotalAberta() {
		return osTotalAberta;
	}


	public void setOsTotalAberta(List<ResumoOrdemServico> osTotal) {
		this.osTotalAberta = osTotal;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	
	public String getUserNameCalculado() {
		return userNameCalculado;
	}

	public void setUserNameCalculado(String userNameCalculado) {
		this.userNameCalculado = userNameCalculado;
	}

	
	
	public List<ResumoOrdemServico> getOsTotalRelatorio() {
		return osTotalRelatorio;
	}


	public void setOsTotalRelatorio(List<ResumoOrdemServico> osTotalRelatorio) {
		this.osTotalRelatorio = osTotalRelatorio;
	}

	

	public Boolean getIndisponivelNoPeriodo() {
		return indisponivelNoPeriodo;
	}


	public void setIndisponivelNoPeriodo(Boolean indisponivelNoPeriodo) {
		this.indisponivelNoPeriodo = indisponivelNoPeriodo;
	}


	public List<Indisponibilidade> getIndisponibilidades() {
		return indisponibilidades;
	}


	public void setIndisponibilidades(List<Indisponibilidade> indisponibilidades) {
		this.indisponibilidades = indisponibilidades;
	}


	public String getNomeCompleto() {
		return (nome + " " + sobrenome);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
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
		Usuario other = (Usuario) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 2873762408153118689L;

}
