package com.example.Tienda.repository;

import com.example.Tienda.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Buscar usuario por email
    Optional<Usuario> findByEmail(String email);

    // Verificar si existe un usuario con cierto email
    boolean existsByEmail(String email);

    // Buscar usuarios por rol
    @Query("SELECT u FROM Usuario u WHERE u.rol.idRol = :idRol")
    List<Usuario> findByRolId(@Param("idRol") Long idRol);

    // Buscar usuarios por nombre o apellido (búsqueda parcial)
    @Query("SELECT u FROM Usuario u WHERE LOWER(u.nombre) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(u.apellido) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Usuario> findByNombreOrApellidoContainingIgnoreCase(@Param("search") String search);

    // Obtener usuario con su rol
    @Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.rol WHERE u.idUsuario = :idUsuario")
    Optional<Usuario> findByIdWithRol(@Param("idUsuario") Long idUsuario);

    // Obtener usuarios que son jefes de tienda
    @Query("SELECT DISTINCT u FROM Usuario u JOIN u.rol r WHERE r.nombreRol = 'JEFE'")
    List<Usuario> findJefes();

    // Usuarios más activos
    @Query("SELECT u.idUsuario, u.nombre, COUNT(b) as totalCompras " +
            "FROM Usuario u JOIN u.boletas b " +
            "GROUP BY u.idUsuario, u.nombre " +
            "ORDER BY totalCompras DESC")
    List<Object[]> findUsuariosMasActivos();

}

