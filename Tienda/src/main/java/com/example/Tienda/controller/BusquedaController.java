package com.example.Tienda.controller;

import com.example.Tienda.entity.Producto;
import com.example.Tienda.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/busqueda")
@CrossOrigin(origins = "*")
public class BusquedaController {

    @Autowired
    private ProductoService productoService;

    // Búsqueda general de productos
    @GetMapping("/productos")
    public ResponseEntity<List<Producto>> buscarProductos(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax,
            @RequestParam(required = false) Boolean soloConStock) {
        try {
            List<Producto> productos = productoService.buscarProductos(nombre, tipo, precioMin, precioMax, soloConStock);

            if (productos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(productos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Búsqueda de productos por rango de precio
    @GetMapping("/productos/precio")
    public ResponseEntity<List<Producto>> buscarProductosPorPrecio(
            @RequestParam Double min,
            @RequestParam Double max) {
        try {
            List<Producto> productos = productoService.findByPrecioBetween(min, max);

            if (productos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(productos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Productos recomendados (basado en valoraciones)
    @GetMapping("/productos/recomendados")
    public ResponseEntity<List<Producto>> obtenerProductosRecomendados(@RequestParam(defaultValue = "10") int limite) {
        try {
            List<Producto> productos = productoService.findProductosRecomendados(limite);

            if (productos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(productos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}