package com.universidad.productosservice.repository;

import com.universidad.productosservice.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
