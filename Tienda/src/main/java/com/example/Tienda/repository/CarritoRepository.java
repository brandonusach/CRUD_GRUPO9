package com.example.Tienda.repository;

import com.example.Tienda.entity.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {

    // Buscar carrito activo de un usuario
    Optional<Carrito> findByUsuarioIdUsuarioAndEstado(Long idUsuario, String estado);

    // Buscar todos los carritos de un usuario
    List<Carrito> findByUsuarioIdUsuario(Long idUsuario);

    // Buscar carritos por estado
    List<Carrito> findByEstado(String estado);

    // Obtener carrito con sus items
    @Query("SELECT c FROM Carrito c LEFT JOIN FETCH c.carritoItems WHERE c.idCarrito = :idCarrito")
    Optional<Carrito> findByIdWithItems(@Param("idCarrito") Long idCarrito);

    // Carritos abandonados (sin actividad reciente)
    @Query("SELECT c FROM Carrito c WHERE c.estado = 'abandonado'")
    List<Carrito> findCarritosAbandonados();
}
