package br.gov.presidencia.dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.io.IOUtils;

import br.gov.presidencia.model.CustValue;
import br.gov.presidencia.model.FilaOrdemServico;
import br.gov.presidencia.model.OrdemServico;
import br.gov.presidencia.model.ResumoOrdemServico;
import br.gov.presidencia.model.Usuario;

@Named
public class OrdemServicoDao extends GenericDao<OrdemServico> {
	
	public OrdemServicoDao() {
		super(OrdemServico.class);
	}
	
	public List<OrdemServico> listAll(){
		
		 List<OrdemServico> lista = new ArrayList<OrdemServico>();
		
			String sql = "SELECT r.id,"+
							"l.value_caption location, "+
							"u.calculated_user_name request_user, "+
							"s.value_caption status, "+
							"ur.calculated_user_name responsibility, "+
							"r.insert_time, "+
							"p.value_caption priority, "+
							"urg.value_caption urgency, "+
							"problem_type, "+
							"r.contact, "+
							"r.version, "+
							"assigned_group FROM service_req r "+
							"left join cust_values l on r.location = l.value_key  and l.list_name = 'location' "+
							"left join cust_values s on r.status = s.value_key  and s.list_name = 'status' "+
							"left join cust_values p on r.priority = p.value_key  and p.list_name = 'priority' "+
							"left join cust_values urg on r.location = urg.value_key  and urg.list_name = 'urgency' "+
							"left join sysaid_user u on u.user_name = r.request_user "+
							"left join sysaid_user ur on ur.user_name = r.responsibility "+
							" where r.sr_type = 1  and r.ARCHIVE != 1 ";

			Query query = getEntityManager().createNativeQuery(sql);
			@SuppressWarnings("unchecked")
			List<Object[]> retorno = query.getResultList();
			
			for(Object[] obj : retorno) {

			OrdemServico os = new OrdemServico();
			os.setId((BigDecimal) obj[0]);
			os.setEnderecoAtendimento((String)obj[1]);
			os.setSolicitante((String)obj[2]);
			os.setStatus((String)obj[3]);
			os.setResponsavel((String)obj[4]);
			os.setDataAbertura((Date)obj[5]);
			os.setPrioridade((String)obj[6]);
			os.setClassificacao((String)obj[7]);
			os.setCategoria((String)obj[8]);
			os.setContato((String)obj[9]); 
			os.setVersion((BigDecimal)obj[10]); 
			os.setGrupo((String)obj[11]); 
			
			
			lista.add(os);
		}
		
		return lista;
		
	}
	
	
	public List<OrdemServico> listAllSubOS(OrdemServico osPai){
		
		 List<OrdemServico> lista = new ArrayList<OrdemServico>();
		
			String sql = ("SELECT r.id,"+
					"l.value_caption location, "+
					"u.calculated_user_name request_user, "+
					"s.value_caption status, "+
					"ur.calculated_user_name responsibility, "+
					"r.insert_time, "+
					"p.value_caption priority, "+
					"urg.value_caption urgency, "+
					"problem_type, "+
					"r.contact, "+
					"r.version, "+
					"r.assigned_group, " +
					"r.PROBLEM_SUB_TYPE, "+
					"r.TITLE "+					
					" FROM service_req r "+
					"left join cust_values l on r.location = l.value_key  and l.list_name = 'location' "+
					"left join cust_values s on r.status = s.value_key  and s.list_name = 'status' "+
					"left join cust_values p on r.priority = p.value_key  and p.list_name = 'priority' "+
					"left join cust_values urg on r.urgency = urg.value_key  and urg.list_name = 'urgency' "+
					"left join sysaid_user u on u.user_name = r.request_user "+
					" left join sysaid_user ur on ur.user_name = r.responsibility "+
					" where r.sr_type = 1 and r.SR_CUST_NUMBERMAINOSI = :idOS ORDER by r.id DESC");

			Query query = getEntityManager().createNativeQuery(sql);
			query.setParameter("idOS", osPai.getId());
			
			@SuppressWarnings("unchecked")
			List<Object[]> retorno = query.getResultList();
			
			for(Object[] obj : retorno) {

			OrdemServico os = new OrdemServico();
			os.setId((BigDecimal) obj[0]);
			os.setEnderecoAtendimento((String)obj[1]);
			os.setSolicitante((String)obj[2]);
			os.setStatus((String)obj[3]);
			os.setResponsavel((String)obj[4]);
			os.setDataAbertura((Date)obj[5]);
			os.setPrioridade((String)obj[6]);
			os.setClassificacao((String)obj[7]);
			os.setCategoria((String)obj[8]);
			os.setContato((String)obj[9]); 
			os.setVersion((BigDecimal)obj[10]); 
			os.setGrupo((String)obj[11]);
			os.setSubCategoria((String)obj[12]);
			os.setTitulo((String)obj[13]);
			
			
			lista.add(os);
		}
		
		return lista;
		
	}
	
	
	public void saveFila(FilaOrdemServico fila) {
		this.getEntityManager().persist(fila);
	}
	
