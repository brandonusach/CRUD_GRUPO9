package com.example.Tienda.controller;

import com.example.Tienda.entity.Tienda;
import com.example.Tienda.service.TiendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tiendas")
@CrossOrigin(origins = "*")
public class TiendaController {

    @Autowired
    private TiendaService tiendaService;

    // CREATE - Crear nueva tienda
    @PostMapping("/crearTienda")
    public ResponseEntity<Tienda> crearTienda(@RequestBody Tienda tienda) {
        try {
            Tienda nuevaTienda = tiendaService.crearTienda(tienda);
            return new ResponseEntity<>(nuevaTienda, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ - Obtener todas las tiendas
    @GetMapping("/getTiendas")
    public ResponseEntity<List<Tienda>> obtenerTodasTiendas() {
        try {
            List<Tienda> tiendas = tiendaService.obtenerTodasTiendas();
            if (tiendas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tiendas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ - Obtener tienda por ID
    @GetMapping("getTienda/{id}")
    public ResponseEntity<Tienda> obtenerTiendaPorId(@PathVariable("id") Long id) {
        Optional<Tienda> tienda = tiendaService.obtenerTiendaPorId(id);
        if (tienda.isPresent()) {
            return new ResponseEntity<>(tienda.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // UPDATE - Actualizar tienda
    @PutMapping("actualizar/{id}")
    public ResponseEntity<Tienda> actualizarTienda(@PathVariable("id") Long id, @RequestBody Tienda tienda) {
        try {
            Tienda tiendaActualizada = tiendaService.actualizarTienda(id, tienda);
            return new ResponseEntity<>(tiendaActualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE - Eliminar tienda
    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<HttpStatus> eliminarTienda(@PathVariable("id") Long id) {
        try {
            tiendaService.eliminarTienda(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener tiendas por jefe
    @GetMapping("/jefe/{idJefe}")
    public ResponseEntity<List<Tienda>> obtenerTiendasPorJefe(@PathVariable("idJefe") Long idJefe) {
        try {
            List<Tienda> tiendas = tiendaService.obtenerTiendasPorJefe(idJefe);
            if (tiendas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tiendas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Buscar tiendas por nombre
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Tienda>> buscarTiendasPorNombre(@PathVariable("nombre") String nombre) {
        try {
            List<Tienda> tiendas = tiendaService.obtenerTiendasPorNombre(nombre);
            if (tiendas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tiendas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener tiendas con productos
    @GetMapping("/con-productos")
    public ResponseEntity<List<Object[]>> obtenerTiendasConProductos() {
        try {
            List<Object[]> tiendas = tiendaService.obtenerTiendasConProductos();
            if (tiendas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tiendas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}