package com.historiasparanodormir.api.controlador;

import com.historiasparanodormir.api.entidad.*;
import com.historiasparanodormir.api.modelo.ModeloComentario;
import com.historiasparanodormir.api.servicio.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@RestController
public class ControladorRest
{
    @Autowired
    ServicioArticulos servicioArticulos;

    @Autowired
    ServicioUsuarios servicioUsuarios;

    @Autowired
    ServicioComentarios servicioComentarios;

    public Usuario autoriza(String authorizationHeader)
    {
        if (authorizationHeader != null && authorizationHeader.startsWith("Basic ")) {
            // Eliminar la parte "Basic " de la cabecera de autorización
            String encodedCredentials = authorizationHeader.substring(6);

            // Decodificar las credenciales en base64
            byte[] decodedBytes = Base64.getDecoder().decode(encodedCredentials);
            String credentials = new String(decodedBytes, StandardCharsets.UTF_8);
            System.out.println("bc");

            // Separar las credenciales en usuario y contraseña
            String[] parts = credentials.split(":");

            String email, pass;
            try
            {
                email = parts[0];
                pass = parts[1];
            }
            catch(Exception e)
            {
                System.out.println("saf");
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }

            return servicioUsuarios.autentica(email, pass);
        } else {
            // La cabecera de autorización no es válida
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/api/usuario/nuevo")
    @CrossOrigin(origins = "*")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario creaUsuario(@RequestBody String jsonStr)
    {
        JSONObject json = new JSONObject(jsonStr);
        return servicioUsuarios.crea(json.getString("email"), json.getString("contrasena"), json.getString("foto"));
    }

    @GetMapping("/api/usuario/acceso")
    @CrossOrigin(origins = "*")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Usuario accede(@RequestHeader("Authorization") String authorizationHeader)
    {
        return autoriza(authorizationHeader);
    }

    @GetMapping("/api/usuario")
    @CrossOrigin(origins = "*")
    @ResponseStatus(HttpStatus.FOUND)
    public Usuario busca(@RequestHeader("Authorization") String authorizationHeader)
    {
        return autoriza(authorizationHeader);
    }

    @PutMapping("/api/usuario/cambio")
    @CrossOrigin(origins = "*")
    @ResponseStatus(HttpStatus.OK)
    public Usuario cambia(@RequestBody String jsonStr, @RequestHeader("Authorization") String authorizationHeader)
    {
        JSONObject json = new JSONObject(jsonStr);
        Usuario usuario = autoriza(authorizationHeader);
        if(!json.getString("nuevoNombre").equals("")) servicioUsuarios.cambiaNombre(usuario, json.getString("nuevoNombre"));
        if(!json.getString("nuevaFoto").equals("")) servicioUsuarios.cambiaFoto(usuario, json.getString("nuevaFoto"));
        if(!json.getString("nuevaContrasena").equals("")) servicioUsuarios.cambiaContrasena(usuario, json.getString("nuevaContrasena"));
        return usuario;
    }

    @PostMapping("/api/articulo/nuevo")
    @CrossOrigin(origins = "*")
    @ResponseStatus(HttpStatus.CREATED)
    public Articulo creaArticulo(@RequestBody String jsonStr, @RequestHeader("Authorization") String authorizationHeader)
    {
        JSONObject json = new JSONObject(jsonStr);
        Usuario usuario = autoriza(authorizationHeader);
        if(usuario == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if(!usuario.admin.equals(1)) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        return servicioArticulos.crea(json.getString("titulo"), json.getString("contenido"));
    }

    @GetMapping("/api/articulo/lista")
    @CrossOrigin(origins = "*")
    @ResponseStatus(HttpStatus.FOUND)
    public String ultimosArticulos() // devuelve la fecha y título de los últimos artículos
    {
        List<Articulo> articulos = servicioArticulos.ultimosArticulos();
        JSONObject jsonLista = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for(Articulo articulo : articulos)
        {
            JSONObject jsonArticulo = new JSONObject();
            jsonArticulo.put("fecha", articulo.fecha);
            jsonArticulo.put("titulo", articulo.titulo);
            jsonArray.put(jsonArticulo);
        }
        jsonLista.put("articulos", jsonArray);
        return jsonLista.toString();
    }

    @GetMapping("/api/articulo/{fecha}")
    @CrossOrigin(origins = "*")
    @ResponseStatus(HttpStatus.FOUND)
    public Articulo buscaArticulo(@PathVariable String fecha)
    {
        Articulo articulo = servicioArticulos.busca(fecha);
        if(articulo == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        return articulo;
    }

    @GetMapping("/api/articulo/ultimo")
    @CrossOrigin(origins = "*")
    @ResponseStatus(HttpStatus.FOUND)
    public Articulo ultimoArticulo()
    {
        List<Articulo> articulos = servicioArticulos.ultimosArticulos();
        return articulos.get(0);
    }

    @PostMapping("/api/articulo/{fechaArticulo}/comentario")
    @CrossOrigin(origins = "*")
    @ResponseStatus(HttpStatus.CREATED)
    public Comentario nuevoComentario(@RequestBody String jsonStr, @PathVariable String fechaArticulo, @RequestHeader("Authorization") String authorizationHeader)
    {
        JSONObject json = new JSONObject(jsonStr);
        Usuario usuario = autoriza(authorizationHeader);
        Articulo articulo = servicioArticulos.busca(fechaArticulo);
        if(articulo == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return servicioComentarios.crea(usuario, articulo, json.getString("contenido"));
    }

    @GetMapping("/api/articulo/{fechaArticulo}/comentario")
    @CrossOrigin(origins = "*")
    @ResponseStatus(HttpStatus.FOUND)
    public List<ModeloComentario> comentariosArticulo(@PathVariable String fechaArticulo)
    {
        Articulo articulo = servicioArticulos.busca(fechaArticulo);
        if(articulo == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return servicioComentarios.busca(articulo).stream().map(comentario -> new ModeloComentario(comentario.usuario.foto, comentario.usuario.email, comentario.usuario.nombre, comentario.fecha, comentario.contenido)).toList();
    }

    @DeleteMapping("/api/articulo/{fechaArticulo}/comentario/{fechaComentario}")
    @CrossOrigin(origins = "*")
    @ResponseStatus(HttpStatus.OK)
    public void borrarComentario(@PathVariable String fechaArticulo, @PathVariable String fechaComentario, @RequestHeader("Authorization") String authorizationHeader)
    {
        Usuario usuario = autoriza(authorizationHeader);
        Articulo articulo = servicioArticulos.busca(fechaArticulo);
        servicioComentarios.borra(usuario, articulo, fechaComentario);
    }
}
