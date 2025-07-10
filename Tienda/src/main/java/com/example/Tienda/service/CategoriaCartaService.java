package com.example.Tienda.service;

import com.example.Tienda.entity.CategoriaCarta;
import com.example.Tienda.repository.CategoriaCartaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoriaCartaService {

    @Autowired
    private CategoriaCartaRepository categoriaCartaRepository;

    // CREATE
    public CategoriaCarta crearCategoriaCarta(CategoriaCarta categoriaCarta) {
        return categoriaCartaRepository.save(categoriaCarta);
    }

    // READ
    public List<CategoriaCarta> obtenerTodasCategoriasCarta() {
        return categoriaCartaRepository.findAll();
    }

    public Optional<CategoriaCarta> obtenerCategoriaCartaPorId(Long id) {
        return categoriaCartaRepository.findById(id);
    }

    public List<CategoriaCarta> obtenerCategoriasCartaPorRareza(String rareza) {
        return categoriaCartaRepository.findByRareza(rareza);
    }

    public List<CategoriaCarta> obtenerCategoriasCartaPorEstado(String estado) {
        return categoriaCartaRepository.findByEstado(estado);
    }

    public List<CategoriaCarta> obtenerCategoriasCartaPorAno(String ano) {
        return categoriaCartaRepository.findByAno(ano);
    }

    // UPDATE
    public CategoriaCarta actualizarCategoriaCarta(Long id, CategoriaCarta categoriaActualizada) {
        CategoriaCarta categoria = categoriaCartaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría de carta no encontrada"));

        categoria.setRareza(categoriaActualizada.getRareza());
        categoria.setEstado(categoriaActualizada.getEstado());
        categoria.setAno(categoriaActualizada.getAno());

        return categoriaCartaRepository.save(categoria);
    }

    // DELETE
    public void eliminarCategoriaCarta(Long id) {
        if (!categoriaCartaRepository.existsById(id)) {
            throw new RuntimeException("Categoría de carta no encontrada");
        }
        categoriaCartaRepository.deleteById(id);
    }

    // Métodos auxiliares
    public boolean existeCategoriaCarta(Long id) {
        return categoriaCartaRepository.existsById(id);
    }
}
