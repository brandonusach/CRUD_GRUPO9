package com.example.Tienda.controller;

import com.example.Tienda.entity.Compra;
import com.example.Tienda.entity.CompraProducto;
import com.example.Tienda.service.CompraProductoService;
import com.example.Tienda.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/compras")
@CrossOrigin(origins = "*")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @Autowired
    private CompraProductoService compraProductoService;

    // CREATE - Crear nueva compra
    @PostMapping("/crearCompra")
    public ResponseEntity<Compra> crearCompra(@RequestBody Compra compra) {
        try {
            Compra nuevaCompra = compraService.crearCompra(compra);
            return new ResponseEntity<>(nuevaCompra, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ - Obtener todas las compras
    @GetMapping("/getCompras")
    public ResponseEntity<List<Compra>> obtenerTodasCompras() {
        try {
            List<Compra> compras = compraService.obtenerTodasCompras();
            if (compras.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(compras, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ - Obtener compra por ID
    @GetMapping("getCompras/{id}")
    public ResponseEntity<Compra> obtenerCompraPorId(@PathVariable("id") Long id) {
        Optional<Compra> compra = compraService.obtenerCompraPorId(id); // Cambiado de findById() a obtenerCompraPorId()
        if (compra.isPresent()) {
            return new ResponseEntity<>(compra.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Obtener compras por usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Compra>> obtenerComprasPorUsuario(@PathVariable("idUsuario") Long idUsuario) {
        try {
            List<Compra> compras = compraService.obtenerComprasPorUsuario(idUsuario); // Cambiado de findByIdUsuario() a obtenerComprasPorUsuario()
            if (compras.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(compras, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener detalles de productos de una compra
    @GetMapping("detalleProductos/{idCompra}")
    public ResponseEntity<List<CompraProducto>> obtenerProductosCompra(@PathVariable("idCompra") Long idCompra) {
        try {
            List<CompraProducto> productos = compraProductoService.obtenerCompraProductosPorCompra(idCompra);
            if (productos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(productos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Agregar producto a compra
    @PostMapping("agregarProductos/{idCompra}")
    public ResponseEntity<CompraProducto> agregarProductoACompra(
            @PathVariable("idCompra") Long idCompra,
            @RequestBody CompraProducto compraProducto) {
        try {

            Optional<Compra> compra = compraService.obtenerCompraPorId(idCompra);
            if (compra.isPresent()) {
                compraProducto.setCompra(compra.get());
                CompraProducto nuevoProducto = compraProductoService.crearCompraProducto(compraProducto);
                return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // UPDATE - Actualizar compra
    @PutMapping("actualizarCompra/{id}")
    public ResponseEntity<Compra> actualizarCompra(@PathVariable("id") Long id, @RequestBody Compra compra) {
        try {
            Compra compraActualizada = compraService.actualizarCompra(id, compra);
            return new ResponseEntity<>(compraActualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE - Eliminar compra
    @DeleteMapping("eliminarCompra/{id}")
    public ResponseEntity<HttpStatus> eliminarCompra(@PathVariable("id") Long id) {
        try {
            compraService.eliminarCompra(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Procesar compra (útil para finalizar una compra y actualizar stock)
    @PostMapping("/{id}/procesar")
    public ResponseEntity<Compra> procesarCompra(@PathVariable("id") Long id) {
        try {
            Optional<Compra> compraOpt = compraService.obtenerCompraPorId(id);
            if (compraOpt.isPresent()) {
                Compra compraProcesada = compraService.procesarCompra(compraOpt.get());
                return new ResponseEntity<>(compraProcesada, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
