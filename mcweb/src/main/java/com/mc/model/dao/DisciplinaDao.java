package com.mc.model.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mc.model.Disciplina;
import com.mc.util.jpa.Transactional;

public class DisciplinaDao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DisciplinaDao.class);
	
	@Transactional
	public Disciplina salvar(Disciplina disciplina) throws PersistenceException {
		
		LOGGER.info("salvar DAO... Disciplina = " + disciplina);
		
		try {
			return manager.merge(disciplina);
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional
	public void excluir(Disciplina disciplina) throws PersistenceException {

		try {
			Disciplina a = manager.find(Disciplina.class, disciplina.getId());
			manager.remove(a);
			manager.flush();
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	public Disciplina buscarPeloCodigo(Long id) {
		return manager.find(Disciplina.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Disciplina> buscarTodos() {
		
		String query="select a from Disciplina a";
		
		Query q = manager.createQuery(query);
		
		return q.getResultList();
	}	
}
