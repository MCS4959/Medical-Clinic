// ------------ CARREGAR DADOS DO localStorage AO ABRIR A PÁGINA -------------
let funcionarios = JSON.parse(localStorage.getItem("funcionarios")) || [];
atualizarTabela();

// ------------ ABRIR / FECHAR MODAL -------------
document.getElementById("abrirModal").onclick = () => abrirModal();
document.getElementById("fecharModal").onclick = () => fecharModal();
document.getElementById("btnCancelar").onclick = () => fecharModal();

function abrirModal() {
    document.getElementById("modal").style.display = "flex";
}

function fecharModal() {
    document.getElementById("modal").style.display = "none";
    document.getElementById("formFuncionario").reset();
    editIndex = null; // limpa modo edição
}

// ----------- SALVAR FUNCIONÁRIO ------ (NOVO OU EDITADO) -------------
let editIndex = null;

document.getElementById("formFuncionario").addEventListener("submit", function (e) {
    e.preventDefault();

    let funcionario = {
        nome: document.getElementById("nome").value,
        id: document.getElementById("id").value,
        cpf: document.getElementById("cpf").value,
        nasc: document.getElementById("nascimento").value,
        cargo: document.getElementById("cargo").value
    };

    if (editIndex === null) {
        funcionarios.push(funcionario); // adiciona novo
    } else {
        funcionarios[editIndex] = funcionario; // salva edição
    }

    salvarLocal();
    atualizarTabela();
    fecharModal();
});

// --------------- ATUALIZA TABELA NA TELA ---------------
function atualizarTabela() {
    let tbody = document.querySelector("#tabelaFuncionarios tbody");
    tbody.innerHTML = "";

    funcionarios.forEach((f, i) => {
        let linha = `
            <tr>
                <td>${f.nome}</td>
                <td>${f.id}</td>
                <td>${f.cpf}</td>
                <td>${f.nasc}</td>
                <td>${f.cargo}</td>
                <td>
                    <button class="btn-editar" onclick="editar(${i})">Editar</button>
                    <button class="btn-excluir" onclick="excluir(${i})">Excluir</button>
                </td>
            </tr>
        `;
        tbody.innerHTML += linha;
    });
}

// --------------- EXCLUIR FUNCIONÁRIO ---------------
function excluir(index) {
    if (confirm("Tem certeza que deseja excluir este funcionário?")) {
        funcionarios.splice(index, 1);
        salvarLocal();
        atualizarTabela();
    }
}

// --------------- EDITAR FUNCIONÁRIO ----------------
function editar(index) {
    let f = funcionarios[index];

    document.getElementById("nome").value = f.nome;
    document.getElementById("id").value = f.id;
    document.getElementById("cpf").value = f.cpf;
    document.getElementById("nascimento").value = f.nasc;
    document.getElementById("cargo").value = f.cargo;

    editIndex = index;
    abrirModal();
}

// --------------- SALVAR NO localStorage ---------------
function salvarLocal() {
    localStorage.setItem("funcionarios", JSON.stringify(funcionarios));
}
// =================== BUSCA INTELIGENTE ===================
document.getElementById("pesquisa").addEventListener("input", function () {
    let termo = this.value.toLowerCase();

    let linhas = document.querySelectorAll("#tabelaFuncionarios tbody tr");

    linhas.forEach(tr => {
        let texto = tr.innerText.toLowerCase();
        tr.style.display = texto.includes(termo) ? "" : "none";
    });
});
// =================== PAGINAÇÃO ===================
let paginaAtual = 1;
let itensPorPagina = 10;

function atualizarTabela() {
    let tbody = document.querySelector("#tabelaFuncionarios tbody");
    tbody.innerHTML = "";

    // cálculo da página
    let inicio = (paginaAtual - 1) * itensPorPagina;
    let fim = inicio + itensPorPagina;

    let pagina = funcionarios.slice(inicio, fim);

    pagina.forEach((f, i) => {
        let linha = `
        <tr>
            <td>${f.nome}</td>
            <td>${f.id}</td>
            <td>${f.cpf}</td>
            <td>${f.nasc}</td>
            <td>${f.cargo}</td>
            <td>
                <button class="btn-editar" onclick="editar(${i + inicio})">Editar</button>
                <button class="btn-excluir" onclick="excluir(${i + inicio})">Excluir</button>
            </td>
        </tr>`;
        tbody.innerHTML += linha;
    });

    gerarPaginacao();
}

// Criar botões inferiores
function gerarPaginacao() {
    let totalPaginas = Math.ceil(funcionarios.length / itensPorPagina);

    let divPag = document.getElementById("paginacao");
    divPag.innerHTML = "";

    for (let i = 1; i <= totalPaginas; i++) {
        let botao = document.createElement("button");
        botao.innerText = i;
        botao.onclick = () => { paginaAtual = i; atualizarTabela(); }

        if (i === paginaAtual) botao.classList.add("ativo");
        divPag.appendChild(botao);
    }
}
document.getElementById("exportarPDF").addEventListener("click", function () {
    let win = window.open("", "_blank");
    win.document.write("<h1>Lista de Funcionários</h1><br>");

    funcionarios.forEach(f => {
        win.document.write(
            `<p><b>Nome:</b> ${f.nome} — <b>ID:</b> ${f.id} — <b>CPF:</b> ${f.cpf} — <b>Nasc:</b> ${f.nasc} — <b>Cargo:</b> ${f.cargo}</p>`
        );
    });

    win.print();
    win.close();
});
document.getElementById("exportarExcel").addEventListener("click", function () {

    let conteudo = "Nome\tID\tCPF\tNascimento\tCargo\n";

    funcionarios.forEach(f => {
        conteudo += `${f.nome}\t${f.id}\t${f.cpf}\t${f.nasc}\t${f.cargo}\n`;
    });

    let blob = new Blob([conteudo], { type: "application/vnd.ms-excel" });
    let link = document.createElement("a");
    link.href = URL.createObjectURL(blob);
    link.download = "funcionarios.xls";
    link.click();
});
