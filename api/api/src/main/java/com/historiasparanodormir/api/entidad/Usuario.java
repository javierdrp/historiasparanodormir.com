package com.historiasparanodormir.api.entidad;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;

@Entity
public class Usuario
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @JsonIgnore public Long id;
    @Column(nullable = false, unique = true) @Email public String email;
    @Column(nullable = false, unique = true) public String nombre;
    @Column(nullable = false) public String contrasena;
    @Column(nullable = false) public String foto;
    @Column(nullable = false) public Integer admin;
}
