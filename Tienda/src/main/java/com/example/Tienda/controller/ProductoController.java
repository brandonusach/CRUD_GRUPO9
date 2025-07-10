package com.example.Tienda.controller;

import com.example.Tienda.entity.Producto;
import com.example.Tienda.repository.ProductoRepository;
import com.example.Tienda.service.CompraProductoService;
import com.example.Tienda.service.ProductoService;
import com.example.Tienda.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CompraProductoService compraProductoService;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UsuarioService usuarioService;

    // CREATE - Crear nuevo producto
    @PostMapping("/crearProducto/usuario/{idUsuario}")
    public ResponseEntity<?> crearProducto(@RequestBody Producto producto, @PathVariable("idUsuario") Long idUsuario) {
        try {
            Producto nuevoProducto = productoService.crearProducto(producto, idUsuario);
            return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", e.getMessage()));
        }
    }


    // READ - Obtener todos los productos
    @GetMapping("/getProductos")
    public ResponseEntity<List<Producto>> obtenerTodosProductos() {
        try {
            List<Producto> productos = productoService.obtenerTodosProductos();
            if (productos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(productos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ - Obtener producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable("id") Long id) {
        Optional<Producto> producto = productoService.obtenerProductoPorId(id);
        if (producto.isPresent()) {
            return new ResponseEntity<>(producto.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // UPDATE - Actualizar producto
    @PutMapping("/{idProducto}/usuario/{idUsuario}")
    public ResponseEntity<?> actualizarProducto(@PathVariable("idProducto") Long idProducto,
                                                @PathVariable("idUsuario") Long idUsuario,
                                                @RequestBody Producto productoActualizado) {
        try {
            Producto producto = productoService.actualizarProducto(idProducto, productoActualizado, idUsuario);
            return ResponseEntity.ok(producto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", e.getMessage()));
        }
    }


    // DELETE - Eliminar producto
    @DeleteMapping("/Eliminar/{idProducto}/usuario/{idUsuario}")
    public ResponseEntity<?> eliminarProducto(@PathVariable("idProducto") Long idProducto,
                                              @PathVariable("idUsuario") Long idUsuario) {
        try {
            productoService.eliminarProducto(idProducto, idUsuario);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error interno del servidor"));
        }
    }




    // Buscar productos por nombre
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Producto>> buscarProductosPorNombre(@PathVariable("nombre") String nombre) {
        try {
            List<Producto> productos = productoService.buscarProductosPorNombre(nombre);
            if (productos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(productos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Buscar productos por tipo
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Producto>> buscarProductosPorTipo(@PathVariable("tipo") String tipo) {
        try {
            List<Producto> productos = productoService.obtenerProductosPorTipo(tipo);
            if (productos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(productos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener productos con stock
    @GetMapping("/stock")
    public ResponseEntity<List<Producto>> obtenerProductosConStock() {
        try {
            List<Producto> productos = productoService.obtenerProductosConStock(0);
            if (productos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(productos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar stock de producto
    @PatchMapping("/{id}/stock")
    public ResponseEntity<Producto> actualizarStock(@PathVariable("id") Long id, @RequestParam int nuevoStock) {
        try {
            Producto producto = productoService.actualizarStock(id, nuevoStock);
            return new ResponseEntity<>(producto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ENDPOINTS ADICIONALES PARA FUNCIONALIDADES ESPECÍFICAS

    // Obtener productos más vendidos
    @GetMapping("/mas-vendidos")
    public ResponseEntity<List<Object[]>> obtenerProductosMasVendidos() {
        try {
            List<Object[]> productos = productoService.obtenerProductosMasVendidos();
            if (productos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(productos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener productos con stock bajo
    @GetMapping("/stock-bajo/{limite}")
    public ResponseEntity<List<Object[]>> obtenerProductosStockBajo(@PathVariable("limite") Integer limite) {
        try {
            List<Object[]> productos = productoService.findProductosBajoStock(limite);
            if (productos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(productos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener conteo de productos
    @GetMapping("/conteo/total")
    public ResponseEntity<Long> obtenerConteoTotal() {
        try {
            long conteo = productoService.countAll();
            return new ResponseEntity<>(conteo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener conteo de productos con stock mayor a un valor
    @GetMapping("/conteo/stock-mayor-a/{stock}")
    public ResponseEntity<Long> obtenerConteoStockMayorA(@PathVariable("stock") Integer stock) {
        try {
            long conteo = productoService.countByStockGreaterThan(stock);
            return new ResponseEntity<>(conteo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Categorizar los productos comprados por usuarios según ubicación geográfica.
    @GetMapping("/comprados/region/{region}")
    public ResponseEntity<List<Object[]>> obtenerProductosPorRegion(@PathVariable("region") String region) {
        try {
            List<Object[]> productos = compraProductoService.obtenerProductosCompradosPorRegion(region);
            if (productos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(productos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Categorizar cartas según su rareza, estado y año
    @GetMapping("/categoriaCarta/filtro/{tipoFiltro}")
    public ResponseEntity<List<Producto>> obtenerPorFiltroCarta(
            @PathVariable("tipoFiltro") String tipoFiltro,
            @RequestParam("valor") String valor) {

        List<Producto> productos;

        switch (tipoFiltro.toLowerCase()) {
            case "rareza":
                productos = productoService.obtenerPorRareza(valor);
                break;
            case "estado":
                productos = productoService.obtenerPorEstado(valor);
                break;
            case "ano":
                productos = productoService.obtenerPorAno(valor);
                break;
            default:
                return ResponseEntity.badRequest().build();
        }

        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productos);
    }

    // Categorizar juegos de mesa según su tipo
    @GetMapping("/categoriaMesa/filtro/{nombreCategoria}")
    public ResponseEntity<List<Producto>> obtenerPorCategoriaMesa(
            @PathVariable("nombreCategoria") String nombreCategoria) {

        List<Producto> productos = productoService.obtenerPorNombreCategoriaMesa(nombreCategoria);

        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productos);
    }

    // Obtener ranking de cartas más deseadas
    @GetMapping("/ranking-cartas-deseadas")
    public ResponseEntity<List<Map<String, Object>>> getRankingCartasDeseadasSinValoraciones() {
        List<Object[]> resultados = productoService.obtenerRankingCartasDeseadas();

        List<Map<String, Object>> respuesta = new ArrayList<>();
        for (Object[] fila : resultados) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("idProducto", fila[0]);
            item.put("nombre", fila[1]);
            item.put("precio", fila[2]);
            item.put("stock", fila[3]);
            item.put("descripcion", fila[4]);
            item.put("vecesDeseada", fila[5]);
            item.put("rareza", fila[6]);
            item.put("estado", fila[7]);
            item.put("ano", fila[8]);
            respuesta.add(item);
        }

        return ResponseEntity.ok(respuesta);
    }

    //Categorizar producto entre juego de mesa y carta (jefe)
    @PutMapping("/{idProducto}/categorizar")
    public ResponseEntity<?> categorizarProducto(@PathVariable("idProducto") Long idProducto,
                                                 @RequestBody Map<String, Object> payload) {
        try {
            String tipoProducto = (String) payload.get("tipoProducto");
            Long idUsuario = Long.valueOf(payload.get("idUsuario").toString());

            Producto producto = productoService.categorizarProducto(idProducto, tipoProducto, idUsuario);
            return ResponseEntity.ok(producto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", e.getMessage()));
        }
    }

}