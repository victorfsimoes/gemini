function checar_caps_lock(ev, id) {
	var e = ev || window.event;
	codigo_tecla = e.keyCode ? e.keyCode : e.which;
	tecla_shift = e.shiftKey ? e.shiftKey : ((codigo_tecla == 16) ? true
			: false);
	if (((codigo_tecla >= 65 && codigo_tecla <= 90) && !tecla_shift)
			|| ((codigo_tecla >= 97 && codigo_tecla <= 122) && tecla_shift)) {
		document.getElementById(id).style.visibility = 'visible';
	} else {
		document.getElementById(id).style.visibility = 'hidden';
	}
}

function habilitar(id) {
	if (document.getElementById(id).style.visibility == 'hidden')
		document.getElementById(id).style.visibility = 'visible';
	else
		document.getElementById(id).style.visibility = 'hidden';
}

function redirecionar(url) {
	window.location.href = url;
}

function redirecionarComponente(id) {
	window.location.hash = id;
}

function offsetLeft(elemento) {
	x = elemento.offsetLeft;
	for (e = elemento.offsetParent; e; e = e.offsetParent)
		x += e.offsetLeft;
	return x;
}

function offsetTop(elemento) {
	y = elemento.offsetTop;
	for (e = elemento.offsetParent; e; e = e.offsetParent)
		y += e.offsetTop;
	return y;
}

function isNumero(e) {
	var tecla = (window.event) ? event.keyCode : e.which;

	if ((tecla > 47 && tecla < 58) || tecla == 46) {
		return true;
	} else if ((tecla != 0) && (tecla != 8))
		return false;
	else
		return true;
}

function irParaTopoDaTela() {

	var posicaoY = window.scrollY;

	while (posicaoY > 0) {

		posicaoY -= 10;

		window.scroll(0, posicaoY);
	}
}

function alerta(msg) {
	window.alert(msg);
}

function piscarTexto(id) {

	window.setInterval(function() {

		document.getElementById(id).style.visibility = 'hidden'

		window.setTimeout(function() {
			document.getElementById(id).style.visibility = 'visible'
		}, 150);
	}, 1 * 1000);
}

function keyPressEnter(event) {
	if (event.keyCode == '13')
		return false;
}