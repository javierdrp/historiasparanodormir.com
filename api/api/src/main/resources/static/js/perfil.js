function isImgUrl(url) {
    const img = new Image();
    img.src = url;
    return new Promise((resolve) => {
        img.onload = () => resolve(true);
        img.onerror = () => resolve(false);
    });
}

const frmUsername = document.getElementById("change-username-form");
const frmPhoto = document.getElementById("change-photo-form");
const frmPassword = document.getElementById("change-pass-form");

const fldNewName = document.getElementById("field-username");
const fldNewPhoto = document.getElementById("field-photo");
const fldOldPass = document.getElementById("field-oldpass");
const fldNewPass = document.getElementById("field-newpass");
const fldConfirmPass = document.getElementById("field-confirmpass");

const errName = document.getElementById("change-username-error");
const errPhoto = document.getElementById("change-photo-error");
const errPass = document.getElementById("change-pass-error");
const errAdmin = document.getElementById("admin-error");

const imgProfilePhoto = document.getElementById("my-profile-photo");
const txtUsername = document.getElementById("my-profile-name");
const txtEmail = document.getElementById("my-profile-email");

const selAdmin = document.getElementById("select-admin");
const frmAdmin = document.getElementById("admin-form");

const fldTitle = document.getElementById("admin-title");
const fldContent = document.getElementById("admin-content");

selAdmin.style.display = "none";
frmAdmin.style.display = "none";
frmPassword.style.display = "none";
frmUsername.style.display = "none";
frmPhoto.style.display = "none";

async function actualizarPerfil() 
{
    actualizarUsuario().then(
        resultado =>
        {
            if(resultado) 
            {
                imgProfilePhoto.src = usuario.foto;
                txtUsername.textContent = usuario.nombre;
                txtEmail.textContent = usuario.email;

                if(usuario.admin) 
                    selAdmin.style.display = "block";

                fldNewName.value = null;
                fldNewPhoto.value = null;
                fldOldPass.value = null;
                fldNewPass.value = null;
                fldConfirmPass.value = null;
            }
            else
                window.location.href = "/perfil/acceso/index.html";
        }
    );
}

actualizarPerfil();

// cerrar sesión
function cerrarSesion() {
    usuario.email = "";
    usuario.contrasena = "";
    usuario.foto = "";
    usuario.nombre = "";
    usuario.admin = false;
    sessionStorage.clear();
    actualizarPerfil();
}

function formAdmin() {
    frmAdmin.style.display = "block";
    frmUsername.style.display = "none";
    frmPhoto.style.display = "none";
    frmPassword.style.display = "none";
}

// cambiar nombre de usuario
function formNombre() {
    frmUsername.style.display = "block";
    frmPhoto.style.display = "none";
    frmPassword.style.display = "none";
    frmAdmin.style.display = "none";

    fldNewName.value = null;
    errName.textContent = "";
}

function cambiarNombre() {
    const newName = fldNewName.value;

    if (!newName) {
        errName.textContent = "No puede estar vacío.";
        return;
    }

    if(newName.includes(" ") || newName.includes(":")) 
    {
        errName.textContent = "El nombre contiene caracteres no permitidos.";
        return;
    }

    errName.textContent = "";
    const txtProfileName = document.getElementById("my-profile-name");
    txtProfileName.textContent = newName;
    cambiarPerfil(newName).then(resultado => {
        if(resultado) actualizarPerfil();
    });
}

// cambiar foto de usuario
function formFoto() {
    frmUsername.style.display = "none";
    frmPhoto.style.display = "block";
    frmPassword.style.display = "none";
    frmAdmin.style.display = "none";

    fldNewPhoto.value = null;
    errPhoto.textContent = "";
}

async function cambiarFoto() {
    const newPhotoUrl = fldNewPhoto.value;

    if (!newPhotoUrl) {
        errPhoto.textContent = "No puede estar vacío.";
    }

    let isValid = await isImgUrl(newPhotoUrl);
    if (!isValid) {
        errPhoto.textContent = "Enlace inválido.";
        fldNewPhoto.value = null;
        return;
    }

    errPhoto.textContent = "";
    imgProfilePhoto.src = newPhotoUrl;
    cambiarPerfil("",newPhotoUrl).then(resultado => {
        if(resultado) actualizarPerfil();
    });
}

// cambiar contraseña
function formContrasena() {
    frmUsername.style.display = "none";
    frmPhoto.style.display = "none";
    frmPassword.style.display = "block";
    frmAdmin.style.display = "none";

    fldOldPass.value = null;
    fldNewPass.value = null;
    fldConfirmPass.value = null;
    errPass.textContent = "";
}

function cambiarContrasena() {
    const oldPass = fldOldPass.value;
    const newPass = fldNewPass.value;
    const confirmPass = fldConfirmPass.value;

    if (!newPass || !oldPass || !confirmPass) {
        errPass.textContent = "Todos los campos son obligatorios.";
        return;
    }

    if (usuario.contrasena != oldPass) {
        errPass.textContent = "Contraseña actual errónea";
        return;
    }

    if(newPass.includes(" ") || newPass.includes(":")) 
    {
        errName.textContent = "La nueva contraseña contiene caracteres no permitidos.";
        return;
    }

    if (newPass != confirmPass) {
        errPass.textContent = "La contraseña repetida no coincide.";
        return;
    }

    errPass.textContent = "";
    cambiarPerfil("","",newPass).then(resultado => {
        if(resultado) actualizarPerfil();
    });
}

// subir artículo
function subirArticulo()
{
    const title = fldTitle.value;
    let content = fldContent.value;

    if(!title || !content)
    {
        errAdmin.textContent = "Rellena todos los campos.";
        return;
    }

    content = content.replace(/\n/g, "\\n");

    if(crearArticulo(title, content))
    {
        errAdmin.textContent = "";
        fldTitle.value = null;
        fldContent.value = null;
    }
    else
        errAdmin.textContent = "Ha ocurrido un error.";
}