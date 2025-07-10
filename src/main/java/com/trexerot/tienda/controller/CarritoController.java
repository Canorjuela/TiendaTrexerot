package com.trexerot.tienda.controller;

import com.trexerot.tienda.model.Carrito;
import com.trexerot.tienda.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    /**
     * Agregar producto al carrito de un usuario.
     * Ejemplo: POST http://localhost:8080/api/carrito/1/3/2
     */
    @PostMapping("/{usuarioId}/{productoId}/{cantidad}")
    public Carrito agregarProducto(
            @PathVariable Long usuarioId,
            @PathVariable Long productoId,
            @PathVariable int cantidad) {

        return carritoService.agregarProducto(usuarioId, productoId, cantidad);
    }

    /**
     * Ver carrito por ID (menos común en la vida real, útil solo si sabes el ID).
     * Ejemplo: GET http://localhost:8080/api/carrito/5
     */
    @GetMapping("/{id}")
    public Carrito verCarrito(@PathVariable Long id) {
        return carritoService.verCarrito(id);
    }

    /**
     * Ver carrito del usuario por su ID (más común y útil).
     * Ejemplo: GET http://localhost:8080/api/carrito/usuario/1
     */
    @GetMapping("/usuario/{usuarioId}")
    public Carrito verCarritoPorUsuario(@PathVariable Long usuarioId) {
        return carritoService.verCarritoPorUsuario(usuarioId);
    }

    /**
     * Finalizar compra del carrito (deberías saber el ID del carrito).
     * Ejemplo: POST http://localhost:8080/api/carrito/comprar/5
     */
    @PostMapping("/comprar/{carritoId}")
    public String finalizarCompra(@PathVariable Long carritoId) {
        return carritoService.finalizarCompra(carritoId);
    }
}
