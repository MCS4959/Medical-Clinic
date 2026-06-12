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
import com.mc.service.EmailService;
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
	private EmailService emailService;
	
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
	    
	    try {
	        Usuario usuarioLogado = loginBean.getUsuarioLogado();
	        
	        if (usuarioLogado != null) {
	            String nomePaciente = usuarioLogado.getNome();
	            
	            if (usuarioLogado.getPerfil() == com.mc.model.enums.Perfil.PACIENTE) {
	                // Se for paciente, busca apenas suas consultas
	                List<Consulta> minhasConsultas = consultaService.buscarConsultasDoPaciente(nomePaciente);
	                this.setConsultas(minhasConsultas);
	            } else {
	                // Se for médico/admin, busca todas
	                this.setConsultas(consultaService.buscarTodos());
	            }
	        } else {
	            this.setConsultas(consultaService.buscarTodos());
	        }
	    } catch (Exception e) {
	        log.error("Erro ao inicializar consultas: " + e.getMessage());
	        this.setConsultas(consultaService.buscarTodos());
	    }
	    
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

		    if (logado != null && logado.getPerfil() == com.mc.model.enums.Perfil.PACIENTE) {
		        this.consulta.setPaciente(logado.getNome());
		    }

		    log.info(consulta.toString());
		    consultaService.salvar(consulta);
		    
		    // ✅ NOVO: Recarrega apenas as consultas do usuário logado
		    if (logado != null && logado.getPerfil() == com.mc.model.enums.Perfil.PACIENTE) {
		        this.consultas = consultaService.buscarConsultasDoPaciente(logado.getNome());
		    } else {
		        // Se for médico/admin, mostra todas
		        this.consultas = consultaService.buscarTodos();
		    }

		    // busca o paciente pelo nome para pegar o email
		    try {
		        String nomePaciente = consulta.getPaciente();
		        if (nomePaciente != null && !nomePaciente.isEmpty()) {

		            // busca o usuário paciente pelo nome
		            Usuario paciente = usuarioService.buscarTodos()
		                .stream()
		                .filter(u -> u.getNome().equals(nomePaciente) 
		                          && u.getPerfil() == com.mc.model.enums.Perfil.PACIENTE)
		                .findFirst()
		                .orElse(null);

		            if (paciente != null && paciente.getEmail() != null) {

		                // formata a data
		                String dataFormatada = consulta.getData() != null
		                    ? consulta.getData().format(
		                        java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm"))
		                    : "A definir";

		                emailService.enviarConfirmacaoConsulta(
		                    paciente.getEmail(),
		                    paciente.getNome(),
		                    consulta.getMedico(),
		                    consulta.getEspecialidade() != null 
		                        ? consulta.getEspecialidade().toString() : "",
		                    dataFormatada
		                );
		            }
		        }
		    } catch (Exception e) {
		        // consulta já foi salva, só loga o erro do email
		        log.warn("Email de confirmação não enviado: " + e.getMessage());
		    }

		    FacesContext.getCurrentInstance().addMessage(null,
		        new FacesMessage(FacesMessage.SEVERITY_INFO,
		            "Consulta agendada com sucesso!", consulta.toString()));

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