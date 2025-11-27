const modal = document.getElementById("modal");
const abrirModal = document.getElementById("abrirModal");
const fecharModal = document.getElementById("fecharModal");
const formFuncionario = document.getElementById("formFuncionario");
const tabela = document.querySelector("#tabelaFuncionarios tbody");
const pesquisaInput = document.getElementById("pesquisa");
const filtroCargo = document.getElementById("filtroCargo");
const paginacao = document.getElementById("paginacao");

let funcionarios = JSON.parse(localStorage.getItem("funcionarios")) || [];
let paginaAtual = 1;
const itensPorPagina = 5;

// Abrir modal
document.getElementById("abrirModal").addEventListener("click", () => {
    let modal = document.getElementById("modal");
    modal.style.display = "flex"; // 🔥 Agora sim centraliza
  
  

  // Máscara automática para CPF
document.getElementById("cpf").addEventListener("input", function (e) {
    let valor = e.target.value.replace(/\D/g, "");
    if (valor.length > 11) valor = valor.slice(0, 11);
    valor = valor.replace(/(\d{3})(\d)/, "$1.$2");
    valor = valor.replace(/(\d{3})(\d)/, "$1.$2");
    valor = valor.replace(/(\d{3})(\d{1,2})$/, "$1-$2");
    e.target.value = valor;
  });
// Máscara automática para data (DD/MM/AAAA)
document.getElementById("nascimento").addEventListener("input", function (e) {
    let valor = e.target.value.replace(/\D/g, "");
    if (valor.length > 8) valor = valor.slice(0, 8);
    valor = valor.replace(/(\d{2})(\d)/, "$1/$2");
    valor = valor.replace(/(\d{2})(\d)/, "$1/$2");
    e.target.value = valor;
  });
    
});

// Fechar modal
document.getElementById("fecharModal").addEventListener("click", () => {
    modal.style.display = "none";
});

// Salvar funcionário
formFuncionario.addEventListener("submit", (e) => {
  e.preventDefault();

  const funcionario = {
    nome: document.getElementById("nome").value,
    id: document.getElementById("id").value,
    cpf: document.getElementById("cpf").value,
    nasc: document.getElementById("nascimento").value,
    cargo: document.getElementById("cargo").value,
  };

  funcionarios.push(funcionario);
  salvar();
  atualizarTabela();
  modal.style.display = "none";
  formFuncionario.reset();
});

// Salvar no LocalStorage
function salvar() {
  localStorage.setItem("funcionarios", JSON.stringify(funcionarios));
}

// Atualiza tabela
function atualizarTabela() {
  tabela.innerHTML = "";

  const listaFiltrada = filtrarFuncionarios();
  const start = (paginaAtual - 1) * itensPorPagina;
  const end = start + itensPorPagina;
  const pagina = listaFiltrada.slice(start, end);

  pagina.forEach((func, index) => {
    const tr = document.createElement("tr");
    tr.innerHTML = `
      <td>${func.nome}</td>
      <td>${func.id}</td>
      <td>${func.cpf}</td>
      <td>${func.nasc}</td>
      <td>${func.cargo}</td>
      <td>
        <button class="btn-editar" onclick="editar(${start + index})">Editar</button>
        <button class="btn-excluir" onclick="excluir(${start + index})">Excluir</button>
      </td>
    `;
    tabela.appendChild(tr);
  });

  gerarPaginacao(listaFiltrada.length);
}

// Excluir funcionário
function excluir(index) {
  if (confirm("Deseja excluir este funcionário?")) {
    funcionarios.splice(index, 1);
    salvar();
    atualizarTabela();
  }
}

// Editar funcionário
function editar(index) {
  const func = funcionarios[index];
  
  document.getElementById("nome").value = func.nome;
  document.getElementById("id").value = func.id;
  document.getElementById("cpf").value = func.cpf;
  document.getElementById("nascimento").value = func.nasc;
  document.getElementById("cargo").value = func.cargo;

  modal.style.display = "block";

  formFuncionario.onsubmit = function (e) {
    e.preventDefault();
    funcionarios[index] = {
      nome: document.getElementById("nome").value,
      id: document.getElementById("id").value,
      cpf: document.getElementById("cpf").value,
      nasc: document.getElementById("nascimento").value,
      cargo: document.getElementById("cargo").value,
    };
    salvar();
    atualizarTabela();
    modal.style.display = "none";
    formFuncionario.reset();
    
    // volta para modo adicionar
    formFuncionario.onsubmit = null;
  };
}

// Filtro e pesquisa
function filtrarFuncionarios() {
  const termo = pesquisaInput.value.toLowerCase();
  const cargo = filtroCargo.value;

  return funcionarios.filter((f) => {
    const matchPesquisa =
      f.nome.toLowerCase().includes(termo) ||
      f.cpf.includes(termo) ||
      f.id.includes(termo);

    const matchCargo = cargo === "" || f.cargo === cargo;
    return matchPesquisa && matchCargo;
  });
}

// Paginação
function gerarPaginacao(total) {
  paginacao.innerHTML = "";
  const totalPaginas = Math.ceil(total / itensPorPagina);

  for (let i = 1; i <= totalPaginas; i++) {
    const btn = document.createElement("button");
    btn.textContent = i;
    btn.classList.toggle("ativo", i === paginaAtual);
    btn.addEventListener("click", () => {
      paginaAtual = i;
      atualizarTabela();
    });
    paginacao.appendChild(btn);
  }
}

// Eventos de busca e filtro
pesquisaInput.addEventListener("input", atualizarTabela);
filtroCargo.addEventListener("change", atualizarTabela);

// Inicialização
atualizarTabela();

// Botão cancelar modal
document.getElementById("cancelarModal").addEventListener("click", () => {
    modal.style.display = "none";
  });
  
  // Fechar ao clicar fora do modal
  window.addEventListener("click", (e) => {
    if (e.target === modal) {
      modal.style.display = "none";
    }
  });
  
  // Alternar entre tema claro / escuro
document.getElementById("toggleDark").addEventListener("click", () => {
    document.body.classList.toggle("dark-mode");
  });

  document.getElementById("exportExcel").addEventListener("click", () => {
    let tabela = document.getElementById("tabelaFuncionarios");
    let html = tabela.outerHTML;
  
    let link = document.createElement("a");
    link.href = 'data:application/vnd.ms-excel,' + encodeURIComponent(html);
    link.download = 'funcionarios.xls';
    link.click();
  });

  document.getElementById("exportPDF").addEventListener("click", () => {
    const win = window.open("", "_blank");
    win.document.write("<html><head><title>Funcionários</title></head><body>");
    win.document.write(document.getElementById("tabelaFuncionarios").outerHTML);
    win.document.write("</body></html>");
    win.document.close();
    win.print();
  });
  
  