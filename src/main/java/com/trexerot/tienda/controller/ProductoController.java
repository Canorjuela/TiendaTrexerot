package com.trexerot.tienda.controller;

import com.trexerot.tienda.model.Producto;
import com.trexerot.tienda.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*") // Permitimos acceso desde el frontend luego
public class ProductoController {

    @Autowired
    private ProductoService service;

    @GetMapping
    public List<Producto> listar() {
        return service.listarTodos();
    }

    @PostMapping
    public Producto crear(@RequestBody Producto producto) {
        return service.guardar(producto);
    }

    @GetMapping("/{id}")
    public Optional<Producto> buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Producto producto) {
        Optional<Producto> productoExistente = service.buscarPorId(id);

        if (productoExistente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto con ID " + id + " no existe.");
        }

        producto.setId(id);
        Producto actualizado = service.guardar(producto);

        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
