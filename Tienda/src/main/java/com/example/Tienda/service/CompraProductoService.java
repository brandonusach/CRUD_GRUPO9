package com.example.Tienda.service;

import com.example.Tienda.entity.CompraProducto;
import com.example.Tienda.repository.CompraProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CompraProductoService {

    @Autowired
    private CompraProductoRepository compraProductoRepository;

    // CREATE
    public CompraProducto crearCompraProducto(CompraProducto compraProducto) {
        return compraProductoRepository.save(compraProducto);
    }

    // READ
    public List<CompraProducto> obtenerTodosCompraProductos() {
        return compraProductoRepository.findAll();
    }

    public Optional<CompraProducto> obtenerCompraProductoPorId(Long idCompra, Long idProducto) {
        return compraProductoRepository.findById(new CompraProducto.CompraProductoId(idCompra, idProducto));
    }

    public List<CompraProducto> obtenerCompraProductosPorCompra(Long idCompra) {
        return compraProductoRepository.findByCompraIdCompra(idCompra);
    }

    public List<CompraProducto> obtenerCompraProductosPorProducto(Long idProducto) {
        return compraProductoRepository.findByProductoIdProducto(idProducto);
    }

    // UPDATE
    public CompraProducto actualizarCompraProducto(Long idCompra, Long idProducto, CompraProducto compraProductoActualizado) {
        CompraProducto compraProducto = compraProductoRepository.findById(new CompraProducto.CompraProductoId(idCompra, idProducto))
                .orElseThrow(() -> new RuntimeException("CompraProducto no encontrado"));

        compraProducto.setCantidad(compraProductoActualizado.getCantidad());
        compraProducto.setPrecioUnitario(compraProductoActualizado.getPrecioUnitario());
        compraProducto.setSubtotal(compraProductoActualizado.getSubtotal());

        return compraProductoRepository.save(compraProducto);
    }

    // DELETE
    public void eliminarCompraProducto(Long idCompra, Long idProducto) {
        CompraProducto.CompraProductoId id = new CompraProducto.CompraProductoId(idCompra, idProducto);
        if (!compraProductoRepository.existsById(id)) {
            throw new RuntimeException("CompraProducto no encontrado");
        }
        compraProductoRepository.deleteById(id);
    }

    // Métodos auxiliares
    public boolean existeCompraProducto(Long idCompra, Long idProducto) {
        return compraProductoRepository.existsById(new CompraProducto.CompraProductoId(idCompra, idProducto));
    }

    public List<CompraProducto> crearCompraProductosDesdeCarrito(Long idCompra, Long idCarrito) {
        return compraProductoRepository.crearCompraProductosDesdeCarrito(idCompra, idCarrito);
    }

    public List<Object[]> obtenerProductosCompradosPorRegion(String region) {
        return compraProductoRepository.findProductosCompradosPorRegion(region);
    }
}