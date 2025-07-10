package com.example.Tienda.controller;

import com.example.Tienda.entity.Valoracion;
import com.example.Tienda.service.ValoracionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/valoraciones")
@CrossOrigin(origins = "*")
public class ValoracionController {

    @Autowired
    private ValoracionService valoracionService;

    // CREATE - Crear nueva valoración
    @PostMapping("crearValoracion")
    public ResponseEntity<?> crearValoracion(@RequestBody Valoracion valoracion) {
        try {
            Valoracion nuevaValoracion = valoracionService.crearValoracion(valoracion);
            return new ResponseEntity<>(nuevaValoracion, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // READ - Obtener todas las valoraciones
    @GetMapping("getValoraciones")
    public ResponseEntity<List<Valoracion>> obtenerTodasValoraciones() {
        try {
            List<Valoracion> valoraciones = valoracionService.obtenerTodasValoraciones();
            if (valoraciones.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(valoraciones, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ - Obtener valoración por ID
    @GetMapping("getValoracion/{id}")
    public ResponseEntity<Valoracion> obtenerValoracionPorId(@PathVariable("id") Long id) {
        Optional<Valoracion> valoracion = valoracionService.obtenerValoracionPorId(id);
        if (valoracion.isPresent()) {
            return new ResponseEntity<>(valoracion.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // UPDATE - Actualizar valoración
    @PutMapping("actualizar/{id}")
    public ResponseEntity<Valoracion> actualizarValoracion(@PathVariable("id") Long id, @RequestBody Valoracion valoracion) {
        try {
            Valoracion valoracionActualizada = valoracionService.actualizarValoracion(id, valoracion);
            return new ResponseEntity<>(valoracionActualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE - Eliminar valoración
    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<HttpStatus> eliminarValoracion(@PathVariable("id") Long id) {
        try {
            valoracionService.eliminarValoracion(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener valoraciones por producto
    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<List<Valoracion>> obtenerValoracionesPorProducto(@PathVariable("idProducto") Long idProducto) {
        try {
            List<Valoracion> valoraciones = valoracionService.obtenerValoracionesPorProducto(idProducto);
            if (valoraciones.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(valoraciones, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener valoraciones por usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Valoracion>> obtenerValoracionesPorUsuario(@PathVariable("idUsuario") Long idUsuario) {
        try {
            List<Valoracion> valoraciones = valoracionService.obtenerValoracionesPorUsuario(idUsuario);
            if (valoraciones.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(valoraciones, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener promedio de valoraciones de un producto
    @GetMapping("/producto/{idProducto}/promedio")
    public ResponseEntity<Double> obtenerPromedioValoracionesProducto(@PathVariable("idProducto") Long idProducto) {
        try {
            Double promedio = valoracionService.obtenerPromedioValoracionProducto(idProducto);
            if (promedio != null) {
                return new ResponseEntity<>(promedio, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener valoraciones por puntuación
    @GetMapping("/puntuacion/{puntuacion}")
    public ResponseEntity<List<Valoracion>> obtenerValoracionesPorPuntuacion(@PathVariable("puntuacion") Integer puntuacion) {
        try {
            List<Valoracion> valoraciones = valoracionService.obtenerValoracionesPorPuntuacion(puntuacion);
            if (valoraciones.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(valoraciones, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Verificar si un usuario ya valoró un producto
    @GetMapping("/verificar/{idUsuario}/{idProducto}")
    public ResponseEntity<Boolean> verificarSiUsuarioYaValoro(@PathVariable("idUsuario") Long idUsuario, @PathVariable("idProducto") Long idProducto) {
        try {
            boolean yaValoro = valoracionService.usuarioYaValoro(idUsuario, idProducto);
            return new ResponseEntity<>(yaValoro, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}