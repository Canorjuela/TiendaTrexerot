package com.trexerot.tienda.service;

import com.trexerot.tienda.model.CategoriaProducto;
import com.trexerot.tienda.model.Producto;
import com.trexerot.tienda.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository repository;

    // Listar todos los productos
    public List<Producto> listarTodos() {
        return repository.findAll();
    }

    // Guardar o actualizar producto con validación de negocio
    public Producto guardar(Producto producto) {
        validarProducto(producto);
        return repository.save(producto);
    }

    // Buscar por ID
    public Optional<Producto> buscarPorId(Long id) {
        return repository.findById(id);
    }

    // Eliminar por ID
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    // Listar productos destacados
    public List<Producto> listarDestacados() {
        return repository.findByDestacadoTrue();
    }

    // Listar productos en oferta
    public List<Producto> listarOfertas() {
        return repository.findByEnOfertaTrue();
    }

    // Listar por categoría
    public List<Producto> listarPorCategoria(CategoriaProducto categoria) {
        return repository.findByCategoria(categoria);
    }

    // Listar los más vendidos (Top 5)
    public List<Producto> listarMasVendidos() {
        return repository.findTop5ByOrderByVentasTotalesDesc();
    }

    // Buscar por nombre o descripción
    public List<Producto> buscarPorTexto(String texto) {
        return repository.findByNombreContainingIgnoreCaseOrDescripcionContainingIgnoreCase(texto, texto);
    }

    // Validaciones de negocio
    private void validarProducto(Producto producto) {
        if (producto.isEnOferta()) {
            if (producto.getDescuento() == null || producto.getDescuento().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Si el producto está en oferta, debe tener un descuento válido.");
            }
            if (producto.getDescuento().compareTo(BigDecimal.valueOf(100)) >= 0) {
                throw new IllegalArgumentException("El descuento no puede ser igual o mayor al 100%.");
            }
        } else {
            // Si no está en oferta, ponemos el descuento a 0 para evitar problemas
            producto.setDescuento(BigDecimal.ZERO);
        }
    }
}
