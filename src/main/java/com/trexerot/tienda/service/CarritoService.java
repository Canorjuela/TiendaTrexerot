package com.trexerot.tienda.service;

import com.trexerot.tienda.model.*;
import com.trexerot.tienda.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    /**
     * Agrega un producto al carrito de un usuario. Si el usuario no tiene carrito, se le crea uno.
     * Si el producto ya está en el carrito, se suma la cantidad.
     */
    public Carrito agregarProducto(Long usuarioId, Long productoId, int cantidad) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        Optional<Producto> productoOpt = productoRepository.findById(productoId);

        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuario con ID " + usuarioId + " no encontrado.");
        }

        if (productoOpt.isEmpty()) {
            throw new RuntimeException("Producto con ID " + productoId + " no encontrado.");
        }

        Usuario usuario = usuarioOpt.get();
        Producto producto = productoOpt.get();
        Carrito carrito = usuario.getCarrito();

        // Si el usuario no tiene carrito, se crea uno
        if (carrito == null) {
            carrito = new Carrito();
            carrito.setUsuario(usuario);
            carrito.setItems(new ArrayList<>());
            usuario.setCarrito(carrito);
        }

        boolean encontrado = false;

        // Buscar si ya existe el producto en el carrito
        for (ItemCarrito item : carrito.getItems()) {
            if (item.getProducto().getId().equals(producto.getId())) {
                item.setCantidad(item.getCantidad() + cantidad);
                encontrado = true;
                break;
            }
        }

        // Si no se encontró el producto en el carrito, se agrega nuevo
        if (!encontrado) {
            ItemCarrito nuevoItem = new ItemCarrito(producto, cantidad);
            carrito.getItems().add(nuevoItem);
        }

        usuarioRepository.save(usuario); // en caso de que se haya creado el carrito
        return carritoRepository.save(carrito);
    }

    /**
     * Devuelve el carrito por su ID (si sabes el ID directamente).
     */
    public Carrito verCarrito(Long carritoId) {
        return carritoRepository.findById(carritoId).orElse(null);
    }

    /**
     * Devuelve el carrito actual del usuario (más útil que buscarlo por ID).
     */
    public Carrito verCarritoPorUsuario(Long usuarioId) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isEmpty()) {
            return null;
        }
        Usuario usuario = usuarioOpt.get();
        return usuario.getCarrito();
    }

    /**
     * Finaliza la compra: descuenta stock y elimina el carrito.
     */
    public String finalizarCompra(Long carritoId) {
        Optional<Carrito> carritoOpt = carritoRepository.findById(carritoId);

        if (carritoOpt.isEmpty()) {
            return "Carrito no encontrado";
        }

        Carrito carrito = carritoOpt.get();

        for (ItemCarrito item : carrito.getItems()) {
            Producto producto = item.getProducto();
            int stockActual = producto.getStock();
            int cantidad = item.getCantidad();

            if (cantidad > stockActual) {
                return "No hay suficiente stock de: " + producto.getNombre();
            }

            producto.setStock(stockActual - cantidad);
            productoRepository.save(producto);
        }

        Usuario usuario = carrito.getUsuario();
        if (usuario != null) {
            usuario.setCarrito(null);
            usuarioRepository.save(usuario);
        }

        carritoRepository.deleteById(carritoId);

        return "Compra realizada con éxito";
    }
}
