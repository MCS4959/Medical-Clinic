package com.mc.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mc.model.Consulta;
import com.mc.model.Usuario;
import com.mc.service.ConsultaService;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Getter
@Setter
@Named
@ViewScoped
public class AtendimentoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ConsultaService consultaService;

    @Inject
    private LoginBean loginBean; // Injetado para identificar o médico logado

    private List<Consulta> consultasDoMedico = new ArrayList<>();
    private Consulta consultaSelecionada = new Consulta();

    @PostConstruct
    public void inicializar() {
        carregarConsultasDoDia();
    }

    public void carregarConsultasDoDia() {
        Usuario medicoLogado = loginBean.getUsuarioLogado();
        
        if (medicoLogado != null) {
            // Filtra todas as consultas trazendo apenas as que possuem o nome do médico logado
            // E que ainda não foram concluídas (status == false)
            this.consultasDoMedico = consultaService.buscarTodos().stream()
                .filter(c -> c.getMedico() != null && c.getMedico().equals(medicoLogado.getNome()) && !c.isStatus())
                .collect(Collectors.toList());
        } else {
            this.consultasDoMedico = new ArrayList<>();
        }
    }

    public void prepararAtendimento(Consulta consulta) {
        this.consultaSelecionada = consulta;
    }

    public void finalizarAtendimento() {
        try {
            log.info("Finalizando atendimento da consulta ID: " + consultaSelecionada.getId());
            
            this.consultaSelecionada.setStatus(true);
            
            consultaService.salvar(consultaSelecionada);
            
            carregarConsultasDoDia();
            
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Atendimento finalizado e salvo com sucesso!", null));
            
            this.consultaSelecionada = new Consulta(); 
            
        } catch (Exception e) {
            log.error("Erro ao finalizar atendimento", e);
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar o atendimento.", null));
        }
    }
}