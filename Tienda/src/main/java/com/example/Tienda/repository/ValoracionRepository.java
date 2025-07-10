package com.example.Tienda.repository;

import com.example.Tienda.entity.Valoracion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ValoracionRepository extends JpaRepository<Valoracion, Long> {

    // Buscar valoraciones por producto
    List<Valoracion> findByProductoIdProducto(Long idProducto);

    // Buscar valoraciones por usuario
    List<Valoracion> findByUsuarioIdUsuario(Long idUsuario);

    // Buscar valoraciones por puntuación
    List<Valoracion> findByPuntuacion(Integer puntuacion);

    // Buscar valoraciones por rango de puntuación
    List<Valoracion> findByPuntuacionBetween(Integer puntuacionMin, Integer puntuacionMax);

    // Obtener promedio de valoraciones por producto
    @Query("SELECT AVG(v.puntuacion) FROM Valoracion v WHERE v.producto.idProducto = :idProducto")
    Double getPromedioValoracionByProducto(@Param("idProducto") Long idProducto);

    // Verificar si un usuario ya valoró un producto
    boolean existsByUsuarioIdUsuarioAndProductoIdProducto(Long idUsuario, Long idProducto);

    // Obtener valoración específica de un usuario para un producto
    Optional<Valoracion> findByUsuarioIdUsuarioAndProductoIdProducto(Long idUsuario, Long idProducto);

    // Contar valoraciones por producto
    @Query("SELECT COUNT(v) FROM Valoracion v WHERE v.producto.idProducto = :idProducto")
    Long countByProductoIdProducto(@Param("idProducto") Long idProducto);

    // Productos mejor valorados
    @Query("SELECT v.producto.idProducto, AVG(v.puntuacion) as promedio " +
            "FROM Valoracion v " +
            "GROUP BY v.producto.idProducto " +
            "HAVING COUNT(v) >= :minValoracines " +
            "ORDER BY promedio DESC")
    List<Object[]> findProductosMejorValorados(@Param("minValoracines") Long minValoracines);

    @Query("SELECT v.producto.idProducto, v.producto.nombre, AVG(v.puntuacion) as promedio, COUNT(v) as totalValoracines " +
            "FROM Valoracion v " +
            "GROUP BY v.producto.idProducto, v.producto.nombre " +
            "HAVING COUNT(v) >= 3 " +
            "ORDER BY promedio DESC")
    List<Object[]> findProductosMejorValorados();

}
