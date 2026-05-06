package com.mc.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.mc.model.Usuario;
import com.mc.model.dao.UsuarioDao;
import com.mc.model.enums.Perfil;
import com.mc.view.LoginBean;

import lombok.extern.log4j.Log4j;

@Log4j
public class UsuarioService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject	
	private UsuarioDao usuarioDao;
	@SuppressWarnings("unused")
	@Inject
	private LoginBean loginBean;
	
	public Usuario salvar(Usuario usuario) {

	    Usuario logado = loginBean.getUsuarioLogado();

	    if (logado != null && logado.getPerfil() == Perfil.ATENDENTE) {
	        if (usuario.getPerfil() != Perfil.PACIENTE) {
	            throw new RuntimeException("ATENDENTE só pode cadastrar PACIENTE");
	        }
	    }

	    return usuarioDao.salvar(usuario);
	}

	public Usuario buscarPorEmail(String email){
		return usuarioDao.buscarPorEmail(email);
	}

	public Usuario autenticar(String email, String senha){

		Usuario usuario_db = buscarPorEmail(email);
		if(usuario_db != null && usuario_db.getSenha().equals(senha)){
			log.info(usuario_db.toString() + " logado");
			return usuario_db;
		}
		return null;
	}
	
	public void excluir(Usuario usuario) {

	    Usuario logado = loginBean.getUsuarioLogado();

	    if (logado != null && logado.getPerfil() == Perfil.ATENDENTE) {
	        if (usuario.getPerfil() != Perfil.PACIENTE) {
	            throw new RuntimeException("ATENDENTE só pode excluir PACIENTE");
	        }
	    }

	    usuarioDao.excluir(usuario);
	}
	
	public List<Usuario> buscarTodos() {
		return usuarioDao.buscarTodos();
	}
	
}
