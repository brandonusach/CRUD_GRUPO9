package com.example.Tienda.repository;

import com.example.Tienda.entity.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {

    // Buscar compras por usuario
    List<Compra> findByUsuarioIdUsuario(Long idUsuario);

    // Buscar compras por boleta
    List<Compra> findByBoletaIdBoleta(Long idBoleta);

    // Buscar compras por método de pago
    List<Compra> findByMetodoPagoIdMetodoPago(Long idMetodoPago);

    // Buscar compras por carrito
    Optional<Compra> findByCarritoIdCarrito(Long idCarrito);

    // Obtener compra con todos sus datos relacionados
    @Query("SELECT c FROM Compra c " +
            "LEFT JOIN FETCH c.usuario " +
            "LEFT JOIN FETCH c.boleta " +
            "LEFT JOIN FETCH c.metodoPago " +
            "LEFT JOIN FETCH c.carrito " +
            "WHERE c.idCompra = :idCompra")
    Optional<Compra> findByIdWithAllData(@Param("idCompra") Long idCompra);

    // Obtener compra con productos
    @Query("SELECT c FROM Compra c " +
            "LEFT JOIN FETCH c.compraProductos cp " +
            "LEFT JOIN FETCH cp.producto " +
            "WHERE c.idCompra = :idCompra")
    Optional<Compra> findByIdWithProductos(@Param("idCompra") Long idCompra);


    // Calcular total de una compra
    @Query("SELECT SUM(cp.cantidad * cp.precioUnitario) " +
            "FROM Compra c " +
            "LEFT JOIN c.compraProductos cp " +
            "WHERE c.idCompra = :idCompra")
    BigDecimal calcularTotalCompra(@Param("idCompra") Long idCompra);

}