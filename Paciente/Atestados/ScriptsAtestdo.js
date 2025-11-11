// Pega os elementos do DOM
const tipoSelect = document.getElementById('tipo');
const formMedico = document.getElementById('formMedico');
const formAtendente = document.getElementById('formAtendente');

// Quando o usuário muda a seleção
tipoSelect.addEventListener('change', function() {
  const tipo = this.value;

  // Esconde todos os formulários
  formMedico.style.display = 'none';
  formAtendente.style.display = 'none';

  // Mostra o formulário correspondente
  if (tipo === 'medico') {
    formMedico.style.display = 'block';
  } else if (tipo === 'atendente') {
    formAtendente.style.display = 'block';
  }
});