	public List<FilaOrdemServico> filaFindAllAtivo(){
		 TypedQuery<FilaOrdemServico> query =
			      this.getEntityManager().createNamedQuery("Fila.findAllAtivo", FilaOrdemServico.class);
		return query.getResultList();
	}
	
	public int limpaFilaPorGrupoHoje(String groupId){
		
		String sql = "UPDATE mgd_controle_fila f set f.ativo = 'N' where  f.group_name = :group and f.assigned_data > :dataHoje";

		//method 1
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
	    Date dateWithoutTime = new Date();
	    try {
			dateWithoutTime = sdf.parse(sdf.format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			Query query = getEntityManager().createNativeQuery(sql);
			query.setParameter("group",groupId);
			query.setParameter("dataHoje",dateWithoutTime);
	
	return query.executeUpdate();
		
	}
	
	public FilaOrdemServico filaFindByOS(OrdemServico os){
		FilaOrdemServico fila = null;
		ResumoOrdemServico resumo = new ResumoOrdemServico();
		resumo.setId(os.getId());
		 TypedQuery<FilaOrdemServico> query =
			      this.getEntityManager().createNamedQuery("Fila.findByOS", FilaOrdemServico.class);
		 query.setParameter("osId", resumo);
		 try {
			fila =  query.getSingleResult();
		} catch (Exception e) {
			return new FilaOrdemServico();
		}
		 return fila;

	}
	
	/**
	 * Atualizacao usando regras do sysaid
	 * @return
	 */
	public int updateQueryOS(OrdemServico os, Usuario tecnico, Usuario user){
		
		String sql = "UPDATE  service_req r SET r.version = :version, r.responsibility = :tecnicoId, r.update_user = :userId, r.update_time = :updateTime,  r.status = ( SELECT s.value_key "+
					 " FROM cust_values s WHERE s.value_caption = 'Encaminhada' AND s.list_name = 'status' ) "
					 + " where r.id = :osId ";

		Query query = getEntityManager().createNativeQuery(sql);
		query.setParameter("updateTime", new Date());
		query.setParameter("tecnicoId", tecnico.getUserName());
		query.setParameter("userId", user.getUserName());
		query.setParameter("osId", os.getId());
		query.setParameter("version", os.getVersion().intValue() +1);
		
		return query.executeUpdate();
	}
	
	public int insertHistorico(BigDecimal osID){
		
/*		String sql = "INSERT INTO service_req_history "+ 
		" select id, account_id, computer_id, ci_id, problem_type, "
		+ "problem_sub_type, title, description, workaround, known_error,"
		+ " status, contact, responsibility, urgency, priority, notes,"
		+ " resolution, solution, insert_time, update_time, close_time,"
		+ " update_user, version+1, knowledge_base, submit_user,"
		+ " submit_user_type, request_user, request_user_type,"
		+ " responsible_manager, email_account, due_date,"
		+ " location, parent_link, escalation,"
		+ " third_level_category, assigned_group, timers_update_time,"
		+ " timer1, timer2, timer3, timer4, timer5, timer6, timer7,"
		+ " timer8, timer9, timer10, cust_list1, cust_list2, cust_text1,"
		+ " cust_text2, cust_notes, cust_int1, cust_int2, cc, project_id,"
		+ " task_id, sr_type, full_name, cust_date1, cust_date2, source,"
		+ " sr_sub_type, followup_planned_date, followup_actual_date, followup_user,"
		+ " followup_text, success_rating, reopen_counter, assign_counter, max_support_level,"
		+ " current_support_level, agreement, survey_status, impact, change_category, archive,"
		+ " closure_information, visible_to_eu, sr_class from service_req where id  =:osID";*/
		
		
		ResultSet result = null;
		ResultSetMetaData rsm = null;
		int total = 0;
		StringBuilder builder = new StringBuilder();

		try {
			result = super.getConnection().createStatement().executeQuery("select * from service_req_history");
			rsm = result.getMetaData();
			total = rsm.getColumnCount();
			builder.append("INSERT INTO service_req_history SELECT ");
			for (int i = 1; i <= total; i++) {
				String nome = rsm.getColumnName(i);
				if (nome.equalsIgnoreCase("version")) {
					nome = "version+1";
				}
				builder.append(nome);
				if (i < total) {
					builder.append(", ");
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		builder.append(" from service_req where id  = "+osID);

		System.out.println(builder.toString());
		
		Query query = getEntityManager().createNativeQuery(builder.toString());
		//query.setParameter("osID", osID);
		return query.executeUpdate();
		
	}
	
	
	public List<CustValue> listaCustValuesporTipo(String tipo){
		
		List<CustValue> lista = new ArrayList<CustValue>();
		String sql = "SELECT c.LIST_NAME,c.VALUE_CAPTION, c.VALUE_KEY  from cust_values c WHERE c.LIST_NAME = :tipo ORDER BY c.VALUE_CAPTION";
		Query query = getEntityManager().createNativeQuery(sql);
		query.setParameter("tipo", tipo);
		
		@SuppressWarnings("unchecked")
		List<Object[]> retorno = query.getResultList();
		for(Object[] obj : retorno) {
			CustValue c = new CustValue();
			c.setListName((String) obj[0]);
			c.setValueCaption((String) obj[1]);
			BigDecimal big = (BigDecimal)obj[2];
			c.setValueKey(big.intValue());
			lista.add(c);
		}
		
		return lista;
		
	}
	
	public int totalListaAll( List<Integer> status, int tipoSeguranca, Usuario user) {
		String sqlSeguranca = aplicaSegurancaNaQuery(tipoSeguranca, user);
		StringBuilder sql = new StringBuilder();
			sql.append("SELECT count(r.id)  FROM service_req r  where r.sr_type = 1 ");
			sql.append(sqlSeguranca);
			sql.append(" and r.ARCHIVE != 1 ");
			
			if(status != null && !status.isEmpty()){
	 			sql.append(" and r.status in :status ");
	 		}
			
		Query query = getEntityManager().createNativeQuery(sql.toString());
			if(status != null && !status.isEmpty()){
				query.setParameter("status", status);
			}
			BigDecimal total = (BigDecimal) query.getSingleResult();
			 
			 return total.intValue();
	}
	
	public List<OrdemServico> listAll(int first, int pageSize, String sortField,String sortOrder, List<Integer> status, int tipoSeguranca, Usuario user) throws SQLException, IOException {
		 List<OrdemServico> lista = new ArrayList<OrdemServico>();
		 
		 String sqlSeguranca = aplicaSegurancaNaQuery(tipoSeguranca, user);
		 
		if(sortField == null){sortField = "insert_time"; sortOrder = "DESC";}
		
			StringBuilder sql = new StringBuilder();
		
			 sql.append("SELECT r.id,"+
							"l.value_caption location, "+
							"u.calculated_user_name request_user, "+
							"s.value_caption status, "+
							"ur.calculated_user_name responsibility, "+
							"r.insert_time, "+
							"p.value_caption priority, "+
							"urg.value_caption urgency, "+
							"problem_type, "+
							"r.contact, "+
							"r.version, "+
							"r.assigned_group, " +
							"r.PROBLEM_SUB_TYPE, "+
							"r.TITLE, "+
							"r.DESCRIPTION "+
							
							" FROM service_req r "+
							"left join cust_values l on r.location = l.value_key  and l.list_name = 'location' "+
							"left join cust_values s on r.status = s.value_key  and s.list_name = 'status' "+
							"left join cust_values p on r.priority = p.value_key  and p.list_name = 'priority' "+
							"left join cust_values urg on r.urgency = urg.value_key  and urg.list_name = 'urgency' "+
							"left join sysaid_user u on u.user_name = r.request_user "+
							"left join sysaid_user ur on ur.user_name = r.responsibility "
							+ " where r.sr_type = 1 "+ sqlSeguranca+" and r.ARCHIVE != 1 ");
							
			 		if(status != null && !status.isEmpty()){
			 			sql.append(" and r.status in :status ");
			 		}
			 				sql.append("  ORDER by  ");
			 				sql.append(sortField);
			 				sql.append(" ");
			 				sql.append(sortOrder);
			
			

			Query query = getEntityManager().createNativeQuery(sql.toString());
			

			if(status != null && !status.isEmpty()){
			query.setParameter("status", status);
			}
			//query.setParameter("sortField", "r.id");
			//query.setParameter("sortOrder", "DESC");
			query.setFirstResult(first);
			query.setMaxResults(pageSize);
			@SuppressWarnings("unchecked")
			List<Object[]> retorno = query.getResultList();
			
			for(Object[] obj : retorno) {

			OrdemServico os = new OrdemServico();
			os.setId((BigDecimal) obj[0]);
			os.setEnderecoAtendimento((String)obj[1]);
			os.setSolicitante((String)obj[2]);
			os.setStatus((String)obj[3]);
			os.setResponsavel((String)obj[4]);
			os.setDataAbertura((Date)obj[5]);
			os.setPrioridade((String)obj[6]);
			os.setClassificacao((String)obj[7]);
			os.setCategoria((String)obj[8]);
			os.setContato((String)obj[9]); 
			os.setVersion((BigDecimal)obj[10]); 
			os.setGrupo((String)obj[11]);
			os.setSubCategoria((String)obj[12]);
			os.setTitulo((String)obj[13]);
			Clob clobObject = (Clob)obj[14];
			
			String clobAsString = convertClobToString(clobObject);
			
			os.setDescricao(clobAsString);
			
			
			lista.add(os);
		}
		
		return lista;
	}

	private String aplicaSegurancaNaQuery(int tipoSeguranca, Usuario user) {
		String sqlSeguranca = "";
		 
		 if(tipoSeguranca == Usuario.All){
			// sqlSeguranca = "";
			}else if(tipoSeguranca == Usuario.AssignedOnly){
				sqlSeguranca = " and r.request_user ="+user.getUserName();
			}
			else if(tipoSeguranca == Usuario.AssigendGroupAndAssignedOnly){
				sqlSeguranca = sqlSeguranca+ " and r.assigned_group in (SELECT grp.GROUP_NAME FROM USER2GROUP grp WHERE grp.USER_NAME ='"+user.getUserName()+"' )";
			}
		return sqlSeguranca;
	}

	private String convertClobToString(Clob clobObject) throws SQLException, IOException {
		InputStream in = clobObject.getAsciiStream();
		StringWriter w = new StringWriter();
		IOUtils.copy(in, w);
		String clobAsString = w.toString();
		return clobAsString;
	}
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 535924374653980099L;

	public void insertLog(BigDecimal id, String tecnico, String username) {
		String texto = "O incidente # "+ id.toString() +" foi atribuído a "+ tecnico;
		String sql = "Insert into SERVICE_REQ_LOG (LOG_ID,ACCOUNT_ID,SERVICE_REQ_ID,LOG_TIME,LOG_TYPE,LOG_DESCRIPTION,EXT_REFERENCE,USER_NAME) "
				+ "SELECT (SELECT MAX(LOG_ID) FROM SERVICE_REQ_LOG)+1,ACCOUNT_ID,SERVICE_REQ_ID,:LOGTIME,'Incident changed','"+texto+"',EXT_REFERENCE+1,:USERNAME "
				+ " FROM SERVICE_REQ_LOG WHERE SERVICE_REQ_ID = :ID "
				+ " AND LOG_ID = (SELECT MAX(LOG_ID) FROM SERVICE_REQ_LOG WHERE SERVICE_REQ_ID = :ID)";
		Query query = getEntityManager().createNativeQuery(sql);
		
		query.setParameter("ID", id);
		query.setParameter("LOGTIME", new Date());
		query.setParameter("USERNAME", username);
		query.executeUpdate();
		
	}





}
