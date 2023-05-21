package com.historiasparanodormir.api.servicio;

import com.historiasparanodormir.api.entidad.Articulo;
import com.historiasparanodormir.api.repositorio.RepoArticulo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface ServicioArticulos
{
    Articulo crea(String titulo, String contenido);
    Articulo busca(String fecha);
    List<Articulo> ultimosArticulos();
}
