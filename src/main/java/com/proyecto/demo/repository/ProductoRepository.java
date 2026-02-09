package com.proyecto.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.proyecto.demo.model.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{

    public List<Producto> findAll();
    public Producto save(Producto producto);
    //public void update(Producto producto);
    public List<Producto> findByNombre(String nombre);

    @Query("SELECT p FROM Producto p " +
       "WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :termino, '%')) " +
       "   OR LOWER(p.codigo) LIKE LOWER(CONCAT('%', :termino, '%')) ")
    List<Producto> buscarPorNombreOCodigo( @Param("termino") String termino );
}
