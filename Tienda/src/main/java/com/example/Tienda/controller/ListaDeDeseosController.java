package com.example.Tienda.controller;

import com.example.Tienda.entity.ListaDeseos;
import com.example.Tienda.service.ListaDeseosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/listas-deseos")
@CrossOrigin(origins = "*")
public class ListaDeDeseosController {

    @Autowired
    private ListaDeseosService listaDeseosService;

    // CREATE - Crear nueva lista de deseos
    @PostMapping("/crearListaDeseos")
    public ResponseEntity<ListaDeseos> crearListaDeseos(@RequestBody ListaDeseos listaDeseos) {
        try {
            ListaDeseos nuevaLista = listaDeseosService.crearListaDeseos(listaDeseos);
            return new ResponseEntity<>(nuevaLista, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ - Obtener todas las listas de deseos
    @GetMapping("/getListaDeDeseos")
    public ResponseEntity<List<ListaDeseos>> obtenerTodasListasDeDeseos() {
        try {
            List<ListaDeseos> listas = listaDeseosService.obtenerTodasListasDeseos();
            if (listas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(listas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ - Obtener lista de deseos por ID
    @GetMapping("getListaDeseo/{id}")
    public ResponseEntity<ListaDeseos> obtenerListaDeDeseosPorId(@PathVariable("id") Long id) {
        Optional<ListaDeseos> lista = listaDeseosService.obtenerListaDeseosPorId(id);
        if (lista.isPresent()) {
            return new ResponseEntity<>(lista.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // UPDATE - Actualizar lista de deseos
    @PutMapping("actualizar/{id}")
    public ResponseEntity<ListaDeseos> actualizarListaDeDeseos(@PathVariable("id") Long id, @RequestBody ListaDeseos listaDeseos) {
        try {
            ListaDeseos listaActualizada = listaDeseosService.actualizarListaDeseos(id, listaDeseos);
            return new ResponseEntity<>(listaActualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE - Eliminar lista de deseos
    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<HttpStatus> eliminarListaDeDeseos(@PathVariable("id") Long id) {
        try {
            listaDeseosService.eliminarListaDeseos(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener listas de deseos por usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<ListaDeseos>> obtenerListasPorUsuario(@PathVariable("idUsuario") Long idUsuario) {
        try {
            List<ListaDeseos> listas = listaDeseosService.obtenerListasDeseosPorUsuario(idUsuario);
            if (listas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(listas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Buscar listas por nombre
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<ListaDeseos>> buscarListasPorNombre(@PathVariable("nombre") String nombre) {
        try {
            List<ListaDeseos> listas = listaDeseosService.obtenerListasDeseosPorNombre(nombre);
            if (listas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(listas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Métodos adicionales para manejar productos en listas
    @PostMapping("/{idLista}/productos/{idProducto}")
    public ResponseEntity<ListaDeseos> agregarProductoALista(@PathVariable("idLista") Long idLista,
                                                             @PathVariable("idProducto") Long idProducto) {
        try {
            ListaDeseos listaActualizada = listaDeseosService.agregarProductoAListaDeseos(idLista, idProducto);
            return new ResponseEntity<>(listaActualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{idLista}/productos/{idProducto}")
    public ResponseEntity<HttpStatus> removerProductoDeLista(@PathVariable("idLista") Long idLista,
                                                             @PathVariable("idProducto") Long idProducto) {
        try {
            listaDeseosService.removerProductoDeListaDeseos(idLista, idProducto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener productos en una lista específica
    @GetMapping("/{idLista}/productos")
    public ResponseEntity<List<Object[]>> obtenerProductosEnLista(@PathVariable("idLista") Long idLista) {
        try {
            List<Object[]> productos = listaDeseosService.obtenerProductosEnListaDeseos(idLista);
            if (productos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(productos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}