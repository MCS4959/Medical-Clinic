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

import com.mc.model.Curso;
import com.mc.model.Disciplina;
import com.mc.service.ManterCursoService;
import com.mc.service.ManterDisciplinaService;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Getter
@Setter
@Named
@ViewScoped
public class ManterDisciplinaBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ManterDisciplinaService manterDisciplinaService;
	@Inject
	private ManterCursoService manterCursoService;

	private Disciplina disciplina = new Disciplina();
	private List<Disciplina> disciplinas = new ArrayList<Disciplina>();
	private List<Curso> cursos = new ArrayList<Curso>();

	
	@PostConstruct
	public void inicializar() {
		log.debug("init pesquisa"); 
		this.setDisciplinas(manterDisciplinaService.buscarTodos());
		this.setCursos(manterCursoService.buscarTodos());
		limpar();
	}
	
	public void salvar() {
		log.info(disciplina.toString());
		manterDisciplinaService.salvar(disciplina);
		this.disciplinas = manterDisciplinaService.buscarTodos();

		FacesContext.getCurrentInstance().
        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
        		"O disciplina foi gravado com sucesso!", 
        		disciplina.toString()));
		
				limpar();
		log.info("disciplina: " + disciplina.toString());
	}
	
	public void excluir() {
		try {
			manterDisciplinaService.excluir(disciplina);
			this.disciplinas = manterDisciplinaService.buscarTodos();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Disciplina " + disciplina.getSigla() + " excluído com sucesso.", null));
			log.info("disciplina excluido = " + disciplina.getSigla());
			
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
			new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um problema", null));
		}
	}
		
	public void limpar() {

		this.disciplina = new Disciplina();
	}
	
}
