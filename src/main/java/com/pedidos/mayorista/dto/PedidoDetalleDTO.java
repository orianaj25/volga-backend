package com.pedidos.mayorista.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PedidoDetalleDTO {

    public Long pedidoId;
    public LocalDateTime fecha;
    public String producto;
    public Integer cantidad;
    public BigDecimal subtotal;
    public BigDecimal totalPedido;
    public String metodoPago;


    public PedidoDetalleDTO(Long pedidoId, LocalDateTime fecha, String producto,
                            Integer cantidad, BigDecimal subtotal, BigDecimal totalPedido, String metodoPago) {
        this.pedidoId = pedidoId;
        this.fecha = fecha;
        this.producto = producto;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.totalPedido = totalPedido;
        this.metodoPago = metodoPago;
    }
}
