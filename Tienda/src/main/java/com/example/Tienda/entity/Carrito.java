package com.example.Tienda.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "carrito")
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrito")
    private Long idCarrito;

    @Size(max = 20, message = "El estado no puede exceder 20 caracteres")
    @Column(name = "estado", length = 20, columnDefinition = "varchar(20) default 'activo'")
    private String estado = "activo";

    @DecimalMin(value = "0.0", message = "El total estimado debe ser mayor o igual a 0")
    @Column(name = "total_estimado", precision = 10, scale = 2, columnDefinition = "decimal(10,2) default 0.00")
    private BigDecimal totalEstimado = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnore
    private Usuario usuario;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CarritoItem> carritoItems;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Compra> compras;

    // Constructores
    public Carrito() {}

    public Carrito(Usuario usuario) {
        this.usuario = usuario;
        this.estado = "activo";
        this.totalEstimado = BigDecimal.ZERO;
    }

    // Getters y Setters
    public Long getIdCarrito() { return idCarrito; }
    public void setIdCarrito(Long idCarrito) { this.idCarrito = idCarrito; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public BigDecimal getTotalEstimado() { return totalEstimado; }
    public void setTotalEstimado(BigDecimal totalEstimado) { this.totalEstimado = totalEstimado; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Set<CarritoItem> getCarritoItems() { return carritoItems; }
    public void setCarritoItems(Set<CarritoItem> carritoItems) { this.carritoItems = carritoItems; }

    public Set<Compra> getCompras() { return compras; }
    public void setCompras(Set<Compra> compras) { this.compras = compras; }
}
