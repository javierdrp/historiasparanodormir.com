package com.historiasparanodormir.api.repositorio;

import com.historiasparanodormir.api.entidad.Articulo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RepoArticulo extends CrudRepository<Articulo, Long>
{
    Articulo findByTitulo(String titulo);
    List<Articulo> findAll();
    Articulo findByFecha(String fecha);
}
