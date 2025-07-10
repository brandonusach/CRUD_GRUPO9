package com.example.Tienda.repository;


import com.example.Tienda.entity.CarritoItem;
import com.example.Tienda.entity.CarritoItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface CarritoItemRepository extends JpaRepository<CarritoItem, CarritoItemId> {

    // Buscar items por carrito
    List<CarritoItem> findByCarritoIdCarrito(Long idCarrito);

    // Buscar items por producto
    List<CarritoItem> findByProductoIdProducto(Long idProducto);

    // Buscar item específico por carrito y producto
    Optional<CarritoItem> findByCarritoIdCarritoAndProductoIdProducto(Long idCarrito, Long idProducto);

    // Calcular total del carrito
    @Query("SELECT SUM(ci.cantidad * ci.precioUnitario) FROM CarritoItem ci WHERE ci.carrito.idCarrito = :idCarrito")
    BigDecimal calcularTotalCarrito(@Param("idCarrito") Long idCarrito);

    // Contar items en un carrito
    @Query("SELECT COUNT(ci) FROM CarritoItem ci WHERE ci.carrito.idCarrito = :idCarrito")
    Long countItemsInCarrito(@Param("idCarrito") Long idCarrito);

    // Obtener cantidad total de productos en un carrito
    @Query("SELECT SUM(ci.cantidad) FROM CarritoItem ci WHERE ci.carrito.idCarrito = :idCarrito")
    Long sumCantidadInCarrito(@Param("idCarrito") Long idCarrito);

    // Eliminar todos los items de un carrito
    void deleteByCarritoIdCarrito(Long idCarrito);

    // Obtener items con información del producto
    @Query("SELECT ci FROM CarritoItem ci LEFT JOIN FETCH ci.producto WHERE ci.carrito.idCarrito = :idCarrito")
    List<CarritoItem> findByCarritoIdCarritoWithProducto(@Param("idCarrito") Long idCarrito);
}