package com.example.Tienda.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "compra_producto")
@IdClass(CompraProducto.CompraProductoId.class)
public class CompraProducto {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_compra", nullable = false)
    private Compra compra;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "subtotal", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    // Constructores
    public CompraProducto() {}

    public CompraProducto(Compra compra, Producto producto, Integer cantidad, BigDecimal precioUnitario, BigDecimal subtotal) {
        this.compra = compra;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    // Getters y Setters
    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    // Clase interna para la clave compuesta
    public static class CompraProductoId implements Serializable {
        private Long compra;
        private Long producto;

        public CompraProductoId() {}

        public CompraProductoId(Long compra, Long producto) {
            this.compra = compra;
            this.producto = producto;
        }

        public Long getCompra() {
            return compra;
        }

        public void setCompra(Long compra) {
            this.compra = compra;
        }

        public Long getProducto() {
            return producto;
        }

        public void setProducto(Long producto) {
            this.producto = producto;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CompraProductoId that = (CompraProductoId) o;
            return Objects.equals(compra, that.compra) && Objects.equals(producto, that.producto);
        }

        @Override
        public int hashCode() {
            return Objects.hash(compra, producto);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompraProducto that = (CompraProducto) o;
        return Objects.equals(compra, that.compra) && Objects.equals(producto, that.producto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(compra, producto);
    }

    @Override
    public String toString() {
        return "CompraProducto{" +
                "compra=" + (compra != null ? compra.getIdCompra() : null) +
                ", producto=" + (producto != null ? producto.getIdProducto() : null) +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", subtotal=" + subtotal +
                '}';
    }
}