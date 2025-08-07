package com.trexerot.tienda.service;

import com.trexerot.tienda.model.Usuario;
import com.trexerot.tienda.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Registro de usuario
    public Usuario registrar(Usuario usuario) {
        // Evita registros duplicados por email
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con ese correo.");
        }
        return usuarioRepository.save(usuario);
    }

    // Login
    public Optional<Usuario> login(String email, String contrasena) {
        return usuarioRepository.findByEmail(email)
                .filter(usuario -> usuario.getContrasena().equals(contrasena));
    }

    // Buscar por ID
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Listar todos
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    // Eliminar por ID
    public boolean eliminar(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Actualizar usuario
    public Usuario actualizar(Usuario usuario) {
        if (!usuarioRepository.existsById(usuario.getId())) {
            throw new RuntimeException("El usuario no existe.");
        }
        return usuarioRepository.save(usuario);
    }
}
