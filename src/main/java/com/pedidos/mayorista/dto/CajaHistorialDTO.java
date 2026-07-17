package com.pedidos.mayorista.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CajaHistorialDTO {

    private Long id;

    private String usuario;

    private LocalDateTime fechaApertura;

    private LocalDateTime fechaCierre;

    private String estado;

    private BigDecimal cajaInicial;

    private BigDecimal ventasEfectivo;

    private BigDecimal ventasDigitales;

    private BigDecimal ingresos;

    private BigDecimal retiros;

    private BigDecimal efectivoEsperado;

    private BigDecimal efectivoContado;

    private BigDecimal diferencia;

    private String observaciones;

    public CajaHistorialDTO(
            Long id,
            String usuario,
            LocalDateTime fechaApertura,
            LocalDateTime fechaCierre,
            String estado,
            BigDecimal cajaInicial,
            BigDecimal ventasEfectivo,
            BigDecimal ventasDigitales,
            BigDecimal ingresos,
            BigDecimal retiros,
            BigDecimal efectivoEsperado,
            BigDecimal efectivoContado,
            BigDecimal diferencia,
            String observaciones) {

        this.id = id;
        this.usuario = usuario;
        this.fechaApertura = fechaApertura;
        this.fechaCierre = fechaCierre;
        this.estado = estado;
        this.cajaInicial = cajaInicial;
        this.ventasEfectivo = ventasEfectivo;
        this.ventasDigitales = ventasDigitales;
        this.ingresos = ingresos;
        this.retiros = retiros;
        this.efectivoEsperado = efectivoEsperado;
        this.efectivoContado = efectivoContado;
        this.diferencia = diferencia;
        this.observaciones = observaciones;
    }

    public Long getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public LocalDateTime getFechaApertura() {
        return fechaApertura;
    }

    public LocalDateTime getFechaCierre() {
        return fechaCierre;
    }

    public String getEstado() {
        return estado;
    }

    public BigDecimal getCajaInicial() {
        return cajaInicial;
    }

    public BigDecimal getVentasEfectivo() {
        return ventasEfectivo;
    }

    public BigDecimal getVentasDigitales() {
        return ventasDigitales;
    }

    public BigDecimal getIngresos() {
        return ingresos;
    }

    public BigDecimal getRetiros() {
        return retiros;
    }

    public BigDecimal getEfectivoEsperado() {
        return efectivoEsperado;
    }

    public BigDecimal getEfectivoContado() {
        return efectivoContado;
    }

    public BigDecimal getDiferencia() {
        return diferencia;
    }

    public String getObservaciones() {
        return observaciones;
    }

}