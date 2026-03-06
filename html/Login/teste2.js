// Seleção de elementos
const tabLogin = document.getElementById('tabLogin');
const tabCadastro = document.getElementById('tabCadastro');
const loginForm = document.getElementById('loginForm');
const cadastroForm = document.getElementById('cadastroForm');
const message = document.getElementById('message'); // div para mensagens

// Alterna entre abas
tabLogin.addEventListener('click', () => {
  tabLogin.classList.add('active');
  tabCadastro.classList.remove('active');
  loginForm.classList.add('active');
  cadastroForm.classList.remove('active');
});

tabCadastro.addEventListener('click', () => {
  tabCadastro.classList.add('active');
  tabLogin.classList.remove('active');
  cadastroForm.classList.add('active');
  loginForm.classList.remove('active');
});

// Evento de submit do login
loginForm.addEventListener('submit', e => {
  e.preventDefault();

  const username = document.getElementById('username').value.trim();
  const password = document.getElementById('password').value.trim();
  const userType = document.getElementById('userType').value;

  // Validação de campos obrigatórios
  if (!username || !password || !userType) {
    message.style.color = "red";
    message.textContent = "⚠️ Por favor, preencha todos os campos antes de continuar.";
    return; // Interrompe o login
  }

  let redirectPage = "";

  // Validação simulada e definição da página de redirecionamento
  if (username === "admin" && password === "1234" && userType === "admin") {
    message.style.color = "black";
    message.textContent = "Bem-vindo, Administrador!";
    redirectPage = "../Administrador/administrador.html";

  } else if (username === "medico" && password === "1234" && userType === "medico") {
    message.style.color = "black";
    message.textContent = "Bem-vindo, Médico!";
    redirectPage = "../Medico/medico.html";

  } else if (username === "paciente" && password === "1234" && userType === "paciente") {
    message.style.color = "black";
    message.textContent = "Bem-vindo, Paciente!";
    redirectPage = "../Paciente/Paciente.html";

  } else if (username === "atendente" && password === "1234" && userType === "atendente") {
    message.style.color = "black";
    message.textContent = "Bem-vindo, Atendente!";
    redirectPage = "../Atendente/atendente.html";

  } else {
    message.style.color = "red";
    message.textContent = "❌ Credenciais inválidas ou tipo de usuário incorreto.";
  }

  // Exibe mensagem de carregamento e redireciona
  if (redirectPage) {
    // Mostra mensagem de redirecionamento
    setTimeout(() => {
      message.style.color = "gray";
      message.textContent = "⏳ Redirecionando...";
    }, 600);

    // Redireciona após 1,8 segundos
    setTimeout(() => {
      window.location.href = redirectPage;
    }, 1800);
  }
});

// Simulação de envio do cadastro
cadastroForm.addEventListener('submit', e => {
  e.preventDefault();
  alert('Cadastro concluído!');
});
