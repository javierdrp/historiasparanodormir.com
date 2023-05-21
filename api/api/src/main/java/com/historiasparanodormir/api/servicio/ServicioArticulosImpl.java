package com.historiasparanodormir.api.servicio;

import com.historiasparanodormir.api.entidad.Articulo;
import com.historiasparanodormir.api.repositorio.RepoArticulo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ServicioArticulosImpl implements ServicioArticulos
{
    @Autowired RepoArticulo repoArticulo;

    @Override
    public Articulo crea(String titulo, String contenido)
    {
        Articulo articulo = new Articulo();
        articulo.titulo = titulo;
        articulo.contenido = contenido;
        articulo.fecha = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        return repoArticulo.save(articulo);
    }

    @Override
    public Articulo busca(String fecha)
    {
        return repoArticulo.findByFecha(fecha);
    }

    @Override
    public List<Articulo> ultimosArticulos()
    {
        List<Articulo> articulos = repoArticulo.findAll();
        articulos.sort(Collections.reverseOrder());
        return articulos;
    }
}
