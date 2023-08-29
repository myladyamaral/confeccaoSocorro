/**
 * 
 */
$(document).ready(function() {
	$('.datepicker').datepicker({
		format: 'dd/mm/yyyy', // Formato da data exibida
		autoclose: true
	});
});

function showHideMensage(document) {
	var alertElement = document.getElementById('alert');

	// Define um tempo (em milissegundos) para o alerta desaparecer
	var timeout = 5000; // 5 segundos

	// Função para remover o alerta após o tempo definido
	function hideAlert() {
		alertElement.classList.add('fade'); // Adiciona classe para suavizar o efeito de desaparecimento
		setTimeout(function() {
			alertElement.remove(); // Remove o elemento do DOM
		}, 1500); // Tempo de suavização (ms)
	}

	// Inicia o timer para o desaparecimento do alerta
	setTimeout(hideAlert, timeout);
}



