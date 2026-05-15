package com.mc.service;

import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import com.mc.model.Agendar;
import com.mc.model.dao.AgendarDao;

public class AgendarService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject	
	private AgendarDao agendarDao;
	
	public void salvar(Agendar agendar) {
		agendarDao.salvar(agendar);
	}
	
	public void excluir(Agendar agendar) {
		this.agendarDao.excluir(agendar);
	}

	public List<Agendar> buscarTodos() {
		
		return agendarDao.buscarTodos();
	}
}