package com.trexerot.tienda.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @Email(message = "El email no es valido")
    @NotBlank(message = "El email es obligario")
    private String email;
    private  String rol;
    @NotBlank(message = "La contraseña es obligatoria")
    private String contrasena;
    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;
    @NotBlank(message = "El número telefonico es obligatorio")
    private String telefono;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "carrito_id")
    @JsonBackReference
    private Carrito carrito;

    public Usuario(){}
    public Usuario (String nombre, String email, String rol,String contrasena,String direccion,String telefono){
        this.nombre =nombre;
        this.email=email;
        this.rol=rol;
        this.contrasena=contrasena;
        this.direccion=direccion;
        this.telefono=telefono;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public  String getContrasena() {
        return contrasena;
    }

    public void setContrasena (String contrasena) {
        this.contrasena = contrasena;
    }

    public  String getDireccion() {
        return direccion;
    }

    public void setDireccion( String direccion) {
        this.direccion = direccion;
    }

    public  String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
