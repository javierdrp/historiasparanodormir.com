package com.historiasparanodormir.api.servicio;

import com.historiasparanodormir.api.entidad.Usuario;

public interface ServicioUsuarios
{
    Usuario crea(String email, String contrasena, String foto);
    Usuario autentica(String email, String contrasena);
    Usuario cambiaNombre(Usuario usuario, String nombre);
    Usuario cambiaFoto(Usuario usuario, String foto);
    Usuario cambiaContrasena(Usuario usuario, String contrasena);
}
