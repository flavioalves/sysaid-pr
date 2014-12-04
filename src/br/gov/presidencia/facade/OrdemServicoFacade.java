package br.gov.presidencia.facade;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.gov.presidencia.dao.GenericDao;
import br.gov.presidencia.dao.OrdemServicoDao;
import br.gov.presidencia.model.CustValue;
import br.gov.presidencia.model.FilaOrdemServico;
import br.gov.presidencia.model.Grupo;
import br.gov.presidencia.model.OrdemServico;
import br.gov.presidencia.model.ResumoOrdemServico;
import br.gov.presidencia.model.Usuario;

@Named
public class OrdemServicoFacade extends GenericFacade<OrdemServico>{
	
	@Inject
	private OrdemServicoDao ordemServicoDao;
	
	
	@Override
	public GenericDao<OrdemServico> getDao() {
		return ordemServicoDao;
	}
	
	public void saveFila(FilaOrdemServico fila) {
		this.ordemServicoDao.saveFila(fila);
		
	}
	
	public int limpaFilaPorGrupoHoje(String groupId){
		ordemServicoDao.beginTransaction();
		int total = this.ordemServicoDao.limpaFilaPorGrupoHoje(groupId);
		ordemServicoDao.commit();
		return total;
	}
	
	/**
	 * Workaround - A regra de negocio diz para settar um responsavel pela OS no sysaid
	 * Contudo, a tabela de controle da OS-> sysaid.service_req �� do sysaid e ele controla as regras de insercao
	 * para manter a consistencia do sistema precisamos reproduzir o mesmo comportamento 
	 * @param entity
	 * @throws Exception
	 */
	public void salvarOSComRegraDeNegocio(Grupo grupo, List<OrdemServico> listaOs, Usuario tecnico, Usuario user) throws Exception {
		try {
			ordemServicoDao.beginTransaction();
			
			//Cria a fila de controle de OS 
			for(OrdemServico os : listaOs){
				//Carrega a Fila
				FilaOrdemServico fila = this.ordemServicoDao.filaFindByOS(os);
				fila.setData(new Date());
				fila.setGrupo(grupo);
				fila.setTecnico(tecnico);
				fila.setAtivo(true);
				
				//Cria ResumoOS
				ResumoOrdemServico res = new ResumoOrdemServico();
				res.setId(os.getId());
				res.setDataAbertura(os.getDataAbertura());
				res.setSolicitante(os.getSolicitante());
				res.setUsuario(tecnico);
				fila.setResumoOS(res);
				
				//Salva Fila
				this.saveFila(fila);
				
				//Insere Historio
				ordemServicoDao.insertHistorico(os.getId());
				//Cria Log
				ordemServicoDao.insertLog(os.getId(),tecnico.getUserName(), user.getUserName());
				//Atualiza OS
				ordemServicoDao.updateQueryOS(os, tecnico, user);
			}
			
			ordemServicoDao.commit();
		} catch (Exception e) {
			getDao().rollback();
			throw e;
		}
	}
	
	public List<OrdemServico> listAll(){
		return this.getOrdemServicoDao().listAll();
	}
	
	public List<OrdemServico> listAllSubOS(OrdemServico osPai){
		return this.getOrdemServicoDao().listAllSubOS(osPai);
	}
	
	public List<CustValue> listaCustValuesporTipo(String tipo){
		return this.getOrdemServicoDao().listaCustValuesporTipo(tipo);
	}
	

	public OrdemServicoDao getOrdemServicoDao() {
		return ordemServicoDao;
	}

	public List<OrdemServico> listAll(int first, int pageSize, String sortField, String sortOrder, List<Integer> filtrosStatus, int tipoSeguranca, Usuario user) throws SQLException, IOException {
		return  this.ordemServicoDao.listAll(first,pageSize,sortField,sortOrder,filtrosStatus, tipoSeguranca, user);
		
	}


	public void setOrdemServicoDao(OrdemServicoDao ordemServicoDao) {
		this.ordemServicoDao = ordemServicoDao;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = -2755014852853884926L;




}
