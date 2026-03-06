package com.mc.model.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mc.model.Curso;
import com.mc.util.jpa.Transactional;

public class CursoDao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CursoDao.class);
	
	@Transactional
	public Curso salvar(Curso curso) throws PersistenceException {
		
		LOGGER.info("salvar DAO... Curso = " + curso);
		
		try {
			return manager.merge(curso);
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional
	public void excluir(Curso curso) throws PersistenceException {

		try {
			Curso a = manager.find(Curso.class, curso.getId());
			manager.remove(a);
			manager.flush();
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	public Curso buscarPeloCodigo(Long id) {
		return manager.find(Curso.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Curso> buscarTodos() {
		
		String query="select a from Curso a";
		
		Query q = manager.createQuery(query);
		
		return q.getResultList();
	}	
}
