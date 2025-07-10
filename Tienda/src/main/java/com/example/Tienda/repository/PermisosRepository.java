package com.example.Tienda.repository;

import com.example.Tienda.entity.Permisos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermisosRepository extends JpaRepository<Permisos, Long> {

    // Buscar permiso por descripción
    Optional<Permisos> findByDescripcionPermiso(String descripcion);

    // Verificar si existe un permiso con cierta descripción
    boolean existsByDescripcionPermiso(String descripcion);

    // Buscar permisos por descripción parcial
    List<Permisos> findByDescripcionPermisoContainingIgnoreCase(String descripcion);

    // Obtener permisos de un rol específico
    @Query("SELECT p FROM Permisos p JOIN p.roles r WHERE r.idRol = :idRol")
    List<Permisos> findPermisosByRol(@Param("idRol") Long idRol);

    // Obtener permisos no asignados a un rol específico
    @Query("SELECT p FROM Permisos p WHERE p.idPermiso NOT IN " +
            "(SELECT p2.idPermiso FROM Permisos p2 JOIN p2.roles r WHERE r.idRol = :idRol)")
    List<Permisos> findPermisosNotInRol(@Param("idRol") Long idRol);
}

