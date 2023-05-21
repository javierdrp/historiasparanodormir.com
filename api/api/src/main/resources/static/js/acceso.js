const btnEntrar = document.getElementById("login-button");

btnEntrar.onclick = async function () {
    const fldEmail = document.getElementById("login-email");
    const fldPass = document.getElementById("login-pass");
    if (await iniciarSesion(fldEmail.value, fldPass.value)) {
        window.location.href = "/perfil/index.html";
    }
    else {
        fldPass.value = "";
        const divError = document.getElementById("login-error");
        divError.textContent = "Email o contraseña incorrectos. Inténtelo de nuevo.";
    }
}