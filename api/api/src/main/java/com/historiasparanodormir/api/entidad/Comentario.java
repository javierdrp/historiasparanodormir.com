package com.historiasparanodormir.api.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Comentario
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) public Long id;
    @ManyToOne(optional = false) @OnDelete(action = OnDeleteAction.CASCADE) public Usuario usuario;
    @ManyToOne(optional = false) @OnDelete(action = OnDeleteAction.CASCADE) public Articulo articulo;
    @Column(nullable = false) public String fecha;
    @Column(nullable = false) public String contenido;
}
