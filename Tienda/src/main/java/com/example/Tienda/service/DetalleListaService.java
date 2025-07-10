package com.example.Tienda.service;

import com.example.Tienda.entity.DetalleLista;
import com.example.Tienda.entity.DetalleListaId;
import com.example.Tienda.repository.DetalleListaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DetalleListaService {

    @Autowired
    private DetalleListaRepository detalleListaRepository;

    // Guardar un nuevo detalle de lista
    public DetalleLista save(DetalleLista detalleLista) {
        return detalleListaRepository.save(detalleLista);
    }

    // Buscar todos los detalles de lista
    public List<DetalleLista> findAll() {
        return detalleListaRepository.findAll();
    }

    // Buscar por ID compuesto
    public Optional<DetalleLista> findById(DetalleListaId id) {
        return detalleListaRepository.findById(id);
    }

    // Buscar por ID de lista
    public List<DetalleLista> findByIdLista(Long idLista) {
        return detalleListaRepository.findByIdLista(idLista);
    }

    // Verificar si existe un producto en una lista
    public boolean existsByIdListaAndIdProducto(Long idLista, Long idProducto) {
        return detalleListaRepository.existsByIdListaAndIdProducto(idLista, idProducto);
    }

    // Eliminar por ID compuesto
    public void deleteById(DetalleListaId id) {
        detalleListaRepository.deleteById(id);
    }

    // Eliminar por ID simple (usando los IDs por separado)
    public void deleteById(Long id) {
        // Este método necesita ser implementado de acuerdo a tu lógica de negocio
        // Por ejemplo, si el ID es una concatenación de idLista e idProducto
        // O si tienes otra forma de identificar el registro
        throw new UnsupportedOperationException("Método no implementado para ID simple");
    }

    // Eliminar producto específico de lista específica
    public void deleteByIdListaAndIdProducto(Long idLista, Long idProducto) {
        detalleListaRepository.deleteByIdListaAndIdProducto(idLista, idProducto);
    }


    // Contar productos en una lista
    public Long countByIdLista(Long idLista) {
        return detalleListaRepository.countByIdLista(idLista);
    }

    // Agregar producto a lista de deseos
    public DetalleLista agregarProductoALista(Long idLista, Long idProducto) {
        // Verificar si ya existe
        if (existsByIdListaAndIdProducto(idLista, idProducto)) {
            throw new IllegalArgumentException("El producto ya está en la lista de deseos");
        }

        DetalleLista detalleLista = new DetalleLista(idLista, idProducto);
        return save(detalleLista);
    }

    // Eliminar producto de lista de deseos
    public void eliminarProductoDeLista(Long idLista, Long idProducto) {
        if (!existsByIdListaAndIdProducto(idLista, idProducto)) {
            throw new IllegalArgumentException("El producto no está en la lista de deseos");
        }

        deleteByIdListaAndIdProducto(idLista, idProducto);
    }
}