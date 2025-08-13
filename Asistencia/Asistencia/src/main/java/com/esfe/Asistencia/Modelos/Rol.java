package com.esfe.Asistencia.Modelos;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
@Entity
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer Id;
@NotBlank (message = "El nombre de rol no puede estar vacío")
private String Nombre;

public Integer getID() {
        return Id;
    }
    public void setID(Integer id) {
        Id = id;
    } 
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
