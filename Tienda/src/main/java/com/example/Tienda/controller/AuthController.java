package com.example.Tienda.controller;

import com.example.Tienda.entity.Rol;
import com.example.Tienda.entity.Usuario;
import com.example.Tienda.service.RolService;
import com.example.Tienda.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService; // Necesario para obtener el rol por ID

    // Login básico
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        try {
            String email = credentials.get("email");
            String password = credentials.get("contrasena");

            Optional<Usuario> usuario = usuarioService.findByEmail(email);

            Map<String, Object> response = new HashMap<>();

            if (usuario.isPresent() && usuario.get().getContrasena().equals(password)) {
                response.put("success", true);
                response.put("message", "Login exitoso");
                response.put("usuario", usuario.get());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("success", false);
                response.put("message", "Credenciales inválidas");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error interno del servidor");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Registro de usuario
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Usuario usuario) {
        try {
            Map<String, Object> response = new HashMap<>();

            // Verificar si el email ya existe
            if (usuarioService.findByEmail(usuario.getEmail()).isPresent()) {
                response.put("success", false);
                response.put("message", "El email ya está registrado");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            // Asignar rol por defecto (cliente = 2) si no tiene rol asignado
            if (usuario.getRol() == null) {
                Optional<Rol> rolCliente = rolService.obtenerRolPorId(2L);
                if (rolCliente.isPresent()) {
                    usuario.setRol(rolCliente.get());
                } else {
                    response.put("success", false);
                    response.put("message", "Error: Rol por defecto no encontrado");
                    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            Usuario nuevoUsuario = usuarioService.save(usuario);

            response.put("success", true);
            response.put("message", "Usuario registrado exitosamente");
            response.put("usuario", nuevoUsuario);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al registrar usuario");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Cambiar contraseña
    @PutMapping("/change-password")
    public ResponseEntity<Map<String, Object>> changePassword(@RequestBody Map<String, String> passwordData) {
        try {
            String email = passwordData.get("email");
            String oldPassword = passwordData.get("oldPassword");
            String newPassword = passwordData.get("newPassword");

            Optional<Usuario> usuario = usuarioService.findByEmail(email);
            Map<String, Object> response = new HashMap<>();

            if (usuario.isPresent() && usuario.get().getContrasena().equals(oldPassword)) {
                Usuario usuarioActualizado = usuario.get();
                usuarioActualizado.setContrasena(newPassword);
                usuarioService.save(usuarioActualizado);

                response.put("success", true);
                response.put("message", "Contraseña actualizada exitosamente");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("success", false);
                response.put("message", "Contraseña actual incorrecta");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al cambiar contraseña");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}