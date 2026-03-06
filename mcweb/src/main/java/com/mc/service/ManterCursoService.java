package com.mc.service;

import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

import com.mc.model.Curso;
import com.mc.model.dao.CursoDao;

public class ManterCursoService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject	
	private CursoDao cursoDao;
	
	public void salvar(Curso curso) {
		cursoDao.salvar(curso);
	}
	
	public void excluir(Curso curso) {
		this.cursoDao.excluir(curso);
	}

	public List<Curso> buscarTodos() {
		
		return cursoDao.buscarTodos();
	}
}
