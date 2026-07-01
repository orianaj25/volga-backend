package com.pedidos.mayorista.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDTO {

    private BigDecimal ventasDelDia;

    private Long pedidosDelDia;

    private Long clientesAtendidos;

    private Integer productosVendidos;

}
