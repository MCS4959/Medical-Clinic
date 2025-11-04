// Apenas um exemplo básico de interação (pode ser expandido)
document.querySelectorAll(".botao").forEach(botao => {
  botao.addEventListener("click", () => {
    alert(`Você clicou em: ${botao.innerText}`);
  });
});
