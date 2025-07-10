package com.example.Tienda.entity;

import java.io.Serializable;
import java.util.Objects;

public class DetalleListaId implements Serializable {

    private Long idLista;
    private Long idProducto;

    // Constructor por defecto
    public DetalleListaId() {}

    // Constructor con parámetros
    public DetalleListaId(Long idLista, Long idProducto) {
        this.idLista = idLista;
        this.idProducto = idProducto;
    }

    // Getters y Setters
    public Long getIdLista() {
        return idLista;
    }

    public void setIdLista(Long idLista) {
        this.idLista = idLista;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    // equals y hashCode son necesarios para las claves compuestas
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetalleListaId that = (DetalleListaId) o;
        return Objects.equals(idLista, that.idLista) &&
                Objects.equals(idProducto, that.idProducto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLista, idProducto);
    }
}