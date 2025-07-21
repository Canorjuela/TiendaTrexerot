package com.trexerot.tienda.service;

import com.trexerot.tienda.model.Producto;
import com.trexerot.tienda.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarTodos() {
        List<Producto> lista = Arrays.asList(new Producto(), new Producto());
        when(productoRepository.findAll()).thenReturn(lista);

        List<Producto> resultado = productoService.listarTodos();

        assertEquals(2, resultado.size());
        verify(productoRepository, times(1)).findAll();
    }

    @Test
    void testGuardar() {
        Producto producto = new Producto();
        when(productoRepository.save(producto)).thenReturn(producto);

        Producto resultado = productoService.guardar(producto);

        assertNotNull(resultado);
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    void testBuscarPorId() {
        Producto producto = new Producto();
        producto.setId(1L);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Optional<Producto> resultado = productoService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
        verify(productoRepository, times(1)).findById(1L);
    }

    @Test
    void testEliminar() {
        Long id = 1L;

        productoService.eliminar(id);

        verify(productoRepository, times(1)).deleteById(id);
    }

    @Test
    void testListarDestacados() {
        List<Producto> destacados = Arrays.asList(new Producto(), new Producto());
        when(productoRepository.findByDestacadoTrue()).thenReturn(destacados);

        List<Producto> resultado = productoService.listarDestacados();

        assertEquals(2, resultado.size());
        verify(productoRepository, times(1)).findByDestacadoTrue();
    }

    @Test
    void testListarOfertas() {
        List<Producto> ofertas = Arrays.asList(new Producto());
        when(productoRepository.findByEnOfertaTrue()).thenReturn(ofertas);

        List<Producto> resultado = productoService.listarOfertas();

        assertEquals(1, resultado.size());
        verify(productoRepository, times(1)).findByEnOfertaTrue();
    }
}
