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

    public Usuario registar (Usuario usuario){
        return  usuarioRepository.save(usuario);
    }
    public Usuario login(String email, String contrasena) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario != null && usuario.getContrasena().equals(contrasena)) {
            return usuario;
        }
        return null;
    }
    public Optional<Usuario> buscarPorId (Long id){
        return usuarioRepository.findById(id);
    }
    public List<Usuario> listar (){
        return usuarioRepository.findAll();
    }
    public boolean eliminar(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public Usuario actualizar(Usuario usuario) {
        return usuarioRepository.save(usuario);  // save() también sirve para actualizar si el ID ya existe
    }

}
