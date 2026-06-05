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

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import com.mc.model.Consulta;
import com.mc.model.Usuario;
import com.mc.model.enums.Especialidade;
import com.mc.service.ConsultaService;
import com.mc.service.UsuarioService;

@Log4j
@Getter
@Setter
@Named
@ViewScoped
public class ConsultaBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ConsultaService consultaService;
	
	@Inject
	private UsuarioService usuarioService;
	
	@Inject
	private LoginBean loginBean; 

	 
	
	
	private Consulta consulta = new Consulta();
	private List<Usuario> todosOsPacientes = new ArrayList<>();
	private List<Consulta> consultas = new ArrayList<Consulta>();
	private List<Especialidade> especialidades = Arrays.asList(Especialidade.values());
	private List<Usuario> medicos = new ArrayList<Usuario>();

	
	@PostConstruct
	public void inicializar() {
		log.debug("init pesquisa"); 
		this.setConsultas(consultaService.buscarTodos());
		
		this.todosOsPacientes = usuarioService.buscarTodos();
		limpar();
	}
	

		public void carregarMedicos() {
			if (this.consulta != null && this.consulta.getEspecialidade() != null) {
				this.medicos = usuarioService.buscarMedicosPorEspecialidade(this.consulta.getEspecialidade());
			} else {
				this.medicos = new ArrayList<>();
			}
		}

		public void prepararEdicao(Consulta consultaSelecionada) {
			this.consulta = consultaSelecionada;
			carregarMedicos(); 
		}
		
	public void salvar() {
		Usuario logado = loginBean.getUsuarioLogado();
		
		if(logado != null && logado.getPerfil() == com.mc.model.enums.Perfil.PACIENTE) {
			this.consulta.setPaciente(logado.getNome());
			
		}
		
		log.info(consulta.toString());
		consultaService.salvar(consulta);
		this.consultas = consultaService.buscarTodos();

		FacesContext.getCurrentInstance().
        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
        		"A consulta foi gravado com sucesso!", 
        		consulta.toString()));
		
				limpar();
		log.info("consulta: " + consulta.toString());
	}
	
	public void excluir() {
		try {
			consultaService.excluir(consulta);
			this.consultas = consultaService.buscarTodos();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Consulta " + consulta.getId() + " excluído com sucesso.", null));
			log.info("consulta excluido = " + consulta.getId());
			
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
			new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um problema", null));
		}
	}
	
	// No ConsultaBean.java
	public Especialidade[] getEspecialidades() {
	    return Especialidade.values();
	}
		
	public void limpar() {

		this.consulta = new Consulta();
		this.medicos = new ArrayList<>();
	}
	
}