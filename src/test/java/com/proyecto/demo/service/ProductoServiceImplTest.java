/*package com.proyecto.demo.service;

import com.proyecto.demo.model.entity.Producto;
import com.proyecto.demo.repository.ProductoRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ProductoServiceImplTest {

    private ProductoServiceImpl productoService;
    private StubProductoRepository stubRepository;

    @BeforeEach
    void setUp() {
        stubRepository = new StubProductoRepository();
        productoService = new ProductoServiceImpl(stubRepository);
    }

    @Test
    void testFindAll() {
        Producto p1 = new Producto();
        p1.setId(1L);
        Producto p2 = new Producto();
        p2.setId(2L);
        stubRepository.save(p1);
        stubRepository.save(p2);

        List<Producto> productos = productoService.findAll();
        assertEquals(2, productos.size());
    }

    @Test
    void testFindByIdFound() {
        Producto p = new Producto();
        p.setId(10L);
        stubRepository.save(p);

        Optional<Producto> result = productoService.findById(10L);
        assertTrue(result.isPresent());
        assertEquals(10L, result.get().getId());
    }

    @Test
    void testFindByIdNotFound() {
        Optional<Producto> result = productoService.findById(99L);
        assertFalse(result.isPresent());
    }

    @Test
    void testSaveValidProducto() {
        Producto p = new Producto();
        p.setId(5L);
        p.setNombre("Test");
        p.setPrecio(100.0);

        Producto saved = productoService.save(p);
        assertEquals("Test", saved.getNombre());
        assertEquals(100.0, saved.getPrecio());
        assertTrue(stubRepository.data.containsKey(5L));
    }

    @Test
    void testSaveInvalidPrecio() {
        Producto p = new Producto();
        p.setNombre("Test");
        p.setPrecio(-1.0);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> productoService.save(p));
        assertEquals("El precio debe ser positivo", ex.getMessage());
    }

    @Test
    void testSaveInvalidNombre() {
        Producto p = new Producto();
        p.setNombre(" ");
        p.setPrecio(10.0);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> productoService.save(p));
        assertEquals("El nombre del producto es obligatorio", ex.getMessage());
    }

    @Test
    void testDeleteByIdExists() {
        Producto p = new Producto();
        p.setId(7L);
        p.setNombre("A");
        p.setPrecio(1.0);
        stubRepository.save(p);

        productoService.deleteById(7L);
        assertFalse(stubRepository.data.containsKey(7L));
    }

    @Test
    void testDeleteByIdNotExists() {
        Exception ex = assertThrows(RuntimeException.class, () -> productoService.deleteById(123L));
        assertEquals("Producto con ID 123 no encontrado", ex.getMessage());
    }

    // Stub interno para el repositorio
    static class StubProductoRepository extends ProductoRepositoryImpl {
        Map<Long, Producto> data = new HashMap<>();

        @Override
        public List<Producto> findAll() {
            return new ArrayList<>(data.values());
        }

        @Override
        public Optional<Producto> findById(Long id) {
            return Optional.ofNullable(data.get(id));
        }

        @Override
        public Producto save(Producto producto) {
            if (producto.getId() == null) {
                producto.setId((long) (data.size() + 1));
            }
            data.put(producto.getId(), producto);
            return producto;
        }

        @Override
        public void deleteById(Long id) {
            data.remove(id);
        }

        @Override
        public boolean existsById(Long id) {
            return data.containsKey(id);
        }
    }
}
*/