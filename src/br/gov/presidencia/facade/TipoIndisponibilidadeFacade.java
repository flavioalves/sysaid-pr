package br.gov.presidencia.facade;

import java.util.List;

import javax.inject.Inject;

import br.gov.presidencia.dao.GenericDao;
import br.gov.presidencia.dao.TipoIndisponibilidadeDao;
import br.gov.presidencia.model.TipoIndisponibilidade;

public class TipoIndisponibilidadeFacade extends
		GenericFacade<TipoIndisponibilidade> {
	
	@Inject
	private TipoIndisponibilidadeDao tipoDao;
	
	
	
	@Override
	public List<TipoIndisponibilidade> listAll() {
		return tipoDao.findAllAtivo();
	}

	@Override
	public GenericDao<TipoIndisponibilidade> getDao() {
		return tipoDao;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 5151372345440630642L;

}
