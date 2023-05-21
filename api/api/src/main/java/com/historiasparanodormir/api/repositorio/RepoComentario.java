package com.historiasparanodormir.api.repositorio;

import com.historiasparanodormir.api.entidad.Articulo;
import com.historiasparanodormir.api.entidad.Comentario;
import com.historiasparanodormir.api.entidad.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RepoComentario extends CrudRepository<Comentario, Long>
{
    List<Comentario> findByArticulo(Articulo articulo);
    Long deleteByUsuarioAndArticuloAndFecha(Usuario usuario, Articulo articulo, String fecha);
}
