package com.example.Tienda.service;

import com.example.Tienda.entity.Permisos;
import com.example.Tienda.repository.PermisosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PermisosService {

    @Autowired
    private PermisosRepository permisosRepository;

    // CREATE
    public Permisos crearPermiso(Permisos permiso) {
        return permisosRepository.save(permiso);
    }

    // READ
    public List<Permisos> obtenerTodosPermisos() {
        return permisosRepository.findAll();
    }

    public Optional<Permisos> obtenerPermisoPorId(Long id) {
        return permisosRepository.findById(id);
    }

    public Optional<Permisos> obtenerPermisoPorDescripcion(String descripcion) {
        return permisosRepository.findByDescripcionPermiso(descripcion);
    }

    public List<Permisos> obtenerPermisosPorRol(Long idRol) {
        return permisosRepository.findPermisosByRol(idRol);
    }

    public List<Permisos> obtenerPermisosNoAsignadosARol(Long idRol) {
        return permisosRepository.findPermisosNotInRol(idRol);
    }

    public List<Permisos> buscarPermisosPorDescripcion(String descripcion) {
        return permisosRepository.findByDescripcionPermisoContainingIgnoreCase(descripcion);
    }

    // UPDATE
    public Permisos actualizarPermiso(Long id, Permisos permisoActualizado) {
        Permisos permiso = permisosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permiso no encontrado"));

        permiso.setDescripcionPermiso(permisoActualizado.getDescripcionPermiso());
        return permisosRepository.save(permiso);
    }

    // DELETE
    public void eliminarPermiso(Long id) {
        if (!permisosRepository.existsById(id)) {
            throw new RuntimeException("Permiso no encontrado");
        }
        permisosRepository.deleteById(id);
    }

    // Métodos auxiliares
    public boolean existePermiso(Long id) {
        return permisosRepository.existsById(id);
    }

    public boolean existePermisoPorDescripcion(String descripcion) {
        return permisosRepository.existsByDescripcionPermiso(descripcion);
    }
}