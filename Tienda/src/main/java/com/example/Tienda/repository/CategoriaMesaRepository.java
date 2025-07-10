package com.example.Tienda.repository;

import com.example.Tienda.entity.CategoriaMesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaMesaRepository extends JpaRepository<CategoriaMesa, Long> {

    // Buscar categoría por nombre
    Optional<CategoriaMesa> findByNombre(String nombre);

    // Verificar si existe una categoría con cierto nombre
    boolean existsByNombre(String nombre);

    // Buscar categorías por nombre parcial (búsqueda)
    List<CategoriaMesa> findByNombreContainingIgnoreCase(String nombre);

    // Obtener categoría con sus productos
    @Query("SELECT cm FROM CategoriaMesa cm LEFT JOIN FETCH cm.productos WHERE cm.idMesa = :idMesa")
    Optional<CategoriaMesa> findByIdWithProductos(@Param("idMesa") Long idMesa);

    // Contar productos en una categoría
    @Query("SELECT COUNT(p) FROM CategoriaMesa cm JOIN cm.productos p WHERE cm.idMesa = :idMesa")
    Long countProductosInCategoria(@Param("idMesa") Long idMesa);

    // Categorías más populares (con más productos)
    @Query("SELECT cm, COUNT(p) as producto_count " +
            "FROM CategoriaMesa cm " +
            "LEFT JOIN cm.productos p " +
            "GROUP BY cm.idMesa " +
            "ORDER BY producto_count DESC")
    List<Object[]> findCategoriasMasPopulares();
}
