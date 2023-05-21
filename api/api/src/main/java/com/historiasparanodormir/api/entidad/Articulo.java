package com.historiasparanodormir.api.entidad;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Articulo implements Comparable
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @JsonIgnore public Long id;
    @Column(nullable = false) public String titulo;
    @Column(nullable = false, length = 10000) public String contenido;
    @Column(nullable = false, unique = true) public String fecha; //YYYYMMDDhhmmss

    @Override
    public int compareTo(Object o)
    {
        if(o instanceof Articulo a)
            return Long.signum(Long.parseLong(fecha) - Long.parseLong(a.fecha));
        else return 0;
    }
}
