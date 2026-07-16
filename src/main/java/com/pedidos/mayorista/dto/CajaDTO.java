package com.pedidos.mayorista.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CajaDTO {

    private Long id;

    private LocalDateTime fechaApertura;

    private LocalDateTime fechaCierre;

    private String usuario;

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

    public CajaDTO() {
    }

    public CajaDTO(
            Long id,
            LocalDateTime fechaApertura,
            LocalDateTime fechaCierre,
            String usuario,
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
        this.fechaApertura = fechaApertura;
        this.fechaCierre = fechaCierre;
        this.usuario = usuario;
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

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(LocalDateTime fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public LocalDateTime getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDateTime fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getCajaInicial() {
        return cajaInicial;
    }

    public void setCajaInicial(BigDecimal cajaInicial) {
        this.cajaInicial = cajaInicial;
    }

    public BigDecimal getVentasEfectivo() {
        return ventasEfectivo;
    }

    public void setVentasEfectivo(BigDecimal ventasEfectivo) {
        this.ventasEfectivo = ventasEfectivo;
    }

    public BigDecimal getVentasDigitales() {
        return ventasDigitales;
    }

    public void setVentasDigitales(BigDecimal ventasDigitales) {
        this.ventasDigitales = ventasDigitales;
    }

    public BigDecimal getIngresos() {
        return ingresos;
    }

    public void setIngresos(BigDecimal ingresos) {
        this.ingresos = ingresos;
    }

    public BigDecimal getRetiros() {
        return retiros;
    }

    public void setRetiros(BigDecimal retiros) {
        this.retiros = retiros;
    }

    public BigDecimal getEfectivoEsperado() {
        return efectivoEsperado;
    }

    public void setEfectivoEsperado(BigDecimal efectivoEsperado) {
        this.efectivoEsperado = efectivoEsperado;
    }

    public BigDecimal getEfectivoContado() {
        return efectivoContado;
    }

    public void setEfectivoContado(BigDecimal efectivoContado) {
        this.efectivoContado = efectivoContado;
    }

    public BigDecimal getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(BigDecimal diferencia) {
        this.diferencia = diferencia;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}