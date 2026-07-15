package com.pedidos.mayorista.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "caja")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Caja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String usuario;

    private LocalDateTime fechaApertura;

    private LocalDateTime fechaCierre;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal cajaInicial = BigDecimal.ZERO;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal ventasEfectivo = BigDecimal.ZERO;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal ventasDigitales = BigDecimal.ZERO;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal ingresos = BigDecimal.ZERO;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal retiros = BigDecimal.ZERO;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal efectivoEsperado = BigDecimal.ZERO;

    @Column(precision = 12, scale = 2)
    private BigDecimal efectivoContado;

    @Column(precision = 12, scale = 2)
    private BigDecimal diferencia;

    private String estado;

    private String observaciones;

    @OneToMany(
            mappedBy = "caja",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<MovimientoCaja> movimientos;

}
