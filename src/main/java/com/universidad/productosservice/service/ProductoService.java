package com.universidad.productosservice.service;

import com.universidad.productosservice.domain.Producto;

import java.util.List;

public interface ProductoService {
    Producto crear(String nombre, Double precio, Integer stock);

    Producto buscarPorId(Long id);

    List<Producto> listarTodos();

    Producto actualizarStock(Long id, Integer nuevoStock);

    void eliminar(Long id);
}
