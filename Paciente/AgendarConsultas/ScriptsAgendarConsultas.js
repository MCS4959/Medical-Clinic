const medicos = [
  {
      id: 1,
      nome: "Dr. João Silva",
      especialidade: "cardiologia",
      diasDisponiveis: [5, 6, 12, 13, 19, 20, 26, 27],
      horariosDisponiveis: {
          5: ["09:00", "09:30", "10:00", "14:00", "15:00"],
          6: ["10:00", "10:30", "11:00", "14:00", "15:30"],
          12: ["09:00", "10:00", "16:00", "16:30"],
          13: ["09:30", "10:30", "14:00", "15:00"],
          19: ["09:00", "09:30", "10:00", "11:00"],
          20: ["14:00", "14:30", "15:00", "16:00"],
          26: ["09:00", "10:00", "10:30"],
          27: ["14:00", "15:00", "16:00"]
      }
  },
  {
      id: 2,
      nome: "Dra. Maria Santos",
      especialidade: "cardiologia",
      diasDisponiveis: [7, 8, 14, 15, 21, 22, 28, 29],
      horariosDisponiveis: {
          7: ["08:00", "08:30", "09:00", "13:00"],
          8: ["10:00", "11:00", "14:00", "15:00"],
          14: ["08:30", "09:00", "14:30", "15:30"],
          15: ["10:00", "10:30", "11:00"],
          21: ["08:00", "09:00", "16:00"],
          22: ["14:00", "15:00", "16:30"],
          28: ["08:30", "09:30", "15:00"],
          29: ["10:00", "11:00", "14:00"]
      }
  },
  {
      id: 3,
      nome: "Dr. Carlos Oliveira",
      especialidade: "dermatologia",
      diasDisponiveis: [3, 4, 10, 11, 17, 18, 24, 25],
      horariosDisponiveis: {
          3: ["08:00", "09:00", "10:00", "15:00"],
          4: ["11:00", "12:00", "14:00"],
          10: ["08:30", "09:30", "10:30", "15:30"],
          11: ["09:00", "11:00", "13:00", "14:00"],
          17: ["08:00", "08:30", "09:00"],
          18: ["13:00", "14:00", "15:00", "16:00"],
          24: ["10:00", "11:00", "14:30"],
          25: ["08:00", "09:00", "15:00", "16:00"]
      }
  },
  {
      id: 4,
      nome: "Dra. Ana Costa",
      especialidade: "pediatria",
      diasDisponiveis: [2, 9, 16, 23, 30],
      horariosDisponiveis: {
          2: ["09:00", "10:00", "11:00", "14:00", "15:00"],
          9: ["09:00", "09:30", "10:00", "14:00"],
          16: ["10:00", "11:00", "15:00", "16:00"],
          23: ["09:00", "10:30", "14:00", "15:00"],
          30: ["10:00", "11:00", "14:30", "16:00"]
      }
  },
  {
      id: 5,
      nome: "Dr. Roberto Alves",
      especialidade: "ortopedia",
      diasDisponiveis: [1, 8, 15, 22, 29],
      horariosDisponiveis: {
          1: ["08:00", "09:00", "10:00", "11:00"],
          8: ["09:00", "10:00", "14:00", "15:00"],
          15: ["08:30", "09:30", "14:30", "15:30"],
          22: ["10:00", "11:00", "15:00"],
          29: ["08:00", "09:00", "10:00", "16:00"]
      }
  }
];

// === VARIÁVEIS GLOBAIS ===
let medicoSelecionado = null;
let dataSelecionada = null;
let horarioSelecionado = null;
let mesAtual = new Date().getMonth();
let anoAtual = new Date().getFullYear();

// === ELEMENTOS DO DOM ===
const selectEspecialidade = document.getElementById("especialidade");
const selectMedico = document.getElementById("medico");
const etapaMedico = document.getElementById("etapa-medico");
const etapaCalendario = document.getElementById("etapa-calendario");
const etapaPaciente = document.getElementById("etapa-paciente");
const etapaHorarios = document.getElementById("etapa-horarios");
const btnAgendar = document.getElementById("btn-agendar");
const btnCancelar = document.getElementById("btn-cancelar");
const mensagem = document.getElementById("mensagem");
const nomePaciente = document.getElementById("nome-paciente");
const telefonePaciente = document.getElementById("telefone-paciente");
const emailPaciente = document.getElementById("email-paciente");
const diasContainer = document.getElementById("dias-container");
const horariosGrid = document.getElementById("horarios-grid");
const mesAnoDisplay = document.getElementById("mes-ano");
const btnAnterior = document.getElementById("btn-anterior");
const btnProximo = document.getElementById("btn-proximo");
const resumoAgendamento = document.getElementById("resumo-agendamento");

