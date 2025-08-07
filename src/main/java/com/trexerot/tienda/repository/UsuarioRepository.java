package com.trexerot.tienda.repository;

import com.trexerot.tienda.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Buscar por email (usado en login, registro, validación, etc.)
    Optional<Usuario> findByEmail(String email);

    // Verificar si ya existe un usuario con un email dado
    boolean existsByEmail(String email);
}
