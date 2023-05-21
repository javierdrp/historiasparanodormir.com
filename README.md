# historiasparanodormir.com

## Instrucciones
- Descargar el proyecto y abrir la carpeta api con IntelliJ
- Ejecutar la ApiApplication
- Navegar a http://localhost:8080/index.html

## Perfil
Se pueden crear un nuevo perfil o iniciar sesión en uno ya existente. Al iniciar sesión, se accede al portal de usuario que permite cambiar la foto, el nombre y la contraseña. Para ello, al hacer click en el dato en cuestión se despliega el formulario correspondiente.

Los perfiles ya creados por defecto son: 
- admin@historiasparanodormir.com (contraseña admin)
- jose@mail.com (contraseña jose)

Si se inicia sesión con el perfil de administrador, aparece la opción de crear un nuevo artículo para el blog.

## Artículos
El enlace "Artículos" de la barra superior muestra el artículo más reciente. Para acceder a un artículo en concreto se pueden utilizar los enlaces de "Últimos artículos" (derecha).

Para mostrar un artículo, se hace una llamada a la API y se utiliza la respuesta para actualizar el HTML de la página.

## Comentarios
Si se ha iniciado sesión, al final de la página del artículo aparece un formulario para escribir un comentario. Los comentarios van asociados a un usuario de forma que cada uno tiene la opción de eliminar sus propios comentarios. El administrador es el único que puede eliminar los comentarios de cualquier usuario.