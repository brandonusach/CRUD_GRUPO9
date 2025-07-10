package com.example.Tienda.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "carrito_item")
public class CarritoItem {
    @EmbeddedId
    private CarritoItemId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("carritoId")
    @JoinColumn(name = "id_carrito")
    @JsonIgnoreProperties({"carritoItems", "usuario", "compras"})
    private Carrito carrito;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productoId")
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    @Column(name = "cantidad", nullable = false, columnDefinition = "integer default 1")
    private Integer cantidad = 1;

    @NotNull(message = "El precio unitario es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio unitario debe ser mayor a 0")
    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    // Constructores
    public CarritoItem() {}

    public CarritoItem(Carrito carrito, Producto producto, Integer cantidad, BigDecimal precioUnitario) {
        this.id = new CarritoItemId(carrito.getIdCarrito(), producto.getIdProducto());
        this.carrito = carrito;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    // Getters y Setters
    public CarritoItemId getId() { return id; }
    public void setId(CarritoItemId id) { this.id = id; }

    public Carrito getCarrito() { return carrito; }
    public void setCarrito(Carrito carrito) { this.carrito = carrito; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }

    // Métodos helper para establecer IDs
    public void setIdCarrito(Long idCarrito) {
        if (this.id == null) {
            this.id = new CarritoItemId();
        }
        this.id.setCarritoId(idCarrito);
    }

    public void setIdProducto(Long idProducto) {
        if (this.id == null) {
            this.id = new CarritoItemId();
        }
        this.id.setProductoId(idProducto);
    }

    // Métodos helper para obtener IDs
    public Long getIdCarrito() {
        return this.id != null ? this.id.getCarritoId() : null;
    }

    public Long getIdProducto() {
        return this.id != null ? this.id.getProductoId() : null;
    }

    // Método para calcular subtotal
    public BigDecimal getSubtotal() {
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }
}