package com.pedidos.mayorista.repository;

import com.pedidos.mayorista.dto.PedidoDetalleDTO;
import com.pedidos.mayorista.model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido,Long> {


    @Query("""
        SELECT new com.pedidos.mayorista.dto.PedidoDetalleDTO(
            p.id,
            p.fecha,
            pr.nombre,
            d.cantidad,
            d.subtotal,
            p.total,
            p.metodoPago
        )
        FROM DetallePedido d
        JOIN d.pedido p
        JOIN d.producto pr
        ORDER BY p.fecha DESC
    """)
    List<PedidoDetalleDTO> listarDetalle();

    @Query("""
    SELECT COALESCE(SUM(d.cantidad),0)
    FROM DetallePedido d
    WHERE d.pedido.fecha BETWEEN :inicio AND :fin
""")
    Integer productosVendidos(LocalDateTime inicio,
                              LocalDateTime fin);
}
