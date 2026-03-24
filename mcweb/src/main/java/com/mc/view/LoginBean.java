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
public class LoginBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioService usuarioService;

	private Usuario usuario = new Usuario();

	@PostConstruct
	public void inicializar() {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Login realizado com sucesso!", usuario.toString()));

		log.info("usuario: " + usuario.toString());
		usuario = new Usuario();
		log.info("init pesquisa");

	}
	
	public void salvar() {
		log.info(usuario.toString());
		usuarioService.salvar(usuario);
		
		FacesContext.getCurrentInstance().
        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
        		"O usuario foi gravado com sucesso!", 
        		usuario.toString()));
		
		log.info("usario: " + usuario.toString());
	}
	

	/* */
	public void login() {
		log.info(usuario.toString());

		if (usuarioService.autenticar(usuario)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Login realizado com sucesso!", usuario.toString()));
			log.info("usuario: " + usuario.toString());
		} else {
			/*nao autenticado*/
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login falhou!", "Email ou senha incorretos"));
		}

	}

}
