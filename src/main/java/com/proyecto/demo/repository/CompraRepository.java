package com.proyecto.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.demo.model.entity.Compra;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {
    public Compra save(Compra compra);
    public Optional<Compra> findById(Long id);
}
