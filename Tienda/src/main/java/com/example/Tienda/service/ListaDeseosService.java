package com.example.Tienda.service;

import com.example.Tienda.entity.ListaDeseos;
import com.example.Tienda.entity.Producto;
import com.example.Tienda.repository.ListaDeseosRepository;
import com.example.Tienda.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ListaDeseosService {

    @Autowired
    private ListaDeseosRepository listaDeseosRepository;

    @Autowired
    private ProductoRepository productoRepository; // Necesario para validar productos

    // CREATE
    public ListaDeseos crearListaDeseos(ListaDeseos listaDeseos) {
        return listaDeseosRepository.save(listaDeseos);
    }

    // READ
    public List<ListaDeseos> obtenerTodasListasDeseos() {
        return listaDeseosRepository.findAll();
    }

    public Optional<ListaDeseos> obtenerListaDeseosPorId(Long id) {
        return listaDeseosRepository.findById(id);
    }

    public List<ListaDeseos> obtenerListasDeseosPorUsuario(Long idUsuario) {
        return listaDeseosRepository.findByUsuarioIdUsuario(idUsuario);
    }

    public List<ListaDeseos> obtenerListasDeseosPorNombre(String nombre) {
        return listaDeseosRepository.findByNombresContainingIgnoreCase(nombre);
    }

    public List<Object[]> obtenerProductosEnListaDeseos(Long idListaDeseos) {
        return listaDeseosRepository.findProductosEnListaDeseos(idListaDeseos);
    }

    // UPDATE
    public ListaDeseos actualizarListaDeseos(Long id, ListaDeseos listaDeseosActualizada) {
        ListaDeseos listaDeseos = listaDeseosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lista de deseos no encontrada"));

        listaDeseos.setNombres(listaDeseosActualizada.getNombres());
        return listaDeseosRepository.save(listaDeseos);
    }

    // DELETE
    public void eliminarListaDeseos(Long id) {
        if (!listaDeseosRepository.existsById(id)) {
            throw new RuntimeException("Lista de deseos no encontrada");
        }
        listaDeseosRepository.deleteById(id);
    }

    // Métodos para manejar productos en la lista
    public ListaDeseos agregarProductoAListaDeseos(Long idListaDeseos, Long idProducto) {
        // Validar que la lista existe
        ListaDeseos listaDeseos = listaDeseosRepository.findById(idListaDeseos)
                .orElseThrow(() -> new RuntimeException("Lista de deseos no encontrada"));

        // Validar que el producto existe
        if (!productoRepository.existsById(idProducto)) {
            throw new RuntimeException("Producto no encontrado");
        }

        // Verificar si el producto ya está en la lista
        if (listaDeseosRepository.existeProductoEnLista(idListaDeseos, idProducto)) {
            throw new RuntimeException("El producto ya está en la lista de deseos");
        }

        // Agregar el producto a la lista
        listaDeseosRepository.agregarProductoAListaDeseos(idListaDeseos, idProducto);

        // Retornar la lista actualizada con productos
        return listaDeseosRepository.findByIdWithProductos(idListaDeseos)
                .orElseThrow(() -> new RuntimeException("Error al obtener la lista actualizada"));
    }

    public void removerProductoDeListaDeseos(Long idListaDeseos, Long idProducto) {
        // Validar que la lista existe
        if (!listaDeseosRepository.existsById(idListaDeseos)) {
            throw new RuntimeException("Lista de deseos no encontrada");
        }

        // Validar que el producto existe en la lista
        if (!listaDeseosRepository.existeProductoEnLista(idListaDeseos, idProducto)) {
            throw new RuntimeException("El producto no está en la lista de deseos");
        }

        // Remover el producto de la lista
        listaDeseosRepository.removerProductoDeListaDeseos(idListaDeseos, idProducto);
    }

    // Métodos auxiliares
    public boolean existeListaDeseos(Long id) {
        return listaDeseosRepository.existsById(id);
    }

    public boolean existeProductoEnLista(Long idListaDeseos, Long idProducto) {
        return listaDeseosRepository.existeProductoEnLista(idListaDeseos, idProducto);
    }

    public Long contarProductosEnLista(Long idListaDeseos) {
        return listaDeseosRepository.countProductosInLista(idListaDeseos);
    }

    public List<ListaDeseos> obtenerListasConProductos(Long idUsuario) {
        return listaDeseosRepository.findByUsuarioIdUsuarioWithProductos(idUsuario);
    }

    public ListaDeseos agregarProducto(Long idLista, Long idProducto) {
        ListaDeseos listaDeseos = listaDeseosRepository.findById(idLista)
                .orElseThrow(() -> new RuntimeException("Lista de deseos no encontrada"));

        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (listaDeseos.getProductos() == null) {
            listaDeseos.setProductos(new HashSet<>());
        }

        listaDeseos.getProductos().add(producto);

        return listaDeseosRepository.save(listaDeseos);
    }

}
