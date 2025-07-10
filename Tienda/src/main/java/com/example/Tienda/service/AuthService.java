package com.example.Tienda.service;

import com.example.Tienda.entity.Usuario;
import com.example.Tienda.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Autenticación básica
    public Optional<Usuario> autenticar(String email, String contrasena) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

        if (usuario.isPresent() && verificarContrasena(contrasena, usuario.get().getContrasena())) {
            return usuario;
        }

        return Optional.empty();
    }

    // Verificar si el usuario tiene un rol específico
    public boolean tieneRol(Long idUsuario, String nombreRol) {
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
        return usuario.isPresent() &&
                usuario.get().getRol().getNombreRol().equals(nombreRol);
    }

    // Verificar si el usuario es jefe
    public boolean esJefe(Long idUsuario) {
        return tieneRol(idUsuario, "JEFE");
    }

    // Verificar si el usuario es cliente
    public boolean esCliente(Long idUsuario) {
        return tieneRol(idUsuario, "CLIENTE");
    }

    // Método auxiliar para verificar contraseña
    private boolean verificarContrasena(String contrasenaIngresada, String contrasenaAlmacenada) {
        // Aquí deberías implementar la verificación con hash
        // Por simplicidad, comparación directa (NO RECOMENDADO EN PRODUCCIÓN)
        return contrasenaIngresada.equals(contrasenaAlmacenada);
    }

    // Cambiar contraseña
    public boolean cambiarContrasena(Long idUsuario, String contrasenaActual, String nuevaContrasena) {
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);

        if (usuario.isPresent() && verificarContrasena(contrasenaActual, usuario.get().getContrasena())) {
            usuario.get().setContrasena(nuevaContrasena);
            usuarioRepository.save(usuario.get());
            return true;
        }

        return false;
    }
}