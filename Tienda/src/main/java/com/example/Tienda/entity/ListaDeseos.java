package com.example.Tienda.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name = "listas_de_deseos")
public class ListaDeseos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_deseo")
    private Long idDeseo;

    @NotBlank(message = "El nombre de la lista es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnore
    private Usuario usuario;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "lista_producto",
            joinColumns = @JoinColumn(name = "id_deseo"),
            inverseJoinColumns = @JoinColumn(name = "id_producto")
    )
    private Set<Producto> productos;

    // Constructores
    public ListaDeseos() {}

    public ListaDeseos(String nombres, Usuario usuario) {
        this.nombres = nombres;
        this.usuario = usuario;
    }

    // Getters y Setters
    public Long getIdDeseo() { return idDeseo; }
    public void setIdDeseo(Long idDeseo) { this.idDeseo = idDeseo; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Set<Producto> getProductos() { return productos; }
    public void setProductos(Set<Producto> productos) { this.productos = productos; }
}

