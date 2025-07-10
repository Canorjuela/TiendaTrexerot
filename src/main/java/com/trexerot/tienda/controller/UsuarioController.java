package com.trexerot.tienda.controller;

import com.trexerot.tienda.model.Usuario;
import com.trexerot.tienda.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public Usuario crear(@Valid @RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @GetMapping
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }
}
