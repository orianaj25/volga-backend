package com.pedidos.mayorista.service;

import com.pedidos.mayorista.model.Caja;
import com.pedidos.mayorista.model.MovimientoCaja;
import com.pedidos.mayorista.repository.CajaRepository;
import com.pedidos.mayorista.repository.MovimientoCajaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class MovimientoCajaService {

    private final MovimientoCajaRepository movimientoRepository;
    private final CajaRepository cajaRepository;

    public MovimientoCajaService(
            MovimientoCajaRepository movimientoRepository,
            CajaRepository cajaRepository) {

        this.movimientoRepository = movimientoRepository;
        this.cajaRepository = cajaRepository;
    }

    /*
     * ===============================
     * REGISTRAR INGRESO
     * ===============================
     */
    @Transactional
    public MovimientoCaja registrarIngreso(
            BigDecimal monto,
            String motivo,
            String usuario) {

        Caja caja = cajaRepository.findByEstado("ABIERTA")
                .orElseThrow(() ->
                        new RuntimeException("No existe una caja abierta"));

        MovimientoCaja movimiento = new MovimientoCaja();

        movimiento.setCaja(caja);
        movimiento.setFecha(
                LocalDateTime.now(
                        ZoneId.of("America/Argentina/Buenos_Aires"))
        );

        movimiento.setTipo("INGRESO");
        movimiento.setMonto(monto);
        movimiento.setMotivo(motivo);
        movimiento.setUsuario(usuario);

        caja.setIngresos(
                caja.getIngresos().add(monto)
        );

        recalcularCaja(caja);

        cajaRepository.save(caja);

        return movimientoRepository.save(movimiento);
    }

    /*
     * ===============================
     * REGISTRAR RETIRO
     * ===============================
     */
    @Transactional
    public MovimientoCaja registrarRetiro(
            BigDecimal monto,
            String motivo,
            String usuario) {

        Caja caja = cajaRepository.findByEstado("ABIERTA")
                .orElseThrow(() ->
                        new RuntimeException("No existe una caja abierta"));

        MovimientoCaja movimiento = new MovimientoCaja();

        movimiento.setCaja(caja);
        movimiento.setFecha(
                LocalDateTime.now(
                        ZoneId.of("America/Argentina/Buenos_Aires"))
        );

        movimiento.setTipo("RETIRO");
        movimiento.setMonto(monto);
        movimiento.setMotivo(motivo);
        movimiento.setUsuario(usuario);

        caja.setRetiros(
                caja.getRetiros().add(monto)
        );

        recalcularCaja(caja);

        cajaRepository.save(caja);

        return movimientoRepository.save(movimiento);
    }

    /*
     * ===============================
     * LISTAR MOVIMIENTOS
     * ===============================
     */
    public List<MovimientoCaja> listarMovimientos() {

        Caja caja = cajaRepository.findByEstado("ABIERTA")
                .orElseThrow(() ->
                        new RuntimeException("No existe una caja abierta"));

        return movimientoRepository.findByCajaOrderByFechaDesc(caja);
    }

    /*
     * ===============================
     * RECALCULAR EFECTIVO ESPERADO
     * ===============================
     */
    private void recalcularCaja(Caja caja) {

        BigDecimal esperado =
                caja.getCajaInicial()
                        .add(caja.getVentasEfectivo())
                        .add(caja.getIngresos())
                        .subtract(caja.getRetiros());

        caja.setEfectivoEsperado(esperado);

    }

    public List<MovimientoCaja> listarPorCaja(Long cajaId) {

        return movimientoRepository.findByCajaIdOrderByFechaAsc(cajaId);

    }

}