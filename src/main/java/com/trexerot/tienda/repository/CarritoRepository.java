package com.trexerot.tienda.repository;

import com.trexerot.tienda.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<Carrito,Long> {
}
