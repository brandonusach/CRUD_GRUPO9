package com.example.Tienda.service;

import com.example.Tienda.entity.Tienda;
import com.example.Tienda.repository.TiendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TiendaService {

    @Autowired
    private TiendaRepository tiendaRepository;

    // CREATE
    public Tienda crearTienda(Tienda tienda) {
        return tiendaRepository.save(tienda);
    }

    // READ
    public List<Tienda> obtenerTodasTiendas() {
        return tiendaRepository.findAll();
    }

    public Optional<Tienda> obtenerTiendaPorId(Long id) {
        return tiendaRepository.findById(id);
    }

    public List<Tienda> obtenerTiendasPorJefe(Long idJefe) {
        return tiendaRepository.findByJefeIdUsuario(idJefe);
    }

    public List<Tienda> obtenerTiendasPorNombre(String nombre) {
        return tiendaRepository.findByNombreTiendaContainingIgnoreCase(nombre);
    }

    public List<Object[]> obtenerTiendasConProductos() {
        return tiendaRepository.findTiendasConProductos();
    }

    // UPDATE
    public Tienda actualizarTienda(Long id, Tienda tiendaActualizada) {
        Tienda tienda = tiendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tienda no encontrada"));

        tienda.setNombreTienda(tiendaActualizada.getNombreTienda());
        tienda.setTelefono(tiendaActualizada.getTelefono());
        tienda.setEmail(tiendaActualizada.getEmail());
        tienda.setJefe(tiendaActualizada.getJefe());
        tienda.setDireccion(tiendaActualizada.getDireccion());

        return tiendaRepository.save(tienda);
    }

    // DELETE
    public void eliminarTienda(Long id) {
        if (!tiendaRepository.existsById(id)) {
            throw new RuntimeException("Tienda no encontrada");
        }
        tiendaRepository.deleteById(id);
    }

    // Métodos auxiliares
    public boolean existeTienda(Long id) {
        return tiendaRepository.existsById(id);
    }
}