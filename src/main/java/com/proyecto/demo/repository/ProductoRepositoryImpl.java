package com.proyecto.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.demo.model.entity.Producto;

@Repository
public interface ProductoRepositoryImpl extends JpaRepository<Producto, Long>{

    public List<Producto> findAll();
    public Producto save(Producto producto);
}
