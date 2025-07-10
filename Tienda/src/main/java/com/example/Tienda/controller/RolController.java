package com.example.Tienda.controller;

import com.example.Tienda.entity.Rol;
import com.example.Tienda.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
public class RolController {

    @Autowired
    private RolService rolService;

    // CREATE - Crear nuevo rol
    @PostMapping("/crearRol")
    public ResponseEntity<Rol> crearRol(@RequestBody Rol rol) {
        try {
            Rol nuevoRol = rolService.crearRol(rol);
            return new ResponseEntity<>(nuevoRol, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ - Obtener todos los roles
    @GetMapping("/getRoles")
    public ResponseEntity<List<Rol>> obtenerTodosRoles() {
        try {
            List<Rol> roles = rolService.obtenerTodosRoles();
            if (roles.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(roles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ - Obtener rol por ID
    @GetMapping("getRol/{id}")
    public ResponseEntity<Rol> obtenerRolPorId(@PathVariable("id") Long id) {
        Optional<Rol> rol = rolService.obtenerRolPorId(id);
        if (rol.isPresent()) {
            return new ResponseEntity<>(rol.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // UPDATE - Actualizar rol
    @PutMapping("actualizar/{id}")
    public ResponseEntity<Rol> actualizarRol(@PathVariable("id") Long id, @RequestBody Rol rol) {
        try {
            Rol rolActualizado = rolService.actualizarRol(id, rol);
            return new ResponseEntity<>(rolActualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE - Eliminar rol
    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<HttpStatus> eliminarRol(@PathVariable("id") Long id) {
        try {
            rolService.eliminarRol(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}