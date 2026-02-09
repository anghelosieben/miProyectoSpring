package com.proyecto.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.demo.model.entity.Venta;

@Repository
public interface VentasRepository extends JpaRepository<Venta, Long>    {
    public Venta save(Venta venta);
    public Optional<Venta> findById(Long id);
    public List<Venta> findAll();
    
}
