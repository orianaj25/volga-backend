package com.pedidos.mayorista.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRequest {

    public String metodoPago;   // 👈 NUEVO
    public String dniCliente;
    public List<ItemPedido> items;
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemPedido {
        public Long productoId;
        public BigDecimal cantidad;
    }
}