package br.gov.presidencia.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import br.gov.presidencia.dao.GenericDao;
import br.gov.presidencia.dao.GrupoDao;
import br.gov.presidencia.model.Grupo;
import br.gov.presidencia.model.GrupoDinamico;
import br.gov.presidencia.model.Usuario;
import br.gov.presidencia.util.MapEntryConverter;

import com.thoughtworks.xstream.XStream;

@Named
public class GrupoFacade extends GenericFacade<Grupo>{

	@Inject
	private GrupoDao grupoDao;
	
	@Override
	public GenericDao<Grupo> getDao() {
		return grupoDao;
	}
		
	public List<Grupo> listAll(){
		List<Grupo> lista =  this.getGrupoDao().findAllAtivo();
		for(Grupo g : lista){
			if(g.getDinamico() == null){
				GrupoDinamico gd = new GrupoDinamico();
				gd.setNome(g.getNome());
				g.setDinamico(gd);
			}
		}
		return lista;
	}
	
	public List<String> lerPermissaoUsuarioPorGrupo(Usuario user){
		
		List<String> lista = new ArrayList<String>();
		List<String> retorno = new ArrayList<String>();
		
		if(user.getGrupos() == null || (user.getGrupos() != null && user.getGrupos().isEmpty())){
			return null;
		}
		for(Grupo g : user.getGrupos()){
			lista.add(g.getNome());
		}
	
		
		List<String> listaPermissao = this.grupoDao.carregaDadosPermissao(lista);
		
		
        XStream magicApi = new XStream();
        magicApi.registerConverter(new MapEntryConverter());
        magicApi.alias("userPermissions", Map.class);

        for(String xml : listaPermissao){
        	@SuppressWarnings("unchecked")
        	Map<String, String> extractedMap = (Map<String, String>) magicApi.fromXML(xml);
        	String p = extractedMap.get("userPermissionHelpDeskViewType");
        	retorno.add(p);
        }

        return retorno;
	}
	
	
	public GrupoDao getGrupoDao() {
		return grupoDao;
	}


	public void setGrupoDao(GrupoDao grupoDao) {
		this.grupoDao = grupoDao;
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 7694829960511015436L;

	
}
