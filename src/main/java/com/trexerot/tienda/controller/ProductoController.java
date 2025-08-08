package com.trexerot.tienda.controller;

import com.trexerot.tienda.model.CategoriaProducto;
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
@CrossOrigin(origins = "*") // Permitir acceso desde cualquier frontend
public class ProductoController {

    @Autowired
    private ProductoService service;

    //  Listar todos
    @GetMapping
    public List<Producto> listar() {
        return service.listarTodos();
    }

    //  Crear nuevo producto
    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody Producto producto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(producto));
    }

    //  Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        Optional<Producto> producto = service.buscarPorId(id);
        if (producto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto con ID " + id + " no existe.");
        }
        return ResponseEntity.ok(producto.get());
    }

    //  Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Producto producto) {
        if (service.buscarPorId(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto con ID " + id + " no existe.");
        }
        producto.setId(id);
        return ResponseEntity.ok(service.guardar(producto));
    }

    //  Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (service.buscarPorId(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto con ID " + id + " no existe.");
        }
        service.eliminar(id);
        return ResponseEntity.ok("Producto eliminado correctamente");
    }

    // Listar destacados
    @GetMapping("/destacados")
    public List<Producto> destacados() {
        return service.listarDestacados();
    }

    //  Listar en oferta
    @GetMapping("/ofertas")
    public List<Producto> ofertas() {
        return service.listarOfertas();
    }

    //  Listar por categoría
    @GetMapping("/categoria/{categoria}")
    public List<Producto> porCategoria(@PathVariable CategoriaProducto categoria) {
        return service.listarPorCategoria(categoria);
    }

    //  Listar más vendidos
    @GetMapping("/mas-vendidos")
    public List<Producto> masVendidos() {
        return service.listarMasVendidos();
    }
}