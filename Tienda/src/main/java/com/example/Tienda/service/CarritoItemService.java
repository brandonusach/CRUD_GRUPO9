package com.example.Tienda.service;

import com.example.Tienda.entity.CarritoItem;
import com.example.Tienda.entity.CarritoItemId;
import com.example.Tienda.repository.CarritoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CarritoItemService {

    @Autowired
    private CarritoItemRepository carritoItemRepository;

    // CREATE
    public CarritoItem save(CarritoItem carritoItem) {
        return carritoItemRepository.save(carritoItem);
    }

    // READ
    public List<CarritoItem> findAll() {
        return carritoItemRepository.findAll();
    }

    public Optional<CarritoItem> findById(CarritoItemId id) {
        return carritoItemRepository.findById(id);
    }

    // Corregido: usar el método correcto del repositorio
    public List<CarritoItem> findByIdCarrito(Long idCarrito) {
        return carritoItemRepository.findByCarritoIdCarrito(idCarrito);
    }

    // Corregido: usar el método correcto del repositorio
    public Optional<CarritoItem> findByIdCarritoAndIdProducto(Long idCarrito, Long idProducto) {
        return carritoItemRepository.findByCarritoIdCarritoAndProductoIdProducto(idCarrito, idProducto);
    }

    // Corregido: usar el método correcto del repositorio
    public List<CarritoItem> findByIdProducto(Long idProducto) {
        return carritoItemRepository.findByProductoIdProducto(idProducto);
    }

    // UPDATE
    public CarritoItem update(CarritoItem carritoItem) {
        return carritoItemRepository.save(carritoItem);
    }

    public CarritoItem updateCantidad(Long idCarrito, Long idProducto, Integer nuevaCantidad) {
        Optional<CarritoItem> itemOptional = carritoItemRepository.findByCarritoIdCarritoAndProductoIdProducto(idCarrito, idProducto);

        if (itemOptional.isPresent()) {
            CarritoItem item = itemOptional.get();
            item.setCantidad(nuevaCantidad);
            return carritoItemRepository.save(item);
        } else {
            throw new RuntimeException("Item no encontrado en el carrito");
        }
    }

    // DELETE
    public void delete(CarritoItem carritoItem) {
        carritoItemRepository.delete(carritoItem);
    }

    public void deleteById(CarritoItemId id) {
        carritoItemRepository.deleteById(id);
    }

    // Corregido: implementar usando el método disponible
    public void deleteByIdCarritoAndIdProducto(Long idCarrito, Long idProducto) {
        Optional<CarritoItem> itemOptional = carritoItemRepository.findByCarritoIdCarritoAndProductoIdProducto(idCarrito, idProducto);
        if (itemOptional.isPresent()) {
            carritoItemRepository.delete(itemOptional.get());
        }
    }

    // Corregido: usar el método correcto del repositorio
    public void deleteByIdCarrito(Long idCarrito) {
        carritoItemRepository.deleteByCarritoIdCarrito(idCarrito);
    }

    // UTILITY METHODS
    public boolean existsByIdCarritoAndIdProducto(Long idCarrito, Long idProducto) {
        return carritoItemRepository.findByCarritoIdCarritoAndProductoIdProducto(idCarrito, idProducto).isPresent();
    }

    // Corregido: usar el método correcto del repositorio
    public Long countItemsInCarrito(Long idCarrito) {
        return carritoItemRepository.countItemsInCarrito(idCarrito);
    }

    // Corregido: usar el método correcto del repositorio
    public BigDecimal calculateCarritoTotal(Long idCarrito) {
        BigDecimal total = carritoItemRepository.calcularTotalCarrito(idCarrito);
        return total != null ? total : BigDecimal.ZERO;
    }

    // BUSINESS LOGIC
    public CarritoItem addOrUpdateItem(Long idCarrito, Long idProducto, Integer cantidad, BigDecimal precioUnitario) {
        Optional<CarritoItem> existingItem = carritoItemRepository.findByCarritoIdCarritoAndProductoIdProducto(idCarrito, idProducto);

        if (existingItem.isPresent()) {
            // Si ya existe, actualizar cantidad
            CarritoItem item = existingItem.get();
            item.setCantidad(item.getCantidad() + cantidad);
            return carritoItemRepository.save(item);
        } else {
            // Si no existe, crear nuevo item
            CarritoItem newItem = new CarritoItem();
            // Nota: Necesitarás setear las propiedades según tu modelo CarritoItem
            // newItem.setCarrito(carritoService.findById(idCarrito));
            // newItem.setProducto(productoService.findById(idProducto));
            newItem.setCantidad(cantidad);
            newItem.setPrecioUnitario(precioUnitario);
            return carritoItemRepository.save(newItem);
        }
    }

    public void removeItem(Long idCarrito, Long idProducto) {
        Optional<CarritoItem> itemOptional = carritoItemRepository.findByCarritoIdCarritoAndProductoIdProducto(idCarrito, idProducto);
        if (itemOptional.isPresent()) {
            carritoItemRepository.delete(itemOptional.get());
        } else {
            throw new RuntimeException("Item no encontrado en el carrito");
        }
    }

    public void clearCarrito(Long idCarrito) {
        carritoItemRepository.deleteByCarritoIdCarrito(idCarrito);
    }
}