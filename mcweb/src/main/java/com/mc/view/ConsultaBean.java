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
import com.mc.model.Consulta;
import com.mc.service.ConsultaService;

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
	private Consulta consulta = new Consulta();
	private List<Consulta> consultas = new ArrayList<Consulta>();

	
	@PostConstruct
	public void inicializar() {
		log.debug("init pesquisa"); 
		this.setConsultas(ConsultaService.buscarTodos());
		limpar();
	}
	
	public void salvar() {
		log.info(consulta.toString());
		ConsultaService.salvar(consulta);
		this.consultas = ConsultaService.buscarTodos();

		FacesContext.getCurrentInstance().
        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
        		"A consulta foi gravado com sucesso!", 
        		consulta.toString()));
		
				limpar();
		log.info("consulta: " + consulta.toString());
	}
	
	public void excluir() {
		try {
			ConsultaService.excluir(consulta);
			this.consultas = ConsultaService.buscarTodos();
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
		
	public void limpar() {

		this.consulta = new Consulta();
	}
	
}