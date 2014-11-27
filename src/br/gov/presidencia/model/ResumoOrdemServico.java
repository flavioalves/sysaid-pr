package br.gov.presidencia.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "service_req")
@NamedQueries({
    @NamedQuery(name="ResumoOS.findAllPorTecnicoPeriodo",
                query="SELECT r FROM ResumoOrdemServico r where r.usuario = :tecnico_name and r.dataAbertura between :dt_inicio and :dt_fim "),
    @NamedQuery(name="ResumoOS.findAllPorTecnicoHoje",
                query="SELECT r FROM ResumoOrdemServico r where r.usuario = :tecnico_name and r.dataAbertura = :dataHoje "),
}) 
public class ResumoOrdemServico extends GenericModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4347982735516618962L;

	@Id
	@Column
	private BigDecimal id;
	
	@ManyToOne(optional = true, targetEntity=Usuario.class)
	@JoinColumn(name="responsibility", referencedColumnName="user_name", updatable = false, insertable= false)
	private Usuario usuario;
	
	@Column(name="request_user", updatable = false, insertable= false)
	private String solicitante;
	
	@Column(name="insert_time", updatable = false, insertable= false)
	private Date dataAbertura;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}

	public Date getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
	}
	
	

}
