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

import com.mc.model.Professor;
import com.mc.service.ManterProfessorService;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Getter
@Setter
@Named
@ViewScoped
public class ManterProfessorBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ManterProfessorService manterProfessorService;
	private Professor professor = new Professor();
	private List<Professor> professores = new ArrayList<Professor>();

	
	@PostConstruct
	public void inicializar() {
		log.debug("init pesquisa"); 
		this.setProfessores(manterProfessorService.buscarTodos());
		limpar();
	}
	
	public void salvar() {
		log.info(professor.toString());
		manterProfessorService.salvar(professor);
		this.professores = manterProfessorService.buscarTodos();

		FacesContext.getCurrentInstance().
        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
        		"O professor foi gravado com sucesso!", 
        		professor.toString()));
		
				limpar();
		log.info("professor: " + professor.toString());
	}
	
	public void excluir() {
		try {
			manterProfessorService.excluir(professor);
			this.professores = manterProfessorService.buscarTodos();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Professor " + professor.getNome() + " excluído com sucesso.", null));
			log.info("professor excluido = " + professor.getNome());
			
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
			new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um problema", null));
		}
	}
		
	public void limpar() {

		this.professor = new Professor();
	}
	
}
