package com.pedidos.mayorista.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimiento_caja")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoCaja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caja_id", nullable = false)
    private Caja caja;

    private LocalDateTime fecha;

    private String usuario;

    @Column(nullable = false)
    private String tipo;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal monto;

    @Column(length = 500)
    private String motivo;

}