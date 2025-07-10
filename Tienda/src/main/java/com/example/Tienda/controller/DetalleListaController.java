package com.example.Tienda.controller;

import com.example.Tienda.entity.DetalleLista;
import com.example.Tienda.service.DetalleListaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalle-listas")
@CrossOrigin(origins = "*")
public class DetalleListaController {

    @Autowired
    private DetalleListaService detalleListaService;

    // CREATE - Agregar producto a lista de deseos
    @PostMapping("agregarProducto")
    public ResponseEntity<DetalleLista> agregarProductoALista(@RequestBody DetalleLista detalleLista) {
        try {
            DetalleLista nuevoDetalle = detalleListaService.save(detalleLista);
            return new ResponseEntity<>(nuevoDetalle, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ - Obtener productos de una lista de deseos
    @GetMapping("/lista/{idLista}")
    public ResponseEntity<List<DetalleLista>> obtenerProductosDeLista(@PathVariable("idLista") Long idLista) {
        try {
            List<DetalleLista> productos = detalleListaService.findByIdLista(idLista);
            if (productos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(productos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE - Eliminar producto de lista de deseos
    @DeleteMapping("eliminarLista/{id}")
    public ResponseEntity<HttpStatus> eliminarProductoDeLista(@PathVariable("id") Long id) {
        try {
            detalleListaService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE - Eliminar producto específico de lista específica
    @DeleteMapping("/lista/{idLista}/producto/{idProducto}")
    public ResponseEntity<HttpStatus> eliminarProductoEspecificoDeLista(
            @PathVariable("idLista") Long idLista,
            @PathVariable("idProducto") Long idProducto) {
        try {
            detalleListaService.deleteByIdListaAndIdProducto(idLista, idProducto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Verificar si producto está en lista
    @GetMapping("/lista/{idLista}/producto/{idProducto}/existe")
    public ResponseEntity<Boolean> verificarProductoEnLista(
            @PathVariable("idLista") Long idLista,
            @PathVariable("idProducto") Long idProducto) {
        try {
            boolean existe = detalleListaService.existsByIdListaAndIdProducto(idLista, idProducto);
            return new ResponseEntity<>(existe, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
