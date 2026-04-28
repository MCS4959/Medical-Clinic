package com.mc.model.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.mc.model.Usuario;
import com.mc.util.jpa.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsuarioDao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;

	public Usuario buscarPorEmail(String email){
		try {
			return manager.createQuery("SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class)
					.setParameter("email", email)
					.getSingleResult();
		} catch (Exception e) {
			return null;}
	}
	
	/** Tentativa de cadastrar o usuario, estou pegando do teste-crud para salvar no banco. 
	 * 
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioDao.class);
	
	@Transactional
	public Usuario salvar(Usuario usuario) throws PersistenceException {
		
		LOGGER.info("salvar DAO... usuario = " + usuario);
		
		try {
			return manager.merge(usuario);
			
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw e;
		}
	}
	public List<Usuario> buscarTodos() {
		String query = "select a from Usuario a";
		
		Query q = manager.createQuery(query);
		
		return q.getResultList();
		}
	
}
