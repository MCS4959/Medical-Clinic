package com.mc.view;

import java.io.Serializable;
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
public class AlterarSenhaBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioService usuarioService;
	
	@Inject
	private LoginBean loginBean;
	
	private String senhaAtual;
	private String novaSenha;
	private String confirmarSenha;
	
	public void alterar() {
		try {
			
				Usuario logado = loginBean.getUsuarioLogado();
				
				usuarioService.alterarSenha(logado, senhaAtual, novaSenha,confirmarSenha);
				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Senha alterada com sucesso!", null));
				
				
				this.senhaAtual = null;
				this.novaSenha = null;
				this.confirmarSenha = null;
				
			}
		catch (Exception e) {
			
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", e.getMessage()));
			}
	}
}