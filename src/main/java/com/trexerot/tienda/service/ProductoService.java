package com.trexerot.tienda.service;

import com.trexerot.tienda.model.Producto;
import com.trexerot.tienda.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository repository;

    public List<Producto> listarTodos() {
        return repository.findAll();
    }

    public Producto guardar(Producto producto) {
        return repository.save(producto);
    }

    public Optional<Producto> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    public List<Producto> listarDestacados() {
        return repository.findByDestacadoTrue();
    }

    public List<Producto> listarOfertas() {
        return repository.findByEnOfertaTrue();
    }
}
