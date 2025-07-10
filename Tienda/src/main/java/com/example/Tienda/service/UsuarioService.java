package com.example.Tienda.service;

import com.example.Tienda.entity.Usuario;
import com.example.Tienda.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // CREATE
    public Usuario crearUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con este email");
        }
        return usuarioRepository.save(usuario);
    }

    // Método save para compatibilidad con controlador
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // READ
    public List<Usuario> obtenerTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    // Método findAll para compatibilidad con controlador
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Método findById para compatibilidad con controlador
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> obtenerUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // Método findByEmail para compatibilidad con controlador
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public List<Usuario> buscarUsuarios(String search) {
        return usuarioRepository.findByNombreOrApellidoContainingIgnoreCase(search);
    }

    public List<Usuario> obtenerUsuariosPorRol(Long idRol) {
        return usuarioRepository.findByRolId(idRol);
    }

    // Método findByIdRol para compatibilidad con controlador
    public List<Usuario> findByIdRol(Long idRol) {
        return usuarioRepository.findByRolId(idRol);
    }

    public List<Usuario> obtenerJefes() {
        return usuarioRepository.findJefes();
    }

    // UPDATE
    public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombre(usuarioActualizado.getNombre());
        usuario.setApellido(usuarioActualizado.getApellido());
        usuario.setEmail(usuarioActualizado.getEmail());
        if (usuarioActualizado.getContrasena() != null) {
            usuario.setContrasena(usuarioActualizado.getContrasena());
        }
        if (usuarioActualizado.getRol() != null) {
            usuario.setRol(usuarioActualizado.getRol());
        }

        return usuarioRepository.save(usuario);
    }

    // DELETE
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    // Método deleteById para compatibilidad con controlador
    public void deleteById(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    // Métodos auxiliares
    public boolean existeUsuario(Long id) {
        return usuarioRepository.existsById(id);
    }

    public boolean existeEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}
