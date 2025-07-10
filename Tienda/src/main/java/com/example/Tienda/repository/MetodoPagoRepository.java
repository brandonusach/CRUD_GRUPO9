package com.example.Tienda.repository;

import com.example.Tienda.entity.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Long> {

    // Buscar métodos de pago por usuario
    List<MetodoPago> findByUsuarioIdUsuario(Long idUsuario);

    // Buscar métodos de pago por tipo - CORREGIDO: tipoPago en lugar de tipoDePago
    List<MetodoPago> findByTipoPago(String tipoPago);

    // Verificar si un usuario ya tiene un método de pago de cierto tipo - CORREGIDO
    boolean existsByUsuarioIdUsuarioAndTipoPago(Long idUsuario, String tipoPago);

    // Buscar métodos de pago por titular
    List<MetodoPago> findByTitularContainingIgnoreCase(String titular);

    // Obtener método de pago con información del usuario
    @Query("SELECT mp FROM MetodoPago mp LEFT JOIN FETCH mp.usuario WHERE mp.idMetodoPago = :idMetodoPago")
    Optional<MetodoPago> findByIdWithUsuario(@Param("idMetodoPago") Long idMetodoPago);
}