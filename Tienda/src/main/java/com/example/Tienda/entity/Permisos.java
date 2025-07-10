package com.example.Tienda.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name = "permisos")
public class Permisos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permiso")
    private Long idPermiso;

    @NotBlank(message = "La descripción del permiso es obligatoria")
    @Size(max = 100, message = "La descripción no puede exceder 100 caracteres")
    @Column(name = "descripcion_permiso", nullable = false, length = 100)
    private String descripcionPermiso;

    @ManyToMany(mappedBy = "permisos", fetch = FetchType.LAZY)
    private Set<Rol> roles;

    // Constructores
    public Permisos() {}

    public Permisos(String descripcionPermiso) {
        this.descripcionPermiso = descripcionPermiso;
    }

    // Getters y Setters
    public Long getIdPermiso() { return idPermiso; }
    public void setIdPermiso(Long idPermiso) { this.idPermiso = idPermiso; }

    public String getDescripcionPermiso() { return descripcionPermiso; }
    public void setDescripcionPermiso(String descripcionPermiso) { this.descripcionPermiso = descripcionPermiso; }

    public Set<Rol> getRoles() { return roles; }
    public void setRoles(Set<Rol> roles) { this.roles = roles; }
}
