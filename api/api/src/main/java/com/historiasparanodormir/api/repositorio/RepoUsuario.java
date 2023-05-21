package com.historiasparanodormir.api.repositorio;

import com.historiasparanodormir.api.entidad.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface RepoUsuario extends CrudRepository<Usuario, Long>
{
    Usuario findByEmail(String email);
    Usuario findByNombre(String nombre);
}
