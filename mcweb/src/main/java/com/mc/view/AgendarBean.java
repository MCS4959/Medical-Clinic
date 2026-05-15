package com.mc.view;

import java.io.Serializable;
import java.util.ArrayList;
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
import com.mc.model.Agendar;
import com.mc.service.AgendarService;

@Log4j
@Getter
@Setter
@Named
@ViewScoped
public class AgendarBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private AgendarService agendarService;
	private Agendar agendar = new Agendar();
	private List<Agendar> agendas = new ArrayList<Agendar>();

	
	@PostConstruct
	public void inicializar() {
		log.debug("init pesquisa"); 
		this.setAgendas(AgendarService.buscarTodos());
		limpar();
	}
	
	public void salvar() {
		log.info(curso.toString());
		manterCursoService.salvar(curso);
		this.cursos = manterCursoService.buscarTodos();

		FacesContext.getCurrentInstance().
        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
        		"O curso foi gravado com sucesso!", 
        		curso.toString()));
		
				limpar();
		log.info("curso: " + curso.toString());
	}
	
	public void excluir() {
		try {
			manterCursoService.excluir(curso);
			this.cursos = manterCursoService.buscarTodos();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Curso " + curso.getSigla() + " excluído com sucesso.", null));
			log.info("curso excluido = " + curso.getSigla());
			
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
			new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um problema", null));
		}
	}
		
	public void limpar() {

		this.curso = new Curso();
	}
	
}