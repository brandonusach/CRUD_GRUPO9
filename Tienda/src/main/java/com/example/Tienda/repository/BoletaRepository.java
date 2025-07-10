package com.example.Tienda.repository;

import com.example.Tienda.entity.Boleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BoletaRepository extends JpaRepository<Boleta, Long> {

    // Buscar boletas por usuario
    List<Boleta> findByUsuarioIdUsuario(Long idUsuario);

    // Buscar boletas por fecha
    List<Boleta> findByFecha(LocalDate fecha);

    // Buscar boletas por rango de fechas
    List<Boleta> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);

    // Obtener boletas con información del usuario
    @Query("SELECT b FROM Boleta b LEFT JOIN FETCH b.usuario WHERE b.idBoleta = :idBoleta")
    Optional<Boleta> findByIdWithUsuario(@Param("idBoleta") Long idBoleta);

    // Reporte de ventas por período
    @Query("SELECT b.fecha, SUM(b.total) as total_ventas " +
            "FROM Boleta b " +
            "WHERE b.fecha BETWEEN :fechaInicio AND :fechaFin " +
            "GROUP BY b.fecha " +
            "ORDER BY b.fecha")
    List<Object[]> getReporteVentasPorPeriodo(@Param("fechaInicio") LocalDate fechaInicio,
                                              @Param("fechaFin") LocalDate fechaFin);


        // Sumar ventas por período
        @Query("SELECT SUM(b.total) FROM Boleta b WHERE b.fecha BETWEEN :fechaInicio AND :fechaFin")
        Double sumVentasPorPeriodo(@Param("fechaInicio") LocalDate fechaInicio,
                                   @Param("fechaFin") LocalDate fechaFin);

        // Contar transacciones por período
        @Query("SELECT COUNT(b) FROM Boleta b WHERE b.fecha BETWEEN :fechaInicio AND :fechaFin")
        Long countTransaccionesPorPeriodo(@Param("fechaInicio") LocalDate fechaInicio,
                                          @Param("fechaFin") LocalDate fechaFin);

        // Ventas del mes actual
        @Query("SELECT SUM(b.total) FROM Boleta b WHERE YEAR(b.fecha) = YEAR(CURRENT_DATE) AND MONTH(b.fecha) = MONTH(CURRENT_DATE)")
        Double sumVentasDelMes();

}