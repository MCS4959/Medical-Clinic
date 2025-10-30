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

  const username = document.getElementById('username').value;
  const password = document.getElementById('password').value;
  const userType = document.getElementById('userType').value;

  let redirectPage = "";

  // Validação simulada e definição da página de redirecionamento
  if (username === "admin" && password === "1234" && userType === "admin") {
    message.style.color = "black";
    message.textContent = "Bem-vindo, Administrador!";
    redirectPage = "admin.html";
  } else if (username === "medico" && password === "1234" && userType === "medico") {
    message.style.color = "black";
    message.textContent = "Bem-vindo, Médico!";
    redirectPage = "medico.html";
  } else if (username === "paciente" && password === "1234" && userType === "paciente") {
    message.style.color = "black";
    message.textContent = "Bem-vindo, Paciente!";
    redirectPage = "paciente.html";
  } else if (username === "atendente" && password === "1234" && userType === "atendente") {
    message.style.color = "black";
    message.textContent = "Bem-vindo, Atendente!";
    redirectPage = "atendente.html";
  } else {
    message.style.color = "red";
    message.textContent = "Credenciais inválidas ou tipo de usuário incorreto.";
  }

  // Redireciona após 1 segundo, se as credenciais forem válidas
  if (redirectPage !== "") {
    setTimeout(() => {
      window.location.href = redirectPage;
    }, 1000);
  }
});

// Simulação de envio do cadastro
cadastroForm.addEventListener('submit', e => {
  e.preventDefault();
  alert('Cadastro concluído!');
});
