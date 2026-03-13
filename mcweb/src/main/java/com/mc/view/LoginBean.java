package com.mc.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mc.model.Usuario;
import com.mc.service.UsuarioService;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;


@Log4j
@Getter
@Setter
@Named
@ViewScoped
public class LoginBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioService usuarioService;

	private Usuario usuario;
	
	

	
	@PostConstruct
	public void inicializar() {
		log.info("init pesquisa"); 		

	}
	

	/* */
	public void Login() {
		log.info(usuario.toString());

		// buscar o usuario por email e comparar a senha
		if(usuarioService.autenticar(usuario)){
			// autenticado
		}
		else{
			// nao autenticado
		}
	

		FacesContext.getCurrentInstance().
        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
        		"Login realizado com sucesso!", 
        		usuario.toString()));
		
		log.info("usuario: " + usuario.toString());
	}
	
	
	
}
