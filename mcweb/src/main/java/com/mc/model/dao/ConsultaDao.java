package com.mc.model.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mc.model.Consulta;
import com.mc.util.jpa.Transactional;

public class ConsultaDao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConsultaDao.class);
	
	@Transactional
	public Consulta salvar(Consulta consulta) throws PersistenceException {
		
		LOGGER.info("salvar DAO... Consulta = " + consulta);
		
		try {
			return manager.merge(consulta);
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional
	public void excluir(Consulta consulta) throws PersistenceException {

		try {
			Consulta a = manager.find(Consulta.class, consulta.getId());
			manager.remove(a);
			manager.flush();
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	public Consulta buscarPeloCodigo(Long id) {
		return manager.find(Consulta.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Consulta> buscarTodos() {
		
		String query="select a from Curso a";
		
		Query q = manager.createQuery(query);
		
		return q.getResultList();
	}	
}