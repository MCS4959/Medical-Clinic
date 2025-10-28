const tabLogin = document.getElementById('tabLogin');
const tabCadastro = document.getElementById('tabCadastro');
const loginForm = document.getElementById('loginForm');
const cadastroForm = document.getElementById('cadastroForm');

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

// Simulação de envio
loginForm.addEventListener('submit', e => {
  e.preventDefault();
  alert('Login realizado com sucesso!');
});

cadastroForm.addEventListener('submit', e => {
  e.preventDefault();
  alert('Cadastro concluído!');
});
