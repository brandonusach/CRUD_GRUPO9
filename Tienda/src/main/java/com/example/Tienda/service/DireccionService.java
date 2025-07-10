package com.example.Tienda.service;

import com.example.Tienda.entity.Direccion;
import com.example.Tienda.repository.DireccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DireccionService {

    @Autowired
    private DireccionRepository direccionRepository;

    // CREATE
    public Direccion save(Direccion direccion) {
        return direccionRepository.save(direccion);
    }

    public Direccion crearDireccion(Direccion direccion) {
        return direccionRepository.save(direccion);
    }

    // READ
    public List<Direccion> findAll() {
        return direccionRepository.findAll();
    }

    public List<Direccion> obtenerTodasDirecciones() {
        return direccionRepository.findAll();
    }

    public Optional<Direccion> findById(Long id) {
        return direccionRepository.findById(id);
    }

    public Optional<Direccion> obtenerDireccionPorId(Long id) {
        return direccionRepository.findById(id);
    }


    public List<Direccion> obtenerDireccionesPorComuna(String comuna) {
        return direccionRepository.findByComuna(comuna);
    }

    public List<Direccion> findByCiudad(String ciudad) {
        return direccionRepository.findByCiudad(ciudad);
    }

    public List<Direccion> obtenerDireccionesPorCiudad(String ciudad) {
        return direccionRepository.findByCiudad(ciudad);
    }

    public List<Direccion> findByRegion(String region) {
        return direccionRepository.findByRegion(region);
    }

    public List<Direccion> obtenerDireccionesPorRegion(String region) {
        return direccionRepository.findByRegion(region);
    }

    // UPDATE
    public Direccion actualizarDireccion(Long id, Direccion direccionActualizada) {
        Direccion direccion = direccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dirección no encontrada"));

        direccion.setCalle(direccionActualizada.getCalle());
        direccion.setNumero(direccionActualizada.getNumero());
        direccion.setComuna(direccionActualizada.getComuna());
        direccion.setCiudad(direccionActualizada.getCiudad());
        direccion.setRegion(direccionActualizada.getRegion());

        return direccionRepository.save(direccion);
    }

    // DELETE
    public void deleteById(Long id) {
        if (!direccionRepository.existsById(id)) {
            throw new RuntimeException("Dirección no encontrada");
        }
        direccionRepository.deleteById(id);
    }

    public void eliminarDireccion(Long id) {
        if (!direccionRepository.existsById(id)) {
            throw new RuntimeException("Dirección no encontrada");
        }
        direccionRepository.deleteById(id);
    }

    // Métodos auxiliares
    public boolean existeDireccion(Long id) {
        return direccionRepository.existsById(id);
    }
}