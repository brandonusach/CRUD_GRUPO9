package com.example.Tienda.repository;

import com.example.Tienda.entity.DetalleLista;
import com.example.Tienda.entity.DetalleListaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DetalleListaRepository extends JpaRepository<DetalleLista, DetalleListaId> {

    // Buscar productos por ID de lista
    List<DetalleLista> findByIdLista(Long idLista);

    // Verificar si existe un producto en una lista específica
    boolean existsByIdListaAndIdProducto(Long idLista, Long idProducto);

    // Eliminar un producto específico de una lista específica
    @Modifying
    @Transactional
    @Query("DELETE FROM DetalleLista d WHERE d.idLista = :idLista AND d.idProducto = :idProducto")
    void deleteByIdListaAndIdProducto(@Param("idLista") Long idLista, @Param("idProducto") Long idProducto);


    // Contar productos en una lista
    @Query("SELECT COUNT(d) FROM DetalleLista d WHERE d.idLista = :idLista")
    Long countByIdLista(@Param("idLista") Long idLista);
}