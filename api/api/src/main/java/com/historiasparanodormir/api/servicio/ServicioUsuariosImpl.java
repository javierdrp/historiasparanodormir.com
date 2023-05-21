package com.historiasparanodormir.api.servicio;

import com.historiasparanodormir.api.entidad.Usuario;
import com.historiasparanodormir.api.repositorio.RepoUsuario;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Random;

@Service
public class ServicioUsuariosImpl implements ServicioUsuarios
{
    @Autowired RepoUsuario repoUsuario;

    @Override
    public Usuario crea(String email, String contrasena, String foto)
    {
        String nombre;
        do {
            nombre = "usuario" + (new Random()).nextInt(10000);
        } while(repoUsuario.findByNombre(nombre) != null);
        Usuario usuario = new Usuario();
        usuario.nombre = nombre;
        usuario.email = email;
        usuario.contrasena = contrasena;
        usuario.foto = foto;
        usuario.admin = (email.equals("admin@historiasparanodormir.com")) ? 1 : 0;

        return repoUsuario.save(usuario);
    }

    @Override
    public Usuario autentica(String email, String contrasena)
    {
        System.out.println(email + " " + contrasena);
        Usuario usuario = repoUsuario.findByEmail(email);
        if(usuario != null && Objects.equals(usuario.contrasena, contrasena))
        {
            System.out.println("ok");
            return usuario;
        }
        else
        {
            System.out.println("error");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    @Transactional
    public Usuario cambiaNombre(Usuario usuario, String nombre)
    {
        usuario.nombre = nombre;
        return usuario;
    }

    @Override
    @Transactional
    public Usuario cambiaFoto(Usuario usuario, String foto)
    {
        usuario.foto = foto;
        return usuario;
    }

    @Override
    @Transactional
    public Usuario cambiaContrasena(Usuario usuario, String contrasena)
    {
        usuario.contrasena = contrasena;
        return usuario;
    }
}
