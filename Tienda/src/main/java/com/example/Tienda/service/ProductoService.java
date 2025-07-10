package com.example.Tienda.service;

import com.example.Tienda.entity.*;
import com.example.Tienda.repository.CategoriaCartaRepository;
import com.example.Tienda.repository.CategoriaMesaRepository;
import com.example.Tienda.repository.ProductoRepository;
import com.example.Tienda.repository.TiendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CategoriaMesaRepository categoriaMesaRepository;

    @Autowired
    private CategoriaCartaRepository categoriaCartaRepository;

    @Autowired
    private TiendaRepository tiendaRepository;

    // CREATE
    public Producto crearProducto(Producto producto, Long idUsuario) {
        validarPermisoJefeOAdmin(idUsuario);

        // Validar tiendas
        if (producto.getTiendas() == null || producto.getTiendas().isEmpty()) {
            throw new RuntimeException("Debe asignar al menos una tienda");
        }

        // Cargar tiendas completas desde DB
        Set<Tienda> tiendasValidas = producto.getTiendas().stream()
                .map(t -> tiendaRepository.findById(t.getIdTienda())
                        .orElseThrow(() -> new RuntimeException("Tienda no encontrada: " + t.getIdTienda())))
                .collect(Collectors.toSet());

        // Validar categorías según tipoProducto
        if ("juego de mesa".equalsIgnoreCase(producto.getTipoProducto())) {
            if (producto.getCategoriasMesa() == null || producto.getCategoriasMesa().isEmpty()) {
                throw new RuntimeException("Debe asignar al menos una categoría de mesa");
            }

            Set<CategoriaMesa> categoriasMesaValidas = producto.getCategoriasMesa().stream()
                    .map(cm -> categoriaMesaRepository.findById(cm.getIdMesa())
                            .orElseThrow(() -> new RuntimeException("Categoría Mesa no encontrada: " + cm.getIdMesa())))
                    .collect(Collectors.toSet());

            producto.setCategoriasMesa(categoriasMesaValidas);
            producto.setCategoriasCarta(null);

        } else if ("carta".equalsIgnoreCase(producto.getTipoProducto())) {
            if (producto.getCategoriasCarta() == null || producto.getCategoriasCarta().isEmpty()) {
                throw new RuntimeException("Debe asignar al menos una categoría carta");
            }

            Set<CategoriaCarta> categoriasCartaValidas = producto.getCategoriasCarta().stream()
                    .map(cc -> categoriaCartaRepository.findById(cc.getIdCarta())
                            .orElseThrow(() -> new RuntimeException("Categoría Carta no encontrada: " + cc.getIdCarta())))
                    .collect(Collectors.toSet());

            producto.setCategoriasCarta(categoriasCartaValidas);
            producto.setCategoriasMesa(null);

        } else {
            throw new RuntimeException("Tipo de producto inválido");
        }

        producto.setTiendas(tiendasValidas);

        // Guardar producto primero (para que tenga ID)
        Producto productoGuardado = productoRepository.save(producto);

        // Agregar producto a cada tienda (dueña real) y guardar
        for (Tienda tienda : tiendasValidas) {
            tienda.getProductos().add(productoGuardado);
            tiendaRepository.save(tienda);
        }

        return productoGuardado;
    }



    // READ
    public List<Producto> obtenerTodosProductos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> obtenerProductoPorId(Long id) {
        return productoRepository.findById(id);
    }

    public List<Producto> buscarProductosPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Producto> obtenerProductosPorTipo(String tipo) {
        return productoRepository.findByTipoProducto(tipo);
    }

    public List<Producto> obtenerProductosPorRangoPrecio(BigDecimal min, BigDecimal max) {
        return productoRepository.findByPrecioBetween(min, max);
    }

    public List<Producto> obtenerProductosConStock(Integer stockMinimo) {
        return productoRepository.findByStockGreaterThan(stockMinimo);
    }

    public List<Producto> obtenerProductosSinStock() {
        return productoRepository.findByStockLessThanEqual(0);
    }

    public List<Producto> obtenerProductosMasValorados() {
        return productoRepository.findProductosMasValorados();
    }

    public List<Object[]> obtenerProductosMasVendidos() {
        return productoRepository.findProductosMasVendidos();
    }

    // Alias para compatibilidad
    public List<Object[]> findProductosMasVendidos() {
        return obtenerProductosMasVendidos();
    }

    // Requerimiento específico: Ranking de cartas más solicitadas
    public List<Object[]> obtenerRankingCartasMasSolicitadas() {
        return productoRepository.findRankingCartasMasSolicitadas();
    }

    public List<Producto> obtenerProductosPorCategoriaMesa(Long idMesa) {
        return productoRepository.findByCategoriaMesa(idMesa);
    }

    public List<Producto> obtenerProductosPorCategoriaCarta(Long idCarta) {
        return productoRepository.findByCategoriaCarta(idCarta);
    }

    // MÉTODOS FALTANTES AGREGADOS PARA BUSQUEDACONTROLLER

    /**
     * Búsqueda general de productos con múltiples filtros
     */
    public List<Producto> buscarProductos(String nombre, String tipo, Double precioMin, Double precioMax, Boolean soloConStock) {
        List<Producto> productos = productoRepository.findAll();

        // Filtrar por nombre si se proporciona
        if (nombre != null && !nombre.trim().isEmpty()) {
            productos = productos.stream()
                    .filter(p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                    .collect(Collectors.toList());
        }

        // Filtrar por tipo si se proporciona
        if (tipo != null && !tipo.trim().isEmpty()) {
            productos = productos.stream()
                    .filter(p -> p.getTipoProducto().equalsIgnoreCase(tipo))
                    .collect(Collectors.toList());
        }

        // Filtrar por precio mínimo si se proporciona
        if (precioMin != null) {
            productos = productos.stream()
                    .filter(p -> p.getPrecio().doubleValue() >= precioMin)
                    .collect(Collectors.toList());
        }

        // Filtrar por precio máximo si se proporciona
        if (precioMax != null) {
            productos = productos.stream()
                    .filter(p -> p.getPrecio().doubleValue() <= precioMax)
                    .collect(Collectors.toList());
        }

        // Filtrar solo productos con stock si se solicita
        if (soloConStock != null && soloConStock) {
            productos = productos.stream()
                    .filter(p -> p.getStock() > 0)
                    .collect(Collectors.toList());
        }

        return productos;
    }

    /**
     * Buscar productos por rango de precio usando Double
     */
    public List<Producto> findByPrecioBetween(Double min, Double max) {
        BigDecimal minBig = BigDecimal.valueOf(min);
        BigDecimal maxBig = BigDecimal.valueOf(max);
        return productoRepository.findByPrecioBetween(minBig, maxBig);
    }

    /**
     * Obtener productos recomendados basado en valoraciones
     */
    public List<Producto> findProductosRecomendados(int limite) {
        List<Producto> productosValorados = productoRepository.findProductosMasValorados();

        // Limitar el resultado al número especificado
        if (productosValorados.size() > limite) {
            return productosValorados.subList(0, limite);
        }

        return productosValorados;
    }

    // UPDATE
    @Transactional
    public Producto actualizarProducto(Long id, Producto productoActualizado, Long idUsuario) {
        validarPermisoJefeOAdmin(idUsuario);

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setNombre(productoActualizado.getNombre());
        producto.setDescripcion(productoActualizado.getDescripcion());
        producto.setPrecio(productoActualizado.getPrecio());
        producto.setStock(productoActualizado.getStock());
        producto.setUrlImagen(productoActualizado.getUrlImagen());
        producto.setTipoProducto(productoActualizado.getTipoProducto());

        // Limpiar categorías anteriores
        producto.getCategoriasMesa().clear();
        producto.getCategoriasCarta().clear();

        // Asignar nuevas categorías de mesa o carta según tipoProducto
        if ("juego de mesa".equalsIgnoreCase(productoActualizado.getTipoProducto())) {
            if (productoActualizado.getCategoriasMesa() == null || productoActualizado.getCategoriasMesa().isEmpty()) {
                throw new RuntimeException("Debe asignar al menos una categoría de mesa");
            }
            Set<CategoriaMesa> categoriasMesaValidas = productoActualizado.getCategoriasMesa().stream()
                    .map(cm -> categoriaMesaRepository.findById(cm.getIdMesa())
                            .orElseThrow(() -> new RuntimeException("Categoría Mesa no encontrada: " + cm.getIdMesa())))
                    .collect(Collectors.toSet());

            producto.setCategoriasMesa(categoriasMesaValidas);
            producto.setCategoriasCarta(null);

        } else if ("carta".equalsIgnoreCase(productoActualizado.getTipoProducto())) {
            if (productoActualizado.getCategoriasCarta() == null || productoActualizado.getCategoriasCarta().isEmpty()) {
                throw new RuntimeException("Debe asignar al menos una categoría carta");
            }
            Set<CategoriaCarta> categoriasCartaValidas = productoActualizado.getCategoriasCarta().stream()
                    .map(cc -> categoriaCartaRepository.findById(cc.getIdCarta())
                            .orElseThrow(() -> new RuntimeException("Categoría Carta no encontrada: " + cc.getIdCarta())))
                    .collect(Collectors.toSet());

            producto.setCategoriasCarta(categoriasCartaValidas);
            producto.setCategoriasMesa(null);

        } else {
            throw new RuntimeException("Tipo de producto inválido");
        }

        // Actualizar tiendas (dueñas reales)
        // Primero, obtener tiendas actuales para remover la relación con el producto
        Set<Tienda> tiendasActuales = producto.getTiendas() != null ? producto.getTiendas() : new HashSet<>();
        for (Tienda tienda : tiendasActuales) {
            tienda.getProductos().remove(producto);
            tiendaRepository.save(tienda);
        }

        // Validar y cargar nuevas tiendas del producto actualizado
        if (productoActualizado.getTiendas() == null || productoActualizado.getTiendas().isEmpty()) {
            throw new RuntimeException("Debe asignar al menos una tienda");
        }
        Set<Tienda> tiendasNuevas = productoActualizado.getTiendas().stream()
                .map(t -> tiendaRepository.findById(t.getIdTienda())
                        .orElseThrow(() -> new RuntimeException("Tienda no encontrada: " + t.getIdTienda())))
                .collect(Collectors.toSet());

        producto.setTiendas(tiendasNuevas);

        // Guardar producto para que tenga estado actualizado
        Producto productoGuardado = productoRepository.save(producto);

        // Asociar producto a las nuevas tiendas y guardar las tiendas
        for (Tienda tienda : tiendasNuevas) {
            tienda.getProductos().add(productoGuardado);
            tiendaRepository.save(tienda);
        }

        return productoGuardado;
    }



    public Producto actualizarStock(Long id, Integer nuevoStock) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setStock(nuevoStock);
        return productoRepository.save(producto);
    }

    // DELETE
    @Transactional
    public void eliminarProducto(Long idProducto, Long idUsuario) {
        validarPermisoJefeOAdmin(idUsuario);

        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Limpiar relaciones (como ya lo hacías antes)
        if (producto.getUsuarios() != null) {
            for (Usuario usuario : producto.getUsuarios()) {
                usuario.getProductos().remove(producto);
            }
            producto.getUsuarios().clear();
        }

        if (producto.getTiendas() != null) {
            for (Tienda tienda : producto.getTiendas()) {
                tienda.getProductos().remove(producto);
            }
            producto.getTiendas().clear();
        }

        if (producto.getListasDeDeseos() != null) {
            for (var lista : producto.getListasDeDeseos()) {
                lista.getProductos().remove(producto);
            }
            producto.getListasDeDeseos().clear();
        }

        productoRepository.save(producto); // persistir cambios
        productoRepository.delete(producto); // eliminar
    }



    // Métodos auxiliares
    public boolean existeProducto(Long id) {
        return productoRepository.existsById(id);
    }

    public boolean tieneStock(Long id, Integer cantidad) {
        Optional<Producto> producto = productoRepository.findById(id);
        return producto.isPresent() && producto.get().getStock() >= cantidad;
    }

    // Métodos de conteo para reportes
    public long countAll() {
        return productoRepository.count();
    }

    public long countByStockGreaterThan(Integer stock) {
        return productoRepository.countByStockGreaterThan(stock);
    }

    public long countByStock(Integer stock) {
        return productoRepository.countByStock(stock);
    }

    public List<Object[]> findProductosBajoStock(Integer limite) {
        return productoRepository.findProductosStockBajo(limite);
    }

    public List<Producto> obtenerPorRareza(String rareza) {
        return productoRepository.findByCategoriaCartaRareza(rareza);
    }

    public List<Producto> obtenerPorEstado(String estado) {
        return productoRepository.findByCategoriaCartaEstado(estado);
    }

    public List<Producto> obtenerPorAno(String ano) {
        return productoRepository.findByCategoriaCartaAno(ano);
    }

    public List<Producto> obtenerPorNombreCategoriaMesa(String nombre) {
        return productoRepository.findByCategoriaMesaNombre(nombre);
    }

    public List<Object[]> obtenerRankingCartasDeseadas() {
        return productoRepository.rankingCartasDeseadas();
    }

    private void validarPermisoJefeOAdmin(Long idUsuario) {
        Usuario usuario = usuarioService.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String rol = usuario.getRol().getNombreRol();
        if (!rol.equalsIgnoreCase("Jefe") && !rol.equalsIgnoreCase("Administrador")) {
            throw new RuntimeException("No tienes permisos para realizar esta acción");
        }
    }

    public Producto categorizarProducto(Long idProducto, String tipoProducto, Long idUsuario) {
        validarPermisoJefeOAdmin(idUsuario);

        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (!tipoProducto.equalsIgnoreCase("juego de mesa") && !tipoProducto.equalsIgnoreCase("carta")) {
            throw new RuntimeException("Tipo de producto inválido. Debe ser 'juego de mesa' o 'carta'.");
        }

        producto.setTipoProducto(tipoProducto);
        return productoRepository.save(producto);
    }
    public List<Producto> obtenerProductosPorUbicacionDeUsuario(Long idUsuario) {
        return productoRepository.findProductosPorComunaDelUsuario(idUsuario);
    }
}