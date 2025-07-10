package com.example.Tienda.service;

import com.example.Tienda.entity.CategoriaMesa;
import com.example.Tienda.repository.CategoriaMesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoriaMesaService {

    @Autowired
    private CategoriaMesaRepository categoriaMesaRepository;

    // CREATE
    public CategoriaMesa crearCategoriaMesa(CategoriaMesa categoriaMesa) {
        return categoriaMesaRepository.save(categoriaMesa);
    }

    // READ
    public List<CategoriaMesa> obtenerTodasCategoriasMesa() {
        return categoriaMesaRepository.findAll();
    }

    public Optional<CategoriaMesa> obtenerCategoriaMesaPorId(Long id) {
        return categoriaMesaRepository.findById(id);
    }

    public List<CategoriaMesa> obtenerCategoriasMesaPorNombre(String nombre) {
        return categoriaMesaRepository.findByNombreContainingIgnoreCase(nombre);
    }

    // UPDATE
    public CategoriaMesa actualizarCategoriaMesa(Long id, CategoriaMesa categoriaActualizada) {
        CategoriaMesa categoria = categoriaMesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría de mesa no encontrada"));

        categoria.setNombre(categoriaActualizada.getNombre());
        return categoriaMesaRepository.save(categoria);
    }

    // DELETE
    public void eliminarCategoriaMesa(Long id) {
        if (!categoriaMesaRepository.existsById(id)) {
            throw new RuntimeException("Categoría de mesa no encontrada");
        }
        categoriaMesaRepository.deleteById(id);
    }

    // Métodos auxiliares
    public boolean existeCategoriaMesa(Long id) {
        return categoriaMesaRepository.existsById(id);
    }
}
