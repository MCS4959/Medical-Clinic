// script.js
document.getElementById('loginForm').addEventListener('submit', function(e) {
  e.preventDefault();

  const userType = document.getElementById('userType').value;
  const username = document.getElementById('username').value;
  const password = document.getElementById('password').value;
  const message = document.getElementById('message');

  // Simulação de autenticação (substituir com backend real)
  if (username === "admin" && password === "admin123" && userType === "admin") {
    message.style.color = "Black";
    message.textContent = "Bem-vindo, Administrador!";
    // redirecionar para painel admin
  } else if (username === "medico" && password === "medico123" && userType === "medico") {
    message.style.color = "Black";
    message.textContent = "Bem-vindo, Médico!";
    // redirecionar para painel médico
  } else if (username === "paciente" && password === "paciente123" && userType === "paciente") {
    message.style.color = "Black";
    message.textContent = "Bem-vindo, Paciente!";
    // redirecionar para painel paciente
    } else if (username === "atendente" && password === "atendente123" && userType === "atendente") {
    message.style.color = "Black";
    message.textContent = "Bem-vindo, Atendente!";
    // redirecionar para painel atendente
  } else {
    message.style.color = "red";
    message.textContent = "Credenciais inválidas ou tipo de usuário incorreto.";
  }
  // Seleciona os elementos
const loginForm = document.getElementById('login');
const cadastroForm = document.getElementById('cadastro');
const mostrarCadastro = document.getElementById('mostrarCadastro');
const mostrarLogin = document.getElementById('mostrarLogin');

// Alterna entre login e cadastro
mostrarCadastro.addEventListener('click', (e) => {
    e.preventDefault();
    loginForm.classList.add('hidden');
    cadastroForm.classList.remove('hidden');
});

mostrarLogin.addEventListener('click', (e) => {
    e.preventDefault();
    cadastroForm.classList.add('hidden');
    loginForm.classList.remove('hidden');
});

// Simulação de envio de formulários
document.getElementById('loginForm').addEventListener('submit', (e) => {
    e.preventDefault();
    alert('Login efetuado com sucesso!');
});

document.getElementById('cadastroForm').addEventListener('submit', (e) => {
    e.preventDefault();
    alert('Cadastro realizado com sucesso!');
});

});