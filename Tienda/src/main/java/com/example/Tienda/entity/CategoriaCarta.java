package com.example.Tienda.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name = "categoria_carta")
public class CategoriaCarta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carta")
    private Long idCarta;

    @NotBlank(message = "La rareza es obligatoria")
    @Size(max = 100, message = "La rareza no puede exceder 100 caracteres")
    @Column(name = "rareza", nullable = false, length = 100)
    private String rareza;

    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 100, message = "El estado no puede exceder 100 caracteres")
    @Column(name = "estado", nullable = false, length = 100)
    private String estado;

    @NotBlank(message = "El año es obligatorio")
    @Size(max = 10, message = "El año no puede exceder 10 caracteres")
    @Column(name = "ano", nullable = false, length = 10)
    private String ano;

    @ManyToMany(mappedBy = "categoriasCarta", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Producto> productos;

    // Constructores
    public CategoriaCarta() {}

    public CategoriaCarta(String rareza, String estado, String ano) {
        this.rareza = rareza;
        this.estado = estado;
        this.ano = ano;
    }

    // Getters y Setters
    public Long getIdCarta() { return idCarta; }
    public void setIdCarta(Long idCarta) { this.idCarta = idCarta; }

    public String getRareza() { return rareza; }
    public void setRareza(String rareza) { this.rareza = rareza; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getAno() { return ano; }
    public void setAno(String ano) { this.ano = ano; }

    public Set<Producto> getProductos() { return productos; }
    public void setProductos(Set<Producto> productos) { this.productos = productos; }
}
