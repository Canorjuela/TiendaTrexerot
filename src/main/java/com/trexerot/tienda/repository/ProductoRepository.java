package com.trexerot.tienda.repository;

import com.trexerot.tienda.model.CategoriaProducto;
import com.trexerot.tienda.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Buscar por categoría
    List<Producto> findByCategoria(CategoriaProducto categoria);

    // Buscar productos en oferta
    List<Producto> findByEnOfertaTrue();

    // Buscar productos destacados
    List<Producto> findByDestacadoTrue();

    // Buscar los más vendidos
    List<Producto> findTop5ByOrderByVentasTotalesDesc();

    // Buscar por nombre o descripción (para buscador en frontend)
    List<Producto> findByNombreContainingIgnoreCaseOrDescripcionContainingIgnoreCase(String nombre, String descripcion);
}
