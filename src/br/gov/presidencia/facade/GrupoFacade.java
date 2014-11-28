package br.gov.presidencia.facade;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import br.gov.presidencia.dao.GenericDao;
import br.gov.presidencia.dao.GrupoDao;
import br.gov.presidencia.model.Grupo;
import br.gov.presidencia.model.GrupoDinamico;
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
	
	public Map<String, String> lerPermissaoGrupo(String grupoNome){
		
		String result = this.grupoDao.carregaDadosPermissao(grupoNome);
		
		//InputStream is = new ByteArrayInputStream(grupo.getDadosPermissao());

		System.out.println(result);
		
        XStream magicApi = new XStream();
        magicApi.registerConverter(new MapEntryConverter());
        magicApi.alias("userPermissions", Map.class);

        @SuppressWarnings("unchecked")
		Map<String, String> extractedMap = (Map<String, String>) magicApi.fromXML(result);
        
        return extractedMap;
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
