package com.example.Tienda.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name = "metodo_de_pago")
public class MetodoPago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_metodo_pago")
    private Long idMetodoPago;

    @NotBlank(message = "El tipo de pago es obligatorio")
    @Size(max = 50, message = "El tipo de pago no puede exceder 50 caracteres")
    @Column(name = "tipo_de_pago", nullable = false, length = 50)
    private String tipoPago;

    @NotBlank(message = "El detalle es obligatorio")
    @Size(max = 100, message = "El detalle no puede exceder 100 caracteres")
    @Column(name = "detalle", nullable = false, length = 100)
    private String detalle;

    @NotBlank(message = "El titular es obligatorio")
    @Size(max = 50, message = "El titular no puede exceder 50 caracteres")
    @Column(name = "titular", nullable = false, length = 50)
    private String titular;

    @NotBlank(message = "La fecha de vencimiento es obligatoria")
    @Size(max = 50, message = "La fecha de vencimiento no puede exceder 50 caracteres")
    @Column(name = "fecha_de_vencimiento", nullable = false, length = 50)
    private String fechaDeVencimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonBackReference(value = "usuario-metodosPago")
    private Usuario usuario;

    @OneToMany(mappedBy = "metodoPago", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "metodoPago-compras")
    private Set<Compra> compras;

    // Constructores
    public MetodoPago() {}

    public MetodoPago(String tipoPago, String detalle, String titular, String fechaDeVencimiento, Usuario usuario) {
        this.tipoPago = tipoPago;
        this.detalle = detalle;
        this.titular = titular;
        this.fechaDeVencimiento = fechaDeVencimiento;
        this.usuario = usuario;
    }

    // Getters y Setters
    public Long getIdMetodoPago() { return idMetodoPago; }
    public void setIdMetodoPago(Long idMetodoPago) { this.idMetodoPago = idMetodoPago; }

    public String getTipoDePago() { return tipoPago; }
    public void setTipoDePago(String tipoPago) { this.tipoPago = tipoPago; }

    public String getDetalle() { return detalle; }
    public void setDetalle(String detalle) { this.detalle = detalle; }

    public String getTitular() { return titular; }
    public void setTitular(String titular) { this.titular = titular; }

    public String getFechaDeVencimiento() { return fechaDeVencimiento; }
    public void setFechaDeVencimiento(String fechaDeVencimiento) { this.fechaDeVencimiento = fechaDeVencimiento; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Set<Compra> getCompras() { return compras; }
    public void setCompras(Set<Compra> compras) { this.compras = compras; }
}

