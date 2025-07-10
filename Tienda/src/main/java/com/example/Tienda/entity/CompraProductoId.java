package com.example.Tienda.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CompraProductoId implements Serializable {
    @Column(name = "id_compra")
    private Long compraId;

    @Column(name = "id_producto")
    private Long productoId;

    // Constructores
    public CompraProductoId() {}

    public CompraProductoId(Long compraId, Long productoId) {
        this.compraId = compraId;
        this.productoId = productoId;
    }

    // Getters y Setters
    public Long getCompraId() { return compraId; }
    public void setCompraId(Long compraId) { this.compraId = compraId; }

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompraProductoId that = (CompraProductoId) o;
        return Objects.equals(compraId, that.compraId) && Objects.equals(productoId, that.productoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(compraId, productoId);
    }
}