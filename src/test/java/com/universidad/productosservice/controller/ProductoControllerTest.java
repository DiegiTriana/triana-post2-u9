package com.universidad.productosservice.controller;

import com.universidad.productosservice.domain.Producto;
import com.universidad.productosservice.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductoController.class)
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @Test
    void listarProductos_retorna200ConLista() throws Exception {
        List<Producto> lista = List.of(
                new Producto(1L, "Laptop", 1500.0, 10),
                new Producto(2L, "Mouse", 50.0, 100)
        );
        when(productoService.listarTodos()).thenReturn(lista);

        mockMvc.perform(get("/api/productos").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nombre").value("Laptop"));
    }

    @Test
    void crearProducto_datosValidos_retorna201() throws Exception {
        Producto creado = new Producto(1L, "Tablet", 800.0, 5);
        when(productoService.crear(anyString(), anyDouble(), anyInt())).thenReturn(creado);

        String json = """
                {"nombre":"Tablet","precio":800.0,"stock":5}
                """;

        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Tablet"));
    }

    @Test
    void buscarProducto_noExistente_retorna404() throws Exception {
        when(productoService.buscarPorId(99L))
                .thenThrow(new RuntimeException("Producto no encontrado: 99"));

        mockMvc.perform(get("/api/productos/99"))
                .andExpect(status().isNotFound());
    }
}
