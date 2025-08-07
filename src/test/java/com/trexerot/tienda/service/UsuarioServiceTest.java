package com.trexerot.tienda.service;

import com.trexerot.tienda.model.Usuario;
import com.trexerot.tienda.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Camila");
        usuario.setEmail("camila@mail.com");
        usuario.setContrasena("1234");
        usuario.setDireccion("Calle 1");
        usuario.setTelefono("123456789");
    }

    @Test
    void testRegistrarUsuarioExitoso() {
        when(usuarioRepository.existsByEmail(usuario.getEmail())).thenReturn(false);
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioService.registrar(usuario);

        assertNotNull(resultado);
        assertEquals("Camila", resultado.getNombre());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void testRegistrarUsuarioDuplicado() {
        when(usuarioRepository.existsByEmail(usuario.getEmail())).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            usuarioService.registrar(usuario);
        });

        assertEquals("Ya existe un usuario con ese correo.", ex.getMessage());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void testLoginExitoso() {
        when(usuarioRepository.findByEmail("camila@mail.com")).thenReturn(Optional.of(usuario));

        Optional<Usuario> login = usuarioService.login("camila@mail.com", "1234");

        assertTrue(login.isPresent());
        assertEquals("Camila", login.get().getNombre());
    }

    @Test
    void testLoginFallidoContraseñaIncorrecta() {
        when(usuarioRepository.findByEmail("camila@mail.com")).thenReturn(Optional.of(usuario));

        Optional<Usuario> login = usuarioService.login("camila@mail.com", "incorrecta");

        assertTrue(login.isEmpty());
    }

    @Test
    void testLoginFallidoEmailNoExistente() {
        when(usuarioRepository.findByEmail("otro@mail.com")).thenReturn(Optional.empty());

        Optional<Usuario> login = usuarioService.login("otro@mail.com", "1234");

        assertTrue(login.isEmpty());
    }

    @Test
    void testBuscarPorIdExistente() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Camila", resultado.get().getNombre());
    }

    @Test
    void testBuscarPorIdInexistente() {
        when(usuarioRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Usuario> resultado = usuarioService.buscarPorId(2L);

        assertFalse(resultado.isPresent());
    }

    @Test
    void testListarUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<Usuario> lista = usuarioService.listar();

        assertEquals(1, lista.size());
        assertEquals("Camila", lista.get(0).getNombre());
    }

    @Test
    void testEliminarUsuarioExistente() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);

        boolean eliminado = usuarioService.eliminar(1L);

        assertTrue(eliminado);
        verify(usuarioRepository).deleteById(1L);
    }

    @Test
    void testEliminarUsuarioInexistente() {
        when(usuarioRepository.existsById(2L)).thenReturn(false);

        boolean eliminado = usuarioService.eliminar(2L);

        assertFalse(eliminado);
        verify(usuarioRepository, never()).deleteById(anyLong());
    }

    @Test
    void testActualizarUsuarioExistente() {
        when(usuarioRepository.existsById(usuario.getId())).thenReturn(true);
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario actualizado = usuarioService.actualizar(usuario);

        assertNotNull(actualizado);
        assertEquals("Camila", actualizado.getNombre());
    }

    @Test
    void testActualizarUsuarioInexistente() {
        when(usuarioRepository.existsById(usuario.getId())).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            usuarioService.actualizar(usuario);
        });

        assertEquals("El usuario no existe.", ex.getMessage());
        verify(usuarioRepository, never()).save(any());
    }
}
