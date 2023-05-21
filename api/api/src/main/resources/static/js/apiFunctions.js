let usuario = {
    email: sessionStorage.getItem("usuario-email") || "",
    contrasena: sessionStorage.getItem("usuario-contrasena") || "",
    nombre: "",
    foto: "",
    admin: false
}

function credenciales(email = usuario.email, pass = usuario.contrasena)
{
    return 'Basic ' + btoa(email + ':' + pass);
}

function formatearFecha(raw)
{
    return `${raw.substring(6,8)}-${raw.substring(4,6)}-${raw.substring(0,4)}`;
}

function formatearFechaHora(raw)
{
    return `${raw.substring(6,8)}-${raw.substring(4,6)}-${raw.substring(0,4)} ${raw.substring(8,10)}:${raw.substring(10,12)}`;
}

async function cambiarPerfil(nuevoNombre = "", nuevaFoto = "", nuevaContrasena = "") {
    try {
        const objeto = await peticion('/api/usuario/cambio', {
            method: 'PUT',
            mode: 'cors',
            headers: { 
                'Content-type': 'application/json',
                'Authorization': credenciales()
            },
            body: JSON.stringify({
                nuevoNombre: nuevoNombre,
                nuevaFoto: nuevaFoto,
                nuevaContrasena: nuevaContrasena
            })
        });
        console.log('Respuesta:', objeto);
        if (objeto.error) {
            return false;
        }
    } catch (error) {
        console.error('Error:', error);
        return false;
    }
    return true;
}

async function actualizarUsuario() {
    if(!usuario.email || !usuario.contrasena) return false;
    let objeto = null;
    console.log("aaa");
    try {
        objeto = await peticion('/api/usuario', {
            method: 'GET',
            mode: 'cors',
            headers: { 
                'Content-type': 'application/json',
                'Authorization': credenciales()
            },
        });
        console.log('Respuesta:', objeto);
        if (objeto.error) {
            return false;
        }
    } catch (error) {
        console.error('Error:', error);
        return false;
    }

    usuario.email = objeto.email;
    usuario.contrasena = objeto.contrasena;
    usuario.foto = objeto.foto;
    usuario.nombre = objeto.nombre;
    usuario.admin = (objeto.admin == "1");
    sessionStorage.setItem("usuario-email", usuario.email);
    sessionStorage.setItem("usuario-contrasena", usuario.contrasena);
    return true;
}

const peticion = async function (url, config) {
    const respuesta = await fetch(url, config);
    return await respuesta.json();
}

async function iniciarSesion(email, pass) {
    if (!email || !pass) return false;

    let objeto;
    try {
        objeto = await peticion('/api/usuario/acceso', {
            method: 'GET',
            mode: 'cors',
            headers: { 
                'Content-type': 'application/json',
                'Authorization': credenciales(email, pass)
            }
        });
        console.log('Respuesta:', objeto);
        if (objeto.error) {
            return false;
        }
    } catch (error) {
        console.error('Error:', error);
        return false;
    }

    usuario.email = objeto.email;
    usuario.contrasena = objeto.contrasena;
    usuario.foto = objeto.foto;
    usuario.nombre = objeto.nombre;
    usuario.admin = (objeto.admin == "1");
    sessionStorage.setItem("usuario-email", usuario.email);
    sessionStorage.setItem("usuario-contrasena", usuario.contrasena);
    return true;
}

async function buscarArticulo(fecha) {
    if (!fecha) return null;

    let objeto;
    try {
        objeto = await peticion(`/api/articulo/${fecha}`, {
            method: 'GET',
            mode: 'cors',
            headers: { 'Content-type': 'application/json' }
        });
        console.log('Respuesta:', objeto);
        if (objeto.error) {
            return null;
        }
    } catch (error) {
        console.error('Error:', error);
        return null;
    }

    return objeto;
}

