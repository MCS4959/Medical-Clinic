package com.mc.model.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mc.model.Aluno;
import com.mc.util.jpa.Transactional;

public class AlunoDao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AlunoDao.class);
	
	@Transactional
	public Aluno salvar(Aluno aluno) throws PersistenceException {
		
		LOGGER.info("salvar DAO... aluno = " + aluno);
		
		try {
			return manager.merge(aluno);
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional
	public void excluir(Aluno aluno) throws PersistenceException {

		try {
			Aluno a = manager.find(Aluno.class, aluno.getId());
			manager.remove(a);
			manager.flush();
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	public Aluno buscarPeloCodigo(Long id) {
		return manager.find(Aluno.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Aluno> buscarTodos() {
		
		String query="select a from Aluno a";
		
		Query q = manager.createQuery(query);
		
		return q.getResultList();
	}	
}
