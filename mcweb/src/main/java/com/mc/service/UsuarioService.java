package com.mc.service;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.mc.model.Usuario;
import com.mc.model.dao.UsuarioDao;
import com.mc.model.enums.Perfil;
import com.mc.view.LoginBean;


import lombok.extern.log4j.Log4j;


@Log4j
@ApplicationScoped
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
		
		if(email.equals("toto@gmail.com") && senha.equals("1234567")) {		
			Usuario usuario = new Usuario();
			usuario.setEmail("toto@gmail.com");
			usuario.setNome("TOTO");
			usuario.setPerfil(Perfil.ADMIN);

			return usuario;

		}

		Usuario usuario_db = buscarPorEmail(email);
		if(usuario_db != null && usuario_db.getSenha().equals(senha)){
			log.info(usuario_db.toString() + " logado");
			return usuario_db;
		}
		return null;
	}
	
	
	public void alterarSenha(Usuario usuario, String senhaAtual, String novaSenha, String confirmarSenha) {
		
		if (!usuario.getSenha().equals(senhaAtual)) {
			throw new RuntimeException("Senha atual incorreta.");
		}
		
		if (!novaSenha.equals(confirmarSenha)) {
			throw new RuntimeException("A nova senha e a confirmação não coincidem.");
		}
		
		if (novaSenha.length() < 6) {
			throw new RuntimeException("A nova senha deve ter pelo menos 6 caracteres.");
		}
		
		usuario.setSenha(novaSenha);
		usuarioDao.salvar(usuario);
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

	    Usuario logado = loginBean.getUsuarioLogado();

	    if (logado != null && logado.getPerfil() == Perfil.ATENDENTE) {
	        return usuarioDao.buscarTodos()
	                .stream()
	                .filter(u -> u.getPerfil() == Perfil.PACIENTE)
	                .toList();
	    }

	    return usuarioDao.buscarTodos();
	}
	
	public List<Usuario> buscarMedicosPorEspecialidade(com.mc.model.enums.Especialidade especialidade) {
	    if (especialidade == null) {
	        return new java.util.ArrayList<>();
	    }
	    // Filtra na base de dados todos os utilizadores que são MÉDICOS e têm a especialidade escolhida
	    return usuarioDao.buscarTodos()
	            .stream()
	            .filter(u -> u.getPerfil() == com.mc.model.enums.Perfil.MEDICO && u.getEspecialidade() == especialidade)
	            .toList();
	}
	
}
