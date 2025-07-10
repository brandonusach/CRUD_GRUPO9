package com.example.Tienda.service;

import com.example.Tienda.entity.Rol;
import com.example.Tienda.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    // CREATE
    public Rol crearRol(Rol rol) {
        return rolRepository.save(rol);
    }

    // READ
    public List<Rol> obtenerTodosRoles() {
        return rolRepository.findAll();
    }

    public Optional<Rol> obtenerRolPorId(Long id) {
        return rolRepository.findById(id);
    }

    public Optional<Rol> obtenerRolPorNombre(String nombre) {
        return rolRepository.findByNombreRol(nombre);
    }

    // UPDATE
    public Rol actualizarRol(Long id, Rol rolActualizado) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        rol.setNombreRol(rolActualizado.getNombreRol());
        return rolRepository.save(rol);
    }

    // DELETE
    public void eliminarRol(Long id) {
        if (!rolRepository.existsById(id)) {
            throw new RuntimeException("Rol no encontrado");
        }
        rolRepository.deleteById(id);
    }

    // Métodos auxiliares
    public boolean existeRol(Long id) {
        return rolRepository.existsById(id);
    }

    public boolean existeRolPorNombre(String nombre) {
        return rolRepository.existsByNombreRol(nombre);
    }
}
