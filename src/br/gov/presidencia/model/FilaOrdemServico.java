package br.gov.presidencia.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;


@Entity
@SequenceGenerator(name = "filaOSSequence", sequenceName = "MGD_FILOS_ID_SEQ", allocationSize = 1)
@Table(name="mgd_controle_fila")
@NamedQueries({
    @NamedQuery(name="Fila.findAllAtivo",
                query="SELECT f FROM FilaOrdemServico f where f.ativo = TRUE "),
    @NamedQuery(name="Fila.findByOS",
                query="SELECT f FROM FilaOrdemServico f where f.resumoOS = :osId "),
    @NamedQuery(name="Fila.findPorUserName",
                query="SELECT f FROM FilaOrdemServico f  WHERE  f.data >= :dataHoje and f.tecnico = :userName "),
})
public class FilaOrdemServico extends GenericModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "filaOSSequence")
	@Column
	private Integer id;
	
	@ManyToOne(optional = false, targetEntity=Usuario.class, fetch = FetchType.LAZY)
	@JoinColumn(name="user_name", referencedColumnName="user_name")
	private Usuario tecnico;
	
	@ManyToOne(optional = false, targetEntity=ResumoOrdemServico.class, fetch = FetchType.EAGER)
	@JoinColumn(name="service_req_id", referencedColumnName="id")
	private ResumoOrdemServico resumoOS;
	
	@ManyToOne(optional = true, targetEntity=Grupo.class, fetch = FetchType.LAZY)
	@JoinColumn(name="group_name", referencedColumnName="group_name")
	private Grupo grupo;
	
	@Column(name="assigned_data")
	private Date data;
	
	@Column
	@Type(type="yes_no")
	private Boolean ativo;
	
	

	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public Usuario getTecnico() {
		return tecnico;
	}



	public void setTecnico(Usuario tecnico) {
		this.tecnico = tecnico;
	}



	public ResumoOrdemServico getResumoOS() {
		return resumoOS;
	}



	public void setResumoOS(ResumoOrdemServico resumoOS) {
		this.resumoOS = resumoOS;
	}



	public Grupo getGrupo() {
		return grupo;
	}



	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}



	public Date getData() {
		return data;
	}



	public void setData(Date data) {
		this.data = data;
	}


	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}




	/**
	 * 
	 */
	private static final long serialVersionUID = 1281950227940925626L;

	

}