async function buscarUltimoArticulo() {
    let objeto;
    try {
        objeto = await peticion(`/api/articulo/ultimo`, {
            method: 'GET',
            mode: 'cors',
            headers: { 'Content-type': 'application/json' }
        });
        console.log('Respuesta:', objeto);
        if (objeto.error) {
            return null;
        }
    } catch (error) {
        console.error('Error:', error);
        return null;
    }

    return objeto;
}

async function crearCuenta(email, pass) {
    if (!email || !pass) return false;

    let objeto;
    try {
        objeto = await peticion('/api/usuario/nuevo', {
            method: 'POST',
            mode: 'cors',
            headers: { 'Content-type': 'application/json' },
            body: JSON.stringify({
                email: email,
                contrasena: pass,
                foto: 'https://cdn-icons-png.flaticon.com/512/149/149071.png'
            })
        });
        console.log('Respuesta:', objeto);
        if (objeto.error) {
            return false;
        }
    } catch (error) {
        console.error('Error:', error);
        return false;
    }

    usuario.email = objeto.email;
    usuario.contrasena = objeto.contrasena;
    usuario.foto = objeto.foto;
    usuario.nombre = objeto.nombre;
    usuario.admin = (objeto.admin == "1");
    sessionStorage.setItem("usuario-email", usuario.email);
    sessionStorage.setItem("usuario-contrasena", usuario.contrasena);
    return true;
}

async function crearArticulo(title, content) {
    try {
        const objeto = await peticion('/api/articulo/nuevo', {
            method: 'POST',
            mode: 'cors',
            headers: { 
                'Content-type': 'application/json',
                'Authorization': credenciales()
            },
            body: JSON.stringify({
                titulo: title,
                contenido: content
            })
        });
        console.log('Respuesta:', objeto);
        if (objeto.error) {
            return false;
        }
    } catch (error) {
        console.error('Error:', error);
        return false;
    }

    return true;
}

async function getLatestArticles()
{
    let objeto;
    try {
        objeto = await peticion('/api/articulo/lista', {
            method: 'GET',
            mode: 'cors',
            headers: { 'Content-type': 'application/json' }
        });
        console.log('Respuesta:', objeto);
        if (objeto.error) {
            return null;
        }
    } catch (error) {
        console.error('Error:', error);
        return null;
    }

    return objeto;
}

async function crearComentario(fechaArticulo, contenido) {
    try {
        console.log(usuario.email + usuario.contrasena);
        const objeto = await peticion(`/api/articulo/${fechaArticulo}/comentario`, {
            method: 'POST',
            mode: 'cors',
            headers: { 
                'Content-type': 'application/json',
                'Authorization': credenciales()
            },
            body: JSON.stringify({
                contenido: contenido
            })
        });
        console.log('Respuesta:', objeto);
        if (objeto.error) {
            return false;
        }
    } catch (error) {
        console.error('Error:', error);
        return false;
    }

    return true;
}

async function buscarComentariosArticulo(fechaArticulo)
{
    let objeto;
    try {
        objeto = await peticion(`/api/articulo/${fechaArticulo}/comentario`, {
            method: 'GET',
            mode: 'cors',
            headers: { 'Content-type': 'application/json' }
        });
        console.log('Respuesta:', objeto);
        if (objeto.error) {
            return null;
        }
    } catch (error) {
        console.error('Error:', error);
        return null;
    }

    return objeto;
}

async function borraComentario(fechaArticulo, fechaComentario)
{
    let objeto;
    try {
        objeto = await peticion(`/api/articulo/${fechaArticulo}/comentario/${fechaComentario}`, {
            method: 'DELETE',
            mode: 'cors',
            headers: { 
                'Content-type': 'application/json',
                'Authorization': credenciales()
            }
        });
        console.log('Respuesta:', objeto);
        if (objeto.error) {
            return false;
        }
    } catch (error) {
        console.error('Error:', error);
        return false;
    }

    return true;
}