// === EVENTO: Especialidade Selecionada ===
selectEspecialidade.addEventListener("change", (e) => {
  const especialidade = e.target.value;
  selectMedico.innerHTML = '<option value="">Selecione o médico</option>';
  etapaMedico.style.display = "none";
  etapaCalendario.classList.remove("ativo");
  etapaPaciente.classList.remove("ativo");
  etapaHorarios.classList.remove("ativo");
  resumoAgendamento.innerHTML = "";
  medicoSelecionado = null;
  dataSelecionada = null;
  horarioSelecionado = null;

  if (!especialidade) return;

  const medicosFiltrados = medicos.filter(m => m.especialidade === especialidade);
  medicosFiltrados.forEach(m => {
      const opt = document.createElement("option");
      opt.value = m.id;
      opt.textContent = m.nome;
      selectMedico.appendChild(opt);
  });

  if (medicosFiltrados.length > 0) {
      etapaMedico.style.display = "block";
  }
});

// === EVENTO: Médico Selecionado ===
selectMedico.addEventListener("change", (e) => {
  const idMedico = parseInt(e.target.value, 10);
  etapaCalendario.classList.remove("ativo");
  etapaPaciente.classList.remove("ativo");
  etapaHorarios.classList.remove("ativo");
  resumoAgendamento.innerHTML = "";
  dataSelecionada = null;
  horarioSelecionado = null;

  if (!idMedico) return;

  medicoSelecionado = medicos.find(m => m.id === idMedico);
  mesAtual = new Date().getMonth();
  anoAtual = new Date().getFullYear();
  renderizarCalendario();
  etapaCalendario.classList.add("ativo");
});

// === RENDERIZAR CALENDÁRIO ===
function renderizarCalendario() {
  const primeiroDia = new Date(anoAtual, mesAtual, 1);
  const ultimoDia = new Date(anoAtual, mesAtual + 1, 0);
  const diaInicio = primeiroDia.getDay();
  const diasNoMes = ultimoDia.getDate();

  // Atualizar cabeçalho
  const meses = ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
                "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"];
  mesAnoDisplay.textContent = `${meses[mesAtual]} ${anoAtual}`;

  // Limpar dias
  diasContainer.innerHTML = "";

  // Adicionar dias do mês anterior
  const ultimoDiaMesAnterior = new Date(anoAtual, mesAtual, 0).getDate();
  for (let i = diaInicio - 1; i >= 0; i--) {
      const dia = document.createElement("div");
      dia.className = "dia outro-mes";
      dia.textContent = ultimoDiaMesAnterior - i;
      diasContainer.appendChild(dia);
  }

  // Adicionar dias do mês atual
  for (let i = 1; i <= diasNoMes; i++) {
      const dia = document.createElement("div");
      dia.textContent = i;

      if (medicoSelecionado.diasDisponiveis.includes(i)) {
          dia.className = "dia disponivel";
          dia.addEventListener("click", () => selecionarDia(i, dia));
      } else {
          dia.className = "dia desabilitado";
      }

      diasContainer.appendChild(dia);
  }

  // Adicionar dias do próximo mês
  const totalCelulas = diasContainer.children.length;
  for (let i = 1; totalCelulas + i <= 42; i++) {
      const dia = document.createElement("div");
      dia.className = "dia outro-mes";
      dia.textContent = i;
      diasContainer.appendChild(dia);
  }
}

// === BOTÕES MÊS ===
btnAnterior.addEventListener("click", () => {
  if (mesAtual === 0) {
      mesAtual = 11;
      anoAtual--;
  } else {
      mesAtual--;
  }
  renderizarCalendario();
});

