package com.example.Tienda.repository;

import com.example.Tienda.entity.ListaDeseos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ListaDeseosRepository extends JpaRepository<ListaDeseos, Long> {

    // Buscar listas de deseos por usuario
    List<ListaDeseos> findByUsuarioIdUsuario(Long idUsuario);

    // Buscar listas de deseos por nombre
    List<ListaDeseos> findByNombresContainingIgnoreCase(String nombres);

    // Buscar lista de deseos específica por usuario y nombre
    Optional<ListaDeseos> findByUsuarioIdUsuarioAndNombres(Long idUsuario, String nombres);

    // Verificar si existe una lista con cierto nombre para un usuario
    boolean existsByUsuarioIdUsuarioAndNombres(Long idUsuario, String nombres);

    // Obtener lista de deseos con sus productos
    @Query("SELECT l FROM ListaDeseos l LEFT JOIN FETCH l.productos WHERE l.idDeseo = :idDeseo")
    Optional<ListaDeseos> findByIdWithProductos(@Param("idDeseo") Long idDeseo);

    // Obtener listas de deseos de un usuario con sus productos
    @Query("SELECT l FROM ListaDeseos l LEFT JOIN FETCH l.productos WHERE l.usuario.idUsuario = :idUsuario")
    List<ListaDeseos> findByUsuarioIdUsuarioWithProductos(@Param("idUsuario") Long idUsuario);

    // Contar productos en una lista de deseos
    @Query("SELECT COUNT(p) FROM ListaDeseos l JOIN l.productos p WHERE l.idDeseo = :idDeseo")
    Long countProductosInLista(@Param("idDeseo") Long idDeseo);

    // Obtener productos en una lista específica - MÉTODO AGREGADO
    @Query("SELECT p.idProducto, p.nombre, p.precio, p.descripcion, p.urlImagen " +
            "FROM ListaDeseos l JOIN l.productos p WHERE l.idDeseo = :idListaDeseos")
    List<Object[]> findProductosEnListaDeseos(@Param("idListaDeseos") Long idListaDeseos);

    // Agregar producto a lista de deseos - MÉTODO AGREGADO
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO lista_producto (id_deseo, id_producto) VALUES (:idListaDeseos, :idProducto)",
            nativeQuery = true)
    void agregarProductoAListaDeseos(@Param("idListaDeseos") Long idListaDeseos, @Param("idProducto") Long idProducto);

    // Remover producto de lista de deseos - MÉTODO AGREGADO
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM lista_producto WHERE id_deseo = :idListaDeseos AND id_producto = :idProducto",
            nativeQuery = true)
    void removerProductoDeListaDeseos(@Param("idListaDeseos") Long idListaDeseos, @Param("idProducto") Long idProducto);

    // Verificar si un producto está en una lista - MÉTODO ADICIONAL ÚTIL
    @Query(value = "SELECT COUNT(*) > 0 FROM lista_producto WHERE id_deseo = :idListaDeseos AND id_producto = :idProducto",
            nativeQuery = true)
    boolean existeProductoEnLista(@Param("idListaDeseos") Long idListaDeseos, @Param("idProducto") Long idProducto);

}