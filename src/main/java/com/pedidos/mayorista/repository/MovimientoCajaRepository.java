package com.pedidos.mayorista.repository;

import com.pedidos.mayorista.model.Caja;
import com.pedidos.mayorista.model.MovimientoCaja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimientoCajaRepository extends JpaRepository<MovimientoCaja, Long> {

    List<MovimientoCaja> findByCajaIdOrderByFechaAsc(Long cajaId);

    List<MovimientoCaja> findByCajaOrderByFechaDesc(Caja caja);

    List<MovimientoCaja> findAllByOrderByFechaDesc();

}