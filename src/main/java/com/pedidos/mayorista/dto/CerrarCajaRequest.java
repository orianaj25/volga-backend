package com.pedidos.mayorista.dto;

import java.math.BigDecimal;

public class CerrarCajaRequest {

    private BigDecimal efectivoContado;
    private String observaciones;

    public BigDecimal getEfectivoContado() {
        return efectivoContado;
    }

    public void setEfectivoContado(BigDecimal efectivoContado) {
        this.efectivoContado = efectivoContado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}