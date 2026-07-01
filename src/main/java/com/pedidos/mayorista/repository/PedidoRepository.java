package com.pedidos.mayorista.repository;

import com.pedidos.mayorista.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {
    // Total vendido en el día
    @Query("""
        SELECT COALESCE(SUM(p.total),0)
        FROM Pedido p
        WHERE p.fecha BETWEEN :inicio AND :fin
    """)
    BigDecimal ventasDelDia(LocalDateTime inicio, LocalDateTime fin);


    // Cantidad de pedidos
    @Query("""
        SELECT COUNT(p)
        FROM Pedido p
        WHERE p.fecha BETWEEN :inicio AND :fin
    """)
    Long pedidosDelDia(LocalDateTime inicio, LocalDateTime fin);


    // Clientes distintos
    @Query("""
        SELECT COUNT(DISTINCT p.dniCliente)
        FROM Pedido p
        WHERE p.fecha BETWEEN :inicio AND :fin
    """)
    Long clientesDelDia(LocalDateTime inicio, LocalDateTime fin);

}


