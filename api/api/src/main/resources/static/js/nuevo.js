const btnEntrar = document.getElementById("login-button");

btnEntrar.onclick = async function () {
    const fldEmail = document.getElementById("login-email");
    const fldPass = document.getElementById("login-pass");
    const fldPassConfirm = document.getElementById("login-passconfirm");
    const divError = document.getElementById("login-error");
    if (fldEmail.value && fldPass.value && fldPassConfirm.value) {
        if (fldPass.value == fldPassConfirm.value) {
            if (await crearCuenta(fldEmail.value, fldPass.value))
                window.location.href = "/perfil/index.html";
            else
            {
                divError.textContent = "Email inválido.";
            }
        }
        else
            divError.textContent = "La contraseña no coincide.";
    }
    else
        divError.textContent = "Por favor, rellena todos los campos.";
}