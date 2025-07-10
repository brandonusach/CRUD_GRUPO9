package com.example.Tienda.controller;

import com.example.Tienda.entity.MetodoPago;
import com.example.Tienda.service.MetodoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/metodos-pago")
@CrossOrigin(origins = "*")
public class MetodoDePagoController {

    @Autowired
    private MetodoPagoService metodoPagoService;

    // CREATE - Crear nuevo método de pago
    @PostMapping("/crearMetodoPago")
    public ResponseEntity<MetodoPago> crearMetodoDePago(@RequestBody MetodoPago metodoDePago) {
        try {
            MetodoPago nuevoMetodo = metodoPagoService.crearMetodoPago(metodoDePago);
            return new ResponseEntity<>(nuevoMetodo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ - Obtener todos los métodos de pago
    @GetMapping("/getMetodosDePagos")
    public ResponseEntity<List<MetodoPago>> obtenerTodosMetodosDePago() {
        try {
            List<MetodoPago> metodos = metodoPagoService.obtenerTodosMetodosPago();
            if (metodos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(metodos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ - Obtener método de pago por ID
    @GetMapping("getMetodoDePago/{id}")
    public ResponseEntity<MetodoPago> obtenerMetodoPagoPorId(@PathVariable("id") Long id) {
        Optional<MetodoPago> metodo = metodoPagoService.obtenerMetodoPagoPorId(id);
        if (metodo.isPresent()) {
            return new ResponseEntity<>(metodo.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // UPDATE - Actualizar método de pago
    @PutMapping("actualizar/{id}")
    public ResponseEntity<MetodoPago> actualizarMetodoDePago(@PathVariable("id") Long id, @RequestBody MetodoPago metodoPago) {
        try {
            MetodoPago metodoActualizado = metodoPagoService.actualizarMetodoPago(id, metodoPago);
            return new ResponseEntity<>(metodoActualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE - Eliminar método de pago
    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<HttpStatus> eliminarMetodoPago(@PathVariable("id") Long id) {
        try {
            metodoPagoService.eliminarMetodoPago(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener métodos de pago por usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<MetodoPago>> obtenerMetodosPorUsuario(@PathVariable("idUsuario") Long idUsuario) {
        try {
            List<MetodoPago> metodos = metodoPagoService.obtenerMetodosPagoPorUsuario(idUsuario);
            if (metodos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(metodos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener métodos por tipo de pago
    @GetMapping("/tipo/{tipoPago}")
    public ResponseEntity<List<MetodoPago>> obtenerMetodosPorTipo(@PathVariable("tipoPago") String tipoPago) {
        try {
            List<MetodoPago> metodos = metodoPagoService.obtenerMetodosPagoPorTipo(tipoPago);
            if (metodos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(metodos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

