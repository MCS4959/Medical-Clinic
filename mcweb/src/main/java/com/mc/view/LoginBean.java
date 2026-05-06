package com.mc.view;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.mc.model.Usuario;
import com.mc.service.UsuarioService;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Getter
@Setter
@Named
@SessionScoped
public class LoginBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioService usuarioService;

	private Usuario usuario = new Usuario();
	private String email;
	private String senha;

	@PostConstruct
	public void inicializar() {
		
		usuario = new Usuario();
		log.info("init pesquisa");

	}
	
	

	/* */
	public String login() {
		log.info("logando.......");
		
		HttpSession session = getSession(); // creates new empty session
		
		usuario = (Usuario)session.getAttribute("usuario");
		
		// se usuario não está na sessao = não está logado
		if(usuario == null) {
			
			usuario = usuarioService.autenticar(email, senha);
			if (usuario != null) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Login realizado com sucesso!", usuario.getNome()));
				log.info("usuario: " + usuario.toString());
				
				session.setAttribute("usuario", usuario);
			} else {
				/*nao autenticado*/
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login falhou!", "Email ou senha incorretos"));
				
				return "/restrito/login.xhtml";
			}
		}
		return "/restrito/home.xhtml";		
	}
	
	public String sair() {
		log.info("Session invalidate");		
		
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();	    
		
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "/index.xhtml";
	}
	
	
	private HttpSession getSession() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession session = request.getSession();
		
		return session;
	}
	public Usuario getUsuarioLogado() {
	    HttpSession session = getSession();
	    return (Usuario) session.getAttribute("usuario");
	}

}
