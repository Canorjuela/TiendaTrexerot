package com.trexerot.tienda.repository;

import com.trexerot.tienda.model.ItemCarrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCarritoRepository extends JpaRepository<ItemCarrito,Long> {
}
