package com.mc.service;

import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

import com.mc.model.Professor;
import com.mc.model.dao.ProfessorDao;

public class ManterProfessorService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject	
	private ProfessorDao professorDao;
	
	public void salvar(Professor professor) {
		professorDao.salvar(professor);
	}
	
	public void excluir(Professor professor) {
		this.professorDao.excluir(professor);
	}

	public List<Professor> buscarTodos() {
		
		return professorDao.buscarTodos();
	}
}
