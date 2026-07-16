package com.pedidos.mayorista.dto;

import java.math.BigDecimal;

public class AbrirCajaRequest {

    private BigDecimal cajaInicial;
    private String usuario;

    public BigDecimal getCajaInicial() {
        return cajaInicial;
    }

    public void setCajaInicial(BigDecimal cajaInicial) {
        this.cajaInicial = cajaInicial;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}