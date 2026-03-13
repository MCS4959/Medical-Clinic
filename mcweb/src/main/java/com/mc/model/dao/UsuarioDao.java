package com.mc.model.dao;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.mc.model.Usuario;

public class UsuarioDao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;

	public Usuario buscarPorEmail(String email){
		// fazer a busca
		manager.find(null, email);
		return null;
	}
	
}
