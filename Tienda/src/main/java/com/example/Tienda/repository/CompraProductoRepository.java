package com.example.Tienda.repository;

import com.example.Tienda.entity.CompraProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompraProductoRepository extends JpaRepository<CompraProducto, CompraProducto.CompraProductoId> {

    // Buscar por compra
    List<CompraProducto> findByCompraIdCompra(Long idCompra);

    // Buscar por producto
    List<CompraProducto> findByProductoIdProducto(Long idProducto);

    // Crear registros de compra_producto desde el carrito
    @Query(value = "INSERT INTO compra_producto (id_compra, id_producto, cantidad, precio_unitario, subtotal) " +
            "SELECT :idCompra, ci.id_producto, ci.cantidad, ci.precio_unitario, " +
            "(ci.cantidad * ci.precio_unitario) " +
            "FROM carrito_item ci " +
            "WHERE ci.id_carrito = :idCarrito", nativeQuery = true)
    List<CompraProducto> crearCompraProductosDesdeCarrito(@Param("idCompra") Long idCompra, @Param("idCarrito") Long idCarrito);

    // Obtener productos más vendidos
    @Query("SELECT cp.producto.nombre, SUM(cp.cantidad) as totalVendido " +
            "FROM CompraProducto cp " +
            "GROUP BY cp.producto.idProducto, cp.producto.nombre " +
            "ORDER BY totalVendido DESC")
    List<Object[]> findProductosMasVendidos();


    // Obtener productos comprados por usuarios según la región
    @Query("SELECT cp.producto.nombre, SUM(cp.cantidad) as totalVendido " +
            "FROM CompraProducto cp " +
            "JOIN cp.compra c " +
            "JOIN c.usuario u " +
            "JOIN u.direcciones d " +
            "WHERE LOWER(d.region) = LOWER(:region) " +
            "GROUP BY cp.producto.idProducto, cp.producto.nombre " +
            "ORDER BY totalVendido DESC")
    List<Object[]> findProductosCompradosPorRegion(@Param("region") String region);

}