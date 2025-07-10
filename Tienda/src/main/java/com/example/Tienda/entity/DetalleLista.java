package com.example.Tienda.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "lista_producto")
@IdClass(DetalleListaId.class)
public class DetalleLista {

    @Id
    @Column(name = "id_deseo")
    private Long idLista;

    @Id
    @Column(name = "id_producto")
    private Long idProducto;

    @ManyToOne
    @JoinColumn(name = "id_deseo", insertable = false, updatable = false)
    private ListaDeseos listaDeseos;

    @ManyToOne
    @JoinColumn(name = "id_producto", insertable = false, updatable = false)
    private Producto producto;

    // Constructores
    public DetalleLista() {}

    public DetalleLista(Long idLista, Long idProducto) {
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

    public ListaDeseos getListaDeseos() {
        return listaDeseos;
    }

    public void setListaDeseos(ListaDeseos listaDeseos) {
        this.listaDeseos = listaDeseos;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}