package com.example.Tienda.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 255, message = "El nombre no puede exceder 255 caracteres")
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "stock", columnDefinition = "integer default 0")
    private Integer stock = 0;

    @Size(max = 500, message = "La URL de imagen no puede exceder 500 caracteres")
    @Column(name = "url_imagen", length = 500)
    private String urlImagen;

    @NotBlank(message = "El tipo de producto es obligatorio")
    @Size(max = 100, message = "El tipo de producto no puede exceder 100 caracteres")
    @Column(name = "tipo_producto", nullable = false, length = 100)
    private String tipoProducto;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("producto_valoracion")
    private Set<Valoracion> valoraciones;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<CarritoItem> carritoItems;

    @ManyToMany(mappedBy = "productos",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<ListaDeseos> listasDeDeseos;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "producto_categoria_mesa",
            joinColumns = @JoinColumn(name = "id_producto"),
            inverseJoinColumns = @JoinColumn(name = "id_mesa")
    )
    private Set<CategoriaMesa> categoriasMesa;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "producto_categoria_carta",
            joinColumns = @JoinColumn(name = "id_producto"),
            inverseJoinColumns = @JoinColumn(name = "id_carta")
    )
    private Set<CategoriaCarta> categoriasCarta;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CompraProducto> compraProducto;

    @ManyToMany(mappedBy = "productos")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Tienda> tiendas;

    @ManyToMany(mappedBy = "productos")
    @JsonIgnore
    private Set<Usuario> usuarios;

    // Constructores
    public Producto() {}

    public Producto(String nombre, String descripcion, BigDecimal precio, Integer stock, String tipoProducto) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.tipoProducto = tipoProducto;
    }

    // Getters y Setters
    public Long getIdProducto() { return idProducto; }
    public void setIdProducto(Long idProducto) { this.idProducto = idProducto; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public String getUrlImagen() { return urlImagen; }
    public void setUrlImagen(String urlImagen) { this.urlImagen = urlImagen; }

    public String getTipoProducto() { return tipoProducto; }
    public void setTipoProducto(String tipoProducto) { this.tipoProducto = tipoProducto; }

    public Set<Valoracion> getValoraciones() { return valoraciones; }
    public void setValoraciones(Set<Valoracion> valoraciones) { this.valoraciones = valoraciones; }

    public Set<CarritoItem> getCarritoItems() { return carritoItems; }
    public void setCarritoItems(Set<CarritoItem> carritoItems) { this.carritoItems = carritoItems; }

    public Set<CategoriaMesa> getCategoriasMesa() { return categoriasMesa; }
    public void setCategoriasMesa(Set<CategoriaMesa> categoriasMesa) { this.categoriasMesa = categoriasMesa; }

    public Set<CategoriaCarta> getCategoriasCarta() { return categoriasCarta; }
    public void setCategoriasCarta(Set<CategoriaCarta> categoriasCarta) { this.categoriasCarta = categoriasCarta; }

    public Set<ListaDeseos> getListasDeDeseos() {
        return listasDeDeseos;
    }

    public void setListasDeDeseos(Set<ListaDeseos> listasDeDeseos) {
        this.listasDeDeseos = listasDeDeseos;
    }

    public Set<Tienda> getTiendas() {
        return tiendas;
    }

    public void setTiendas(Set<Tienda> tiendas) {
        this.tiendas = tiendas;
    }
    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}