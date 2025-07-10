package com.example.Tienda.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name = "direccion")
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_direccion")
    private Long idDireccion;

    @NotBlank(message = "La calle es obligatoria")
    @Size(max = 255, message = "La calle no puede exceder 255 caracteres")
    @Column(name = "calle", nullable = false)
    private String calle;

    @NotNull(message = "El número es obligatorio")
    @Column(name = "numero", nullable = false)
    private Integer numero;

    @NotBlank(message = "La comuna es obligatoria")
    @Size(max = 100, message = "La comuna no puede exceder 100 caracteres")
    @Column(name = "comuna", nullable = false, length = 100)
    private String comuna;

    @NotBlank(message = "La ciudad es obligatoria")
    @Size(max = 100, message = "La ciudad no puede exceder 100 caracteres")
    @Column(name = "ciudad", nullable = false, length = 100)
    private String ciudad;

    @NotBlank(message = "La región es obligatoria")
    @Size(max = 100, message = "La región no puede exceder 100 caracteres")
    @Column(name = "region", nullable = false, length = 100)
    private String region;

    @ManyToMany(mappedBy = "direcciones", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Usuario> usuarios;

    @OneToMany(mappedBy = "direccion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Tienda> tiendas;

    // Constructores
    public Direccion() {}

    public Direccion(String calle, Integer numero, String comuna, String ciudad, String region) {
        this.calle = calle;
        this.numero = numero;
        this.comuna = comuna;
        this.ciudad = ciudad;
        this.region = region;
    }

    // Getters y Setters
    public Long getIdDireccion() { return idDireccion; }
    public void setIdDireccion(Long idDireccion) { this.idDireccion = idDireccion; }

    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }

    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }

    public String getComuna() { return comuna; }
    public void setComuna(String comuna) { this.comuna = comuna; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public Set<Usuario> getUsuarios() { return usuarios; }
    public void setUsuarios(Set<Usuario> usuarios) { this.usuarios = usuarios; }

    public Set<Tienda> getTiendas() { return tiendas; }
    public void setTiendas(Set<Tienda> tiendas) { this.tiendas = tiendas; }
}
