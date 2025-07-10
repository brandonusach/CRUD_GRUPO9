package com.example.Tienda.service;

import com.example.Tienda.entity.Compra;
import com.example.Tienda.entity.CompraProducto;
import com.example.Tienda.entity.Producto;
import com.example.Tienda.repository.CompraRepository;
import com.example.Tienda.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private ProductoRepository productoRepository;

    // CREATE
    public Compra crearCompra(Compra compra) {
        return compraRepository.save(compra);
    }

    // READ
    public List<Compra> obtenerTodasCompras() {
        return compraRepository.findAll();
    }

    public Optional<Compra> obtenerCompraPorId(Long id) {
        return compraRepository.findById(id);
    }

    public List<Compra> obtenerComprasPorUsuario(Long idUsuario) {
        return compraRepository.findByUsuarioIdUsuario(idUsuario);
    }

    public List<Compra> obtenerComprasPorMetodoPago(Long idMetodoPago) {
        return compraRepository.findByMetodoPagoIdMetodoPago(idMetodoPago);
    }


    // UPDATE
    public Compra actualizarCompra(Long id, Compra compraActualizada) {
        Compra compra = compraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));

        compra.setMetodoPago(compraActualizada.getMetodoPago());
        compra.setCarrito(compraActualizada.getCarrito());
        compra.setBoleta(compraActualizada.getBoleta());

        return compraRepository.save(compra);
    }

    // DELETE
    public void eliminarCompra(Long id) {
        if (!compraRepository.existsById(id)) {
            throw new RuntimeException("Compra no encontrada");
        }
        compraRepository.deleteById(id);
    }

    // Métodos específicos para procesamiento de compras
    public Compra procesarCompra(Compra compra) {
        // Validar stock de productos
        for (CompraProducto cp : compra.getCompraProductos()) {
            Producto producto = productoRepository.findById(cp.getProducto().getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            if (producto.getStock() < cp.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            // Actualizar stock
            producto.setStock(producto.getStock() - cp.getCantidad());
            productoRepository.save(producto);
        }

        return compraRepository.save(compra);
    }

    // Métodos auxiliares
    public boolean existeCompra(Long id) {
        return compraRepository.existsById(id);
    }

    public BigDecimal calcularTotalCompra(Long idCompra) {
        return compraRepository.calcularTotalCompra(idCompra);
    }
}
