package com.example.Tienda.controller;

import com.example.Tienda.entity.Direccion;
import com.example.Tienda.service.DireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/direcciones")
@CrossOrigin(origins = "*")
public class DireccionController {

    @Autowired
    private DireccionService direccionService;

    // CREATE - Crear nueva dirección
    @PostMapping("/crearDireccion")
    public ResponseEntity<Direccion> crearDireccion(@RequestBody Direccion direccion) {
        try {
            Direccion nuevaDireccion = direccionService.save(direccion);
            return new ResponseEntity<>(nuevaDireccion, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ - Obtener todas las direcciones
    @GetMapping("/getDirecciones")
    public ResponseEntity<List<Direccion>> obtenerTodasDirecciones() {
        try {
            List<Direccion> direcciones = direccionService.findAll();
            if (direcciones.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(direcciones, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ - Obtener dirección por ID
    @GetMapping("getDireccion/{id}")
    public ResponseEntity<Direccion> obtenerDireccionPorId(@PathVariable("id") Long id) {
        Optional<Direccion> direccion = direccionService.findById(id);
        if (direccion.isPresent()) {
            return new ResponseEntity<>(direccion.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // UPDATE - Actualizar dirección
    @PutMapping("actualizarDireccion/{id}")
    public ResponseEntity<Direccion> actualizarDireccion(@PathVariable("id") Long id, @RequestBody Direccion direccion) {
        Optional<Direccion> direccionExistente = direccionService.findById(id);
        if (direccionExistente.isPresent()) {
            Direccion direccionActualizada = direccionExistente.get();
            direccionActualizada.setCalle(direccion.getCalle());
            direccionActualizada.setNumero(direccion.getNumero());
            direccionActualizada.setComuna(direccion.getComuna());
            direccionActualizada.setCiudad(direccion.getCiudad());
            direccionActualizada.setRegion(direccion.getRegion());
            return new ResponseEntity<>(direccionService.save(direccionActualizada), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE - Eliminar dirección
    @DeleteMapping("eliminarDireccion/{id}")
    public ResponseEntity<HttpStatus> eliminarDireccion(@PathVariable("id") Long id) {
        try {
            direccionService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Buscar direcciones por ciudad
    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<List<Direccion>> buscarDireccionesPorCiudad(@PathVariable("ciudad") String ciudad) {
        try {
            List<Direccion> direcciones = direccionService.findByCiudad(ciudad);
            if (direcciones.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(direcciones, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Buscar direcciones por región
    @GetMapping("/region/{region}")
    public ResponseEntity<List<Direccion>> buscarDireccionesPorRegion(@PathVariable("region") String region) {
        try {
            List<Direccion> direcciones = direccionService.findByRegion(region);
            if (direcciones.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(direcciones, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
