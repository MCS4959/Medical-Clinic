 // Redireciona ao clicar nos botões
    document.getElementById("btnMinhasConsultas").onclick = function() {
      window.location.href = "../Medico/MinhasConsultas/Consultas.html";
    };

    document.getElementById("btnRelatorio").onclick = function() {
      window.location.href = "../Medico/MinhasConsultas/Consultas.html";
    };
    
    document.querySelector('.classicone-usuario img').addEventListener('click', function(event) {
      event.preventDefault();
      const menu = document.getElementById('usuarioMenu');
      if (menu.style.display === 'flex') {
        menu.style.display = 'none';
      } else {
        menu.style.display = 'flex';
      }
    });
    