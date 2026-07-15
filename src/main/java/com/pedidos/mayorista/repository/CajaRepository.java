package com.pedidos.mayorista.repository;

import com.pedidos.mayorista.model.Caja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CajaRepository extends JpaRepository<Caja, Long> {

    Optional<Caja> findByEstado(String estado);

    boolean existsByEstado(String estado);

    List<Caja> findAllByOrderByFechaAperturaDesc();

}