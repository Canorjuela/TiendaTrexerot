package com.trexerot.tienda.controller;

import com.trexerot.tienda.model.LoginDTO;
import com.trexerot.tienda.model.Usuario;
import com.trexerot.tienda.repository.UsuarioRepository;
import com.trexerot.tienda.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    //Registro
    @PostMapping
    public Usuario registrar (@Valid @RequestBody Usuario usuario) {
        return usuarioService.registar(usuario);
    }

    //Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO datos) {
        Usuario usuario = usuarioService.login(datos.getEmail(), datos.getContrasena());
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email o contraseña incorrectos");
        }
    }
    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.listar();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        boolean eliminado = usuarioService.eliminar(id);
        if (eliminado) {
            return ResponseEntity.ok("Usuario eliminado");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }    }

}
