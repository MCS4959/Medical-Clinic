package com.mc.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mc.model.Usuario;
import com.mc.model.enums.Especialidade;
import com.mc.model.enums.Perfil;
import com.mc.service.UsuarioService;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Getter
@Setter
@Named
@ViewScoped
public class CadastrarUsuarioBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioService usuarioService;

	private Usuario usuario = new Usuario();
	private List<Usuario> usuarios = new ArrayList<Usuario>();
	private List<Perfil> perfis = Arrays.asList(Perfil.values());
	private List<Especialidade> especialidades = Arrays.asList(Especialidade.values());

	@PostConstruct
	public void inicializar() {		

		log.info("init pesquisa");
		this.setUsuarios(usuarioService.buscarTodos());
		
	}
	
	public void salvar() {
		log.info(usuario.toString());
		
		usuario = usuarioService.salvar(usuario);
		this.setUsuarios(usuarioService.buscarTodos());
		
		FacesContext.getCurrentInstance().
        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
        		"O usuario foi gravado com sucesso!", 
        		usuario.toString()));
		limpar();
		log.info("usuario: " + usuario.toString());
	}	
	
	public void excluir() {
		try {
			usuarioService.excluir(usuario);
			this.usuarios = usuarioService.buscarTodos();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Usuario " + usuario.getNome() + " excluído com sucesso.", null));
			log.info("usuario excluido = " + usuario.getNome());
			
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
			new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um problema", null));
		}
	}
	
	public void limpar() {

		this.usuario = new Usuario();
	}

}
