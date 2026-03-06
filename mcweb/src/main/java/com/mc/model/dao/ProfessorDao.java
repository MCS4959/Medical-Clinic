package com.mc.model.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mc.model.Professor;
import com.mc.util.jpa.Transactional;

public class ProfessorDao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProfessorDao.class);
	
	@Transactional
	public Professor salvar(Professor professor) throws PersistenceException {
		
		LOGGER.info("salvar DAO... professor = " + professor);
		
		try {
			return manager.merge(professor);
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional
	public void excluir(Professor professor) throws PersistenceException {

		try {
			Professor a = manager.find(Professor.class, professor.getId());
			manager.remove(a);
			manager.flush();
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	public Professor buscarPeloCodigo(Long id) {
		return manager.find(Professor.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Professor> buscarTodos() {
		
		String query="select a from Professor a";
		
		Query q = manager.createQuery(query);
		
		return q.getResultList();
	}	
}
