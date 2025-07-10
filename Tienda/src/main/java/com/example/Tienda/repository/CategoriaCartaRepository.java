package com.example.Tienda.repository;

import com.example.Tienda.entity.CategoriaCarta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaCartaRepository extends JpaRepository<CategoriaCarta, Long> {

    // Buscar categorías por rareza
    List<CategoriaCarta> findByRareza(String rareza);

    // Buscar categorías por estado
    List<CategoriaCarta> findByEstado(String estado);

    // Buscar categorías por año
    List<CategoriaCarta> findByAno(String ano);

    // Buscar categorías por rareza y estado
    List<CategoriaCarta> findByRarezaAndEstado(String rareza, String estado);

    // Buscar categorías por rareza y año
    List<CategoriaCarta> findByRarezaAndAno(String rareza, String ano);

    // Buscar categorías por estado y año
    List<CategoriaCarta> findByEstadoAndAno(String estado, String ano);

    // Buscar categorías por todos los criterios
    List<CategoriaCarta> findByRarezaAndEstadoAndAno(String rareza, String estado, String ano);

    // Verificar si existe una categoría con ciertos criterios
    boolean existsByRarezaAndEstadoAndAno(String rareza, String estado, String ano);

    // Obtener categoría con sus productos
    @Query("SELECT cc FROM CategoriaCarta cc LEFT JOIN FETCH cc.productos WHERE cc.idCarta = :idCarta")
    Optional<CategoriaCarta> findByIdWithProductos(@Param("idCarta") Long idCarta);

    // Contar productos en una categoría
    @Query("SELECT COUNT(p) FROM CategoriaCarta cc JOIN cc.productos p WHERE cc.idCarta = :idCarta")
    Long countProductosInCategoria(@Param("idCarta") Long idCarta);

    // Obtener años disponibles
    @Query("SELECT DISTINCT cc.ano FROM CategoriaCarta cc ORDER BY cc.ano")
    List<String> findDistinctAnos();

    // Obtener rarezas disponibles
    @Query("SELECT DISTINCT cc.rareza FROM CategoriaCarta cc ORDER BY cc.rareza")
    List<String> findDistinctRarezas();

    // Obtener estados disponibles
    @Query("SELECT DISTINCT cc.estado FROM CategoriaCarta cc ORDER BY cc.estado")
    List<String> findDistinctEstados();
}

