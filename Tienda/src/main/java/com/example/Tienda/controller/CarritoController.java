package com.example.Tienda.controller;

import com.example.Tienda.entity.Carrito;
import com.example.Tienda.entity.CarritoItem;
import com.example.Tienda.service.CarritoItemService;
import com.example.Tienda.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/carrito")
@CrossOrigin(origins = "*")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private CarritoItemService carritoItemService;

    // CREATE - Crear nuevo carrito
    @PostMapping("/crearCarrito")
    public ResponseEntity<Carrito> crearCarrito(@RequestBody Carrito carrito) {
        try {
            Carrito nuevoCarrito = carritoService.save(carrito);
            return new ResponseEntity<>(nuevoCarrito, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ - Obtener carrito por usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<Carrito> obtenerCarritoPorUsuario(@PathVariable("idUsuario") Long idUsuario) {
        Optional<Carrito> carrito = carritoService.findByIdUsuarioAndEstado(idUsuario, "activo");
        if (carrito.isPresent()) {
            return new ResponseEntity<>(carrito.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Agregar producto al carrito
    @PostMapping("/{idCarrito}/productos")
    public ResponseEntity<?> agregarProductoAlCarrito(
            @PathVariable("idCarrito") Long idCarrito,
            @RequestBody Map<String, Object> payload) {
        try {
            Long idProducto = Long.valueOf(payload.get("idProducto").toString());
            Integer cantidad = Integer.valueOf(payload.get("cantidad").toString());

            Carrito carritoActualizado = carritoService.agregarProductoAlCarrito(idCarrito, idProducto, cantidad);
            return new ResponseEntity<>(carritoActualizado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // Obtener items del carrito
    @GetMapping("/{idCarrito}/items")
    public ResponseEntity<List<CarritoItem>> obtenerItemsCarrito(@PathVariable("idCarrito") Long idCarrito) {
        try {
            List<CarritoItem> items = carritoItemService.findByIdCarrito(idCarrito);
            if (items.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar cantidad de producto en carrito
    @PutMapping("/{idCarrito}/productos/{idProducto}")
    public ResponseEntity<CarritoItem> actualizarCantidadProducto(
            @PathVariable("idCarrito") Long idCarrito,
            @PathVariable("idProducto") Long idProducto,
            @RequestParam int cantidad) {
        try {
            Optional<CarritoItem> itemExistente = carritoItemService.findByIdCarritoAndIdProducto(idCarrito, idProducto);
            if (itemExistente.isPresent()) {
                CarritoItem item = itemExistente.get();
                item.setCantidad(cantidad);
                return new ResponseEntity<>(carritoItemService.save(item), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Eliminar producto del carrito
    @DeleteMapping("/{idCarrito}/productos/{idProducto}")
    public ResponseEntity<HttpStatus> eliminarProductoDelCarrito(
            @PathVariable("idCarrito") Long idCarrito,
            @PathVariable("idProducto") Long idProducto) {
        try {
            carritoItemService.deleteByIdCarritoAndIdProducto(idCarrito, idProducto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Vaciar carrito
    @DeleteMapping("/{idCarrito}/vaciar")
    public ResponseEntity<HttpStatus> vaciarCarrito(@PathVariable("idCarrito") Long idCarrito) {
        try {
            carritoItemService.deleteByIdCarrito(idCarrito);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Cambiar estado del carrito
    @PatchMapping("/{idCarrito}/estado")
    public ResponseEntity<Carrito> cambiarEstadoCarrito(
            @PathVariable("idCarrito") Long idCarrito,
            @RequestParam String estado) {
        Optional<Carrito> carrito = carritoService.findById(idCarrito);
        if (carrito.isPresent()) {
            Carrito carritoActualizado = carrito.get();
            carritoActualizado.setEstado(estado);
            return new ResponseEntity<>(carritoService.save(carritoActualizado), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
