package com.pedidos.mayorista.service;

import com.pedidos.mayorista.model.Caja;
import com.pedidos.mayorista.model.Pedido;
import com.pedidos.mayorista.repository.CajaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class CajaService {

    private final CajaRepository cajaRepository;

    public CajaService(CajaRepository cajaRepository) {
        this.cajaRepository = cajaRepository;
    }

    /*
     * Obtiene la caja abierta.
     */
    public Optional<Caja> obtenerCajaAbierta() {

        return cajaRepository.findByEstado("ABIERTA");

    }

    /*
     * Lista todas las cajas.
     */
    public List<Caja> listar() {

        return cajaRepository.findAllByOrderByFechaAperturaDesc();

    }

    /*
     * Registrar automáticamente una venta.
     * Es llamado desde PedidoService.
     */
    @Transactional
    public void registrarVenta(Pedido pedido) {

        Optional<Caja> cajaOpt = obtenerCajaAbierta();

        if (cajaOpt.isEmpty()) {
            return;
        }

        Caja caja = cajaOpt.get();

        if ("EFECTIVO".equalsIgnoreCase(pedido.getMetodoPago())) {

            caja.setVentasEfectivo(
                    caja.getVentasEfectivo().add(pedido.getTotal())
            );

        } else {

            caja.setVentasDigitales(
                    caja.getVentasDigitales().add(pedido.getTotal())
            );

        }

        recalcularCaja(caja);

        cajaRepository.save(caja);

    }

    /*
     * Recalcula el efectivo esperado.
     */
    private void recalcularCaja(Caja caja) {

        BigDecimal esperado =
                caja.getCajaInicial()
                        .add(caja.getVentasEfectivo())
                        .add(caja.getIngresos())
                        .subtract(caja.getRetiros());

        caja.setEfectivoEsperado(esperado);

    }

    /*
     * Abrir una caja.
     */
    @Transactional
    public Caja abrirCaja(BigDecimal cajaInicial,
                          String usuario) {

        if (obtenerCajaAbierta().isPresent()) {

            throw new RuntimeException(
                    "Ya existe una caja abierta."
            );

        }

        Caja caja = new Caja();

        caja.setFechaApertura(
                LocalDateTime.now(
                        ZoneId.of("America/Argentina/Buenos_Aires"))
        );

        caja.setUsuario(usuario);

        caja.setEstado("ABIERTA");

        caja.setCajaInicial(cajaInicial);

        caja.setVentasEfectivo(BigDecimal.ZERO);

        caja.setVentasDigitales(BigDecimal.ZERO);

        caja.setIngresos(BigDecimal.ZERO);

        caja.setRetiros(BigDecimal.ZERO);

        caja.setEfectivoEsperado(cajaInicial);

        caja.setEfectivoContado(BigDecimal.ZERO);

        caja.setDiferencia(BigDecimal.ZERO);

        caja.setObservaciones("");

        return cajaRepository.save(caja);

    }

    /*
     * Cerrar caja.
     */
    @Transactional
    public Caja cerrarCaja(BigDecimal efectivoContado,
                           String observaciones) {

        Caja caja = obtenerCajaAbierta()
                .orElseThrow(() ->
                        new RuntimeException("No existe una caja abierta."));

        caja.setFechaCierre(
                LocalDateTime.now(
                        ZoneId.of("America/Argentina/Buenos_Aires"))
        );

        caja.setEstado("CERRADA");

        caja.setEfectivoContado(efectivoContado);

        BigDecimal diferencia =
                efectivoContado.subtract(
                        caja.getEfectivoEsperado());

        caja.setDiferencia(diferencia);

        caja.setObservaciones(observaciones);

        return cajaRepository.save(caja);

    }

}