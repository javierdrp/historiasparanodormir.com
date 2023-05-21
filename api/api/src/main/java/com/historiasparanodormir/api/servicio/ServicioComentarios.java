package com.historiasparanodormir.api.servicio;

import com.historiasparanodormir.api.entidad.Articulo;
import com.historiasparanodormir.api.entidad.Comentario;
import com.historiasparanodormir.api.entidad.Usuario;

import java.util.List;

public interface ServicioComentarios
{
    Comentario crea(Usuario usuario, Articulo articulo, String contenido);
    List<Comentario> busca(Articulo articulo);
    Long borra(Usuario usuario, Articulo articulo, String fecha);
}
