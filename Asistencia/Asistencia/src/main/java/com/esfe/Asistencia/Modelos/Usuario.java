package com.esfe.Asistencia.Modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.*;


@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer Id;
@NotBlank(message = "El nombre es requerido")
private String Login;

@NotBlank(message = "La contraseña es requerido")
private String Clave;

private int Status;
@ManyToMany(fetch = FetchType.EAGER)
@JoinTable(name = "usuario_rol",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "rol_id"))
private List<Rol> Roles;
public Integer getId() {
    return Id;
}
public void setId(Integer id) {
    Id = id;
}
public String getLogin() {
    return Login;
}
public void setLogin(String login) {
    Login = login;
}
public String getClave() {
    return Clave;
}
public void setClave(String clave) {
    Clave = clave;
}
public int getStatus() {
    return Status;
}
public void setStatus(int status) {
    Status = status;
}
public List<Rol> getRoles() {
    return Roles;
}
public void setRoles(List<Rol> roles) {
    Roles = roles;
}


}
