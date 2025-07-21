package com.trexerot.tienda.service;

import com.trexerot.tienda.model.*;
import com.trexerot.tienda.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarritoServiceTest {

    @Mock
    private CarritoRepository carritoRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private CarritoService carritoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAgregarProducto_NuevoCarritoYProductoNuevo() {
        Long userId = 1L, prodId = 10L;
        Usuario usuario = new Usuario();
        usuario.setId(userId);
        usuario.setCarrito(null);

        Producto producto = new Producto();
        producto.setId(prodId);
        producto.setStock(20);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(productoRepository.findById(prodId)).thenReturn(Optional.of(producto));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        when(carritoRepository.save(any(Carrito.class))).thenAnswer(inv -> inv.getArgument(0));

        Carrito resultado = carritoService.agregarProducto(userId, prodId, 2);

        assertNotNull(resultado);
        assertEquals(1, resultado.getItems().size());
        assertEquals(2, resultado.getItems().get(0).getCantidad());
    }

    @Test
    void testAgregarProducto_YaExistenteEnCarrito() {
        Long userId = 1L, prodId = 10L;
        Producto producto = new Producto();
        producto.setId(prodId);

        ItemCarrito item = new ItemCarrito(producto, 2);
        Carrito carrito = new Carrito();
        carrito.setItems(new ArrayList<>(List.of(item)));

        Usuario usuario = new Usuario();
        usuario.setId(userId);
        usuario.setCarrito(carrito);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(productoRepository.findById(prodId)).thenReturn(Optional.of(producto));
        when(carritoRepository.save(any(Carrito.class))).thenAnswer(inv -> inv.getArgument(0));

        Carrito resultado = carritoService.agregarProducto(userId, prodId, 3);

        assertEquals(1, resultado.getItems().size());
        assertEquals(5, resultado.getItems().get(0).getCantidad());
    }

    @Test
    void testVerCarritoPorUsuario() {
        Long userId = 1L;
        Carrito carrito = new Carrito();

        Usuario usuario = new Usuario();
        usuario.setId(userId);
        usuario.setCarrito(carrito);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        Carrito resultado = carritoService.verCarritoPorUsuario(userId);

        assertNotNull(resultado);
    }

    @Test
    void testFinalizarCompra_SinStock() {
        Long carritoId = 5L;
        Producto producto = new Producto();
        producto.setNombre("Audífonos");
        producto.setStock(1);

        ItemCarrito item = new ItemCarrito(producto, 2);
        Carrito carrito = new Carrito();
        carrito.setItems(List.of(item));

        when(carritoRepository.findById(carritoId)).thenReturn(Optional.of(carrito));

        String mensaje = carritoService.finalizarCompra(carritoId);

        assertEquals("No hay suficiente stock de: Audífonos", mensaje);
        verify(productoRepository, never()).save(any());
    }

    @Test
    void testFinalizarCompra_Exitosa() {
        Long carritoId = 2L;

        Producto producto = new Producto();
        producto.setNombre("Carcasa");
        producto.setStock(10);

        ItemCarrito item = new ItemCarrito(producto, 2);
        Carrito carrito = new Carrito();
        carrito.setItems(List.of(item));

        Usuario usuario = new Usuario();
        carrito.setUsuario(usuario);

        when(carritoRepository.findById(carritoId)).thenReturn(Optional.of(carrito));

        String resultado = carritoService.finalizarCompra(carritoId);

        assertEquals("Compra realizada con éxito", resultado);
        verify(productoRepository).save(producto);
        verify(carritoRepository).deleteById(carritoId);
        verify(usuarioRepository).save(usuario);
    }
}
