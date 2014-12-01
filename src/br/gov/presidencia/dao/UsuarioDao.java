package br.gov.presidencia.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.gov.presidencia.model.FilaOrdemServico;
import br.gov.presidencia.model.ResumoOrdemServico;
import br.gov.presidencia.model.Usuario;

@Named
public class UsuarioDao extends GenericDao<Usuario> {
	
	public UsuarioDao() {
		super(Usuario.class);
	}
	
	public List<Usuario> listAll(){
		
		List<Usuario> lista = new ArrayList<Usuario>();
		return lista;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> listAllDisponiveisPorGrupo(String grupoId, Boolean verificaFila){

		List<Usuario> lista = new ArrayList<Usuario>();
		String sql =  null;
		
		if(verificaFila){
			 sql = "SELECT g.user_name FROM user2group g where g.group_name = :group and g.user_name not in "
					+ " (select i.user_name FROM mgd_indisponibilidade i where :dataHoje between i.dt_inicio and i.dt_fim and i.ativo = 'Y') "
					+ " and g.user_name not in (select f.user_name FROM mgd_controle_fila f WHERE f.ativo = 'Y' and f.ASSIGNED_DATA >= :dataHoje) ORDER by g.user_name";
			
		}else{
			sql = "SELECT g.user_name FROM user2group g where g.group_name = :group and g.user_name not in "
					+ " (select i.user_name FROM mgd_indisponibilidade i where :dataHoje between i.dt_inicio and i.dt_fim and i.ativo = 'Y') "
					+ " ORDER by g.user_name";	
		}
		 
		
		Query query = getEntityManager().createNativeQuery(sql);
		query.setParameter("group", grupoId);
		/** Ajuste de Data - Para evitar usar SYSDATE porque a Data do servidor de App estava diferente da Data do Banco ***/
		
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
	    Date dateWithoutTime = new Date();
	    try {
			dateWithoutTime = sdf.parse(sdf.format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    /*** Ajuste de Data **/
		
		query.setParameter("dataHoje", dateWithoutTime);
		@SuppressWarnings("unchecked")
		List<String> retorno = query.getResultList();
		for(String obj: retorno){
			Usuario user = this.find(obj);
			this.refresh(user);
			
			//Nao queria fazer isso mas o SYSDATE do banco nao funciona. Ja pedi para ajustar mas infezlimente nao vao arrumar
			//No dia que o SYSDATE funciona essa query nao sera mais necessaria
			Query queryTotal = getEntityManager().createNamedQuery("Fila.findPorUserName", FilaOrdemServico.class);
			queryTotal.setParameter("dataHoje", dateWithoutTime);
			queryTotal.setParameter("userName", user);
			
			user.setListaFilaOrdemServico(queryTotal.getResultList());
			
			//user.getOsHoje();
			//.user.getOsTotal();
			lista.add(user);
		}

		return lista;
	}
	
	public List<Usuario> listAllPorGrupoRelatorioPerido(String grupoId,
			Date inicio, Date fim) {
		
		
		List<Usuario> lista = new ArrayList<Usuario>();
		String sql;
		
		if(inicio == null || fim == null){
			 sql = "SELECT DISTINCT(g.user_name), i.ativo FROM user2group g left join mgd_indisponibilidade i on g.user_name = i.user_name and "
					+ "  :dataHoje  between i.dt_inicio and i.dt_fim and i.ativo = 'Y' "
					+ " where  g.group_name =:groupId";
		}else{
			 sql = "SELECT DISTINCT(g.user_name), i.ativo FROM user2group g left join mgd_indisponibilidade i on g.user_name = i.user_name and "
				+ "  ((:dt_inicio between i.dt_inicio and i.dt_fim) or (:dt_fim between i.dt_inicio and i.dt_fim) or (i.dt_inicio >= :dt_inicio and i.dt_fim <= :dt_fim)) and i.ativo = 'Y' "
				+ " where  g.group_name =:groupId";
		}
		
		Query query = getEntityManager().createNativeQuery(sql);
		if(inicio == null || fim == null){
			query.setParameter("dataHoje", new Date());
		};
		
		query.setParameter("groupId", grupoId);
		
		if(inicio != null && fim != null){
		query.setParameter("dt_inicio", inicio);
		query.setParameter("dt_fim", fim);
		}
		
		
		@SuppressWarnings("unchecked")
		List<Object[]> retorno = query.getResultList();
		for(Object[] obj: retorno){
			Usuario user = this.find(obj[0]);
			Character ativo = ((Character)obj[1]);
			if(ativo != null && ativo.charValue() == 'Y' ){
				user.setIndisponivelNoPeriodo(true);
			}else{
				user.setIndisponivelNoPeriodo(false);
			}
			
			this.refresh(user);
			user.setOsTotalRelatorio(recuperaResumoOSPorTecnico(user, inicio,fim));
			//user.getOsHoje();
			//.user.getOsTotal();
			lista.add(user);
		}
		
		
		return lista;
	}
	
	private List<ResumoOrdemServico> recuperaResumoOSPorTecnico(Usuario user_name,
			Date inicio, Date fim){
		 TypedQuery<ResumoOrdemServico> query = null;
		 
		 if(inicio == null || fim == null){
			 query = this.getEntityManager().createNamedQuery("ResumoOS.findAllPorTecnicoHoje", ResumoOrdemServico.class);
			 query.setParameter("tecnico_name", user_name);
			 query.setParameter("dataHoje", new Date());
			 
		 }else{
			 query = this.getEntityManager().createNamedQuery("ResumoOS.findAllPorTecnicoPeriodo", ResumoOrdemServico.class);
			 query.setParameter("tecnico_name", user_name);
				query.setParameter("dt_inicio", inicio);
				query.setParameter("dt_fim", fim);
		 }
			 
		 return query.getResultList();
	}
	
	public Usuario find(String userName) {
	       Query query = getEntityManager().createQuery("From Usuario user WHERE UPPER(user.userName) = UPPER(:userName)");
	       query.setParameter("userName", userName);
	       return (Usuario) query.getSingleResult();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3501828520309506308L;



}
