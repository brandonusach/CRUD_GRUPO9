package com.example.Tienda.repository;

import com.example.Tienda.entity.Tienda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TiendaRepository extends JpaRepository<Tienda, Long> {

    // Buscar tienda por nombre
    Optional<Tienda> findByNombreTienda(String nombreTienda);

    // Verificar si existe una tienda con cierto nombre
    boolean existsByNombreTienda(String nombreTienda);

    // Buscar tiendas por jefe
    List<Tienda> findByJefeIdUsuario(Long idJefe);

    // Buscar tiendas por email
    Optional<Tienda> findByEmail(String email);

    // Buscar tiendas por teléfono
    Optional<Tienda> findByTelefono(String telefono);

    // MÉTODO AGREGADO: Buscar tiendas por nombre (contiene, ignora mayúsculas/minúsculas)
    List<Tienda> findByNombreTiendaContainingIgnoreCase(String nombre);

    // Buscar tiendas por comuna (a través de la dirección)
    @Query("SELECT t FROM Tienda t JOIN t.direccion d WHERE d.comuna = :comuna")
    List<Tienda> findByDireccionComuna(@Param("comuna") String comuna);

    // Buscar tiendas por ciudad (a través de la dirección)
    @Query("SELECT t FROM Tienda t JOIN t.direccion d WHERE d.ciudad = :ciudad")
    List<Tienda> findByDireccionCiudad(@Param("ciudad") String ciudad);

    // Buscar tiendas por región (a través de la dirección)
    @Query("SELECT t FROM Tienda t JOIN t.direccion d WHERE d.region = :region")
    List<Tienda> findByDireccionRegion(@Param("region") String region);

    // Obtener tienda con todos sus datos relacionados
    @Query("SELECT t FROM Tienda t " +
            "LEFT JOIN FETCH t.jefe " +
            "LEFT JOIN FETCH t.direccion " +
            "WHERE t.idTienda = :idTienda")
    Optional<Tienda> findByIdWithAllData(@Param("idTienda") Long idTienda);

    // Obtener tienda con sus productos
    @Query("SELECT t FROM Tienda t LEFT JOIN FETCH t.productos WHERE t.idTienda = :idTienda")
    Optional<Tienda> findByIdWithProductos(@Param("idTienda") Long idTienda);

    // Obtener tiendas con stock de un producto específico
    @Query("SELECT t FROM Tienda t JOIN t.productos p WHERE p.idProducto = :idProducto")
    List<Tienda> findTiendasConProducto(@Param("idProducto") Long idProducto);

    // MÉTODO AGREGADO: Obtener tiendas con sus productos (para mostrar información resumida)
    @Query("SELECT t.idTienda, t.nombreTienda, COUNT(p) as cantidadProductos " +
            "FROM Tienda t LEFT JOIN t.productos p " +
            "GROUP BY t.idTienda, t.nombreTienda")
    List<Object[]> findTiendasConProductos();
}