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
        MockitoAnnotations.openMocks(this); // Inicializa los mocks

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Camila");
        usuario.setEmail("camila@mail.com");
        usuario.setContrasena("1234");
        usuario.setDireccion("Mi calle");
        usuario.setTelefono("123456789");
    }

    @Test
    void testRegistrarUsuario() {
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioService.registar(usuario);

        assertNotNull(resultado);
        assertEquals("Camila", resultado.getNombre());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void testLoginExitoso() {
        when(usuarioRepository.findByEmail("camila@mail.com")).thenReturn(usuario);

        Usuario login = usuarioService.login("camila@mail.com", "1234");

        assertNotNull(login);
        assertEquals("Camila", login.getNombre());
    }

    @Test
    void testLoginFallido() {
        when(usuarioRepository.findByEmail("camila@mail.com")).thenReturn(usuario);

        Usuario login = usuarioService.login("camila@mail.com", "wrong");

        assertNull(login);
    }

    @Test
    void testBuscarPorId() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Camila", resultado.get().getNombre());
    }

    @Test
    void testListar() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<Usuario> lista = usuarioService.listar();

        assertEquals(1, lista.size());
    }

    @Test
    void testEliminarExistente() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);

        boolean eliminado = usuarioService.eliminar(1L);

        assertTrue(eliminado);
        verify(usuarioRepository).deleteById(1L);
    }

    @Test
    void testEliminarInexistente() {
        when(usuarioRepository.existsById(2L)).thenReturn(false);

        boolean eliminado = usuarioService.eliminar(2L);

        assertFalse(eliminado);
        verify(usuarioRepository, never()).deleteById(anyLong());
    }

    @Test
    void testActualizar() {
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario actualizado = usuarioService.actualizar(usuario);

        assertNotNull(actualizado);
        assertEquals("Camila", actualizado.getNombre());
    }
}
