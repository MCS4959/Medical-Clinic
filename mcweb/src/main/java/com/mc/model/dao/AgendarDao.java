package com.mc.model.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mc.model.Agendar;
import com.mc.util.jpa.Transactional;

public class AgendarDao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AgendarDao.class);
	
	@Transactional
	public Agendar salvar(Agendar agendar) throws PersistenceException {
		
		LOGGER.info("salvar DAO... Agendar = " + agendar);
		
		try {
			return manager.merge(agendar);
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional
	public void excluir(Agendar agendar) throws PersistenceException {

		try {
			Agendar a = manager.find(Agendar.class, agendar.getId());
			manager.remove(a);
			manager.flush();
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	public Agendar buscarPeloCodigo(Long id) {
		return manager.find(Agendar.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Agendar> buscarTodos() {
		
		String query="select a from Curso a";
		
		Query q = manager.createQuery(query);
		
		return q.getResultList();
	}	
}