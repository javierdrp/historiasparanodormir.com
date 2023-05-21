async function fixFooter() {
    var ultimoElemento = document.getElementsByTagName('footer')[0];
    var windowHeight = window.innerHeight;
    var contenidoHeight = document.body.clientHeight;

    if (contenidoHeight > windowHeight || window.pageYOffset > 0) {
        ultimoElemento.classList = "";
    } else {
        ultimoElemento.classList = "fixed-footer";
    }
}

fixFooter();
window.setInterval(fixFooter, 100);