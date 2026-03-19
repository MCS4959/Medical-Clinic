package com.mc.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.mc.model.Usuario;
import com.mc.model.dao.UsuarioDao;

import lombok.extern.log4j.Log4j;

@Log4j
public class UsuarioService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject	
	private UsuarioDao usuarioDao;

	public Usuario buscarPorEmail(String email){
		return usuarioDao.buscarPorEmail(email);
	}

	public boolean autenticar(Usuario usuario){

		Usuario usuario_db = buscarPorEmail(usuario.getEmail());
		if(usuario_db != null && usuario_db.getSenha().equals(usuario.getSenha())){
			// buscar o usuario por email e comparar a senha
			log.info(usuario_db.toString() + " logado");
			return true;
		}
		return false;
	}
	
	
}
