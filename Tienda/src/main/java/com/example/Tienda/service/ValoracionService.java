package com.example.Tienda.service;

import com.example.Tienda.entity.Valoracion;
import com.example.Tienda.repository.ValoracionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ValoracionService {

    @Autowired
    private ValoracionRepository valoracionRepository;

    // CREATE
    public Valoracion crearValoracion(Valoracion valoracion) {
        // Validar que el usuario no haya valorado ya este producto
        if (valoracionRepository.existsByUsuarioIdUsuarioAndProductoIdProducto(
                valoracion.getUsuario().getIdUsuario(),
                valoracion.getProducto().getIdProducto())) {
            throw new RuntimeException("El usuario ya ha valorado este producto");
        }
        return valoracionRepository.save(valoracion);
    }

    // READ
    public List<Valoracion> obtenerTodasValoraciones() {
        return valoracionRepository.findAll();
    }

    public Optional<Valoracion> obtenerValoracionPorId(Long id) {
        return valoracionRepository.findById(id);
    }

    public List<Valoracion> obtenerValoracionesPorUsuario(Long idUsuario) {
        return valoracionRepository.findByUsuarioIdUsuario(idUsuario);
    }

    public List<Valoracion> obtenerValoracionesPorProducto(Long idProducto) {
        return valoracionRepository.findByProductoIdProducto(idProducto);
    }

    public List<Valoracion> obtenerValoracionesPorPuntuacion(Integer puntuacion) {
        return valoracionRepository.findByPuntuacion(puntuacion);
    }

    public Double obtenerPromedioValoracionProducto(Long idProducto) {
        return valoracionRepository.getPromedioValoracionByProducto(idProducto);
    }

    // UPDATE
    public Valoracion actualizarValoracion(Long id, Valoracion valoracionActualizada) {
        Valoracion valoracion = valoracionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Valoración no encontrada"));

        valoracion.setPuntuacion(valoracionActualizada.getPuntuacion());
        return valoracionRepository.save(valoracion);
    }

    // DELETE
    public void eliminarValoracion(Long id) {
        if (!valoracionRepository.existsById(id)) {
            throw new RuntimeException("Valoración no encontrada");
        }
        valoracionRepository.deleteById(id);
    }

    // Métodos auxiliares
    public boolean existeValoracion(Long id) {
        return valoracionRepository.existsById(id);
    }

    public boolean usuarioYaValoro(Long idUsuario, Long idProducto) {
        return valoracionRepository.existsByUsuarioIdUsuarioAndProductoIdProducto(idUsuario, idProducto);
    }

    // Método para obtener valoración específica de un usuario para un producto
    public Optional<Valoracion> obtenerValoracionUsuarioProducto(Long idUsuario, Long idProducto) {
        return valoracionRepository.findByUsuarioIdUsuarioAndProductoIdProducto(idUsuario, idProducto);
    }

    // Método para contar valoraciones por producto
    public Long contarValoracionesPorProducto(Long idProducto) {
        return valoracionRepository.countByProductoIdProducto(idProducto);
    }

    // Método para obtener productos mejor valorados
    public List<Object[]> obtenerProductosMejorValorados(Long minValoraciones) {
        return valoracionRepository.findProductosMejorValorados(minValoraciones);
    }
}