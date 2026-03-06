package com.mc.service;

import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

import com.mc.model.Disciplina;
import com.mc.model.dao.DisciplinaDao;

public class ManterDisciplinaService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject	
	private DisciplinaDao disciplinaDao;
	
	public void salvar(Disciplina disciplina) {
		disciplinaDao.salvar(disciplina);
	}
	
	public void excluir(Disciplina disciplina) {
		this.disciplinaDao.excluir(disciplina);
	}

	public List<Disciplina> buscarTodos() {
		
		return disciplinaDao.buscarTodos();
	}
}
