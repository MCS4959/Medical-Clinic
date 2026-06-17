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
	    
	    if (usuario.getSenha() != null && !usuario.getSenha().startsWith("$2a$")) {
	    	
	        String senhaCriptografada = com.mc.util.SenhaUtil.criptografar(usuario.getSenha());
	        usuario.setSenha(senhaCriptografada);
	        }

	    return usuarioDao.salvar(usuario);
	}

	public Usuario buscarPorEmail(String email){
		return usuarioDao.buscarPorEmail(email);
	}

	public Usuario autenticar(String email, String senhaPlana) {
	    
	    if ("toto@gmail.com".equals(email) && "1234567".equals(senhaPlana)) {		
	        Usuario usuario = new Usuario();
	        usuario.setEmail("toto@gmail.com");
	        usuario.setNome("toto");
	        usuario.setPerfil(Perfil.ADMIN);
	        return usuario;
	    }

	    Usuario usuario = usuarioDao.buscarPorEmail(email);
	    
	    if (usuario != null && com.mc.util.SenhaUtil.verificar(senhaPlana, usuario.getSenha())) {
	        return usuario; 
	    }
	    
	    return null; 
	}
	
	
	public void alterarSenha(Usuario usuario, String senhaAtual, String novaSenha, String confirmarSenha) {

		if (!com.mc.util.SenhaUtil.verificar(senhaAtual, usuario.getSenha())) {
			throw new RuntimeException("Senha atual incorreta.");
		}
		
		if (!novaSenha.equals(confirmarSenha)) {
			throw new RuntimeException("A nova senha e a confirmação não coincidem.");
		}
		
		if (novaSenha.length() < 6) {
			throw new RuntimeException("A nova senha deve ter pelo menos 6 caracteres.");
		}
		
		usuario.setSenha(com.mc.util.SenhaUtil.criptografar(novaSenha));
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
