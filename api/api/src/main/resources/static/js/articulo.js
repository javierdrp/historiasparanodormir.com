const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);

let fechaArticulo = urlParams.get("fecha");

async function actualizarArticulo(fecha)
{
    let articulo = await buscarUltimoArticulo();

    if(fecha)
    {
        articulo2 = await buscarArticulo(fecha);
        if(articulo2) articulo = articulo2;
    }

    const divArticulo = document.getElementById("articulo");
    divArticulo.innerHTML = "";
    const hdrTitulo = document.createElement("h2");
    hdrTitulo.textContent = articulo.titulo;
    divArticulo.appendChild(hdrTitulo);
    const divFecha = document.createElement("div");
    divFecha.id = "article-date";
    divFecha.textContent = formatearFecha(articulo.fecha);
    fechaArticulo = articulo.fecha;
    divArticulo.appendChild(divFecha);

    let parrafos = articulo.contenido.split('\\n');
    for(let i = 0; i < parrafos.length; i++)
    {
        if(!parrafos[i]) continue;
        const parContenido = document.createElement("p");
        parContenido.textContent = parrafos[i];
        divArticulo.appendChild(parContenido);
    } 
    actualizarComentarios();
    actualizarUsuario().then(() => visibilidadMiComentario());
    
}


actualizarArticulo(fechaArticulo);

async function enviarComentario()
{
    const errComentario = document.getElementById("my-comment-error");
    const fldContenido = document.getElementById("my-comment-area");
    const contenido = fldContenido.value;
    if(!contenido) 
    {
        errComentario.textContent = "El comentario no puede estar vacío.";
        return;
    }

    crearComentario(fechaArticulo, contenido)
    .then((respuesta) => {
        if(respuesta)
        {
            errComentario.textContent = "";
            fldContenido.value = null;
            actualizarComentarios();
        }
        else errComentario.textContent = "No se ha podido enviar el comentario.";
    });
}

async function actualizarComentarios()
{
    const divComentarios = document.getElementById("comments");
    console.log(divComentarios);
    divComentarios.innerHTML = "";

    const comentarios = await buscarComentariosArticulo(fechaArticulo);
    console.log(comentarios.length);
    for(let i = 0; i < comentarios.length; i++)
    {
        const divComentario = document.createElement("div");
        divComentario.className = "comment";
        const imgUsuario = document.createElement("img");
        imgUsuario.src = comentarios[i].foto;
        imgUsuario.className = "comment-photo";
        divComentario.appendChild(imgUsuario);
        const divContenido = document.createElement("div");
        const bldUsuario = document.createElement("b");
        bldUsuario.textContent = comentarios[i].usuario;
        bldUsuario.className = "comment-username";
        divContenido.appendChild(bldUsuario);
        if(usuario.admin || comentarios[i].email == usuario.email)
        {
            const spnEliminar = document.createElement("span");
            spnEliminar.className = "select-cursor";
            spnEliminar.style.fontSize = ".75rem";
            spnEliminar.textContent = "     Eliminar comentario";
            spnEliminar.onclick = () => 
            {
                borraComentario(fechaArticulo, comentarios[i].fecha).then(() =>
                {
                    actualizarComentarios();
                });
            }
            divContenido.appendChild(spnEliminar);
        }
        const divFecha = document.createElement("div");
        divFecha.className = "comment-date";
        divFecha.textContent = formatearFechaHora(comentarios[i].fecha);
        divContenido.appendChild(divFecha);
        const parContenido = document.createElement("p");
        parContenido.className = "comment-content";
        parContenido.textContent = comentarios[i].contenido;
        divContenido.appendChild(parContenido);
        divComentario.appendChild(divContenido);
        divComentarios.appendChild(divComentario);
    }
}

async function visibilidadMiComentario()
{
    const frmMiComentario = document.getElementById("my-comment-form");
    if(usuario.email && usuario.contrasena)
    {
        frmMiComentario.style.display = "block";
        const imgMiComentario = document.getElementById("my-comment-photo");
        imgMiComentario.src = usuario.foto;
    }
    else
    {
        frmMiComentario.style.display = "none";
    }
}