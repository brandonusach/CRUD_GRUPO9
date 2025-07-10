package com.example.Tienda.service;

import com.example.Tienda.entity.Boleta;
import com.example.Tienda.repository.BoletaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BoletaService {

    @Autowired
    private BoletaRepository boletaRepository;

    // CREATE
    public Boleta crearBoleta(Boleta boleta) {
        return boletaRepository.save(boleta);
    }

    // Método save para compatibilidad con el controller
    public Boleta save(Boleta boleta) {
        return boletaRepository.save(boleta);
    }

    // READ
    public List<Boleta> obtenerTodasBoletas() {
        return boletaRepository.findAll();
    }

    // Método findAll para compatibilidad con el controller
    public List<Boleta> findAll() {
        return boletaRepository.findAll();
    }

    public Optional<Boleta> obtenerBoletaPorId(Long id) {
        return boletaRepository.findById(id);
    }

    // Método findById para compatibilidad con el controller
    public Optional<Boleta> findById(Long id) {
        return boletaRepository.findById(id);
    }

    public List<Boleta> obtenerBoletasPorUsuario(Long idUsuario) {
        return boletaRepository.findByUsuarioIdUsuario(idUsuario);
    }

    // Método findByIdUsuario para compatibilidad con el controller
    public List<Boleta> findByIdUsuario(Long idUsuario) {
        return boletaRepository.findByUsuarioIdUsuario(idUsuario);
    }

    public List<Boleta> obtenerBoletasPorFecha(LocalDate fecha) {
        return boletaRepository.findByFecha(fecha);
    }

    public List<Boleta> obtenerBoletasPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return boletaRepository.findByFechaBetween(fechaInicio, fechaFin);
    }

    // Método findByFechaBetween para compatibilidad con el controller
    public List<Boleta> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin) {
        return boletaRepository.findByFechaBetween(fechaInicio, fechaFin);
    }

    public List<Object[]> obtenerReporteVentas(LocalDate fechaInicio, LocalDate fechaFin) {
        return boletaRepository.getReporteVentasPorPeriodo(fechaInicio, fechaFin);
    }

    // Método para calcular total de ventas por período
    public Double calcularTotalVentasPorPeriodo(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Boleta> boletas = boletaRepository.findByFechaBetween(fechaInicio, fechaFin);
        return boletas.stream()
                .mapToDouble(b -> b.getTotal().doubleValue())
                .sum();
    }

    // UPDATE
    public Boleta actualizarBoleta(Long id, Boleta boletaActualizada) {
        Boleta boleta = boletaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Boleta no encontrada"));

        boleta.setFecha(boletaActualizada.getFecha());
        boleta.setTotal(boletaActualizada.getTotal());

        return boletaRepository.save(boleta);
    }

    // DELETE
    public void eliminarBoleta(Long id) {
        if (!boletaRepository.existsById(id)) {
            throw new RuntimeException("Boleta no encontrada");
        }
        boletaRepository.deleteById(id);
    }

    // Método deleteById para compatibilidad con el controller
    public void deleteById(Long id) {
        if (!boletaRepository.existsById(id)) {
            throw new RuntimeException("Boleta no encontrada");
        }
        boletaRepository.deleteById(id);
    }

    // Métodos auxiliares
    public boolean existeBoleta(Long id) {
        return boletaRepository.existsById(id);
    }
}