package com.example.Tienda.service;

import com.example.Tienda.entity.Carrito;
import com.example.Tienda.entity.CarritoItem;
import com.example.Tienda.entity.Producto;
import com.example.Tienda.repository.CarritoRepository;
import com.example.Tienda.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    // CREATE
    public Carrito save(Carrito carrito) {
        return carritoRepository.save(carrito);
    }

    public Carrito crearCarrito(Carrito carrito) {
        return carritoRepository.save(carrito);
    }

    // READ
    public List<Carrito> findAll() {
        return carritoRepository.findAll();
    }

    public Optional<Carrito> findById(Long id) {
        return carritoRepository.findById(id);
    }

    public List<Carrito> obtenerTodosCarritos() {
        return carritoRepository.findAll();
    }

    public Optional<Carrito> obtenerCarritoPorId(Long id) {
        return carritoRepository.findById(id);
    }

    public Optional<Carrito> findByIdUsuarioAndEstado(Long idUsuario, String estado) {
        return carritoRepository.findByUsuarioIdUsuarioAndEstado(idUsuario, estado);
    }

    public Optional<Carrito> obtenerCarritoActivoPorUsuario(Long idUsuario) {
        return carritoRepository.findByUsuarioIdUsuarioAndEstado(idUsuario, "activo");
    }

    public List<Carrito> obtenerCarritosPorUsuario(Long idUsuario) {
        return carritoRepository.findByUsuarioIdUsuario(idUsuario);
    }

    public List<Carrito> obtenerCarritosPorEstado(String estado) {
        return carritoRepository.findByEstado(estado);
    }

    public List<Carrito> obtenerCarritosAbandonados() {
        return carritoRepository.findCarritosAbandonados();
    }

    // UPDATE
    public Carrito update(Carrito carrito) {
        return carritoRepository.save(carrito);
    }

    public Carrito actualizarCarrito(Long id, Carrito carritoActualizado) {
        Carrito carrito = carritoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        carrito.setEstado(carritoActualizado.getEstado());
        carrito.setTotalEstimado(carritoActualizado.getTotalEstimado());

        return carritoRepository.save(carrito);
    }

    public Carrito procesarCarrito(Long id) {
        Carrito carrito = carritoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        carrito.setEstado("procesado");
        return carritoRepository.save(carrito);
    }

    // DELETE
    public void delete(Carrito carrito) {
        carritoRepository.delete(carrito);
    }

    public void deleteById(Long id) {
        carritoRepository.deleteById(id);
    }

    public void eliminarCarrito(Long id) {
        if (!carritoRepository.existsById(id)) {
            throw new RuntimeException("Carrito no encontrado");
        }
        carritoRepository.deleteById(id);
    }

    // UTILITY METHODS
    public boolean existsById(Long id) {
        return carritoRepository.existsById(id);
    }


    private void actualizarTotalCarrito(Carrito carrito) {
        BigDecimal total = carrito.getCarritoItems().stream()
                .map(item -> item.getPrecioUnitario().multiply(BigDecimal.valueOf(item.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        carrito.setTotalEstimado(total);
    }

    @Transactional
    public Carrito agregarProductoAlCarrito(Long carritoId, Long productoId, Integer cantidad) {
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (producto.getStock() < cantidad) {
            throw new RuntimeException("Stock insuficiente");
        }

        Optional<CarritoItem> itemExistente = carrito.getCarritoItems().stream()
                .filter(item -> item.getProducto().getIdProducto().equals(productoId))
                .findFirst();

        if (itemExistente.isPresent()) {
            CarritoItem item = itemExistente.get();
            item.setCantidad(item.getCantidad() + cantidad);
        } else {
            CarritoItem nuevoItem = new CarritoItem();
            nuevoItem.setCarrito(carrito);
            nuevoItem.setProducto(producto);
            nuevoItem.setCantidad(cantidad);
            nuevoItem.setPrecioUnitario(producto.getPrecio());
            // Aquí setea también el id embebido:
            nuevoItem.setIdCarrito(carrito.getIdCarrito());
            nuevoItem.setIdProducto(producto.getIdProducto());

            carrito.getCarritoItems().add(nuevoItem);
        }

        actualizarTotalCarrito(carrito);

        return carritoRepository.save(carrito);
    }

}