btnProximo.addEventListener("click", () => {
  if (mesAtual === 11) {
      mesAtual = 0;
      anoAtual++;
  } else {
      mesAtual++;
  }
  renderizarCalendario();
});

// === EVENTO: Dia Selecionado ===
function selecionarDia(dia, elemento) {
  // Remover seleção anterior
  document.querySelectorAll(".dia.selecionado").forEach(d => {
      d.classList.remove("selecionado");
  });

  elemento.classList.add("selecionado");
  dataSelecionada = new Date(anoAtual, mesAtual, dia);
  horarioSelecionado = null;

  // Mostrar formulário de paciente e horários
  etapaPaciente.classList.add("ativo");
  etapaHorarios.classList.add("ativo");
  renderizarHorarios(dia);
  resumoAgendamento.innerHTML = "";
}

// === RENDERIZAR HORÁRIOS ===
function renderizarHorarios(dia) {
  horariosGrid.innerHTML = "";
  const horarios = medicoSelecionado.horariosDisponiveis[dia] || [];

  horarios.forEach(h => {
      const btn = document.createElement("button");
      btn.type = "button";
      btn.className = "btn-horario";
      btn.textContent = h;
      btn.addEventListener("click", () => {
          document.querySelectorAll(".btn-horario.selecionado").forEach(b => {
              b.classList.remove("selecionado");
          });
          btn.classList.add("selecionado");
          horarioSelecionado = h;
          atualizarBotaoAgendar();
      });
      horariosGrid.appendChild(btn);
  });
}

// === ATUALIZAR BOTÃO AGENDAR ===
function atualizarBotaoAgendar() {
  const temNome = nomePaciente.value.trim() !== "";
  if (temNome && horarioSelecionado && dataSelecionada && medicoSelecionado) {
      btnAgendar.disabled = false;
  } else {
      btnAgendar.disabled = true;
  }
}

nomePaciente.addEventListener("input", atualizarBotaoAgendar);
telefonePaciente.addEventListener("input", atualizarBotaoAgendar);

// === EVENTO: Confirmar Agendamento ===
btnAgendar.addEventListener("click", () => {
  if (!nomePaciente.value.trim()) {
      mostrarMensagem("Por favor, preencha seu nome.", "erro");
      return;
  }

  const dataFormatada = dataSelecionada.toLocaleDateString("pt-BR");
  const resumo = `
      <h3>✓ Agendamento Confirmado!</h3>
      <p><strong>Paciente:</strong> ${nomePaciente.value}</p>
      <p><strong>Telefone:</strong> ${telefonePaciente.value || "Não informado"}</p>
      <p><strong>Email:</strong> ${emailPaciente.value || "Não informado"}</p>
      <p><strong>Médico:</strong> ${medicoSelecionado.nome}</p>
      <p><strong>Data:</strong> ${dataFormatada}</p>
      <p><strong>Horário:</strong> ${horarioSelecionado}</p>
  `;
  resumoAgendamento.innerHTML = resumo;
  resumoAgendamento.className = "resumo-agendamento";

  mostrarMensagem(`Consulta agendada com sucesso para ${nomePaciente.value}!`, "sucesso");

  // Limpar formulário após 2 segundos
  setTimeout(() => {
      limparFormulario();
  }, 2000);
});

// === BOTÃO CANCELAR ===
btnCancelar.addEventListener("click", limparFormulario);

function limparFormulario() {
  selectEspecialidade.value = "";
  selectMedico.innerHTML = '<option value="">Selecione o médico</option>';
  nomePaciente.value = "";
  telefonePaciente.value = "";
  emailPaciente.value = "";
  resumoAgendamento.innerHTML = "";
  mensagem.innerHTML = "";

  etapaMedico.style.display = "none";
  etapaCalendario.classList.remove("ativo");
  etapaPaciente.classList.remove("ativo");
  etapaHorarios.classList.remove("ativo");

  medicoSelecionado = null;
  dataSelecionada = null;
  horarioSelecionado = null;
  atualizarBotaoAgendar();
}

function mostrarMensagem(texto, tipo) {
  mensagem.textContent = texto;
  mensagem.className = "mensagem " + tipo;
  setTimeout(() => {
      mensagem.className = "mensagem";
  }, 3000);
}