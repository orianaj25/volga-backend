package com.pedidos.mayorista.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRequest {

    public String metodoPago;   // 👈 NUEVO

    public List<ItemPedido> items;

    public static class ItemPedido {
        public Long productoId;
        public Integer cantidad;
    }
}