package com.historiasparanodormir.api.servicio;

import com.historiasparanodormir.api.entidad.Articulo;
import com.historiasparanodormir.api.entidad.Comentario;
import com.historiasparanodormir.api.entidad.Usuario;
import com.historiasparanodormir.api.repositorio.RepoComentario;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ServicioComentariosImpl implements ServicioComentarios
{
    @Autowired
    RepoComentario repoComentario;

    @Override
    public Comentario crea(Usuario usuario, Articulo articulo, String contenido)
    {
        Comentario comentario = new Comentario();
        comentario.usuario = usuario;
        comentario.articulo = articulo;
        comentario.fecha = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        comentario.contenido = contenido;

        repoComentario.save(comentario);
        return comentario;
    }

    @Override
    public List<Comentario> busca(Articulo articulo)
    {
        return repoComentario.findByArticulo(articulo);
    }

    @Override
    @Transactional
    public Long borra(Usuario usuario, Articulo articulo, String fecha)
    {
        return repoComentario.deleteByUsuarioAndArticuloAndFecha(usuario, articulo, fecha);
    }


}
