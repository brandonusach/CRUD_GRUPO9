package com.example.Tienda.repository;

import com.example.Tienda.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Buscar productos por nombre (búsqueda parcial)
    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    // Buscar productos por tipo
    List<Producto> findByTipoProducto(String tipoProducto);

    // Buscar productos por rango de precio
    List<Producto> findByPrecioBetween(BigDecimal precioMin, BigDecimal precioMax);

    // Buscar productos con stock disponible
    List<Producto> findByStockGreaterThan(Integer stock);

    // Buscar productos sin stock
    List<Producto> findByStockLessThanEqual(Integer stock);

    // Contar productos por stock mayor que
    long countByStockGreaterThan(Integer stock);

    // Contar productos por stock específico
    long countByStock(Integer stock);

    // Productos con stock bajo (personalizado) - Versión que retorna nombre y stock
    @Query("SELECT p.nombre, p.stock FROM Producto p WHERE p.stock < :limite ORDER BY p.stock ASC")
    List<Object[]> findProductosStockBajo(@Param("limite") Integer limite);

    // Productos con stock bajo - Versión que retorna ID, nombre y stock
    @Query("SELECT p.idProducto, p.nombre, p.stock " +
            "FROM Producto p " +
            "WHERE p.stock <= :stockMinimo " +
            "ORDER BY p.stock ASC")
    List<Object[]> findProductosStockBajoDetallado(@Param("stockMinimo") Integer stockMinimo);

    // Productos más valorados
    @Query("SELECT p FROM Producto p LEFT JOIN p.valoraciones v " +
            "GROUP BY p.idProducto " +
            "ORDER BY AVG(v.puntuacion) DESC")
    List<Producto> findProductosMasValorados();

    // Productos más vendidos
    @Query(value = "SELECT p.id_producto, p.nombre, COALESCE(SUM(cp.cantidad), 0) as totalVendido " +
            "FROM producto p " +
            "LEFT JOIN compra_producto cp ON cp.id_producto = p.id_producto " +
            "GROUP BY p.id_producto, p.nombre " +
            "ORDER BY totalVendido DESC",
            nativeQuery = true)
    List<Object[]> findProductosMasVendidos();

    // Top 5 productos más vendidos
    @Query(value = "SELECT p.id_producto, p.nombre, COALESCE(SUM(cp.cantidad), 0) as totalVendido " +
            "FROM producto p " +
            "LEFT JOIN compra_producto cp ON cp.id_producto = p.id_producto " +
            "GROUP BY p.id_producto, p.nombre " +
            "ORDER BY totalVendido DESC " +
            "LIMIT 5",
            nativeQuery = true)
    List<Object[]> findTop5ProductosMasVendidos();

    // Ranking de cartas más solicitadas
    @Query(value = "SELECT p.id_producto, p.nombre, COALESCE(SUM(cp.cantidad), 0) as totalSolicitado " +
            "FROM producto p " +
            "LEFT JOIN compra_producto cp ON cp.id_producto = p.id_producto " +
            "WHERE p.tipo_producto = 'CARTA' " +
            "GROUP BY p.id_producto, p.nombre " +
            "ORDER BY totalSolicitado DESC",
            nativeQuery = true)
    List<Object[]> findRankingCartasMasSolicitadas();

    // Buscar productos por categoría mesa
    @Query("SELECT p FROM Producto p JOIN p.categoriasMesa cm WHERE cm.idMesa = :idMesa")
    List<Producto> findByCategoriaMesa(@Param("idMesa") Long idMesa);

    // Buscar productos por categoría carta
    @Query("SELECT p FROM Producto p JOIN p.categoriasCarta cc WHERE cc.idCarta = :idCarta")
    List<Producto> findByCategoriaCarta(@Param("idCarta") Long idCarta);


    // Contar productos sin stock
    @Query("SELECT COUNT(p) FROM Producto p WHERE p.stock = 0")
    Long countProductosSinStock();

    @Query("SELECT p FROM Producto p JOIN p.categoriasCarta c WHERE c.rareza = :rareza")
    List<Producto> findByCategoriaCartaRareza(@Param("rareza") String rareza);

    @Query("SELECT p FROM Producto p JOIN p.categoriasCarta c WHERE c.estado = :estado")
    List<Producto> findByCategoriaCartaEstado(@Param("estado") String estado);

    @Query("SELECT p FROM Producto p JOIN p.categoriasCarta c WHERE c.ano = :ano")
    List<Producto> findByCategoriaCartaAno(@Param("ano") String ano);

    @Query("SELECT p FROM Producto p JOIN p.categoriasMesa m WHERE m.nombre = :nombre")
    List<Producto> findByCategoriaMesaNombre(@Param("nombre") String nombre);

    @Query("""
        SELECT p.idProducto, p.nombre, p.precio, p.stock, p.descripcion,
        COUNT(ld) AS vecesDeseada,
        cc.rareza, cc.estado, cc.ano
        FROM Producto p
        JOIN p.listasDeDeseos ld
        JOIN p.categoriasCarta cc
        WHERE p.tipoProducto = 'Carta'
        GROUP BY p.idProducto, p.nombre, p.precio, p.stock, p.descripcion,
                cc.rareza, cc.estado, cc.ano
        ORDER BY vecesDeseada DESC
""")
    List<Object[]> rankingCartasDeseadas();

    @Query("""
    SELECT DISTINCT p FROM Producto p
    JOIN p.tiendas t
    JOIN t.direccion dTienda
    JOIN Usuario u ON u.idUsuario = :idUsuario
    JOIN u.direcciones dUsuario
    WHERE LOWER(dTienda.comuna) = LOWER(dUsuario.comuna)
""")
    List<Producto> findProductosPorComunaDelUsuario(@Param("idUsuario") Long idUsuario);

}