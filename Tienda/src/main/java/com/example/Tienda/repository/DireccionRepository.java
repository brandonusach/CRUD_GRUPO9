package com.example.Tienda.repository;

import com.example.Tienda.entity.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Long> {


    // Buscar direcciones por comuna
    List<Direccion> findByComuna(String comuna);

    // Buscar direcciones por ciudad
    List<Direccion> findByCiudad(String ciudad);

    // Buscar direcciones por región
    List<Direccion> findByRegion(String region);

    // Buscar direcciones por comuna y ciudad
    List<Direccion> findByComunaAndCiudad(String comuna, String ciudad);

    // Buscar direcciones completas
    @Query("SELECT d FROM Direccion d WHERE d.calle = :calle AND d.numero = :numero AND d.comuna = :comuna")
    List<Direccion> findByCalleAndNumeroAndComuna(@Param("calle") String calle,
                                                  @Param("numero") Integer numero,
                                                  @Param("comuna") String comuna);
}