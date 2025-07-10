package com.example.Tienda.controller;

import com.example.Tienda.entity.Boleta;
import com.example.Tienda.service.BoletaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/boletas")
@CrossOrigin(origins = "*")
public class BoletaController {

    @Autowired
    private BoletaService boletaService;

    // CREATE - Crear nueva boleta
    @PostMapping("/crearBoleta")
    public ResponseEntity<Boleta> crearBoleta(@RequestBody Boleta boleta) {
        try {
            // Establecer fecha actual si no se proporciona
            if (boleta.getFecha() == null) {
                boleta.setFecha(LocalDate.now());
            }
            Boleta nuevaBoleta = boletaService.save(boleta);
            return new ResponseEntity<>(nuevaBoleta, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ - Obtener todas las boletas
    @GetMapping("/getboletas")
    public ResponseEntity<List<Boleta>> obtenerTodasBoletas() {
        try {
            List<Boleta> boletas = boletaService.findAll();
            if (boletas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(boletas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ - Obtener boleta por ID
    @GetMapping("getboleta/{id}")
    public ResponseEntity<Boleta> obtenerBoletaPorId(@PathVariable("id") Long id) {
        Optional<Boleta> boleta = boletaService.findById(id);
        if (boleta.isPresent()) {
            return new ResponseEntity<>(boleta.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // UPDATE - Actualizar boleta
    @PutMapping("actualizarBoleta/{id}")
    public ResponseEntity<Boleta> actualizarBoleta(@PathVariable("id") Long id, @RequestBody Boleta boleta) {
        Optional<Boleta> boletaExistente = boletaService.findById(id);
        if (boletaExistente.isPresent()) {
            Boleta boletaActualizada = boletaExistente.get();
            boletaActualizada.setIdUsuario(boleta.getIdUsuario());
            boletaActualizada.setFecha(boleta.getFecha());
            boletaActualizada.setTotal(boleta.getTotal());
            return new ResponseEntity<>(boletaService.save(boletaActualizada), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE - Eliminar boleta
    @DeleteMapping("eliminarBoleta/{id}")
    public ResponseEntity<HttpStatus> eliminarBoleta(@PathVariable("id") Long id) {
        try {
            boletaService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener boletas por usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Boleta>> obtenerBoletasPorUsuario(@PathVariable("idUsuario") Long idUsuario) {
        try {
            List<Boleta> boletas = boletaService.findByIdUsuario(idUsuario);
            if (boletas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(boletas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener boletas por rango de fechas
    @GetMapping("/fechas")
    public ResponseEntity<List<Boleta>> obtenerBoletasPorFechas(
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin) {
        try {
            LocalDate inicio = LocalDate.parse(fechaInicio);
            LocalDate fin = LocalDate.parse(fechaFin);
            List<Boleta> boletas = boletaService.findByFechaBetween(inicio, fin);
            if (boletas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(boletas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener total de ventas por período
    @GetMapping("/ventas/total")
    public ResponseEntity<Double> obtenerTotalVentas(
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin) {
        try {
            LocalDate inicio = LocalDate.parse(fechaInicio);
            LocalDate fin = LocalDate.parse(fechaFin);
            Double total = boletaService.calcularTotalVentasPorPeriodo(inicio, fin);
            return new ResponseEntity<>(total != null ? total : 0.0, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

