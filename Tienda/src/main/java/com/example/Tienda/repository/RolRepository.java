package com.example.Tienda.repository;

import com.example.Tienda.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    // Buscar rol por nombre
    Optional<Rol> findByNombreRol(String nombreRol);

    // Verificar si existe un rol con cierto nombre
    boolean existsByNombreRol(String nombreRol);

    // Consulta personalizada para obtener roles con sus permisos
    @Query("SELECT r FROM Rol r LEFT JOIN FETCH r.permisos WHERE r.idRol = :idRol")
    Optional<Rol> findByIdWithPermisos(Long idRol);
}

