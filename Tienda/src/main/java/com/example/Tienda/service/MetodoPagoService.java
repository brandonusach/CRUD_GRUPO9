package com.example.Tienda.service;

import com.example.Tienda.entity.MetodoPago;
import com.example.Tienda.repository.MetodoPagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MetodoPagoService {

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    // CREATE
    public MetodoPago crearMetodoPago(MetodoPago metodoPago) {
        return metodoPagoRepository.save(metodoPago);
    }

    // READ
    public List<MetodoPago> obtenerTodosMetodosPago() {
        return metodoPagoRepository.findAll();
    }

    public Optional<MetodoPago> obtenerMetodoPagoPorId(Long id) {
        return metodoPagoRepository.findById(id);
    }

    public List<MetodoPago> obtenerMetodosPagoPorUsuario(Long idUsuario) {
        return metodoPagoRepository.findByUsuarioIdUsuario(idUsuario);
    }

    public List<MetodoPago> obtenerMetodosPagoPorTipo(String tipo) {
        return metodoPagoRepository.findByTipoPago(tipo);
    }

    // UPDATE - CORREGIDO
    public MetodoPago actualizarMetodoPago(Long id, MetodoPago metodoPagoActualizado) {
        MetodoPago metodoPago = metodoPagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));

        // ✅ Usar getTipoDePago() en lugar de getTipoPago()
        metodoPago.setTipoDePago(metodoPagoActualizado.getTipoDePago());
        metodoPago.setDetalle(metodoPagoActualizado.getDetalle());
        metodoPago.setTitular(metodoPagoActualizado.getTitular());
        // ✅ Usar getFechaDeVencimiento() en lugar de getFechaVencimiento()
        metodoPago.setFechaDeVencimiento(metodoPagoActualizado.getFechaDeVencimiento());

        return metodoPagoRepository.save(metodoPago);
    }

    // DELETE
    public void eliminarMetodoPago(Long id) {
        if (!metodoPagoRepository.existsById(id)) {
            throw new RuntimeException("Método de pago no encontrado");
        }
        metodoPagoRepository.deleteById(id);
    }

    // Métodos auxiliares
    public boolean existeMetodoPago(Long id) {
        return metodoPagoRepository.existsById(id);
    }
}